package com.minigod.zero.bpmn.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @ClassName: IOCloseUtil
 * @Description:
 * @Author chenyu
 * @Date 2022/8/25
 * @Version 1.0
 */
public class IOCloseUtil {
    /**
     *   IO流关闭工具类
     */
    public static void close(Closeable... io) {
        for (Closeable temp : io) {
            try {
                if (null != temp)
                    temp.close();
            } catch (IOException e) {
                System.out.println("" + e.getMessage());
            }
        }
    }

    public static <T extends Closeable> void closeAll(T... io) {
        for (Closeable temp : io) {
            try {
                if (null != temp)
                    temp.close();
            } catch (IOException e) {
                System.out.println("" + e.getMessage());
            }
        }

    }

}
