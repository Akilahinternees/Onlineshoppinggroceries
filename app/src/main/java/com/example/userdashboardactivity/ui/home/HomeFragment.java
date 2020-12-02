package com.example.userdashboardactivity.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userdashboardactivity.AddToCart;
import com.example.userdashboardactivity.ImageAdapter;
import com.example.userdashboardactivity.R;
import com.example.userdashboardactivity.Uploadproducts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements ImageAdapter.OnItemClickListener {
    public static final String EXTRA_URL="imageUrl";
    public static final String EXTRA_NAME="name";
    public static final String EXTRA_PRICE="price";
    public static final String EXTRA_DESCRIPTION="description";
    public static final String EXTRA_EMAIL="email";
     RecyclerView mRecyclerView;
     ImageAdapter mAdapter;
     ProgressDialog dialog;
     TextView Uemail;
     String USEREMAIL;
     FirebaseStorage mStorage;
     DatabaseReference mDatabaseRef;
     ValueEventListener mDBListener;
     List<Uploadproducts> mUploads;



    //private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Intent intent=getActivity().getIntent();
        String user_id=intent.getStringExtra("user_id");
        String email=intent.getStringExtra("email");



        mRecyclerView = root.findViewById(R.id.recycler_view);
        Uemail=root.findViewById(R.id.USER_EMAIL);
        Uemail.setText(email);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapter(getContext(),mUploads);

        dialog = new ProgressDialog(getContext());
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
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database is locked", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(HomeFragment.this);




        /*final CardView card1=root.findViewById(R.id.tomato);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddToCart.class));
            }
        });
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onItemClick(int position) {
        USEREMAIL=Uemail.getText().toString().trim();
        Intent detaliIntent=new Intent(getContext(),AddToCart.class);
        Uploadproducts detialedproduct=mUploads.get(position);

        detaliIntent.putExtra(EXTRA_NAME,detialedproduct.getName());
        detaliIntent.putExtra(EXTRA_PRICE,detialedproduct.getPrice());
        detaliIntent.putExtra(EXTRA_DESCRIPTION,detialedproduct.getDescription());
        detaliIntent.putExtra(EXTRA_URL,detialedproduct.getImage_url());
        detaliIntent.putExtra(EXTRA_EMAIL,USEREMAIL);
        startActivity(detaliIntent);
    }
}