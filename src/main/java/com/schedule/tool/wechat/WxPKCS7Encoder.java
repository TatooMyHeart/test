package com.schedule.tool.wechat;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by dell on 2017/7/30.
 */
public class WxPKCS7Encoder {
    private static final Charset CHARSET= Charset.forName("utf-8");
    private static final int BLOCK_SIZE=32;

    public static byte[] encode(int count)
    {
        int amountToPad = BLOCK_SIZE - (count%BLOCK_SIZE);
        if(amountToPad==0)
        {
            amountToPad=BLOCK_SIZE;
        }

        char padChr = chr(amountToPad);
        String tmp = new String();
        for(int index=0;index<amountToPad;index++)
        {
            tmp+=padChr;
        }
        return tmp.getBytes(CHARSET);
    }

    public static byte[] decode(byte[] decrypted)
    {
        int pad=decrypted[decrypted.length-1];
        if(pad<1||pad>32)
        {
            pad=0;
        }
        return Arrays.copyOfRange(decrypted,0,decrypted.length-pad);
    }

    public static char chr(int a)
    {
        byte target = (byte)(a & 0xFF);
        return (char)target;
    }
}
