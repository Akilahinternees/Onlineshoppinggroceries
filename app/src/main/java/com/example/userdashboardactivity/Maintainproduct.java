package com.example.userdashboardactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_DESCRIPTION;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_EMAIL;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_NAME;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_PRICE;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_URL;

public class Maintainproduct extends AppCompatActivity {
    ImageView imageselected;
    TextView name,description,price,pro_id;
    Button delete,update;
    ProgressDialog dialog;
    DatabaseReference dataref;
    StorageReference storageref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainproduct);
        Intent intent=getIntent();
        String imageUrl=intent.getStringExtra("productimage");
        String imagename=intent.getStringExtra("productname");
        String imagedescription=intent.getStringExtra("productdescription");
        String imageprice=intent.getStringExtra("productprice");
        String PRODUCTID=intent.getStringExtra("productid");

        imageselected=findViewById(R.id.PIMAGE);
        name=findViewById(R.id.PNAME);
        description=findViewById(R.id.PDESCRIPTION);
        price=findViewById(R.id.PPRICE);
        pro_id=findViewById(R.id.product_id);
        delete=findViewById(R.id.deleteproduct);
        update=findViewById(R.id.updateproduct);

        Glide.with(this).load(imageUrl).fitCenter().into(imageselected);
        name.setText(imagename);
        description.setText(imagedescription);
        price.setText(imageprice+" FRW");
        pro_id.setText(PRODUCTID);

        dataref= FirebaseDatabase.getInstance().getReference().child("Products").child(PRODUCTID);
        storageref= FirebaseStorage.getInstance().getReference().child("Products").child(PRODUCTID+"jpg");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(getApplicationContext(),Allproducts.class));
                        storageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }
                });

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder=new AlertDialog.Builder(Maintainproduct.this);
                View mview=getLayoutInflater().inflate(R.layout.updateproduct,null);
                ImageView mphoto=mview.findViewById(R.id.profoto);
                EditText mname=mview.findViewById(R.id.proname);
                EditText mdescription=mview.findViewById(R.id.prodecription);
                EditText mprice=mview.findViewById(R.id.proprice);
                Button mButton=mview.findViewById(R.id.confirmupdate);
                ImageView closedialog=mview.findViewById(R.id.closedialog);

                Glide.with(mview.getContext()).load(imageUrl).fitCenter().into(mphoto);
                mname.setText(imagename);
                mdescription.setText(imagedescription);
                mprice.setText(imageprice);

                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Maintainproduct.this,Allproducts.class));
                        finish();

                    }
                });

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(isNameChanged() || isDescriptionChanged() || isPriceChanged()){
                          Toast.makeText(getApplicationContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(Maintainproduct.this,Allproducts.class));
                          finish();
                      }
                      else{
                          Snackbar.make(v, "Data is same and can not be updated ", Snackbar.LENGTH_LONG)
                                  .setAction("Action", null).show();
                      }
                    }

                    private boolean isNameChanged() {
                        if(!imagename.equals(mname.getText().toString().trim())){
                            dataref.child("name").setValue(mname.getText().toString().trim());
                            return true;
                        }
                        else{
                            return false;
                        }
                    }

                    private boolean isDescriptionChanged() {
                        if(!imagedescription.equals(mdescription.getText().toString().trim())){
                            dataref.child("description").setValue(mdescription.getText().toString().trim());
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    private boolean isPriceChanged() {
                        if(!imageprice.equals(mprice.getText().toString().trim())){
                            dataref.child("price").setValue(mprice.getText().toString().trim());
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                });
                mbuilder.setView(mview);
                AlertDialog dialog=mbuilder.create();
                dialog.show();

            }
        });
    }
}