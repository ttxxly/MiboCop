package top.ttxxly.blog.mibocop.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ttxxly on 2017/4/7.
 */

public class MD5Utils {

    /**
     * 返回解密后的String
     * @param passwd：要加密的密码
     * @return 加密后的 String
     */
    public static String encode(String passwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(passwd.getBytes());//进行加密，返回加密后的字节数组

            StringBuffer sb = new StringBuffer();
            for (byte b: bytes
                 ) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if(hexString.length() == 1) {
                    hexString = "0" + hexString;
                }

                sb.append(hexString);
            }

            String md5 = sb.toString();

            return md5;
        } catch (NoSuchAlgorithmException e) {
            //没有此算法
            e.printStackTrace();
        }

        return null;
    }
}
