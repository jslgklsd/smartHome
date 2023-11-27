package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
    private Button btn_register;
    private View viewInflated;
    private AlertDialog.Builder builder;
    private SharedPreferences sp1;
    private SharedPreferences.Editor spEditor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();          // 控件初始化
        createSharedPreferences();  // 创建接口 sp、spEditor

        btn_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(btn_password, et_password);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();   // 显示注册对话框
            }
        });
    }

    // 控件初始化
    private void initializeViews() {
        et_account = findViewById(R.id.et_account);
        btn_login = findViewById(R.id.btn_login);
        et_password = findViewById(R.id.et_password);
        btn_password = findViewById(R.id.btn_password);
        btn_register = findViewById(R.id.btn_register);
    }

    // 把本地文件test.xml中键对应的值读出来
    public String getDataByKey(SharedPreferences sp, String key) {
        return sp.getString(key, "");
    }

    // 创建接口 sp、spEditor
    public void createSharedPreferences() {
        sp1 = this.getSharedPreferences("test", MODE_PRIVATE);
        spEditor1 = sp1.edit();
    }

    // 密码可见性切换
    public void togglePasswordVisibility(Button button, EditText editText) {
        Drawable currentIcon = button.getBackground();
        Drawable passwordHideIcon = getResources().getDrawable(R.drawable.password_hide);

        if (currentIcon.getConstantState().equals(passwordHideIcon.getConstantState())) {
            button.setBackgroundResource(R.drawable.password_visible);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            button.setBackgroundResource(R.drawable.password_hide);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }
    }

    private void login() {
        String account_et = et_account.getText().toString();
        String password_et = et_password.getText().toString();

        String sp_account = getDataByKey(sp1, "account");    // 以String Key 为索引来获取账号
        String sp_password = getDataByKey(sp1, "password");  // 以String Key 为索引来获取密码

        // 用户输入账号密码与本地文件存储的账号密码一致
        if ((account_et.equals(sp_account)) && (password_et.equals(sp_password))) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    public void showRegisterDialog() {
        builder = new AlertDialog.Builder(LoginActivity.this);  // 创建一个AlertDialog.Builder

        // 设置对话框布局
        viewInflated = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_register, null);
        builder.setView(viewInflated);

        // 添加OK按钮
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // 以键值 <String Key,String Value> 方式加入数据
                EditText et_wangguan = viewInflated.findViewById(R.id.et_wangguan);
                spEditor1.putString("account", et_wangguan.getText().toString());
                EditText dialog_et_password = viewInflated.findViewById(R.id.et_password);
                spEditor1.putString("password", dialog_et_password.getText().toString());
                spEditor1.commit();
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                dialog.dismiss();   // 关闭Dialog
            }
        });

        // 显示Dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}