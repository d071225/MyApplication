package com.dyy.newtest.ui.activity.EncodeActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.utils.AES;
import com.dyy.newtest.utils.AESUtils;
import com.dyy.newtest.utils.RSAUtils;

import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RSAActivity extends AppCompatActivity {

    @BindView(R.id.key)
    TextView key;
    @BindView(R.id.encrypt)
    Button encrypt;
    @BindView(R.id.encrypt_content)
    TextView encryptContent;
    @BindView(R.id.dencrypt)
    Button dencrypt;
    @BindView(R.id.dncrypt_content)
    TextView dncryptContent;
    //    private String str="第二步，我们用JDK的javac.exe文件编译我们的Java源程序，由于JDK是国际版的，在编译的时候，如果我们没有用-encoding参数指定我们的JAVA源程序的编码格式，则javac.exe首先获得我们操作系统默认采用的编码格式，也即在编译java程序时，若我们不指定源程序文件的编码格式，JDK首先获得操作系统的file.encoding参数(它保存的就是操作系统默认的编码格式，如WIN2k，它的值为GBK)，然后JDK就把我们的java源程序从file.encoding编码格式转化为JAVA内部默认的UNICODE格式放入内存中。然后，javac把转换后的unicode格式的文件进行编译成.class类文件，此时.class文件是UNICODE编码的，它暂放在内存中，紧接着，JDK将此以UNICODE编码的编译后的class文件保存到我们的操作系统中形成我们见到的.class文件。";
    private String str = "你好";
    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0N3Y/uud4CdfDPZ7JBf8HDdQ5GuetZ6GQ6T4iw/oNumlgYkdcWk+4UXNSnpG0jSur61Z3kx70SqbqyX1+JhJ7nf7HqpoxrKmctEeQhqs9trg4ZguPjAHMbNCNcEK8NkTMA2PhAebzTAjYgLjMtqWE9Ak4I1p+flj79jDA0o1Fp6S74xASUqYFoFbBGS7A0ClfyLCi4sNogCjTkhhl5q5gEtT7rn678lJs3OQaxnChqDty50Y6duFi6/zpJskStKZd8Xa95BpbRXxiDUbEvvLok99LpOUm4vtz5VpcMCTBmlH3B2S0pC//o4pcEoy1KziNkC8Gn+/sWHdUFIBdH5iwwIDAQAB";
    private String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDQ3dj+653gJ18M9nskF/wcN1Dka561noZDpPiLD+g26aWBiR1xaT7hRc1KekbSNK6vrVneTHvRKpurJfX4mEnud/seqmjGsqZy0R5CGqz22uDhmC4+MAcxs0I1wQrw2RMwDY+EB5vNMCNiAuMy2pYT0CTgjWn5+WPv2MMDSjUWnpLvjEBJSpgWgVsEZLsDQKV/IsKLiw2iAKNOSGGXmrmAS1PuufrvyUmzc5BrGcKGoO3LnRjp24WLr/OkmyRK0pl3xdr3kGltFfGINRsS+8uiT30uk5Sbi+3PlWlwwJMGaUfcHZLSkL/+jilwSjLUrOI2QLwaf7+xYd1QUgF0fmLDAgMBAAECggEAArFpk9fb4EEBs7wV2GBKyt5Xt6o/kJ3ro0GkRr68wsB+ds4ayBSQT/1DaVtsQFeXjQJSL1CTJB5FhY5mvn7QLTf0z+9ojG9PQBm8JyQK6N/nmGEK75hU72odR24mwvY5jhwOpqgmdR3Sxve8kDJLTxQW9aI9JJD3necQlKFHT/RTfimtVhQyuB0UB6Cv8WYKIJvf6r/2RC0KWpM3nuMDKDVBxkJ8A2pMfjF7VZd/peo0hjCgwWAs/D21lXaS5sX3//WStoJGS+G/98XxsZMc0VUpNJZn7gOQKFmtisfd7m/Thxs0DJQWFspr1iCwVpzyhXp22rj42hl23ztDWQtgMQKBgQDzX7APasb8IZ6NtX8k0zyP7l0kLsi/Zj7t1Y8NJMNl/L+nbjaDTcTl+dVl2ibRj357twr1VqcgyYxmFW/tmlRWxPTCA3RMnJEgEmNECQnCRPhTBIwxuEKPW5JKPoS0GpD0FPst/i0TqdovRr4nWmGPlkizYTxr9hPw+E/5VrD72QKBgQDbs9xXuMkMQoE0tMbQoe8FT/AtK2nWOFTJR8ZMPLkEX/lZo0Z9a2PZbUepyVmSUShfLV4L8XIWeAGstjPjktezuU7ssl8gGYSPj0t85yn3DV2qsd89MZqCiA6quJ+hobGPLZ2mo0TfQ+K+60gKejUqgV/ZGkMnLNFqd3mGW2L9+wKBgQCoBrVoP4E4TAHKtrccdpbGjHxT4dUJiA8EZ0BHg81AGZs8fnQ2fTfmy3FQy6enYVmjEoIHVpc2pNBBHqXTNozqBzsww+/EgQgVS3yrWU4aiUGlF2uvXBbgIQnEJpu6OOlBC0dLvsPiYo9XupDIXLckyk5RQl3T7+ZepSGZ9QdmKQKBgAYeNxeWRLr7qx9tnU+4ArDyLe0EFXlqrinAvuvmkPxnM8lkO5VIhUpLucAnpD/n1Kj52XGZsqSfiORnWoCO2UTe9yyuNuns6/0Xb3G1+jwv+hjDxUSt+fCCm23zoDZfC1PCYG6Ag214NXs9kA8CojJh0ep4yb/whpBhbVfpeZM7AoGAJxptBDn23BYqkrWbR2wNeFcIUWVUChobbosm5WLID9ucrFDHRvsFkLaWrrk61CZStpjT4txWD9qBb2/wnX11Rm8j4BI9yYmLWCSZqFZADZMxcvIqtHBNLCXfMyMcIenSFAwlfhGmX2KFgrKnUgP+vSXVigNB4hTZkEtOl/9AN3o=";
    private String aesKey;
    private String encrypt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);
        ButterKnife.bind(this);
