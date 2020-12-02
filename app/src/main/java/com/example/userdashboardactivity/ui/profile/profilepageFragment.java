package com.example.userdashboardactivity.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.userdashboardactivity.Productsaddedtocart;
import com.example.userdashboardactivity.R;

import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_DESCRIPTION;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_NAME;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_PRICE;
import static com.example.userdashboardactivity.ui.home.HomeFragment.EXTRA_URL;

public class profilepageFragment extends Fragment {
  TextView user_email;
  EditText user_emailtwo,user_phonenumber,user_password;
  Button updating;
  CardView pendingorders;

    private profilepageViewModel profilepageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profilepageViewModel = new ViewModelProvider(this).get(profilepageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Intent intent=getActivity().getIntent();
        String user_id=intent.getStringExtra("user_id");
        String email=intent.getStringExtra("email");

        user_email=root.findViewById(R.id.UserEmail);
        user_emailtwo=root.findViewById(R.id.useremail2);
        user_phonenumber=root.findViewById(R.id.userphonenumber);
        user_password=root.findViewById(R.id.userpassword);
        pendingorders=root.findViewById(R.id.yourpendingorders);

        user_email.setText(email);
        user_emailtwo.getText().toString().trim();
        user_emailtwo.setText(email);

        pendingorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Productsaddedtocart.class));
            }
        });

        return root;
    }
}