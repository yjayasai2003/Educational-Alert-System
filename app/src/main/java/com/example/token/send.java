package com.example.token;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class send extends AppCompatActivity {
    private EditText mtitle, mmessage1;
    private Button sendmsg;
    DatabaseReference db;
    int k = 0;
    String t;
    double latitude;
    double longitude;
    String address;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtitle = findViewById(R.id.title1);
        mmessage1 = findViewById(R.id.message);
        sendmsg = findViewById(R.id.send);
        db = FirebaseDatabase.getInstance().getReference("Users");
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mtitle.getText().toString().trim();
                String message = mmessage1.getText().toString().trim();
                List<String> nodenames = new ArrayList<>();
                Intent intent = getIntent();
                String regds = intent.getStringExtra("mk");
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(send.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(send.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("msg");
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("RegistrationNumber", regds);
                userMap.put("title", title);
                userMap.put("message", message);
                dbref.child(regds).setValue(userMap);
               /* Geocoder geocoder = new Geocoder(send.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude,longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address = addresses.get(0).getAddressLine(0);*/
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            t = userSnapshot.child("Token").getValue().toString();
                            if (t.isEmpty()) {
                                continue;
                            } else {
                                if (!title.equals("") && !message.equals("") && !t.equals("1")) {
                                    FCMSend.pushNotification(
                                            send.this,
                                            t,
                                            title,
                                            message);
                                    Toast.makeText(send.this, "Send Successful", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                    }

                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

}
