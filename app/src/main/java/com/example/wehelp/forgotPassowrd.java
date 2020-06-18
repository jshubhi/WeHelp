package com.example.wehelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotPassowrd extends AppCompatActivity{
    private EditText forogtemail;
    private Button forgotButton;
    private FirebaseAuth firebaseAuth;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();
        forogtemail =(EditText)findViewById(R.id.ForgotPasswordEmail);
        forgotButton=(Button)findViewById(R.id.ForgotPassword);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String useremail=forogtemail.getText().toString().trim();
             if(forogtemail.equals(""))
             {
                 Toast.makeText(forgotPassowrd.this,"Please Enter Your Registered email",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful())
                         {
                             Toast.makeText(forgotPassowrd.this,"Ppassword reset link has been sent to your registered Email",Toast.LENGTH_SHORT).show();
                             finish();
                             startActivity(new Intent(forgotPassowrd.this,MainActivity.class));
                         }
                         else
                         {
                             Toast.makeText(forgotPassowrd.this,"Error in sending reset link",Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }
            }
        });

    }
}
