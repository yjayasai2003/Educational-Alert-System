package com.example.token;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText email,name,number,pass1,pass2,regd;
    String emails,names,numbers,pass1s,pass2s,regds;
    Button register;
    String status;
    private FirebaseFirestore db1 =FirebaseFirestore.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regd=findViewById(R.id.regd_enter);
        email=findViewById(R.id.email_enter);
        name=findViewById(R.id.name_enter);
        number=findViewById(R.id.number);
        pass1=findViewById(R.id.password_enter);
        pass2=findViewById(R.id.password_reenter);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regds=regd.getText().toString();
                emails=email.getText().toString();
                names=name.getText().toString();
                numbers=number.getText().toString();
                pass1s=pass1.getText().toString();
                pass2s=pass2.getText().toString();
                final String[] token = new String[1];
                boolean field_checking = fields_check();
                if (field_checking == true)
                {
                    boolean pass_checking = password_match();
                    if (pass_checking == true)
                    {
                        root = db.getReference().child("Users");
                        root.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(regds).exists()) {
                                    Toast.makeText(Register.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    HashMap<String, String> userMap = new HashMap<>();
                                    userMap.put("Registration Number", regds);
                                    userMap.put("Username", names);
                                    userMap.put("Email", emails);
                                    userMap.put("Contact Number", numbers);
                                    userMap.put("Password", pass1s);
                                    userMap.put("Status",status);
                                   db1.collection("users")
                                                    .add(userMap)
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                @Override
                                                                public void onSuccess(DocumentReference documentReference) {
                                                                    Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();

                                                                }
                                                            })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(Register.this, "Failure", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                    root.child(regds).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Register.this, "Inserted", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this,MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                    else {
                        pass1.setText("");
                        pass2.setText("");
                        Toast.makeText(Register.this, "Passwords did not match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Register.this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean password_match()
    {
        boolean checking = pass1s.equals(pass2s);
        return checking;
    }
    public boolean fields_check()
    {
        if(regds.length()!=0 && emails.length()!=0 && names.length()!=0 && numbers.length()!=0 && pass1s.length()!=0 && pass2s.length()!=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void RadioButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_student:
                if (checked)
                    status="Student";
                break;
            case R.id.radio_teacher:
                if (checked)
                    status="Teacher";
                break;
        }
    }
}