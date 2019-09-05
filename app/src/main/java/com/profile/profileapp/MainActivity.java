package com.profile.profileapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.profile.profileapp.database.entity.profileModel;
import com.profile.profileapp.database.pojo.profilePOJO;
import com.profile.profileapp.utils.photo;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.util.HashMap;
//import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView birthday;
    private int mYear;
    private int mMonth;
    private int mDay;

    private CircularImageView imageview;
    private Button save;
    private EditText username;
    private RadioGroup gender_group;
    private RadioButton male;
    private RadioButton female;

    private Bitmap bitmap;
    private Date birthdate;

    private profilePOJO profilePojo;
    private profileModel user;

//    private Map<Integer, String> monthTranslation = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        birthday = findViewById(R.id.profile_birthday_dateselect);
        save = findViewById(R.id.profile_save_button);
        imageview = findViewById(R.id.profile_imageView);
        username = findViewById(R.id.profile_username_edittext);
        male = findViewById(R.id.profile_male_radiobutton);
        female = findViewById(R.id.profile_female_raidobutton);

        profilePojo = new profilePOJO(getApplication());

        user = profilePojo.getProfile(1);

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

        if (user != null) {
            username.setText(user.getUsername());
            byte[] bitmapdata = user.getPhoto();
            Glide.with(this).load(byteArray2Bitmap(bitmapdata)).into(imageview);
            switch (user.getGender()) {
                case "male":
                    male.setChecked(true);
                    female.setChecked(false);
                    break;
                case "female":
                    female.setChecked(true);
                    male.setChecked(false);
                    break;
            }
            birthday.setText(parseDate(user.getBirthday()));
        }

        requestMultiplePermissions();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] img;
                String gender;

                if (bitmap == null) {
                    img = user.getPhoto();
                } else {
                    img = convertBitmap2byte(bitmap);
                }

                if (female.isChecked()) {
                    gender = "female";
                } else {
                    gender = "male";
                }

                if (user == null) {
                    user = new profileModel(
                            username.getText().toString(),
                            gender,
                            birthdate,
                            img);

                    profilePojo.insert(user);
                } else {
                    user.setGender(gender);
                    user.setPhoto(img);
                    user.setUsername(username.getText().toString());
                    if (birthdate != null) {
                        user.setBirthday(birthdate);
                    }
                    profilePojo.update(user);
                }

            }
        });

    }

    private byte[] convertBitmap2byte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
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

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sDay + "/" + (month+1) + "/" + sYear;
                try {
                    birthdate = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                birthday.setText(sMonth + " " + sDay + ", " + sYear);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private String getMonth(int month){
        return new DateFormatSymbols().getMonths()[month];
//        return monthTranslation.get(month); Translation
    }

    private String addLeftZero(@NotNull String s) {
        String newString = (s.length() < 2) ? "0"+s : s;
        return newString;
    }

    public void showPictureDialog(View view) {
        photo.choosePhoto(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    Glide.with(this).load(bitmap).into(imageview);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

//                         check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
//                            openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private Bitmap byteArray2Bitmap(byte[] bitmapdata) {
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }

    private String parseDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        String sDay = String.valueOf(day);
        String sMonth = getMonth(month);
        String sYear = String.valueOf(year);

        sDay = addLeftZero(sDay);

        return (sMonth + " " + sDay + ", " + sYear);
    }

}
