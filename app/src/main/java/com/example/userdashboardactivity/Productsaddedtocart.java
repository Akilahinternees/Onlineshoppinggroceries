package com.example.userdashboardactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userdashboardactivity.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_URL;

public class Productsaddedtocart extends AppCompatActivity {
    
    ListView alladdedtocart;
    TextView usere_email;
    ArrayList<CartList> cartlist;
    DatabaseReference mDatabaseRef;
    Button confirm;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsaddedtocart);

        Intent intent=getIntent();
        String emailoo=intent.getStringExtra("email");

        alladdedtocart=findViewById(R.id.allyouraddedtocart);
        usere_email=findViewById(R.id.emaile);
        confirm=findViewById(R.id.confirmOrder);
        usere_email.setText(emailoo);
        cartlist=new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("CartLists");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartlist.clear();
                for (DataSnapshot cartlistSnap: dataSnapshot.getChildren()) {
                    CartList cart = cartlistSnap.getValue(CartList.class);
                    cartlist.add(cart);
                }
                CartListAdapter adapter=new CartListAdapter(Productsaddedtocart.this,cartlist);
                alladdedtocart.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("AllOrders/"+System.currentTimeMillis());
                Allorders orders=new Allorders(usere_email.getText().toString(),String.valueOf(System.currentTimeMillis()));
                ref.setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            AlertDialog.Builder mbuilder=new AlertDialog.Builder(Productsaddedtocart.this);
                            View mview=getLayoutInflater().inflate(R.layout.confirmationpage,null);
                            Button mButton=mview.findViewById(R.id.btnRest);
                            ImageView closedialog=mview.findViewById(R.id.closedialog);

                            closedialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Productsaddedtocart.this, MainActivity.class));
                                    finish();
                                }
                            });

                            mButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(Productsaddedtocart.this, MainActivity.class));
                                    finish();

                                }
                            });
                            mbuilder.setView(mview);
                            AlertDialog dialog=mbuilder.create();
                            dialog.show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Saving Fail :-(", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }
}