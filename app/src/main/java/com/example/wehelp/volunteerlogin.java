package com.example.wehelp;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class volunteerlogin extends AppCompatActivity  {
    private DrawerLayout mDrawerlayout3;
    private ActionBarDrawerToggle mToggle3;
    private Button b3,b2;
    private EditText voluEmail,voluPass;
    private FirebaseAuth firebaseAuth;
    private CheckBox cb2;
    private TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_volunteerlogin);
        /*mDrawerlayout3=(DrawerLayout) findViewById(R.id.activity_volunteerlogin);
        mToggle3=new ActionBarDrawerToggle(this,mDrawerlayout3,R.string.Open,R.string.Close);
        mDrawerlayout3.addDrawerListener(mToggle3);
        mToggle3.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        b3= (Button) findViewById(R.id.volunteersignup);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startvsignup();
            }
        });
        b2=(Button)findViewById(R.id.volunteerlogin);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startvlogin();
            }
        });
        voluEmail=(EditText)findViewById(R.id.volunteerUsername);
        voluPass=(EditText)findViewById(R.id.volunteerPassword);


        //HIDING ENTERED PASSWORD INTO DOTS
        cb2= findViewById(R.id.volunteerShowPassword);
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    voluPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    voluPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });



        //TO check weather user is logged in or not
         FirebaseUser user1 =firebaseAuth.getCurrentUser();
        if(user1 != null)
        {
            finish();
            startActivity(new Intent(this,volunteerdashboard.class));
        }


        //FORGOT PASSWORD
        tv2 = (TextView)findViewById(R.id.volunteerForgotPassword);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              forgotttt();
            }
        });


    }


    public void forgotttt()
    {
        Intent I5= new Intent(this,forgotPassowrd.class);
        startActivity(I5);
    }

    public void startvsignup(){
        Intent I3= new Intent(this,signuppage.class);
        startActivity(I3);
    }

    public void startvlogin(){
        if(validate())
        {
            String vemailAddress= voluEmail.getText().toString().trim();
            String vpassword= voluPass.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(vemailAddress,vpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(volunteerlogin.this,"Login Successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(volunteerlogin.this,volunteerdashboard.class));
                    }
                    else  {
                        Toast.makeText(volunteerlogin.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

/*    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.volunteeraboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_volunteerlogin, new aboutus()).commit();
                break;
            case R.id.volunteercontactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_volunteerlogin, new contactus()).commit();
                break;
        }
        mToggle3.onOptionsItemSelected(item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle3.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    private Boolean validate(){
        Boolean result = false;

        String pass=voluPass.getText().toString();
        String mail=voluEmail.getText().toString();

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

}
