 package com.example.userdashboardactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Addproduct extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button btnchooseImage, btnupload;
    EditText ImageName,ImageDiscription,ImagePrice;
    ImageView imgview;
    ProgressBar mProgressBar;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        btnchooseImage=findViewById(R.id.uploadfile);
        btnupload=findViewById(R.id.addthisproduct);
        ImageName=findViewById(R.id.productname);
        ImageDiscription=findViewById(R.id.productdescription);
        ImagePrice=findViewById(R.id.productprice);
        imgview=findViewById(R.id.productimage);
        mProgressBar=findViewById(R.id.progressBar2);

        mStorageRef = FirebaseStorage.getInstance().getReference("Products");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");

        btnchooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask !=null && mUploadTask.isInProgress()){
                    Toast.makeText(Addproduct.this, "Upload in Progress", Toast.LENGTH_LONG).show();
                }else {
                    uploadFile();
                }
            }
        });

    }
    //set choosen image to image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();

            Glide.with(this).load(mImageUri).into(imgview);

        }
    }

    //getting file extension
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    //uplaod product with image
    private void uploadFile(){
        if (mImageUri !=null){
            String name, description,price,time;
            time = String.valueOf(System.currentTimeMillis());
            name = ImageName.getText().toString();
            description = ImageDiscription.getText().toString();
            price = ImagePrice.getText().toString();

            StorageReference fileReference = mStorageRef.child(time
                    +"."+GetFileExtension(mImageUri));

            mUploadTask =fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                //Success
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Thread delay = new Thread(){
                        @Override
                        public void run() {
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    delay.start();
                    Toast.makeText(Addproduct.this, "Upload Successful", Toast.LENGTH_LONG).show();
                    Uploadproducts upload = new Uploadproducts(name,description,price,taskSnapshot.getDownloadUrl().toString(),time);
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(time+"").setValue(upload);
                    startActivity(new Intent(getApplicationContext(),Allproducts.class));

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                //Failure
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Addproduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                //Updating the Progress Bar
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            });

        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }
}