package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.income_and_expense.Adapter.Viewpager_Adapter;
import com.example.income_and_expense.Fragment.ExpenseFragment;
import com.example.income_and_expense.Fragment.IncomeFragment;
import com.example.income_and_expense.Fragment.SummaryFragment;
import com.example.income_and_expense.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Locale;

public class Category_Chart_Activity extends AppCompatActivity {

    TabLayout tab;
    ViewPager2 viewPager;
    String userEmail;

    TextView text_toolbar;
    ImageView back_icon_toolbar;

    Viewpager_Adapter viewpager_adapter;


    //                          Dark and Light Mode

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    private SharedPreferences sharedPreferences;
    boolean isDarkMode;

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";
    SQLiteDatabase database;

    private SQLiteDatabase CreateDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Language default
        setLocal(getSavedLanguage());

        setContentView(R.layout.activity_category_chart);



        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        database = CreateDatabase();



        // Toolbar
        toolbar();

        // Initialize views
        initializeViews();

        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewpager_adapter = new Viewpager_Adapter(this,userEmail);
        viewPager.setAdapter(viewpager_adapter);



        // Dark/ Light set textview
        darkLightSet();

        // Viewpager_Adapter Method
        viewpager_method();

    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    // Toolbar
    @SuppressLint("SetTextI18n")
    private void toolbar() {
        back_icon_toolbar = findViewById(R.id.lefticon_toolbar);
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText(R.string.payment_method_title);

        back_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom behavior on back button press
                Intent intent = new Intent(Category_Chart_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);

            }
        });
    }

    // Initialize views
    private void initializeViews() {
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
    }

    // Dark/ Light set textview
    private void darkLightSet() {

        if (isDarkMode){
            tab.setTabTextColors(Color.WHITE, Color.rgb(2, 136, 209));
            tab.setSelectedTabIndicatorColor(Color.rgb(2, 136, 209));
        }
        else {
            tab.setTabTextColors(Color.BLACK, Color.rgb(2, 136, 209));
            tab.setSelectedTabIndicatorColor(Color.rgb(2, 136, 209));
        }

    }

    // Viewpager_Adapter Method
    private  void  viewpager_method(){

        new TabLayoutMediator(tab, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.tab_summary_lay1_cat_chart);
                        break;
                    case 1:
                        tab.setText(R.string.tab_expense_lay1_cat_chart);
                        break;
                    case 2:
                        tab.setText(R.string.tab_income_lay1_cat_chart);
                        break;
                }
            }
        }).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + position);

                // Summary Fragment
                if (currentFragment instanceof SummaryFragment) {
                    ((SummaryFragment) currentFragment).setUserVisibleHint(true);
                } else {
                    SummaryFragment summaryFragment = (SummaryFragment) getSupportFragmentManager().findFragmentByTag("f0");
                    if (summaryFragment != null) {
                        summaryFragment.setUserVisibleHint(false);
                    }
                }

                // Expense Fragment
                if (currentFragment instanceof ExpenseFragment) {
                    ((ExpenseFragment) currentFragment).setUserVisibleHint(true);
                } else {
                    ExpenseFragment expenseFragment = (ExpenseFragment) getSupportFragmentManager().findFragmentByTag("f1");
                    if (expenseFragment != null) {
                        expenseFragment.setUserVisibleHint(false);
                    }
                }

                // Income Fragment
                if (currentFragment instanceof IncomeFragment) {
                    ((IncomeFragment) currentFragment).setUserVisibleHint(true);
                } else {
                    IncomeFragment incomeFragment = (IncomeFragment) getSupportFragmentManager().findFragmentByTag("f2");
                    if (incomeFragment != null) {
                        incomeFragment.setUserVisibleHint(false);
                    }
                }

            }


        });

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


    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

}