package club.hue.utils;

import java.io.File;

public class CheckFolder {

    // 检查当前路径下是否存在文件夹，如果不存在，则创建
    public static void checkFolderExistence(File file) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
    }
}
