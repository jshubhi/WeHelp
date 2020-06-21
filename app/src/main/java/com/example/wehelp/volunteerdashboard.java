package com.example.wehelp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class volunteerdashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerlayout1;
    private ActionBarDrawerToggle mToggle1;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView v_latTextView, v_lonTextView;
    Button learn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteerdashboard);
        mDrawerlayout1=findViewById(R.id.activity_volunteerdashboard);
        mToggle1=new ActionBarDrawerToggle(this,mDrawerlayout1,R.string.Open,R.string.Close);
        mDrawerlayout1.addDrawerListener(mToggle1);
        mToggle1.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.volunteerhamburgermenu);
        navigationView.setNavigationItemSelectedListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        v_latTextView = findViewById(R.id.v_latTextView);
        v_lonTextView = findViewById(R.id.v_lonTextView);
        learn=findViewById(R.id.volunteerdashanswercall);
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openanswercall();
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        //getting name in vounteer dashboard
        final TextView profusername = (TextView) findViewById(R.id.volunteerdashname);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profusername.setText(userProfile.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(volunteerdashboard.this, "Database Error", Toast.LENGTH_SHORT).show();
                //When class is extending fragment it is necessary to use getActivity() since fragment is a subclass of activity.

            }
        });
        //END of getting name in vounteer dashboard
    }

    @Override
    public void onBackPressed() {
        if(mDrawerlayout1.isDrawerOpen(GravityCompat.START)){
            mDrawerlayout1.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void openanswercall(){
        Intent intentv = new Intent(volunteerdashboard.this, Answercall.class);
        startActivity(intentv);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    v_latTextView.setText(location.getLatitude()+"");
                                    v_lonTextView.setText(location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            v_latTextView.setText(mLastLocation.getLatitude()+"");
            v_lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

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
                firebaseAuth.signOut();
                finish();
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
