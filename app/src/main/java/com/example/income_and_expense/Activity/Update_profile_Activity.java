package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.income_and_expense.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class Update_profile_Activity extends AppCompatActivity {

    private String userEmail;
    TextView text_toolbar;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;


    //Toolbar
    ImageView back_icon_toolbar;

    // Profile Image
    ImageView show_update_img_profile;
    ImageView add_update_img_profile;

    // User Name
    TextView u_name_update_profile;
    EditText update_username_profile;

    // User Email

    TextView u_email_update_profile;
    EditText update_email_profile;

    // User Pasword

    TextView u_password_update_profile;
    EditText update_password_profile;

    // User Confirm Pasword

    TextView u_con_password_update_profile;
    EditText update_con_password_profile;

    // Update Profile BUTTON

    Button update_profile_btn;

    //                          Dark and Light Mode

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";
    SharedPreferences sharedPreferences;
    boolean isDarkMode;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";
    private SQLiteDatabase database;

    private SQLiteDatabase CreateDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dark-Light Mode
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Language default
        setLocal(getSavedLanguage());

        setContentView(R.layout.activity_update_profile);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        database = CreateDatabase();

        // Toolbar
        toolbar();

        //Initialize views
        initializeViews();

        //Fetch and Select Data
        fetchData();

        // Update Button
        updateButton();

        // add Button click
        addButtonClick();


        //Update Text
        updateText();


    }

    // Toolbar
    private void toolbar() {

        back_icon_toolbar = findViewById(R.id.lefticon_toolbar);
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText(R.string.update_profile_title);

        back_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom behavior on back button press
                Intent intent = new Intent(Update_profile_Activity.this, Profile_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);

            }
        });


    }

    //Initialize views
    private void initializeViews() {

        // profile image
        show_update_img_profile = findViewById(R.id.show_update_img_profile);
        add_update_img_profile = findViewById(R.id.add_update_img_profile);

        // Username

        u_name_update_profile = findViewById(R.id.u_name_update_profile);
        update_username_profile = findViewById(R.id.update_username_profile);

        // Email
        u_email_update_profile = findViewById(R.id.u_email_update_profile);
        update_email_profile = findViewById(R.id.update_email_profile);

        // Password

        u_password_update_profile = findViewById(R.id.u_password_update_profile);
        update_password_profile = findViewById(R.id.update_password_profile);

        // Confirm Password

        u_con_password_update_profile = findViewById(R.id.u_con_password_update_profile);
        update_con_password_profile = findViewById(R.id.update_con_password_profile);

        // Update Profile Button
        update_profile_btn = findViewById(R.id.update_profile_btn);


    }

    //Update Button
    private void updateButton() {
        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Update Data
                updateData();

            }
        });
    }


    //Fetch Data
    @SuppressLint("Range")
    private void fetchData() {

        String query = "SELECT * FROM loginandregister_table WHERE email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            byte[] imageBytes = cursor.getBlob(4);
            // Convert byte array to Bitmap
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            show_update_img_profile.setImageBitmap(imageBitmap);

            update_username_profile.setText(name);
            update_email_profile.setText(email);
            update_password_profile.setText(password);
            update_con_password_profile.setText(password);
        }
        cursor.close();
    }

    // Update Data
    @SuppressLint("Range")
    private void updateData() {
        String u_name = update_username_profile.getText().toString().trim();
        String u_email = update_email_profile.getText().toString().trim();
        String u_password = update_password_profile.getText().toString().trim();
        String u_con_password = update_con_password_profile.getText().toString().trim();

        // Convert category image to byte array
        Drawable drawable = show_update_img_profile.getDrawable();
        Bitmap imageBitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] category_image = outputStream.toByteArray();

        if (u_name.isEmpty()) {
            update_username_profile.setError("Enter Your name");
        } else if (u_name.length() < 4) {
            update_username_profile.setError("At least 4 Characters");
        } else if (!u_con_password.equals(u_password)) {
            update_password_profile.setError("Passwords do not match");
        } else {
            // Update statement for loginandregister_table
            String updateLoginTable = "UPDATE loginandregister_table SET name = ?, email = ?, password = ?, img = ? WHERE email = ?";
            SQLiteStatement loginTableStatement = database.compileStatement(updateLoginTable);
            loginTableStatement.bindString(1, u_name);
            loginTableStatement.bindString(2, u_email);
            loginTableStatement.bindString(3, u_password);
            loginTableStatement.bindBlob(4, category_image);
            loginTableStatement.bindString(5, userEmail);

            // Update statement for incexp_tbl
            String updateIncExpTable = "UPDATE incexp_tbl SET user_email = ? WHERE user_email = ?";
            SQLiteStatement incExpStatement = database.compileStatement(updateIncExpTable);
            incExpStatement.bindString(1, u_email);
            incExpStatement.bindString(2, userEmail);

            database.beginTransaction();
            try {
                loginTableStatement.executeUpdateDelete();
                incExpStatement.executeUpdateDelete();
                database.setTransactionSuccessful();

                // Update SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("SELECTED_EMAIL", u_email);
                editor.apply();

                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();

                // Update the userEmail to new email for further operations
                userEmail = u_email;

                Intent intent = new Intent(Update_profile_Activity.this, Profile_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            } finally {
                database.endTransaction();
            }
//            // Execute update
//            statement.executeUpdateDelete();
//
//            // Update SharedPreferences
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("USER_EMAIL", u_email);
//            editor.apply();
//
//            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
//
//            // Update the userEmail to new email for further operations
//            userEmail = u_email;
//
//            Intent i = new Intent(Update_profile_Activity.this, Profile_Activity.class);
//            i.putExtra("USER_EMAIL", userEmail);
//            startActivity(i);
        }
    }

    // Method to convert drawable to bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }

    // add Button click
    private void addButtonClick() {

        add_update_img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSelectionDialog();
            }
        });
    }

    private void showImageSelectionDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Camera option selected
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                        } else if (which == 1) {
                            // Gallery option selected
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                show_update_img_profile.setImageBitmap(photo);
//                // Save the image to SQLite database
//                saveImageToDatabase(photo);
            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    show_update_img_profile.setImageBitmap(bitmap);
//                    // Save the image to SQLite database
//                    saveImageToDatabase(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // <-------------------------------------------------------------------- Languages  ------------------------------------------------------------------------------>
    private void setLocal(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, dm);
        // Save the selected language in SharedPreferences
        saveLanguage(lang);
    }

    private void saveLanguage(String lang) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selected_language", lang);
        editor.apply();
    }

    private String getSavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("selected_language", "en"); // Default to English
    }

    // <-------------------------------------------------------------------- Update Text ------------------------------------------------------------------------------>
    private void updateText() {
        // Layout ---- 1
        u_name_update_profile.setText(R.string.username_update_profile);

        // Layout ---- 2
        u_email_update_profile.setText(R.string.email_update_profile);


        // Layout ---- 3
        u_password_update_profile.setText(R.string.password_update_profile);

        // Layout ---- 4
        u_con_password_update_profile.setText(R.string.con_password_update_profile);


        // Layout ---- 5
        update_profile_btn.setText(R.string.btn_update_profile);
    }


}