package com.example.userdashboardactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_DESCRIPTION;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_EMAIL;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_NAME;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_PRICE;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_URL;

public class AddToCart extends AppCompatActivity {
    ImageView imageselected;
    TextView name,description,price,quantity,useremail;
    Button addition,substration,addingproducttocart;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        Intent intent=getIntent();
        String imageUrl=intent.getStringExtra(EXTRA_URL);
        String imagename=intent.getStringExtra(EXTRA_NAME);
        String imagedescription=intent.getStringExtra(EXTRA_DESCRIPTION);
        String imageprice=intent.getStringExtra(EXTRA_PRICE);
        String USERSEMAIL=intent.getStringExtra(EXTRA_EMAIL);

        imageselected=findViewById(R.id.selectedproduct);
        name=findViewById(R.id.selectedImageName);
        description=findViewById(R.id.selectedImageDescription);
        useremail=findViewById(R.id.USER_EMAILTWO);
        price=findViewById(R.id.selectedImagePrice);
        quantity=findViewById(R.id.quantityofproduct);
        addition=findViewById(R.id.increasequantity);
        substration=findViewById(R.id.reducequantinty);
        addingproducttocart=findViewById(R.id.AddingproductTocart);

        Glide.with(this).load(imageUrl).fitCenter().into(imageselected);
        name.setText(imagename);
        description.setText(imagedescription);
        price.setText(imageprice);
        useremail.setText(USERSEMAIL);


        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int pricetwo=Integer.parseInt(String.valueOf(price.getText().toString()))+Integer.parseInt(String.valueOf(price.getText().toString()));
                int quant= Integer.parseInt(String.valueOf(quantity.getText().toString()))+1;
                //price.setText(String.valueOf(pricetwo));
                quantity.setText(String.valueOf(quant));

            }
        });

        substration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int pricetwo=Integer.parseInt(String.valueOf(price.getText().toString()))-Integer.parseInt(String.valueOf(price.getText().toString()));
                int quant= Integer.parseInt(String.valueOf(quantity.getText().toString()))-1;
                //price.setText(String.valueOf(pricetwo));
                quantity.setText(String.valueOf(quant));
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Saving");
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        addingproducttocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email,productname,productquantity,productprice,choosequantity;
                long time;
                int totalprice;
                time=System.currentTimeMillis();
                user_email=useremail.getText().toString().trim();
                productname=name.getText().toString().trim();
                productquantity=description.getText().toString().trim();
                productprice=price.getText().toString().trim();
                choosequantity=quantity.getText().toString().trim();
                totalprice=Integer.parseInt(productprice)*Integer.parseInt(choosequantity);

                if(Integer.parseInt(choosequantity)<1){
                    Toast.makeText(getApplicationContext(), "The quantity of product you want is 0 ", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("CartLists/"+time);
                    CartList cartlist=new CartList(user_email,productname,choosequantity,productprice,String.valueOf(totalprice),String.valueOf(time));
                    dialog.show();
                    ref.setValue(cartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                String UEMAIL=useremail.getText().toString().trim();
                                Intent listintent=new Intent(getApplicationContext(),Productsaddedtocart.class);

                                listintent.putExtra("email",UEMAIL);
                                startActivity(listintent);

                                Toast.makeText(getApplicationContext(), "Saving Successful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Saving Fail :-(", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                //startActivity(new Intent(getApplicationContext(),Productsaddedtocart.class));
            }
        });
    }
}