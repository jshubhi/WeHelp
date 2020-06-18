package com.example.wehelp;

import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class blindDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_dashboard);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.activity_blind_dashboard);
        NavigationView navigationView = findViewById(R.id.blindhamburgermenu);
        navigationView.setNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.Open, R.string.Close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button btn = (Button) findViewById(R.id.bdashSpecializedHelp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdashspecializedhelp();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();


    }
    public void bdashspecializedhelp()
    {
        Intent intent=new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:7777777777"));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.blindaboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blind_dashboard, new aboutus()).addToBackStack(null).commit();
                break;
            case R.id.blindcontactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blind_dashboard, new contactus()).addToBackStack(null).commit();
                break;
            case R.id.blindverifyme:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blind_dashboard, new verifyme()).addToBackStack(null).commit();
                break;
            case R.id.blindmyprofile:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_blind_dashboard, new myprofile()).addToBackStack(null).commit();
                break;
            case R.id.blindlogout:

               firebaseAuth.signOut();
               finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }
        mDrawerlayout.closeDrawer(GravityCompat.START);
        mToggle.onOptionsItemSelected(item);
        return true;
    }


/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerlayout.closeDrawer(GravityCompat.START);
            }
            else {
                mDrawerlayout.openDrawer(GravityCompat.START);
            }
        }
        return false;
    }


}
