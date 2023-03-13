package club.hue;

import club.hue.service.InfoManagerService;
import club.hue.utils.Md5Util;
import club.hue.utils.SendMailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private InfoManagerService infoManagerService;

    @Test
    void contextLoads() throws Exception {
//        SendMailUtil.sendEmail("quarkape@qq.com", "123456");
//        System.out.println("/users/1659516370623/16595163706231659519869193.jpg".substring(20));
    }

}