package com.ttl.ritz7;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class projectActivity extends AppCompatActivity {
    EditText proName, proDesc, proCost;
    Button btnBrowse, btnUpload;
    ImageView imagePro;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        proName = findViewById(R.id.proName);
        proDesc = findViewById(R.id.proDescription);
        proCost = findViewById(R.id.proCost);
        btnBrowse = findViewById(R.id.btnbrowse);
        btnUpload = findViewById(R.id.btnupload);
        imagePro = findViewById(R.id.image_viewPro);
        storageReference = FirebaseStorage.getInstance().getReference("Project");
        databaseReference = FirebaseDatabase.getInstance().getReference("Project");
        progressDialog = new ProgressDialog(projectActivity.this);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
        imagePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imagePro.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    private void UploadData() {
        if (FilePathUri != null) {

            progressDialog.setTitle("Data is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String pName = proName.getText().toString().trim();
                                    String pDesc = proDesc.getText().toString().trim();
                                    String pCost = proCost.getText().toString().trim();
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                                    @SuppressWarnings("VisibleForTests")
                                    projectUpload projectUploadinfo = new projectUpload(pName, task.getResult().toString(),pDesc,pCost);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(projectUploadinfo);
                                }
                            });
//                            String TempImageName = txtdata.getText().toString().trim();
//                            String ComLocation = textLocation.getText().toString().trim();
//                            String timeUpload = textTime.getText().toString().trim();
//                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//                            @SuppressWarnings("VisibleForTests")
//                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString(),ComLocation,timeUpload);
//                            String ImageUploadId = databaseReference.push().getKey();
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(projectActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }
}