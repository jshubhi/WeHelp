package com.example.wehelp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class myprofile extends Fragment {
    ImageView i1;
    OutputStream outputStream;
    TextView profemail, profmobilenumber, proftype;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //Uri imagePath;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.myprofile, container, false);

        Button b1 = (Button) inflate.findViewById(R.id.MyProfileChangePic);
        i1 = inflate.findViewById(R.id.MyProfilePicView);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }


        });
        //Profile data display
        final TextView profemail = (TextView) inflate.findViewById(R.id.profemail);
        final TextView profmobilenumber = (TextView) inflate.findViewById(R.id.profmobilenumber);
        final TextView proftype = (TextView) inflate.findViewById(R.id.proftype);
        final TextView profusername = (TextView) inflate.findViewById(R.id.profname);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profemail.setText(userProfile.getEmail());
                profmobilenumber.setText(userProfile.getPhonenumber());
                proftype.setText(userProfile.getType());
                profusername.setText(userProfile.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database Error", Toast.LENGTH_SHORT).show();
                //When class is extending fragment it is necessary to use getActivity() since fragment is a subclass of activity.

            }
        });

        //GETTING UPLOADED PROFILE PICTURE
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhotoUrl() != null) {
            //Here we are using glide library for displaying profile pic whose dependencies are added in app level gradle
            Glide.with(this).load(user.getPhotoUrl()).into(i1);

        }

        return inflate;
    }


    //CAMERA and UPLOADING PROFILE PICTURE FORM HERE TO END OF CODE
    public void startCamera() {
        Intent ic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(ic, 777);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 777) {

            Bitmap bm1 = (Bitmap) data.getExtras().get("data");
            i1.setImageBitmap(bm1);//To Display image
            handleupload(bm1);




        }
    }

    private void handleupload(Bitmap bm1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm1.compress(Bitmap.CompressFormat.JPEG, 100, baos);

       String uid =FirebaseAuth.getInstance().getCurrentUser().getUid();
       final StorageReference reference =FirebaseStorage.getInstance().getReference().child("Profile Images").child(uid+".jpeg");

       reference.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
             getDownloadUrl(reference);
               Toast.makeText(getActivity(),"Upload Success",Toast.LENGTH_SHORT).show();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.e(TAG,"onFailure:",e.getCause());
               Toast.makeText(getActivity(),"Upload Failed",Toast.LENGTH_SHORT).show();
           }
       });


    }


    private void getDownloadUrl(StorageReference reference)
    {
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG,"onSuccess:"+uri);
             setUserProfileUrl(uri);
            }
        });
    }


    private void setUserProfileUrl(Uri uri)
    {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request =new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"Upload Success",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
