package com.example.userdashboardactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText phone,email,password;
    Button register;
    TextView AlreadyRegister;
    ProgressDialog dialog;
    private FirebaseAuth firebaseauth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupviews();

        firebaseauth=FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Registering");
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validates()){
                    //upload to database

                    String useremail=email.getText().toString().trim();
                    String userpassword=password.getText().toString().trim();
                    dialog.show();
                    firebaseauth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(Register.this,Login.class));
                                assignPriveledge(0);
                            }else{
                                Snackbar.make(v, "Registration Failed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                //mview.clearFocus();
                                phone.setText("");
                                email.setText("");
                                password.setText("");
                            }

                        }
                    });

                }

            }
        });
        AlreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
    private void setupviews(){
        email=findViewById(R.id.etEmail);
        phone=findViewById(R.id.etpasscode);
        password=findViewById(R.id.etpassword);
        register=findViewById(R.id.btnRegister);
        AlreadyRegister=findViewById(R.id.tvlogin);
    }
    private boolean validates(){
        Boolean result=false;
        String Email=email.getText().toString().trim();
        String Phone=phone.getText().toString().trim();
        String PassWord=password.getText().toString().trim();

        if(Email.isEmpty()){
            email.setError("Please Enter Email");
        }
        else if(Phone.isEmpty()){
            phone.setError("Please Enter Phone");
        }
        else if(PassWord.isEmpty()){
            password.setError("Please Enter Password");
        }
        else if(PassWord.length()<6){
            password.setError("Please Enter Password with 6 or more character");
        }
        else{
            result=true;
        }
        return result;
    }
    public void assignPriveledge(int level){
        firebaseauth=FirebaseAuth.getInstance();
        user=firebaseauth.getCurrentUser();
        String user_id,email,phonenumber;
        user_id=user.getUid();
        email=user.getEmail();


        Level lv=new Level(user_id,String.valueOf(level),email);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users/"+System.currentTimeMillis());
        ref.setValue(lv).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //firebaseauth.signOut();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
                else{
                    assignPriveledge(0);
                }
            }
        });
    }
}