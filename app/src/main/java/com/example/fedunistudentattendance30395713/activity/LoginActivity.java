package com.example.fedunistudentattendance30395713.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.MainData;
import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.bean.User;
import com.example.fedunistudentattendance30395713.sqlite.UserDao;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private UserDao userDao;
    private EditText zhaohao,mima;
    private TextView tv_reg,tv_forget;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        userDao = new UserDao(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Transparent status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //Transparent navigation bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        
        initFindID();

        initClick();

    }

    private void initClick() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // login
                String z = zhaohao.getText().toString().trim();
                String m = mima.getText().toString().trim();
                if (z.isEmpty() || m.isEmpty()){
                    Toast.makeText(LoginActivity.this, "please input account or password", Toast.LENGTH_SHORT).show();
                }else {
                    List<User> l = userDao.query();
                    if (l==null){
                        Toast.makeText(LoginActivity.this, "user does not exist", Toast.LENGTH_SHORT).show();
                    }else {
                        User userbean=null;
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i).getUsername().equals(z)){
                                userbean = l.get(i);
                                break;
                            }
                        }
                        if (userbean==null){
                            Toast.makeText(LoginActivity.this, "user does not exist", Toast.LENGTH_SHORT).show();
                        }else {
                            if (userbean.getPassword().equals(m)){
                                Toast.makeText(LoginActivity.this, "welcome～～", Toast.LENGTH_SHORT).show();
                                MainData.teacher = userbean;
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this, "password is error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
        tv_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Stay tuned", Toast.LENGTH_SHORT).show();
            }
        });
        tv_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The registration page is displayed
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });
    }

    private void initFindID() {
        zhaohao = findViewById(R.id.zhanghao);
        mima = findViewById(R.id.mima);
        tv_reg = findViewById(R.id.tv_reg);
        tv_forget = findViewById(R.id.tv_forget);
        btn_login = findViewById(R.id.btn_login);
    }
}