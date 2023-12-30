package com.example.token;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class teacher_Intermediate extends AppCompatActivity {
    Button fp,nf,lg;
    int k=0;
    @Override
    public void onBackPressed(){

    }
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_intermediate);
        fp=findViewById(R.id.db);
        nf=findViewById(R.id.rn);
        lg=findViewById(R.id.lg);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(teacher_Intermediate.this,pdfupload.class);
                startActivity(i1);
            }
        });
        nf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regds = getIntent().getStringExtra("mykey");
                Intent i2=new Intent(teacher_Intermediate.this,send.class);
                i2.putExtra("mk",regds);
                startActivity(i2);
            }
        });
        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(teacher_Intermediate.this,MainActivity.class);
                startActivity(i3);
            }
        });
    }
}



