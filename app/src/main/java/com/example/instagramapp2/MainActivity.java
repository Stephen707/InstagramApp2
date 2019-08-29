package com.example.instagramapp2;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.instagramapp2.fragments.Home_Fragment;
import com.example.instagramapp2.fragments.Person_Fragment;
import com.example.instagramapp2.fragments.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation_bottom);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.home:
                        fragment = new Home_Fragment();
                        break;
                    case R.id.compose:
                        Intent intent = new Intent(MainActivity.this,ParsePhoto.class);
                        startActivity(intent);
                        finish();
                    case R.id.personne:
                        fragment = new Profile_Fragment();
                        break;
                    case R.id.me:
                        fragment = new Person_Fragment();
                        break;
                }
            fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}
