package com.shiro;

import com.common.Global;
import com.main.pojo.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
    //private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static final int hashIterations = Global.HASHITERATIONS;

    public static void encryptPassword(User user) {
        //String salt=randomNumberGenerator.nextBytes().toHex();
        //String newPassword = new SimpleHash(algorithmName, user.getPassword()).toHex();
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getUserid()), hashIterations).toHex();
        user.setPassword(newPassword);

    }
}
