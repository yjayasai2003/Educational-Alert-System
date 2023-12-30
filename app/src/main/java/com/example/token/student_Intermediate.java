package com.example.token;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class student_Intermediate extends AppCompatActivity {
    Button rn,db,lg1;
    EditText regd;
    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Users");
    @SuppressLint("MissingInflatedId")
    @Override
    public void onBackPressed(){

    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s_intermediate);
        rn=findViewById(R.id.rn);
        db=findViewById(R.id.db);
        lg1=findViewById(R.id.lg1);

        rn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(student_Intermediate.this,LoggedIn.class);
                startActivity(i1);
            }
        });
        db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(student_Intermediate.this, "Single Click for Download\nLong press to view", Toast.LENGTH_SHORT).show();
                Intent i2=new Intent(student_Intermediate.this,downloadpdf.class);
                startActivity(i2);
            }
        });
        lg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(student_Intermediate.this,MainActivity.class);
                startActivity(i3);

            }
        });
    }
}

