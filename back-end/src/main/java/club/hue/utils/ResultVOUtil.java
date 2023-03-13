package club.hue.utils;

import club.hue.vo.ResultVO;

public class ResultVOUtil {

    //return when process succeed and don't need to return any data
    public static ResultVO successSimple() {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setCode(0);
        resultVO.setMessage("success");
        return resultVO;
    }

    //return when process succeed, including data, status code(0 default success)
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMessage("success");
        return resultVO;
    }

    //return when error occurred, including status code and certain message
    public static ResultVO error(Integer code, String message) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(null);
        return resultVO;
    }
}