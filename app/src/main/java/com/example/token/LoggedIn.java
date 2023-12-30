package com.example.token;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LoggedIn extends AppCompatActivity {
    TextView t1;
    String[] mylist = new String[5];
    String[] mylist1 = new String[5];
    String[] mylist2 = new String[5];
    Vector<String> token = new Vector<String>(5);
    Vector<String> token1 = new Vector<>(5);
    Vector<String> token2 = new Vector<>(5);
    List<Item> items = new ArrayList<Item>();
    String o=null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_view);
        t1=findViewById(R.id.message);
   
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("msg");
        db.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String t = null,t11 = null,t2 = null;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    t = userSnapshot.child("RegistrationNumber").getValue().toString();
                    //Toast.makeText(LoggedIn.this, ""+t, Toast.LENGTH_SHORT).show();

                }
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    t11 = userSnapshot.child("title").getValue().toString();
                    //Toast.makeText(LoggedIn.this, ""+t, Toast.LENGTH_SHORT).show();
                    
                }
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    t2 = userSnapshot.child("message").getValue().toString();
                    //Toast.makeText(LoggedIn.this, ""+t, Toast.LENGTH_SHORT).show();
                    
                }
//                a1[0] =token.get(0);a2[0] =token.get(0);a3[0] =token.get(0);a4[0] =token.get(0);a5[0] =token.get(0);
  //              k1[0]=token1.get(0);k2[0]=token1.get(0);k3[0]=token1.get(0);k4[0]=token1.get(0);k5[0]=token1.get(0);
    //            j1[0]=token2.get(0);j2[0]=token2.get(0);j3[0]=token2.get(0);j4[0]=token2.get(0);j5[0]=token2.get(0);
                t1.setText("Teacher Name: "+t+"\n"+"Title                  :"+t11+"\n"+"Message         :"+t2+"\n");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //items.add(new Item("Robert j",o,"sdfa"));
       /* items.add(new Item("James Gunn","james.gunn@email.com","sdfdas"));
        items.add(new Item("Ricky tales","rickey.tales@email.com","sadfa"));
        items.add(new Item("Micky mose","mickey.mouse@email.com","asdfdfa"));
*/
        

    }

}


