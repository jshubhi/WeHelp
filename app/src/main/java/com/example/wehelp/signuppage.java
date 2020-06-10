package com.example.wehelp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("signin");
    EditText username,password,phoneno,type,email;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username=findViewById(R.id.SiUsername);
        password=findViewById(R.id.SiPassowrd);
         phoneno=findViewById(R.id.SiPhoneNumber);
         type=findViewById(R.id.SiType);
         email=findViewById(R.id.SiEmail);

        signup=findViewById(R.id.SiRegister);
        auth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_signuppage);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phone = phoneno.getText().toString();
                String txt_type = type.getText().toString();
                String txt_email = email.getText().toString();
                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)  || TextUtils.isEmpty(txt_type)  || TextUtils.isEmpty(txt_phone)) {
                    Toast.makeText(signuppage.this,"All fields are compulsory", Toast.LENGTH_SHORT).show();
                }
                else if(txt_password.length()<6) {
                    Toast.makeText(signuppage.this,"Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(txt_username,txt_password,txt_phone,txt_type,txt_email);
                }

            }
        });

        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }
    private void register(final String username,String password,final String phone,final String type,final String email ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();
                            myRef = FirebaseDatabase.getInstance().getReference("signin").child(userid);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("phone",phone);
                            hashMap.put("type",type);
                            hashMap.put("email",email);

                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(signuppage.this, blindlogin.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(signuppage.this, "Database error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(signuppage.this, "You can't register with this email or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    }


