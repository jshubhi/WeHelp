package com.example.wehelp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1= (Button)findViewById(R.id.i_am_volunteer);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startvolunteerloginpage();
            }
        });
        b2=(Button)findViewById(R.id.iamaVisuallyImpaired);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startblindloginpage();
            }
        });



    }
    public void startvolunteerloginpage(){
        Intent I1 =new Intent(this, volunteerlogin.class);
        startActivity(I1);
    }
    public void startblindloginpage(){
        Intent I2 =new Intent(this, blindlogin.class);
        startActivity(I2);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


}
