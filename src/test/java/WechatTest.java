
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schedule.paramterBody.EncryptedDataBean;
import com.schedule.paramterBody.WechatBean;

import com.schedule.tool.wechat.AES;

import com.schedule.tool.wechat.WxPKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.web.socket.WebSocketSession;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidAlgorithmParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2017/7/28.
 */

public class WechatTest {


    @Test
    public void test() {
//
//       // String sessionkey="tiihtNczf5v6AKRyjwEUhQ==";
//        String sessionkey="Ek5BpNav2yAAJLguf3Bb/w==";
//        WechatBean wechatBean = new WechatBean();
////        wechatBean.setIv("r7BXXKkLb8qrSNn05n0qiA==");
////        wechatBean.setEncryptedData("CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
////                "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
////                "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
////                "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
////                "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
////                "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
////                "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
////                "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
////                "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
////                "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
////                "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
////                "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
////                "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
////                "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
////                "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
////                "20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
////                "Db/XcxxmK01EpqOyuxINew==");
//        wechatBean.setIv("R8xhxMoXnE9VwWeqhDslYw==");
//        wechatBean.setEncryptedData("IGG7inIm97lrD/3qA1CsPFG72EBjVSAyMSL" +
//                "xtgvmIvttIJ3kB0I2dqU9yyiUeWjTSu58oSeEP0ZNvBHK2gsuUuD" +
//                "pJZCSpFnLITf1BPTq5GAOVfbzI9XR1vTsnzpZlsqUk43qPI4IBbr" +
//                "yItSzs8hCflhQxRQnHHdEYcHaJOyAylPwEWKMX9DlSIwV5NbT3d9" +
//                "nbJPn7NlctY9Q0pE5sRM00utbUEwCVBuxRo/7kAvC7HBlEj/E1jfHD" +
//                "DXRrab9xroZoeoABdKA+oW8xGl7pdd7bZ4q/r9Z01sZA4khQngUQE38" +
//                "5AUNZRhYWUTWkOtz3KYeEx926Rq46mvY6edmuaN/c9UzGpqTXGr" +
//                "UQ56vgxPLDv/QXbKCmz/fWbwzODV70PKaazpyzQB7Et1cTHgfLrJvOmw" +
//                "g6hhn6bXG28JeB3Y0eTlrciPtWxWd51aab0613SXR5Xl+CNPi" +
//                "pzf5PGRxNC7w0qIK1HelY3jr8ESDxcctKs0=");
//
//        AES aes = new AES();
//        try {
//            byte[] resultByte=aes.decrypt(Base64.decodeBase64(wechatBean.getEncryptedData()),
//                    Base64.decodeBase64(sessionkey),Base64.decodeBase64(wechatBean.getIv()));
//            if(null!=resultByte&&resultByte.length>0)
//            {
//                String userInfo=new String(WxPKCS7Encoder.decode(resultByte));
//                System.out.println(userInfo);
//                EncryptedDataBean encryptedDataBean = new EncryptedDataBean();
//                Gson gson= new GsonBuilder().disableInnerClassSerialization().create();
//                encryptedDataBean=gson.fromJson(userInfo,EncryptedDataBean.class);
//                System.out.println(encryptedDataBean.getUnionId());
//                System.out.println(encryptedDataBean.getNickName());
//            }
//        } catch (InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        }
//

        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);
        String s=time+"";
        System.out.println(s);
        long l=Long.valueOf(s);

        System.out.println(l-date.getTime());
    }

}
