package club.hue.controller;

import club.hue.pojo.PostInfomation;
import club.hue.service.BbsService;
import club.hue.service.ZoneService;
import club.hue.utils.COSUploadUtil;
import club.hue.utils.CheckFolder;
import club.hue.utils.DateTransition;
import club.hue.utils.ResultVOUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class BbsController {

    @Autowired
    private BbsService bbsService;

    @Autowired
    private ZoneService zoneService;

//    private String basePath = "/www/apps/lightme/material/";
    private String basePath = "G:\\Projects\\Materials\\lightme\\";

    @RequestMapping("/getPosts")
    public ResponseEntity getPosts(String type, String lastdate) {
        try {
            switch (type) {
                //0-all, 1-hot, 2-memory, 3-share, 4-opinion
                case "0":
                    return ResponseEntity.ok().body(ResultVOUtil.success(bbsService.getHotPosts(lastdate)));
                default:
                    return ResponseEntity.ok().body(ResultVOUtil.success(bbsService.getTypePosts((Integer.parseInt(type)-1) + "", lastdate)));
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/getSearchPosts")
    public ResponseEntity getSearchPosts(String keyword, String lastdate) {
        try {
            return ResponseEntity.ok().body(ResultVOUtil.success(bbsService.getSearchPosts(keyword, lastdate)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/addPicturePost")
    public ResponseEntity getPostPicture(MultipartFile postpicture, String type, String title, String content, String anonymous, HttpSession session) {
        // 只有上传了自定义的图片的时候才会进入此方法
        if (postpicture == null) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
        String userid = (String) session.getAttribute("userid");
        Long dateMills = System.currentTimeMillis();
        String postid = userid + (dateMills + "");
        // 拼接文件名，用当前时间戳加上文件后缀
        String[] fileNames = postpicture.getOriginalFilename().split("\\.");
        String fileName = postid + "." + fileNames[fileNames.length - 1];
        // 上传图片
        File tempFile = new File(basePath + "/users/" + userid + "/", fileName);
//        File tempFile = new File("G:\\Projects\\Materials\\lightme" + "\\users\\" + userid + "\\", fileName);
        try {
           postpicture.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(2, "保存文件出错"));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("postid", postid);
        map.put("authorid", userid);
        map.put("type", Integer.parseInt(type));
        map.put("anonymous", Integer.parseInt(anonymous));
        map.put("title", title);
        map.put("imgtype", 0);
        map.put("imgurl", "/users/" + userid + "/" + fileName);
        map.put("date", new DateTransition().DateMills2String(dateMills));
        try {
            bbsService.addPost(map);
            bbsService.addPostContent(postid, content);
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
        return ResponseEntity.ok().body(ResultVOUtil.successSimple());
    }

    @RequestMapping("/addSimplePost")
    public ResponseEntity addPost(String type, String title, String content, String anonymous, String imgurl, HttpSession session) {
        // 预设图片和纯色背景都会使用此方法
        // 区别预设图片和纯色背景的方法为判断传入的imgurl是否包含wxbg字符串
        String userid = (String) session.getAttribute("userid");
        Long dateMills = System.currentTimeMillis();
        String postid = userid + (dateMills + "");
        Boolean testImgUrl = false;
        if (imgurl.indexOf("/wxbg/") != -1) {
            // 如果是预设图片
            testImgUrl = true;
            imgurl = imgurl.substring(32);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("postid", postid);
        map.put("authorid", userid);
        map.put("type", Integer.parseInt(type));
        map.put("anonymous", Integer.parseInt(anonymous));
        map.put("title", title);
        // 如果是预设图片，type设置为1
        // 如果是纯色背景，type设置为2
        map.put("imgtype", testImgUrl ? 1 : 2);
        map.put("imgurl", imgurl);
        map.put("date", new DateTransition().DateMills2String(dateMills));
        try {
            bbsService.addPost(map);
            bbsService.addPostContent(postid, content);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
        return ResponseEntity.ok().body(ResultVOUtil.successSimple());
    }

    @RequestMapping("/getCertainPost")
    public ResponseEntity getCertainPost(String postid, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            PostInfomation postInfomation = bbsService.getPostInfomation(postid);
            if (postInfomation == null) {
                bbsService.addInfomation(new PostInfomation(postid, clientid, 1, 0, 0, new DateTransition().TimeStamp2StringSimple()));
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("postMain", bbsService.getCertainPost(postid));
            map.put("postInfo", bbsService.getPostInfomation(postid));
            map.put("postComment", bbsService.getPostComment(postid));
            return ResponseEntity.ok().body(ResultVOUtil.success(map));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    // 点赞和取消点赞
    @RequestMapping("/changeAgreeStatus")
    public ResponseEntity changeAgreeStatus(String postid, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            PostInfomation postInfomation = bbsService.getPostInfomation(postid);
            if (postInfomation.getAgree() == 0) {
                bbsService.changeAgreeStatus(1, postid, clientid);
                return ResponseEntity.ok().body(ResultVOUtil.success(1));
            } else {
                bbsService.changeAgreeStatus(0, postid, clientid);
                return ResponseEntity.ok().body(ResultVOUtil.success(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/changeCollectStatus")
    public ResponseEntity changeCollectStatus(String postid, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        try {
            PostInfomation postInfomation = bbsService.getPostInfomation(postid);
            if (postInfomation.getCollect() == 0) {
                bbsService.changeCollectStatus(1, new DateTransition().TimeStamp2StringSimple(), postid, clientid);
                return ResponseEntity.ok().body(ResultVOUtil.success(1));
            } else {
                bbsService.changeCollectStatus(0, new DateTransition().TimeStamp2StringSimple(), postid, clientid);
                return ResponseEntity.ok().body(ResultVOUtil.success(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/addComment")
    public ResponseEntity addComment(String type, String postid, String comment, String quote, String roleb, String authorid, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        Long dateMills = System.currentTimeMillis();
        HashMap<String, Object> map = new HashMap<>();
        map.put("postid", postid);
        map.put("type", type);
        map.put("clientid", clientid);
        map.put("detail", comment);
        map.put("quote", quote);
        map.put("date", new DateTransition().DateMills2String(dateMills));
        try {
            bbsService.addComment(map);
            if (type.equals("0") && !authorid.equals(clientid)) {
                // a message to the author of the post
                HashMap<String, Object> mapp = new HashMap<>();
                String messageid = clientid + (dateMills + "");
                mapp.put("postid", postid);
                mapp.put("messageid", messageid);
                mapp.put("rolea", clientid);
                mapp.put("roleb", authorid);
                mapp.put("type", 0);
                mapp.put("date", new DateTransition().DateMills2String(dateMills));
                zoneService.addMessage(mapp);
            }
            if (type.equals("1")) {
                // a message to the one who are being quote
                HashMap<String, Object> mappp = new HashMap<>();
                String messageid = clientid + (dateMills + "");
                mappp.put("postid", postid);
                mappp.put("messageid", messageid);
                mappp.put("rolea", clientid);
                mappp.put("roleb", roleb);
                mappp.put("type", 1);
                mappp.put("date", new DateTransition().DateMills2String(dateMills));
                zoneService.addMessage(mappp);
            }
            return ResponseEntity.ok().body(ResultVOUtil.success(bbsService.getPostComment(postid)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    @RequestMapping("/toReport")
    public ResponseEntity addReport(String postid, String detail, HttpSession session) {
        String clientid = (String) session.getAttribute("userid");
        String date = new DateTransition().TimeStamp2StringSimple();
        try {
            bbsService.addReport(postid, clientid, detail, date);
            return ResponseEntity.ok().body(ResultVOUtil.successSimple());
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResultVOUtil.error(1, "服务器开小差了，请稍后再试"));
        }
    }

    // 这个接口需要额外发送网络请求，获取阴阳页面的内容
    @RequestMapping("/getDailySentence")
    public ResponseEntity getDailySentence(String type, String titlea, String titleb, String titlec, String titled) throws MalformedURLException {
        Integer typeInt = Integer.parseInt(type);
        String[] dates = new String[4];
        dates[0] = titlea;
        dates[1] = titleb;
        dates[2] = titlec;
        dates[3] = titled;
        String urla = "https://www.dutangapp.cn/u/toxic?date=";
        String urlb = "https://sentence.iciba.com/index.php?c=dailysentence&m=getdetail&title=";
        HttpURLConnection con = null;
        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;
        int times = 4;
        if (typeInt == 1) {
            List<List<String>> resArr = new ArrayList<>();
            try {
                for (int i=0;i<times;i++) {
                    URL url = new URL(urla + dates[i]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json;charset=GBK");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    int resCode = con.getResponseCode();
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        //将响应流转换成字符串
                        resultBuffer = new StringBuffer();
                        String line;
                        buffer = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        while ((line = buffer.readLine()) != null) {
                            resultBuffer.append(line);
                        }
                        JSONObject res = JSONObject.parseObject(resultBuffer.toString());
                        JSONArray arr = JSONObject.parseArray(res.getString("data"));
                        List<String> list = new ArrayList<>();
                        for (int j=0;j<arr.size();j++) {
                            list.add(arr.getJSONObject(j).getString("data"));
                        }
                        resArr.add(list);
                    }
                }
                return ResponseEntity.ok().body(ResultVOUtil.success(resArr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (typeInt == 0) {
            List<HashMap<String, String>> resArr = new ArrayList<>();
            try {
                for (int i=0;i<times;i++) {
                    URL url = new URL(urlb + dates[i]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "application/json;charset=GBK");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setUseCaches(false);
                    int resCode = con.getResponseCode();
                    if (resCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        //将响应流转换成字符串
                        resultBuffer = new StringBuffer();
                        String line;
                        buffer = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                        while ((line = buffer.readLine()) != null) {
                            resultBuffer.append(line);
                        }
                        JSONObject res = JSONObject.parseObject(resultBuffer.toString());
                        HashMap<String, String> map = new HashMap<>();
                        map.put("content", res.getString("content"));
                        map.put("note", res.getString("note"));
                        map.put("picture2", res.getString("picture2"));
                        resArr.add(map);
                    }
                }
                return ResponseEntity.ok().body(ResultVOUtil.success(resArr));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return ResponseEntity.ok().body(ResultVOUtil.error(2, "传入参数有误"));
        }
        return ResponseEntity.ok().body(ResultVOUtil.error(1, "请求失败"));
    }
}
