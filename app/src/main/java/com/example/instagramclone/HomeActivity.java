package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.instagramclone.Fragment.AddFragment;
import com.example.instagramclone.Fragment.FavFragment;
import com.example.instagramclone.Fragment.HomeFragment;
import com.example.instagramclone.Fragment.ProfileFragment;
import com.example.instagramclone.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView btm_nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new HomeFragment()).commit();

        btm_nav  = findViewById(R.id.bottom_navbar);
        btm_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;
                switch (item.getItemId()) {
                    case R.id.home: temp= new HomeFragment();
                        break;
                    case R.id.serach: temp= new SearchFragment();
                        break;
                    case R.id.add:
                        temp= null;
                        startActivity(new Intent(HomeActivity.this, PostActivity.class));

                        break;
                    case R.id.fav: temp= new FavFragment();
                        break;
                    case R.id.profile:
                        SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                        editor.putString("proflieid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();
                        temp= new ProfileFragment();
                        break;
                }
                if(temp!= null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,temp).commit();

                }
                return true;
            }
        });
    }


}