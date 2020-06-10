package com.example.wehelp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class blindlogin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DrawerLayout mDrawerlayout2;
    private ActionBarDrawerToggle mToggle2;
    private Button b3,b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
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
      b3=(Button)findViewById(R.id.blindlogin);
      b3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              startblogin();
          }
      });
    }
    public void startbsignup(){
        Intent I1= new Intent(this,signuppage.class);
        startActivity(I1);
    }
    public void startblogin(){
        Intent I2=new Intent(this,blindDashboard.class);
        startActivity(I2);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.volunteeraboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blindlogin, new aboutus()).commit();
                break;
            case R.id.volunteercontactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blindlogin, new contactus()).commit();
                break;
        }
        mToggle2.onOptionsItemSelected(item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle2.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
