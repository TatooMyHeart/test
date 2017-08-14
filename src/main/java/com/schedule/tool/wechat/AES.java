package com.schedule.tool.wechat;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * Created by dell on 2017/7/30.
 */
public class AES {
    public static boolean initialized = false;

    public byte[] decrypt(byte[] content,byte[] keyByte,byte[] ivByte) throws InvalidAlgorithmParameterException {
        initialize();
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        Key sKeySpec= new SecretKeySpec(keyByte,"AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE,sKeySpec,generateIV(ivByte));
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] result = new byte[0];
        try {
            result = cipher.doFinal(content);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void initialize(){
        if(initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }

    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(iv));
        return parameters;
    }
}
