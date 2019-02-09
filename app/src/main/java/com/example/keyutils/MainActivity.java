package com.example.keyutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "XXYY";
    private EditText et;
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private AlignTextView tv1;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        et
                = (EditText) findViewById(R.id.et);
        bt1
                = (Button) findViewById(R.id.bt1);
        bt2
                = (Button) findViewById(R.id.bt2);
        bt3
                = (Button) findViewById(R.id.bt3);
        tv1
                = (AlignTextView) findViewById(R.id.tv1);
        tv2
                = (TextView) findViewById(R.id.tv2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                String md5Content = et.getText().toString();
                tv1.setText(MD5Utils.getMD5Code(md5Content));
                tv2.setText("");
                break;
            case R.id.bt2:
                String rsaContent = et.getText().toString();
                try {
                    KeyPair keyPair = RSAUtils.getKeyPair();
                    PublicKey publicKey = RSAUtils.getPublicKey(keyPair);
                    PrivateKey privateKey = RSAUtils.getPrivateKey(keyPair);
                    String enContent = RSAUtils.publicEncrypt(rsaContent, publicKey);
                    Log.e(TAG, "加密结果：" + enContent);
                    tv1.setText(enContent);
                    String deContent = RSAUtils.privateDecrypt(enContent, privateKey);
                    tv2.setText(deContent);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt3:
                String aesContent = et.getText().toString();
                String encryptResult = AESUtils.encrypt("12345678", aesContent);
                tv1.setText(encryptResult);
                String result = AESUtils.decrypt("12345678", encryptResult);
                tv2.setText(result);
                break;
        }
    }
}
