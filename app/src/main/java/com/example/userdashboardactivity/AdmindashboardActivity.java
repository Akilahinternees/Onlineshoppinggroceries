package com.example.userdashboardactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AdmindashboardActivity extends AppCompatActivity {
    public CardView card1,card2,card3,card4,card5,card6,card7;
    private FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindashboard);

        firebaseauth= FirebaseAuth.getInstance();

        card1=findViewById(R.id.Allconfirmedorders);
        card2=findViewById(R.id.Allpendingorders);
        card3=findViewById(R.id.Addproducts);
        card4=findViewById(R.id.Maintainproduct);
        card5=findViewById(R.id.Allusers);
        card6=findViewById(R.id.AddAdmin);
        card7=findViewById(R.id.adminlogout);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Addproduct.class));
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),Allproducts.class));
                    
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseauth.signOut();
                startActivity(new Intent(AdmindashboardActivity.this,MainActivity.class));
                finish();
            }
        });

    }
}