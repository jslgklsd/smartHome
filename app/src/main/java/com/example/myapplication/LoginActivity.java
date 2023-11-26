package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    private EditText et_password;
    private Button btn_password;
    private EditText et_account;
    private Button btn_login;
    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_password = findViewById(R.id.et_password);
        btn_password = findViewById(R.id.btn_password);
        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentIcon = btn_password.getBackground();
                Drawable passwordHideIcon = getResources().getDrawable(R.drawable.password_hide);

                if(currentIcon.getConstantState().equals(passwordHideIcon.getConstantState())) {
                    btn_password.setBackgroundResource(R.drawable.password_visible);
                    et_password.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    btn_password.setBackgroundResource(R.drawable.password_hide);
                    et_password.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                }
            }
        });

        et_account = findViewById(R.id.et_account);
        btn_login = findViewById(R.id.btn_login);
        cb = findViewById(R.id.cb);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if ((account.equals("Admin")) && (password.equals("12345678"))){    // 账号密码正确
                    // 用户勾选了记住密码
                    if (cb.isChecked())
                        saveCredentials(account, password);

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 从本地文件中读取已保存的账号密码
        String savedAccount = getSavedAccount();
        String savedPassword = getSavedPassword();
        if (!TextUtils.isEmpty(savedAccount) && !TextUtils.isEmpty(savedPassword)) {
            et_account.setText(savedAccount);
            et_password.setText(savedPassword);
            cb.setChecked(true);
        }
    }

    // 保存账号密码到本地文件
    private void saveCredentials(String account, String password) {
        try {
            FileOutputStream outputStream = openFileOutput("credentials.txt", Context.MODE_PRIVATE);
            outputStream.write((account + "\n" + password).getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从本地文件中读取已保存的账号
    private String getSavedAccount() {
        try {
            FileInputStream inputStream = openFileInput("credentials.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String account = reader.readLine();
            reader.close();
            return account;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 从本地文件中读取已保存的密码
    private String getSavedPassword() {
        try {
            FileInputStream inputStream = openFileInput("credentials.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 跳过第一行，读取第二行作为密码
            reader.readLine();
            String password = reader.readLine();
            reader.close();
            return password;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}