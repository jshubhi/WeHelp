package com.example.wehelp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//implements NavigationView.OnNavigationItemSelectedListener
public class blindlogin extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerlayout2;
    private ActionBarDrawerToggle mToggle2;
    private Button b3,b4;
    private EditText blindEmail,blindPass;
    private FirebaseAuth firebaseAuth;
    private CheckBox cb1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blindlogin);
        /*mDrawerlayout2=(DrawerLayout) findViewById(R.id.activity_blindlogin);
        mToggle2=new ActionBarDrawerToggle(this,mDrawerlayout2,R.string.Open,R.string.Close);
        mDrawerlayout2.addDrawerListener(mToggle2);
        mToggle2.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        b4= (Button) findViewById(R.id.blindsignup);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startbsignup();

            }
        });

        //check weather user is logged in or not,if logged in it ask fingerprint for logging again
        FirebaseUser user =firebaseAuth.getCurrentUser();
        if(user != null) {
            blindsettings b = new blindsettings();
            if (b.validation())
            {
                finish();
                //
                final Executor executor = Executors.newSingleThreadExecutor();
                final BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
                        .setTitle("Finger Print Authentication Required for loging in ")

                        .setNegativeButton("cancel", executor, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).build();

                biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        // super.onAuthenticationSucceeded(result);
                        startActivity(new Intent(blindlogin.this, blindDashboard.class));

                    }
                });
            }
            else
            {
                startActivity(new Intent(blindlogin.this, blindDashboard.class));
            }
        }
        blindEmail=(EditText)findViewById(R.id.blindUsername);
        blindPass=(EditText)findViewById(R.id.blindPassword);



        //For converting password into dots
        cb1= findViewById(R.id.blindShowPassword);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    blindPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    blindPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });




        //FORGOT PASSWORD
        tv1 = (TextView)findViewById(R.id.blindForgotPassword);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(blindlogin.this,forgotPassowrd.class));
            }
        });


        //FINGERPRINT AUTHENTICATION
       /* final Executor executor= Executors.newSingleThreadExecutor();
        final BiometricPrompt biometricPrompt =new BiometricPrompt.Builder(this)
                .setTitle("FingerPrintAuthentication")
                .setSubtitle("Subtitle")
                .setDescription("Description")
                .setNegativeButton("cancel", executor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).build();*/


        b3=(Button)findViewById(R.id.blindlogin);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              /*  biometricPrompt.authenticate(new CancellationSignal(), executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                       // super.onAuthenticationSucceeded(result);
                        startblogin();

                    }
                });*/
                startblogin();
            }


        });


    }
    public void startbsignup(){
        Intent I1= new Intent(this,signuppage.class);
        startActivity(I1);
    }


   public void startblogin(){
       if(validate())
       {
       String bemailAddress= blindEmail.getText().toString().trim();
       String bpassword= blindPass.getText().toString().trim();

       firebaseAuth.signInWithEmailAndPassword(bemailAddress,bpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   checkEmailVerification();
                   Toast.makeText(blindlogin.this,"Login Successful",Toast.LENGTH_SHORT).show();
               }
               else  {
                   Toast.makeText(blindlogin.this,"Login Failed",Toast.LENGTH_SHORT).show();
               }
           }
       });

     }
   }




    private Boolean validate(){
        Boolean result = false;

        String pass=blindPass.getText().toString();
        String mail=blindEmail.getText().toString();

        if(mail.isEmpty()) {
            Toast.makeText(this, "Please enter Email-Id", Toast.LENGTH_SHORT).show();
        }

        else if(pass.isEmpty()){
                Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            }

        else {
            result = true;
        }
        return result;
    }


    //EMAIL VERIFICATION
    private void checkEmailVerification(){
        FirebaseUser firebaseUser =firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag =firebaseUser.isEmailVerified();

        if(emailflag)
        {
            finish();
            startActivity(new Intent(blindlogin.this,blindDashboard.class));
        }
        else
        {
            Toast.makeText(this,"Please Verify your email and login again",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    //Password Reset
    private void passwordReset()
    {

    }
}
