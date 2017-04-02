package top.ttxxly.blog.mibocop.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ttxxly on 2017/3/31.
 * 这个是我的工具类，作用是将从服务器请求的输入流转化成字符串，或者是字节数组
 */

public class StreamUtils {
    public static String Stream2String(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int len ;
        byte[] buffer = new byte[1024];

        while ( (len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        in.close();
        out.close();

        return out.toString();
    }

    public static byte[] readInputStream(InputStream is) throws IOException{
        byte[] b = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        while ((len = is.read(b)) != -1) {
            bos.write(b, 0, len);
        }

        is.close();
        bos.close();

        return bos.toByteArray();
    }
}




