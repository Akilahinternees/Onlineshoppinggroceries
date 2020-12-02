package com.example.userdashboardactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Allproducts extends AppCompatActivity implements ProductAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private  ProductAdapter mAdaper;
    private ProgressDialog dialog;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Uploadproducts> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allproducts);

        mRecyclerView = findViewById(R.id.allrecycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mUploads = new ArrayList<>();
        mAdaper = new ProductAdapter(this,mUploads);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        dialog.show();

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Uploadproducts upload = postSnapshot.getValue(Uploadproducts.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                    Collections.reverse(mUploads);
                }
                mAdaper.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Allproducts.this, "Database is locked", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdaper);
        mAdaper.setOnItemClickListener(Allproducts.this);
    }
    @Override
    public void onItemClick(int position) {

        Intent intent=new Intent(getApplicationContext(),Maintainproduct.class);
        Uploadproducts detialedproduct=mUploads.get(position);

        intent.putExtra("productname",detialedproduct.getName());
        intent.putExtra("productprice",detialedproduct.getPrice());
        intent.putExtra("productdescription",detialedproduct.getDescription());
        intent.putExtra("productimage",detialedproduct.getImage_url());
        intent.putExtra("productid",detialedproduct.getKey());
        startActivity(intent);
    }
}