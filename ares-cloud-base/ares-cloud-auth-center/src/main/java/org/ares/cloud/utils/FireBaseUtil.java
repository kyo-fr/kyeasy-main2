package org.ares.cloud.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import org.ares.cloud.common.exception.BusinessException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @Author hugo  tangxkwork@163.com
 * @Description
 * @Date 2024/08/06/01:40
 **/
@Component
public class FireBaseUtil {
    //获取AndroidConfig.Builder对象
    private static AndroidConfig.Builder androidConfigBuilder = AndroidConfig.builder();
    //获取AndroidNotification.Builder对象
    private static AndroidNotification.Builder androidNotifiBuilder = AndroidNotification.builder();
    public static FirebaseApp firebaseApp;
    static {
        init();
    }
    public static void main(String[] args) {
        try {
            UserRecord user = getUserById("jeRGWA0iyUVvedcfpxYGXFTuMxv2");
            System.out.println(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化app
     */
    public static void init() {
        try {
            //设置环境
            InputStream serviceAccount = new ClassPathResource("firebase-adminsdk.json").getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    //.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                    .build();

            firebaseApp =  FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * token验证
     * @param idToken 通亨
     * @throws FirebaseAuthException 验证多雾
     */
    public static String verifyIdToken(String idToken) throws FirebaseAuthException{
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }

    /**
     * 获取用户信息
     * @param uid 用户id
     * @return  用户信息
     */
    public static UserRecord getUserById(String uid){
        FirebaseAuth instance = FirebaseAuth.getInstance(firebaseApp);
        UserRecord user = null;
        try {
            user = instance.getUserAsync(uid).get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("get user from firebase by uid error");
        }
        return user;
    }
}
