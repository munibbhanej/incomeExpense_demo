package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.income_and_expense.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Profile_Activity extends AppCompatActivity {


    private String userEmail;
    int[] color;
    int[] color1;


    // Toolbar
    ImageView lefticon_toolbar_profile;
    TextView text_toolbar_profile;
    CardView edit_toolbar_profile;
    CardView delete_toolbar_profile;

    // Profile Image
    ImageView show_img_profile;

    // Pie Chart
    PieChart piechart_income_profile, piechart_expense_profile;

    TextView income_text_profile, expense_text_profile;

    // Select data and show Textview

    TextView select_username_profile;
    TextView select_email_profile;
    TextView select_password_profile;

    TextView u_name_profile, u_email_profile, u_password_profile;

    ImageView yes_delete_btn_dialog, cancel_btn_dialog;

    //                          Dark and Light Mode

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    private SharedPreferences sharedPreferences;
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

        if (isDarkMode) {
            color = new int[]{Color.rgb(77, 218, 84)};
        } else {
            color = new int[]{Color.rgb(29, 90, 32)};

        }

        if (isDarkMode) {
            color1 = new int[]{Color.rgb(255, 56, 56)};
        } else {
            color1 = new int[]{Color.rgb(230, 49, 49)};

        }

        // Language default
        setLocal(getSavedLanguage());

        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");


        database = CreateDatabase();


        // Toolbar
        toolbar();

        //Initialize views
        initializeViews();

        //Dark-Light Mode
        darklightmode();

        //  INCOME Barchart show
        income_Piechart();

        //  EXPENSE Barchart show
        expense_Piechart();

        // Fetch user name
        fetchUsernameEmailPassword();

        //Update Text
        updateText();


    }

    // Toolbar
    private void toolbar() {

        lefticon_toolbar_profile = findViewById(R.id.lefticon_toolbar_profile);
        text_toolbar_profile = findViewById(R.id.text_toolbar_profile);
        edit_toolbar_profile = findViewById(R.id.edit_toolbar_profile);
        delete_toolbar_profile = findViewById(R.id.delete_toolbar_profile);

        lefticon_toolbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);

            }
        });

        edit_toolbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, Update_profile_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });

        delete_toolbar_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog  = new Dialog(Profile_Activity.this);
                dialog.setContentView(R.layout.delete_account_custom_dialog);

                 yes_delete_btn_dialog = dialog.findViewById(R.id.yes_delete_btn_dialog);
                cancel_btn_dialog = dialog.findViewById(R.id.cancel_btn_dialog);

                yes_delete_btn_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteData();
                        dialog.dismiss();

                        Intent intent = new Intent(Profile_Activity.this, Login_Activity.class);
                        intent.putExtra("USER_EMAIL", userEmail);
                        startActivity(intent);
                    }
                });

                cancel_btn_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });



                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.show();

            }
        });


    }


    //Initialize views
    private void initializeViews() {

        // profile image
        show_img_profile = findViewById(R.id.show_img_profile);

        // Pie chart
        piechart_income_profile = findViewById(R.id.piechart_income_profile);
        piechart_expense_profile = findViewById(R.id.piechart_expense_profile);

        income_text_profile = findViewById(R.id.income_text_profile);
        expense_text_profile = findViewById(R.id.expense_text_profile);


        // Select textview
        select_username_profile = findViewById(R.id.select_username_profile);
        select_email_profile = findViewById(R.id.select_email_profile);
        select_password_profile = findViewById(R.id.select_password_profile);

        u_name_profile = findViewById(R.id.u_name_profile);
        u_email_profile = findViewById(R.id.u_email_profile);
        u_password_profile = findViewById(R.id.u_password_profile);

    }

    //Dark-Light Mode
    private void darklightmode() {

        if (isDarkMode) {
            income_text_profile.setTextColor(getResources().getColor(R.color.income2));
            expense_text_profile.setTextColor(getResources().getColor(R.color.expense2));//Black
        } else {
            income_text_profile.setTextColor(getResources().getColor(R.color.income1));
            expense_text_profile.setTextColor(getResources().getColor(R.color.expense1));//Black
        }

    }

    //  INCOME Barchart show
    @SuppressLint("Range")
    private void income_Piechart() {

        try {
            String query2 = "SELECT category, SUM(income) AS totalincome FROM incexp_tbl WHERE user_email = ? GROUP BY category";
            Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});

            int totalincome = 0;
            if (cursor2.moveToFirst()) {
                do {
                    double categoryincome = cursor2.getDouble(cursor2.getColumnIndex("totalincome"));
                    totalincome += categoryincome;
                } while (cursor2.moveToNext());
            }
            int all_total = totalincome;

            List<PieEntry> entries = new ArrayList<>();
            if (cursor2.moveToFirst()) {
                do {
                    String category = cursor2.getString(cursor2.getColumnIndex("category"));
                    double categoryincome = cursor2.getDouble(cursor2.getColumnIndex("totalincome"));
                    if (categoryincome != 0) {
                        entries.add(new PieEntry((float) categoryincome, category));
                    }
                } while (cursor2.moveToNext());
            }

            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(color);
            PieData data = new PieData(dataSet);

            piechart_income_profile.setData(data);

            // Set data to PieChart
            piechart_income_profile.setData(data);

            // Customize value and label colors
            dataSet.setValueTextColor(Color.TRANSPARENT); // Hide value text
            dataSet.setValueTextSize(0f); // Hide value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

            // Hide category labels
            piechart_income_profile.setEntryLabelColor(Color.TRANSPARENT); // Hide entry label color

            piechart_income_profile.getLegend().setEnabled(false);
            piechart_income_profile.getDescription().setEnabled(false);

            piechart_income_profile.setDrawHoleEnabled(true); // Enable the hole in the center
            piechart_income_profile.setHoleRadius(65f); // Set the radius of the hole
            piechart_income_profile.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
            piechart_income_profile.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
            piechart_income_profile.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

            String centerText = String.valueOf(all_total);
            piechart_income_profile.setCenterText(centerText); // Set the text in the center of the hole

            if (isDarkMode) {
                piechart_income_profile.setCenterTextColor(getColor(R.color.income2));
            } else {
                piechart_income_profile.setCenterTextColor(getColor(R.color.income1));
            }

            piechart_income_profile.setCenterTextSize(15f); // Set the size of the center text
            piechart_income_profile.animateXY(800, 800);

            piechart_income_profile.invalidate(); // Refresh the chart

        } catch (Exception e) {
            Toast.makeText(Profile_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //  EXPENSE Barchart show
    @SuppressLint("Range")
    private void expense_Piechart() {

        try {
            String query2 = "SELECT category, SUM(expense) AS totalincome FROM incexp_tbl WHERE user_email = ? GROUP BY category";
            Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});

            int totalincome = 0;
            if (cursor2.moveToFirst()) {
                do {
                    double categoryincome = cursor2.getDouble(cursor2.getColumnIndex("totalincome"));
                    totalincome += categoryincome;
                } while (cursor2.moveToNext());
            }
            int all_total = totalincome;

            List<PieEntry> entries = new ArrayList<>();
            if (cursor2.moveToFirst()) {
                do {
                    String category = cursor2.getString(cursor2.getColumnIndex("category"));
                    double categoryincome = cursor2.getDouble(cursor2.getColumnIndex("totalincome"));
                    if (categoryincome != 0) {
                        entries.add(new PieEntry((float) categoryincome, category));
                    }
                } while (cursor2.moveToNext());
            }

            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(color1);
            PieData data = new PieData(dataSet);

            piechart_expense_profile.setData(data);

            // Set data to PieChart
            piechart_expense_profile.setData(data);

            // Customize value and label colors
            dataSet.setValueTextColor(Color.TRANSPARENT); // Hide value text
            dataSet.setValueTextSize(0f); // Hide value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

            // Hide category labels
            piechart_expense_profile.setEntryLabelColor(Color.TRANSPARENT); // Hide entry label color

            piechart_expense_profile.getLegend().setEnabled(false);
            piechart_expense_profile.getDescription().setEnabled(false);

            piechart_expense_profile.setDrawHoleEnabled(true); // Enable the hole in the center
            piechart_expense_profile.setHoleRadius(65f); // Set the radius of the hole
            piechart_expense_profile.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
            piechart_expense_profile.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
            piechart_expense_profile.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

            String centerText = String.valueOf(all_total);
            piechart_expense_profile.setCenterText(centerText); // Set the text in the center of the hole

            if (isDarkMode) {
                piechart_expense_profile.setCenterTextColor(getColor(R.color.expense2));
            } else {
                piechart_expense_profile.setCenterTextColor(getColor(R.color.expense1));
            }

            piechart_expense_profile.setCenterTextSize(15f); // Set the size of the center text
            piechart_expense_profile.animateXY(800, 800);

            piechart_expense_profile.invalidate(); // Refresh the chart


        } catch (Exception e) {
            Toast.makeText(Profile_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Fetch user name,email,password
    @SuppressLint("Range")
    private void fetchUsernameEmailPassword() {
        try {
            String query = "SELECT * FROM loginandregister_table WHERE email = ?";
            Cursor cursor = database.rawQuery(query, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                byte[] imageBytes = cursor.getBlob(4);
                // Convert byte array to Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                show_img_profile.setImageBitmap(imageBitmap);
                select_username_profile.setText(name);
                select_email_profile.setText(email);

                // Hide the first five characters
                String modifiedText = hideFirstFiveCharacters(password);
                select_password_profile.setText(modifiedText);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(Profile_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Delete all data login email
    private void deleteData() {
        String delete = "DELETE FROM incexp_tbl WHERE user_email=?";
        database.execSQL(delete, new String[]{userEmail});

        String user_data_delete = "DELETE FROM loginandregister_table WHERE email=?";
        database.execSQL(user_data_delete, new String[]{userEmail});
    }


    private String hideFirstFiveCharacters(String text) {
        if (text.length() <= 5) {
            // If the text is 5 characters or less, return a string of asterisks of the same length
            return new String(new char[text.length()]).replace("\0", "*");
        } else {
            // Otherwise, replace the first 5 characters with asterisks and keep the rest
            return "*****" + text.substring(5);
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
        income_text_profile.setText(R.string.total_income_profile);

        // Layout ---- 2
        expense_text_profile.setText(R.string.total_expense_profile);


        // Layout ---- 3
        u_name_profile.setText(R.string.username_profile);

        // Layout ---- 4
        u_email_profile.setText(R.string.email_profile);


        // Layout ---- 5
        u_password_profile.setText(R.string.password_profile);
    }

    @Override
    public void onBackPressed() {
        // Close the login activity
        super.onBackPressed();
        Intent intent = new Intent(Profile_Activity.this, Home_Activity.class);
        intent.putExtra("USER_EMAIL", userEmail);
        startActivity(intent);
    }

}