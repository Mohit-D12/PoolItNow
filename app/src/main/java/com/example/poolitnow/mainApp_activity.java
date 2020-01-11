package com.example.poolitnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class mainApp_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Fragment defaultFragment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_activity);
        defaultFragment = new CabpoolFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                defaultFragment).commit();

        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener);


        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.string_open, R.string.string_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);                  //displaying button to open navigation drawer
        actionBarDrawerToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //switching between fragments via bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    //switching fragments via bottomNavigationBar
                    Fragment selectedFragment = new CabpoolFragment();

                    switch(menuItem.getItemId()) {
                        case R.id.bottomNavigationBar:
                            selectedFragment = new CabpoolFragment();
                            break;
                        case R.id.drivers_bottomNavigationBar:
                            selectedFragment = new DriversFragment();
                            break;
                        case R.id.upcoming_bottomNavigationBar:
                            selectedFragment = new UpcomingFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;

                }
            };

    //for allowing drawer to open when we click the button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    //for switching between fragments via navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.home_navigationBar)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CabpoolFragment()).commit();
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.profile_naviagationBar)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProfileFragment()).commit();
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.about_navigationBar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AboutFragment()).commit();
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.logOut_navigationBar)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
