package com.example.fedunistudentattendance30395713.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fedunistudentattendance30395713.R;
import com.example.fedunistudentattendance30395713.bean.User;
import com.example.fedunistudentattendance30395713.sqlite.UserDao;

import java.util.List;

public class RegActivity extends AppCompatActivity {
    private EditText zhaohao,mima1,mima2;
    private Button btn_reg,btn_back;
    private UserDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reg);
        dao = new UserDao(this);

        initFindID();

        initClick();
    }

    private void initClick() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // reg
                String z = zhaohao.getText().toString().trim();
                String m1 = mima1.getText().toString().trim();
                String m2 = mima2.getText().toString().trim();
                if (z.isEmpty()||m1.isEmpty()||m2.isEmpty()){
                    Toast.makeText(RegActivity.this, "please input msg", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!m1.equals(m2)){
                    Toast.makeText(RegActivity.this, "password inconsistency", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> l = dao.query();
                if (l!=null) {
                    User userbean=null;
                    for (int i = 0; i < l.size(); i++) {
                        if (l.get(i).getUsername().equals(z)){
                            userbean = l.get(i);
                            break;
                        }
                    }
                    if (userbean!=null){
                        Toast.makeText(RegActivity.this, "account has already been registered", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //Start registration
                dao.insert(new User(0,z,m1));
                Toast.makeText(RegActivity.this, "registration successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initFindID() {
        zhaohao = findViewById(R.id.zhanghao);
        mima1 = findViewById(R.id.mima1);
        mima2 = findViewById(R.id.mima2);
        btn_reg = findViewById(R.id.btn_reg);
        btn_back = findViewById(R.id.btn_back);
    }
}