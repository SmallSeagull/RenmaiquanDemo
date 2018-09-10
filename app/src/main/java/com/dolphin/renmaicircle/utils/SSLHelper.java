package com.dolphin.renmaicircle.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Dolphin on 2018/8/7.
 */

public class SSLHelper {
    private static final String KEY_STORE_TYPE_BKS = "bks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
//    public static final String KEY_STORE_CLIENT_PATH = "server.pfx";//P12文件
    public static final String KEY_STORE_CLIENT_PATH = "custom002.p12";//P12文件
    private static final String KEY_STORE_TRUST_PATH = "client.truststore";//truststore文件
    public static final String KEY_STORE_PASSWORD = "password";//P12文件密码
    private static final String KEY_STORE_TRUST_PASSWORD = "password";//truststore文件密码

    public static SSLSocketFactory getSSLSocketFactory(Context context) {
        SSLSocketFactory factory = null;

        try {
            // 服务器端需要验证的客户端证书
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE_BKS);

            InputStream ksIn = context.getResources().getAssets()
                    .open(KEY_STORE_CLIENT_PATH);
            InputStream tsIn = context.getResources().getAssets()
                    .open(KEY_STORE_TRUST_PATH);
            try {
                keyStore.load(ksIn, KEY_STORE_PASSWORD.toCharArray());
                trustStore.load(tsIn, KEY_STORE_TRUST_PASSWORD.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    ksIn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    tsIn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //信任管理器
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            //密钥管理器
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
            //初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(), null);
            factory =  sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        return factory;
    }

}

