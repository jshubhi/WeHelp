package com.example.wehelp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

public class blindsettings extends Fragment {

    public ToggleButton tg;
    TextView tv1;
    public static final String ex="Toogle";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        final View inflate = inflater.inflate(R.layout.blindsettings, container, false);
        //SHAReD PREFERENCE FOR STORING THE VALUE OF THE BUTTON
        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        tg= inflate.findViewById(R.id.toggleButton);
        tg.setChecked(preferences.getBoolean("value",false));
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tg.isChecked()){
                    SharedPreferences.Editor editor=getActivity().getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    tg.setChecked(true);
                    validation();
                }
                else
                {
                    SharedPreferences.Editor editor=getActivity().getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    tg.setChecked(false);
                }
            }
        });
        tv1 = (TextView)inflate.findViewById(R.id.enablefingerprint);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });
//  bottomNavigationView.setSelectedItemId(R.id.nav_home);
        BottomNavigationView bottomNavigationView=inflate.findViewById(R.id.bdashbottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(getActivity().getApplicationContext(),blindDashboard.class));
                        getActivity().overridePendingTransition(0,0);
                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_blind_dashboard, new blindsettings()).addToBackStack(null).commit();
                        //break;
                    case R.id.nav_settings:
                        return true;
                }
                return false;
            }
        });

        return inflate;
    }
    public boolean validation()
    {
        return true;
    }
}

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blindsettings);
          final View inflate = inflater.inflate(R.layout.blindsettings, container, false);
        FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final DatabaseReference fingerprint = firebaseDatabase.getReference(firebaseAuth.getUid());
        cb4 =inflate.findViewById(R.id.bsettingsfingerprint);
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    validation();
                }
            }
        });

    }
    public boolean validation()
    {
        return true;
    }

}*/