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

public class volunteerlogin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout3;
    private ActionBarDrawerToggle mToggle3;
    private Button b3,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
    public void startvsignup(){
        Intent I3= new Intent(this,signuppage.class);
        startActivity(I3);
    }

    public void startvlogin(){
        Intent I4= new Intent(this,volunteerdashboard.class);
        startActivity(I4);
    }

    @Override
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
    }

}
