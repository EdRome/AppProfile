package com.profile.profileapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView birthday;
    private int mYear;
    private int mMonth;
    private int mDay;

//    private Map<Integer, String> monthTranslation = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        birthday = findViewById(R.id.profile_birthday_textview);


/*      To perform month translation, use this section
        monthTranslation.put(0, "Enero");
        monthTranslation.put(1, "Febrero");
        monthTranslation.put(2, "Marzo");
        monthTranslation.put(3, "Abril");
        monthTranslation.put(4, "Mayo");
        monthTranslation.put(5, "Junio");
        monthTranslation.put(6, "Julio");
        monthTranslation.put(7, "Agosto");
        monthTranslation.put(8, "Septiembre");
        monthTranslation.put(9, "Octubre");
        monthTranslation.put(10, "Noviembre");
        monthTranslation.put(11, "Diciembre");
*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void pickDateOnClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String sDay = String.valueOf(day);
                String sMonth = getMonth(month);
                String sYear = String.valueOf(year);

                sDay = addLeftZero(sDay);

                birthday.setText(sMonth + " " + sDay + ", " + sYear);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private String getMonth(int month){
        return new DateFormatSymbols().getMonths()[month];
//        return monthTranslation.get(month); Translation
    }

    private String addLeftZero(String s) {
        String newString = (s.length() < 2) ? "0"+s : s;
        return newString;
    }
}
