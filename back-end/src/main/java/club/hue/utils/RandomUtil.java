package club.hue.utils;

public class RandomUtil {

    /**
     * 生成6位验证码
     *
     * @return
     */
    public synchronized static String getCode() {
        StringBuffer code = new StringBuffer();
        int num;
        for (int i = 0; i < 6; i++) {
            num = (int) (Math.random() * 10);
            code.append(String.valueOf(num));
        }
        return code.toString();
    }
}
