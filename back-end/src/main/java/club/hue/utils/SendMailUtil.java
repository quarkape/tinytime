package club.hue.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailUtil {
    //邮件服务器主机名
    // QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
    private static String myEmailSMTPHost = "smtp.qq.com";

    //发件人邮箱，这里替换成你自己的邮箱就可以了，不过注意qq邮箱每天发邮件的数量是有限制的
    private static String myEmailAccount = "quarkape@qq.com";

    //发件人邮箱密码（授权码）
    //在开启SMTP服务时会获取到一个授权码，把授权码填在这里
    private static String myEmailPassword = "xxx";

    /**
     * 邮件单发（自由编辑短信，并发送，适用于私信）
     *
     * @param toEmailAddress 收件箱地址
     * @throws Exception
     */
    public static void sendEmail(String toEmailAddress, String authcode) throws Exception {

        Properties props = new Properties();

        // 开启debug调试
        props.setProperty("mail.debug", "false");

        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");

        // 端口号
        props.put("mail.smtp.port", 465);

        // 设置邮件服务器主机名
        props.setProperty("mail.smtp.host", myEmailSMTPHost);

        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        /**SSL认证，注意腾讯邮箱是基于SSL加密的，所以需要开启才可以使用**/
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        //设置是否使用ssl安全连接（一般都使用）
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        //创建会话
        Session session = Session.getInstance(props);

        //获取邮件对象
        //发送的消息，基于观察者模式进行设计的
        Message msg = new MimeMessage(session);

        //设置邮件标题
        msg.setSubject("有温度、有态度、有深度的话题");

        //设置邮件内容
        //使用StringBuilder，因为StringBuilder加载速度会比String快，而且线程安全性也不错
        StringBuilder builder = new StringBuilder();

        //写入内容
        builder.append(
                "<table width=\"100%\">" +
                "<tbody><tr><td style=\"width:100%\"><center><table style=\"width:600px;margin:0 auto;font-size:18px\"><tbody><tr><td style=\"margin:0 auto;overflow:hidden\">" +
                "<div style=\"margin:0 auto;max-width:600px;text-align:center\"><img height=\"auto\" style=\"width:auto;max-width:100%;height:auto\" src=\"https://b.meta-analyse.club/imgs/wxbg/maillogo.jpg\" alt=\"\"></div>" +
                "<div style=\"margin:0 auto;max-width:600px\"><p><center>欢迎使用话题起源说，您本次的随机验证码为:</center></p></div>" +
                "<div style=\"margin:0 auto;max-width:600px\"><p style=\"font-weight:bolder;font-size:24px;text-decoration:underline;text-align:center\"><center><strong>" + authcode + "</strong></center></p></div>" +
                "<div style=\"margin:0 auto;max-width:600px\"><p><center>验证码有效期30分钟，请尽快使用。</center></p></div>" +
                "<div style=\"margin:0 auto;max-width:600px\"><p><center>如有疑问请联系:<span style=\"text-decoration:underline\">quarkape@qq.com</span></center></p></div>" +
                "<div style=\"margin:0 auto;max-width:600px\"><p><center>祝您生活愉快!</center></p></div>\n" +
                "</td></tr></tbody></table></center></td></tr></tbody>" +
                "</table>"
                );

        //设置显示的发件时间
        msg.setSentDate(new Date());

        //设置邮件内容
//        msg.setText(builder.toString());
        msg.setContent(builder.toString(), "text/html;charset=UTF-8");
        //设置发件人邮箱
        // InternetAddress 的三个参数分别为: 发件人邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
        msg.setFrom(new InternetAddress(myEmailAccount, "话题起源说", "UTF-8"));

        //得到邮差对象
        Transport transport = session.getTransport();

        //连接自己的邮箱账户
        //密码不是自己QQ邮箱的密码，而是在开启SMTP服务时所获取到的授权码
        //connect(host, user, password)
        transport.connect(myEmailSMTPHost, myEmailAccount, myEmailPassword);

        //发送邮件
        transport.sendMessage(msg, new Address[]{new InternetAddress(toEmailAddress)});

        //将该邮件保存到本地
//        OutputStream out = new FileOutputStream("MyEmail.eml");
//        msg.writeTo(out);
//        out.flush();
//        out.close();

        transport.close();
    }
}
