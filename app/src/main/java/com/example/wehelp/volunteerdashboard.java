package com.example.wehelp;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

public class volunteerdashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerlayout1;
    private ActionBarDrawerToggle mToggle1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerdashboard);
        mDrawerlayout1=(DrawerLayout) findViewById(R.id.activity_volunteerdashboard);
        mToggle1=new ActionBarDrawerToggle(this,mDrawerlayout1,R.string.Open,R.string.Close);
        mDrawerlayout1.addDrawerListener(mToggle1);
        mToggle1.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.volunteerhamburgermenu);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.volunteeraboutus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_volunteerdashboard, new aboutus()).addToBackStack(null).commit();
                break;
            case R.id.volunteercontactus:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_volunteerdashboard, new contactus()).addToBackStack(null).commit();
                break;

            case R.id.volunteerlogout:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }
        mDrawerlayout1.closeDrawer(GravityCompat.START);
        mToggle1.onOptionsItemSelected(item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerlayout1.isDrawerOpen(GravityCompat.START)) {
                mDrawerlayout1.closeDrawer(GravityCompat.START);
            }
            else {
                mDrawerlayout1.openDrawer(GravityCompat.START);
            }
        }
        return false;
    }
}
