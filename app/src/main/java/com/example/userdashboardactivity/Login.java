package com.example.userdashboardactivity;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.userdashboardactivity.ui.profile.profilepageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText Email,password;
    Button logine;
    TextView informing,signingup,forget;
    int counter=5;
    ProgressDialog dialog;
    private FirebaseAuth firebaseauth;
    FirebaseUser user;
    String user_id,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email=findViewById(R.id.etEmail);
        password=findViewById(R.id.etpasscode);
        logine=findViewById(R.id.btnLogin);
        informing=findViewById(R.id.tvAttempt);
        signingup=findViewById(R.id.tvSignup);
        forget=findViewById(R.id.tvForget);
        //informing.setText("Nr of attempt remaining : 5");
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        firebaseauth=FirebaseAuth.getInstance();
        /*FirebaseUser firebaseUser=firebaseauth.getCurrentUser();

        if(firebaseUser !=null){
            finish();
            startActivity(new Intent(Login.this,UserDashboard.class));
        }*/
        logine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String User_Email=Email.getText().toString().trim();
                String Pass_word=password.getText().toString().trim();
                if(User_Email.isEmpty()){
                    Email.setError("Please Enter your Email");
                }
                else if(Pass_word.isEmpty()){
                    password.setError("Please Enter password");
                }
                else if(Pass_word.length()<6){
                    password.setError("Please Enter password with 6 or more character");
                }
                else{

                    dialog.show();
                    firebaseauth.signInWithEmailAndPassword(User_Email,Pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialog.dismiss();
                            if(task.isSuccessful()){

                                    checkPriveledge();
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();



                            }else{
                                counter--;
                                Email.setText("");
                                password.setText("");
                                //informing.setText("Nr of attempt remaining: "+ String.valueOf(counter));
                                //Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                Snackbar.make(v, "Nr of attempt remaining: "+ String.valueOf(counter), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                if(counter==0){
                                    logine.setEnabled(false);
                                }}
                        }
                    });
                    
                }
            }
        });


        signingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder=new AlertDialog.Builder(Login.this);
                View mview=getLayoutInflater().inflate(R.layout.resetpassword,null);
                EditText mEmail=mview.findViewById(R.id.etEmailRest);
                Button mButton=mview.findViewById(R.id.btnRest);
                ImageView closedialog=mview.findViewById(R.id.closedialog);

                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Login.this,Login.class));

                    }
                });

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user_email=mEmail.getText().toString().trim();
                        if(user_email.isEmpty()){
                            mEmail.setError("Please Enter your Email");
                        }

                        else{
                            dialog.show();
                            firebaseauth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if(task.isSuccessful()){
                                        //Toast.makeText(Login.this,"Resent message sent to"+user_email,Toast.LENGTH_SHORT).show();
                                        Snackbar.make(v, "Resent message set to: "+ user_email, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        //mview.clearFocus();
                                        mEmail.setText("");
                                        //startActivity(new Intent(MainActivity.this,second.class));
                                    }else{
                                        Toast.makeText(Login.this,"Reset Failed",Toast.LENGTH_SHORT).show();
                                        //mEmail.setText("");
                                    }
                                }
                            });


                        }
                    }
                });
                mbuilder.setView(mview);
                AlertDialog dialog=mbuilder.create();
                dialog.show();

            }
        });

    }
    public void checkPriveledge(){
        firebaseauth=FirebaseAuth.getInstance();
        user=firebaseauth.getCurrentUser();
        user_id=user.getUid();
        email=user.getEmail();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()) {
                    Level lvs=snap.getValue(Level.class);
                    if(lvs.getUser_id().equals(user_id) && lvs.getLevel().equals("1")){

                        startActivity(new Intent(getApplicationContext(),AdmindashboardActivity.class));
                            finish();
                    }
                    else{

                        Intent detaliIntent=new Intent(getApplicationContext(), UserdashboardActivity.class);
                        detaliIntent.putExtra("user_id",user_id);
                        detaliIntent.putExtra("email",email);
                        startActivity(new Intent(detaliIntent));
                            finish();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this,"database locked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}