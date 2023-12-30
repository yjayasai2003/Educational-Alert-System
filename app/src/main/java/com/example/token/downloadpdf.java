package com.example.token;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class downloadpdf extends AppCompatActivity {
    ListView listView;
    DatabaseReference databaseReference;
    List<putpdf> uploadPDF;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadpdf);
        listView = findViewById(R.id.listview);
        uploadPDF = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("uploadPDF");
        retrievedata();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                new AlertDialog.Builder(downloadpdf.this)
                        .setMessage("Choose your option")
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String s=listView.getItemAtPosition(i).toString();
                                Intent intent = new Intent(downloadpdf.this,webview.class);
                                intent.putExtra("key1",s);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Close",null).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                putpdf putpdf = uploadPDF.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                Toast.makeText(downloadpdf.this, "Single Click for Download\nLong press to view", Toast.LENGTH_SHORT).show();
                intent.setData(Uri.parse(putpdf.getUrl()));
                startActivity(intent);
            }
        });
    }
    private void retrievedata()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadPDF");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    putpdf putpdf = ds.getValue(com.example.token.putpdf.class);
                    uploadPDF.add(putpdf);
                }
                String[] uploadName = new String[uploadPDF.size()];
                for (int i = 0; i < uploadName.length; i++)
                {
                    uploadName[i] = uploadPDF.get(i).getName();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,uploadName){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView( position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(20);
                        textView.getEditableText();
                        return view;
                    }
                };
                 listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}