//        KeyPair keyPair=RSAUtils.generateRSAKeyPair(RSAUtils.DEFAULT_KEY_SIZE);
//        PrivateKey aPrivate = keyPair.getPrivate();
//        PublicKey aPublic = keyPair.getPublic();
//        LogUtils.e(new String(aPublic.getEncoded()));
//        LogUtils.e(Base64.encodeToString(aPublic.getEncoded(),Base64.DEFAULT));
        byte[] pub = Base64.decode(publicKey, Base64.DEFAULT);
        byte[] pri = Base64.decode(privateKey, Base64.DEFAULT);
        try {
            byte[] bytes = RSAUtils.encryptByPublicKeyForSpilt(str.getBytes(), pub);
            String encode = Base64.encodeToString(bytes, Base64.DEFAULT);
            LogUtils.e(encode);
            byte[] bytes1 = RSAUtils.decryptByPrivateKeyForSpilt(bytes, pri);
            String dncode = new String(bytes1);
            LogUtils.e(dncode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * AES加解密
         */
        aesKey = AESUtils.generateKey();
        LogUtils.e(aesKey);
        key.setText(aesKey);


        SecretKeySpec secretKeySpec = AES.generateKey();
        LogUtils.e(AES.generateKeyString());
        String encrypt = AES.encrypt(str, secretKeySpec.getEncoded());
        LogUtils.e(encrypt);
        String decrypt = AES.decrypt(encrypt, secretKeySpec.getEncoded());
        LogUtils.e(decrypt);
    }

    @OnClick({R.id.encrypt, R.id.dencrypt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.encrypt:
                encrypt1 = AESUtils.encrypt(aesKey, str);
                LogUtils.e(encrypt1);
                encryptContent.setText(encrypt1);
                break;
            case R.id.dencrypt:
                String decrypt = AESUtils.decrypt(aesKey, encrypt1);
                LogUtils.e(decrypt);
                dncryptContent.setText(decrypt);
                break;
        }
    }
}
