package com.example.token;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText password,regdno;
    Button login,register;
    String pass, regd;
    String verification_pass;
    String status_pass;
    String read_id="",read_pass="";
    @SuppressLint("MissingInflatedId")
    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_here);
        password=findViewById(R.id.password);
        regdno=findViewById(R.id.regdno);
        login=findViewById(R.id.logging);
        register=findViewById(R.id.register);
        final String[] token = new String[1];
        try
        {
            read_id="";
            read_pass="";
            String path = "" + getFilesDir() + "/" + "login_cred" + ".txt";
            File file = new File(path);
            if(file.exists())
            {
                FileInputStream fis =new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                read_id=read_id+br.readLine();
                read_pass=read_pass+br.readLine();
                password.setText(read_pass);
                regdno.setText(read_id);
            }

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Register.class);
                startActivity(intent);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                regd=""+regdno.getText().toString();
                pass=""+password.getText().toString();
                if(regd.length()!=0 && pass.length()!=0)
                {
                    FirebaseMessaging.getInstance().getToken()
                            .addOnCompleteListener(new OnCompleteListener<String>() {
                                @Override
                                public void onComplete(@NonNull Task<String> task) {
                                    if (!task.isSuccessful()) {
                                        return;
                                    }
                                    token[0] = task.getResult();
                                }
                            });
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
                    db.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(regd).exists()) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    if (userSnapshot.getKey().equals(regd)) {
                                        verification_pass = userSnapshot.child("Password").getValue().toString();
                                        status_pass=userSnapshot.child("Status").getValue().toString();
                                    }
                                }

                                if(pass.equals(verification_pass) && status_pass.equals("Student")) {
                                    try {
                                        FileOutputStream fos = new FileOutputStream("" + getFilesDir() + "/" + "login_cred" + ".txt");
                                        String credintials = regd+"\n"+pass;
                                        fos.write(credintials.getBytes());
                                        fos.close();
                                    }
                                    catch (FileNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                    catch (IOException e)
                                    {
                                        e.printStackTrace();
                                    }

                                    db.child(regd).child("Token").setValue(token[0]);
                                    Intent intent = new Intent(MainActivity.this, student_Intermediate.class);
                                    intent.putExtra("mykey",regd);
                                    startActivity(intent);
                                }

                                else if(pass.equals(verification_pass) && status_pass.equals("Teacher")) {
                                    try {
                                        FileOutputStream fos = new FileOutputStream("" + getFilesDir() + "/" + "login_cred" + ".txt");
                                        String credintials = regd + "\n" + pass;
                                        fos.write(credintials.getBytes());
                                        fos.close();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    db.child(regd).child("Token").setValue("1");
                                    Intent intent = new Intent(MainActivity.this, teacher_Intermediate.class);
                                    intent.putExtra("mykey",regd);
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this, "Logged in as Teacher", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    password.setText("");
                                    Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else {
                                Toast.makeText(MainActivity.this, "Sorry! User does not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}