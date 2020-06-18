package com.example.wehelp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signuppage extends AppCompatActivity {
    private EditText username,password,phonenumber,type,email;
    private Button signup;
    private FirebaseAuth firebaseAuth;
    private CheckBox cb3;
    String name,pass,ty,pn,mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signuppage);
   /*     //Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

     /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        firebaseAuth= FirebaseAuth.getInstance();
        setupUIViews();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //upload to database
                    String emailaddress;
                    emailaddress = email.getText().toString().trim();
                    String passworddd;
                    passworddd = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(emailaddress,passworddd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                              /* This code is used if we do not want email verification
                              Toast.makeText(signuppage.this,"Regestration Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signuppage.this,MainActivity.class));*/
                              //with Email Verification
                                sendEmailVerification();
                                sendUserData();
                            }
                            else  {
                                Toast.makeText(signuppage.this,"Regestration Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
    private void setupUIViews(){

        username=(EditText)findViewById(R.id.SiUsername);
        password=(EditText)findViewById(R.id.SiPassowrd);
        cb3= findViewById(R.id.SignInShowPassword);
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        phonenumber=(EditText)findViewById(R.id.SiPhoneNumber);
        type=(EditText)findViewById(R.id.SiType);
        email=(EditText)findViewById(R.id.SiEmail);
        signup=(Button)findViewById(R.id.SiRegister);

    }

    private Boolean validate(){
        Boolean result = false;
         name=username.getText().toString();
         pass=password.getText().toString();
         mail=email.getText().toString();
         ty=type.getText().toString();
         pn=phonenumber.getText().toString();

        if(name.isEmpty() || pass.isEmpty() || mail.isEmpty()|| ty.isEmpty() ||pn.isEmpty() ){
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification()
     {
        final FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        Toast.makeText(signuppage.this,"Successfully Registered and Verification mail has been sent to your registered email address",Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(signuppage.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(signuppage.this,"Verification mail hasn't been sent",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void sendUserData()
    {
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile= new UserProfile(name,ty,pn,mail);
        myRef.setValue(userProfile);
    }
}
