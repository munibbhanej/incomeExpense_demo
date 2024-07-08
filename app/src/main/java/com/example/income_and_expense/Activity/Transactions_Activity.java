package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;


import com.example.income_and_expense.Adapter.Incomeexpense_Adapter;
import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Transactions_Activity extends AppCompatActivity {

    private String userEmail;

    private Calendar currentMonth;
    private Calendar calendar;

    List<Income_Expense_Model> list;

    private Cursor cursor;

    ImageView lefticon_toolbar;
    TextView text_toolbar;


    // Dark and Light Mode
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    private SharedPreferences sharedPreferences;


    boolean isDarkMode;


    // ----- All ------
    ListView Transaction_all_list;
    TextView income_all_transaction, expense_all_transaction, balance_all_transaction;
    TextView income_all_transaction_txt, expense_all_transaction_txt, balance_all_transaction_txt;
    Button transaction_all_btn, transaction_all_1_btn;
    RelativeLayout all_page;
    Button income_all_btn, expense_all_btn;
    int all_balance;

    CardView card_all_trans;

    TextView date_trans_all, category_trans_all, inc_trans_all, exp_trans_all, textView_all;

    ImageView transaction_all_no_data_img;


    // ----- Today ------
    ListView Transaction_today_list;
    TextView income_today_transaction, expense_today_transaction, balance_today_transaction, previousbalance_today_transaction, totalbalance_today_transaction;
    TextView income_today_transaction_txt, expense_today_transaction_txt, balance_today_transaction_txt, previousbalance_today_transaction_txt, totalbalance_today_transaction_txt;
    Button transaction_today_btn, transaction_today_1_btn;
    RelativeLayout today_page;
    Button income_today_btn, expense_today_btn;

    CardView card_today_trans;


    TextView date_trans_today, category_trans_today, inc_trans_today, exp_trans_today, textView_today;

    ImageView transaction_today_no_data_img;



    int today_balance;
    int today_pre_bal1;
    int today_pre_bal2;
    int today_pre_bal;
    int today_total;


    // ----- Weekly ------
    ListView Transaction_weekly_list;
    TextView income_weekly_transaction, expense_weekly_transaction, balance_weekly_transaction, previousbalance_weekly_transaction, totalbalance_weekly_transaction;
    TextView income_weekly_transaction_txt, expense_weekly_transaction_txt, balance_weekly_transaction_txt, previousbalance_weekly_transaction_txt, totalbalance_weekly_transaction_txt;
    Button transaction_weekly_btn, transaction_weekly_1_btn;
    RelativeLayout weekly_page;
    Button income_weekly_btn, expense_weekly_btn;

    CardView card_weekly_trans;

    TextView date_trans_weekly, category_trans_weekly, inc_trans_weekly, exp_trans_weekly, textView_weekly;

    RelativeLayout tab_weekly_lessthan, tab_weekly_Greaterthan;

    ImageView less_transaction_weekly, greater_transaction_weekly;

    ImageView transaction_weekly_no_data_img;



    int weekly_balance;
    int weekly_pre_bal1;
    int weekly_pre_bal2;
    int weekly_pre_bal;
    int weekly_total;


    // ----- Monthly ------
    ListView Transaction_monthly_list;
    TextView income_monthly_transaction, expense_monthly_transaction, balance_monthly_transaction, previousbalance_monthly_transaction, totalbalance_monthly_transaction;
    TextView income_monthly_transaction_txt, expense_monthly_transaction_txt, balance_monthly_transaction_txt, previousbalance_monthly_transaction_txt, totalbalance_monthly_transaction_txt;
    Button transaction_monthly_btn, transaction_monthly_1_btn;
    RelativeLayout monthly_page;
    Button income_monthly_btn, expense_monthly_btn;

    CardView card_monthly_trans;

    TextView date_trans_monthly, category_trans_monthly, inc_trans_monthly, exp_trans_monthly, textView_monthly;

    RelativeLayout tab_monthly_lessthan, tab_monthly_Greaterthan;

    ImageView less_transaction_monthly, greater_transaction_monthly;

    ImageView transaction_monthly_no_data_img;


    int monthly_balance;
    int monthly_pre_bal1;
    int monthly_pre_bal2;
    int monthly_pre_bal;
    int monthly_total;


    // ----- Yearly ------
    ListView Transaction_yearly_list;
    TextView income_yearly_transaction, expense_yearly_transaction, balance_yearly_transaction, previousbalance_yearly_transaction, totalbalance_yearly_transaction;
    TextView income_yearly_transaction_txt, expense_yearly_transaction_txt, balance_yearly_transaction_txt, previousbalance_yearly_transaction_txt, totalbalance_yearly_transaction_txt;
    Button transaction_yearly_btn, transaction_yearly_1_btn;
    RelativeLayout yearly_page;
    Button income_yearly_btn, expense_yearly_btn;

    CardView card_yearly_trans;

    TextView date_trans_yearly, category_trans_yearly, inc_trans_yearly, exp_trans_yearly, textView_yearly;

    RelativeLayout tab_yearly_lessthan, tab_yearly_Greaterthan;

    ImageView less_transaction_yearly, greater_transaction_yearly;

    ImageView transaction_yearly_no_data_img;



    int yearly_balance;
    int yearly_pre_bal1;
    int yearly_pre_bal2;
    int yearly_pre_bal;
    int yearly_total;


    Button income_btn_data_show_all, expense_btn_data_show_all, date_ascending_btn_data_show_all, date_descending_btn_data_show_all;
    Button income_btn_data_show_today, expense_btn_data_show_today, date_ascending_btn_data_show_today, date_descending_btn_data_show_today;
    Button income_btn_data_show_weekly, expense_btn_data_show_weekly, date_ascending_btn_data_show_weekly, date_descending_btn_data_show_weekly;


    Button income_btn_data_show_monthly, expense_btn_data_show_monthly, date_ascending_btn_data_show_monthly, date_descending_btn_data_show_monthly;


    Button income_btn_data_show_yearly, expense_btn_data_show_yearly, date_ascending_btn_data_show_yearly, date_descending_btn_data_show_yearly;

    Incomeexpense_Adapter adapter1;

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";
    private SQLiteDatabase database;

    private SQLiteDatabase CreateDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

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

        setContentView(R.layout.activity_transactions);


        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        currentMonth = Calendar.getInstance();
        calendar = Calendar.getInstance();

        database = CreateDatabase();

        // Toolbar
        toolbar();

        //Initialize views
        initializeViews();

        // Dark/ Light set textview
        darkLightSet();

        // All Data
        allDataShow();

        // Today Data
        todayDataShow();

        // Weekly Data
        weeklyDataShow();

        // Monthly Data
        monthlyDataShow();

        // Yearly Data
        yearlyDataShow();

        //Update Text
        updateText();

    }

    //   <- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ->
    // Toolbar
    @SuppressLint("SetTextI18n")
    private void toolbar() {
        lefticon_toolbar = findViewById(R.id.lefticon_toolbar);
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText(R.string.transaction_title);

        lefticon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom behavior on back button press
                Intent intent = new Intent(Transactions_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }

    // Initialize views
    private void initializeViews() {
        //--------   All   -----
        transaction_all_btn = findViewById(R.id.transaction_all_btn);
        transaction_all_1_btn = findViewById(R.id.transaction_all_1_btn);
        all_page = findViewById(R.id.all_page);
        income_all_btn = findViewById(R.id.income_all_btn);
        expense_all_btn = findViewById(R.id.expense_all_btn);
        card_all_trans = findViewById(R.id.card_all_trans);


        date_trans_all = findViewById(R.id.date_trans_all);
        category_trans_all = findViewById(R.id.category_trans_all);
        inc_trans_all = findViewById(R.id.inc_trans_all);
        exp_trans_all = findViewById(R.id.exp_trans_all);

        textView_all = findViewById(R.id.textView_all);


        //--------   Today   -----
        transaction_today_btn = findViewById(R.id.transaction_today_btn);
        transaction_today_1_btn = findViewById(R.id.transaction_today_1_btn);
        today_page = findViewById(R.id.today_page);
        income_today_btn = findViewById(R.id.income_today_btn);
        expense_today_btn = findViewById(R.id.expense_today_btn);
        card_today_trans = findViewById(R.id.card_today_trans);


        date_trans_today = findViewById(R.id.date_trans_today);
        category_trans_today = findViewById(R.id.category_trans_today);
        inc_trans_today = findViewById(R.id.inc_trans_today);
        exp_trans_today = findViewById(R.id.exp_trans_today);

        textView_today = findViewById(R.id.textView_today);


        //--------   Weekly   -----
        transaction_weekly_btn = findViewById(R.id.transaction_weekly_btn);
        transaction_weekly_1_btn = findViewById(R.id.transaction_weekly_1_btn);
        weekly_page = findViewById(R.id.weekly_page);
        income_weekly_btn = findViewById(R.id.income_weekly_btn);
        expense_weekly_btn = findViewById(R.id.expense_weekly_btn);
        card_weekly_trans = findViewById(R.id.card_weekly_trans);

        date_trans_weekly = findViewById(R.id.date_trans_weekly);
        category_trans_weekly = findViewById(R.id.category_trans_weekly);
        inc_trans_weekly = findViewById(R.id.inc_trans_weekly);
        exp_trans_weekly = findViewById(R.id.exp_trans_weekly);

        textView_weekly = findViewById(R.id.textView_weekly);

        tab_weekly_lessthan = findViewById(R.id.tab_weekly_lessthan);
        tab_weekly_Greaterthan = findViewById(R.id.tab_weekly_Greaterthan);

        less_transaction_weekly = findViewById(R.id.less_transaction_weekly);
        greater_transaction_weekly = findViewById(R.id.greater_transaction_weekly);


        //--------   Monthly   -----
        transaction_monthly_btn = findViewById(R.id.transaction_monthly_btn);
        transaction_monthly_1_btn = findViewById(R.id.transaction_monthly_1_btn);
        monthly_page = findViewById(R.id.monthly_page);
        income_monthly_btn = findViewById(R.id.income_monthly_btn);
        expense_monthly_btn = findViewById(R.id.expense_monthly_btn);
        card_monthly_trans = findViewById(R.id.card_monthly_trans);

        date_trans_monthly = findViewById(R.id.date_trans_monthly);
        category_trans_monthly = findViewById(R.id.category_trans_monthly);
        inc_trans_monthly = findViewById(R.id.inc_trans_monthly);
        exp_trans_monthly = findViewById(R.id.exp_trans_monthly);

        textView_monthly = findViewById(R.id.textView_monthly);

        tab_monthly_lessthan = findViewById(R.id.tab_monthly_lessthan);
        tab_monthly_Greaterthan = findViewById(R.id.tab_monthly_Greaterthan);

        less_transaction_monthly = findViewById(R.id.less_transaction_monthly);
        greater_transaction_monthly = findViewById(R.id.greater_transaction_monthly);


        //--------   Yearly   -----
        transaction_yearly_btn = findViewById(R.id.transaction_yearly_btn);
        transaction_yearly_1_btn = findViewById(R.id.transaction_yearly_1_btn);
        yearly_page = findViewById(R.id.yearly_page);
        income_yearly_btn = findViewById(R.id.income_yearly_btn);
        expense_yearly_btn = findViewById(R.id.expense_yearly_btn);
        card_yearly_trans = findViewById(R.id.card_yearly_trans);

        date_trans_yearly = findViewById(R.id.date_trans_yearly);
        category_trans_yearly = findViewById(R.id.category_trans_yearly);
        inc_trans_yearly = findViewById(R.id.inc_trans_yearly);
        exp_trans_yearly = findViewById(R.id.exp_trans_yearly);

        textView_yearly = findViewById(R.id.textView_yearly);

        tab_yearly_lessthan = findViewById(R.id.tab_yearly_lessthan);
        tab_yearly_Greaterthan = findViewById(R.id.tab_yearly_Greaterthan);

        less_transaction_yearly = findViewById(R.id.less_transaction_yearly);
        greater_transaction_yearly = findViewById(R.id.greater_transaction_yearly);


        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

//         ALL
        income_btn_data_show_all = findViewById(R.id.income_btn_data_show_all);
        expense_btn_data_show_all = findViewById(R.id.expense_btn_data_show_all);
        date_ascending_btn_data_show_all = findViewById(R.id.date_ascending_btn_data_show_all);
        date_descending_btn_data_show_all = findViewById(R.id.date_descending_btn_data_show_all);

        // TODAY
        income_btn_data_show_today = findViewById(R.id.income_btn_data_show_today);
        expense_btn_data_show_today = findViewById(R.id.expense_btn_data_show_today);
        date_ascending_btn_data_show_today = findViewById(R.id.date_ascending_btn_data_show_today);
        date_descending_btn_data_show_today = findViewById(R.id.date_descending_btn_data_show_today);

        // WEEKLY
        income_btn_data_show_weekly = findViewById(R.id.income_btn_data_show_weekly);
        expense_btn_data_show_weekly = findViewById(R.id.expense_btn_data_show_weekly);
        date_ascending_btn_data_show_weekly = findViewById(R.id.date_ascending_btn_data_show_weekly);
        date_descending_btn_data_show_weekly = findViewById(R.id.date_descending_btn_data_show_weekly);


        // MONTHLY
        income_btn_data_show_monthly = findViewById(R.id.income_btn_data_show_monthly);
        expense_btn_data_show_monthly = findViewById(R.id.expense_btn_data_show_monthly);
        date_ascending_btn_data_show_monthly = findViewById(R.id.date_ascending_btn_data_show_monthly);
        date_descending_btn_data_show_monthly = findViewById(R.id.date_descending_btn_data_show_monthly);


        // YEARLY
        income_btn_data_show_yearly = findViewById(R.id.income_btn_data_show_yearly);
        expense_btn_data_show_yearly = findViewById(R.id.expense_btn_data_show_yearly);
        date_ascending_btn_data_show_yearly = findViewById(R.id.date_ascending_btn_data_show_yearly);
        date_descending_btn_data_show_yearly = findViewById(R.id.date_descending_btn_data_show_yearly);


//        xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx


//        All var assign
        income_all_transaction = findViewById(R.id.income_all_transaction);
        expense_all_transaction = findViewById(R.id.expense_all_transaction);
        balance_all_transaction = findViewById(R.id.balance_all_transaction);

        income_all_transaction_txt = findViewById(R.id.income_all_transaction_txt);
        expense_all_transaction_txt = findViewById(R.id.expense_all_transaction_txt);
        balance_all_transaction_txt = findViewById(R.id.balance_all_transaction_txt);

        Transaction_all_list = findViewById(R.id.Transaction_all_list);
        list = new ArrayList<>();
        transaction_all_no_data_img = findViewById(R.id.transaction_all_no_data_img);


        //Today var assign
        income_today_transaction = findViewById(R.id.income_today_transaction);
        expense_today_transaction = findViewById(R.id.expense_today_transaction);
        balance_today_transaction = findViewById(R.id.balance_today_transaction);
        previousbalance_today_transaction = findViewById(R.id.previousbalance_today_transaction);
        totalbalance_today_transaction = findViewById(R.id.totalbalance_today_transaction);

        income_today_transaction_txt = findViewById(R.id.income_today_transaction_txt);
        expense_today_transaction_txt = findViewById(R.id.expense_today_transaction_txt);
        balance_today_transaction_txt = findViewById(R.id.balance_today_transaction_txt);
        previousbalance_today_transaction_txt = findViewById(R.id.previousbalance_today_transaction_txt);
        totalbalance_today_transaction_txt = findViewById(R.id.totalbalance_today_transaction_txt);


        Transaction_today_list = findViewById(R.id.Transaction_today_list);

        transaction_today_no_data_img = findViewById(R.id.transaction_today_no_data_img);


        //Weekly var assign
        income_weekly_transaction = findViewById(R.id.income_weekly_transaction);
        expense_weekly_transaction = findViewById(R.id.expense_weekly_transaction);
        balance_weekly_transaction = findViewById(R.id.balance_weekly_transaction);
        previousbalance_weekly_transaction = findViewById(R.id.previousbalance_weekly_transaction);
        totalbalance_weekly_transaction = findViewById(R.id.totalbalance_weekly_transaction);

        income_weekly_transaction_txt = findViewById(R.id.income_weekly_transaction_txt);
        expense_weekly_transaction_txt = findViewById(R.id.expense_weekly_transaction_txt);
        balance_weekly_transaction_txt = findViewById(R.id.balance_weekly_transaction_txt);
        previousbalance_weekly_transaction_txt = findViewById(R.id.previousbalance_weekly_transaction_txt);
        totalbalance_weekly_transaction_txt = findViewById(R.id.totalbalance_weekly_transaction_txt);

        Transaction_weekly_list = findViewById(R.id.Transaction_weekly_list);

        transaction_weekly_no_data_img = findViewById(R.id.transaction_weekly_no_data_img);


        //Monthly var assign
        income_monthly_transaction = findViewById(R.id.income_monthly_transaction);
        expense_monthly_transaction = findViewById(R.id.expense_monthly_transaction);
        balance_monthly_transaction = findViewById(R.id.balance_monthly_transaction);
        previousbalance_monthly_transaction = findViewById(R.id.previousbalance_monthly_transaction);
        totalbalance_monthly_transaction = findViewById(R.id.totalbalance_monthly_transaction);

        income_monthly_transaction_txt = findViewById(R.id.income_monthly_transaction_txt);
        expense_monthly_transaction_txt = findViewById(R.id.expense_monthly_transaction_txt);
        balance_monthly_transaction_txt = findViewById(R.id.balance_monthly_transaction_txt);
        previousbalance_monthly_transaction_txt = findViewById(R.id.previousbalance_monthly_transaction_txt);
        totalbalance_monthly_transaction_txt = findViewById(R.id.totalbalance_monthly_transaction_txt);

        Transaction_monthly_list = findViewById(R.id.Transaction_monthly_list);

        transaction_monthly_no_data_img = findViewById(R.id.transaction_monthly_no_data_img);


        //Yearly var assign
        income_yearly_transaction = findViewById(R.id.income_yearly_transaction);
        expense_yearly_transaction = findViewById(R.id.expense_yearly_transaction);
        balance_yearly_transaction = findViewById(R.id.balance_yearly_transaction);
        previousbalance_yearly_transaction = findViewById(R.id.previousbalance_yearly_transaction);
        totalbalance_yearly_transaction = findViewById(R.id.totalbalance_yearly_transaction);

        income_yearly_transaction_txt = findViewById(R.id.income_yearly_transaction_txt);
        expense_yearly_transaction_txt = findViewById(R.id.expense_yearly_transaction_txt);
        balance_yearly_transaction_txt = findViewById(R.id.balance_yearly_transaction_txt);
        previousbalance_yearly_transaction_txt = findViewById(R.id.previousbalance_yearly_transaction_txt);
        totalbalance_yearly_transaction_txt = findViewById(R.id.totalbalance_yearly_transaction_txt);

        Transaction_yearly_list = findViewById(R.id.Transaction_yearly_list);

        transaction_yearly_no_data_img = findViewById(R.id.transaction_yearly_no_data_img);

    }

    // Dark/ Light set textview
    private void darkLightSet() {
        //Dark and Light Mode
        if (isDarkMode) {
            // all
            inc_trans_all.setTextColor(getResources().getColor(R.color.income2));
            exp_trans_all.setTextColor(getResources().getColor(R.color.expense2));
            income_all_transaction_txt.setTextColor(getResources().getColor(R.color.income2));
            expense_all_transaction_txt.setTextColor(getResources().getColor(R.color.expense2));
            balance_all_transaction_txt.setTextColor(getResources().getColor(R.color.total2));
            income_all_transaction.setTextColor(getResources().getColor(R.color.income2));
            expense_all_transaction.setTextColor(getResources().getColor(R.color.expense2));
            // today
            inc_trans_today.setTextColor(getResources().getColor(R.color.income2));
            exp_trans_today.setTextColor(getResources().getColor(R.color.expense2));
            income_today_transaction_txt.setTextColor(getResources().getColor(R.color.income2));
            expense_today_transaction_txt.setTextColor(getResources().getColor(R.color.expense2));
            balance_today_transaction_txt.setTextColor(getResources().getColor(R.color.total2));
            income_today_transaction.setTextColor(getResources().getColor(R.color.income2));
            expense_today_transaction.setTextColor(getResources().getColor(R.color.expense2));
            // weekly
            inc_trans_weekly.setTextColor(getResources().getColor(R.color.income2));
            exp_trans_weekly.setTextColor(getResources().getColor(R.color.expense2));
            income_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.income2));
            expense_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.expense2));
            balance_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.total2));
            income_weekly_transaction.setTextColor(getResources().getColor(R.color.income2));
            expense_weekly_transaction.setTextColor(getResources().getColor(R.color.expense2));
            less_transaction_weekly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_transaction_weekly.setImageDrawable(getDrawable(R.drawable.greater_then_light));


            // monthly
            inc_trans_monthly.setTextColor(getResources().getColor(R.color.income2));
            exp_trans_monthly.setTextColor(getResources().getColor(R.color.expense2));
            income_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.income2));
            expense_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.expense2));
            balance_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.total2));
            income_monthly_transaction.setTextColor(getResources().getColor(R.color.income2));
            expense_monthly_transaction.setTextColor(getResources().getColor(R.color.expense2));
            less_transaction_monthly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_transaction_monthly.setImageDrawable(getDrawable(R.drawable.greater_then_light));

            // yearly
            inc_trans_yearly.setTextColor(getResources().getColor(R.color.income2));
            exp_trans_yearly.setTextColor(getResources().getColor(R.color.expense2));
            income_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.income2));
            expense_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.expense2));
            balance_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.total2));
            income_yearly_transaction.setTextColor(getResources().getColor(R.color.income2));
            expense_yearly_transaction.setTextColor(getResources().getColor(R.color.expense2));
            less_transaction_yearly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_transaction_yearly.setImageDrawable(getDrawable(R.drawable.greater_then_light));
        } else {
            // all
            inc_trans_all.setTextColor(getResources().getColor(R.color.income1));
            exp_trans_all.setTextColor(getResources().getColor(R.color.expense1));
            income_all_transaction_txt.setTextColor(getResources().getColor(R.color.income1));
            expense_all_transaction_txt.setTextColor(getResources().getColor(R.color.expense1));
            balance_all_transaction_txt.setTextColor(getResources().getColor(R.color.total1));
            income_all_transaction.setTextColor(getResources().getColor(R.color.income1));
            expense_all_transaction.setTextColor(getResources().getColor(R.color.expense1));
            // today
            inc_trans_today.setTextColor(getResources().getColor(R.color.income1));
            exp_trans_today.setTextColor(getResources().getColor(R.color.expense1));
            income_today_transaction_txt.setTextColor(getResources().getColor(R.color.income1));
            expense_today_transaction_txt.setTextColor(getResources().getColor(R.color.expense1));
            balance_today_transaction_txt.setTextColor(getResources().getColor(R.color.total1));
            income_today_transaction.setTextColor(getResources().getColor(R.color.income1));
            expense_today_transaction.setTextColor(getResources().getColor(R.color.expense1));
            // weekly
            inc_trans_weekly.setTextColor(getResources().getColor(R.color.income1));
            exp_trans_weekly.setTextColor(getResources().getColor(R.color.expense1));
            income_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.income1));
            expense_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.expense1));
            balance_weekly_transaction_txt.setTextColor(getResources().getColor(R.color.total1));
            income_weekly_transaction.setTextColor(getResources().getColor(R.color.income1));
            expense_weekly_transaction.setTextColor(getResources().getColor(R.color.expense1));
            less_transaction_weekly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_transaction_weekly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));
            // monthly
            inc_trans_monthly.setTextColor(getResources().getColor(R.color.income1));
            exp_trans_monthly.setTextColor(getResources().getColor(R.color.expense1));
            income_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.income1));
            expense_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.expense1));
            balance_monthly_transaction_txt.setTextColor(getResources().getColor(R.color.total1));
            income_monthly_transaction.setTextColor(getResources().getColor(R.color.income1));
            expense_monthly_transaction.setTextColor(getResources().getColor(R.color.expense1));
            less_transaction_monthly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_transaction_monthly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));
            // yearly
            inc_trans_yearly.setTextColor(getResources().getColor(R.color.income1));
            exp_trans_yearly.setTextColor(getResources().getColor(R.color.expense1));
            income_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.income1));
            expense_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.expense1));
            balance_yearly_transaction_txt.setTextColor(getResources().getColor(R.color.total1));
            income_yearly_transaction.setTextColor(getResources().getColor(R.color.income1));
            expense_yearly_transaction.setTextColor(getResources().getColor(R.color.expense1));
            less_transaction_yearly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_transaction_yearly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));

        }
    }

    // All Data
    private void allDataShow() {

        // xxxxx Default Show List Data xxxxx
        try {

            // Fetch income and expense for the ALL
            String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);

                }
                while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use


            adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
            Transaction_all_list.setAdapter(adapter1);

            checkListData();

            set_All_Data();
            adapter1.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // xxxxx tran_all_btn _1 Click Data Refresh xxxxx
        transaction_all_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                transaction_today_1_btn.setVisibility(View.VISIBLE);
                transaction_weekly_1_btn.setVisibility(View.VISIBLE);
                transaction_monthly_1_btn.setVisibility(View.VISIBLE);
                transaction_yearly_1_btn.setVisibility(View.VISIBLE);
                transaction_all_1_btn.setVisibility(View.INVISIBLE);
                //btn-1
                card_all_trans.setVisibility(View.VISIBLE);
                card_today_trans.setVisibility(View.INVISIBLE);
                card_weekly_trans.setVisibility(View.INVISIBLE);
                card_monthly_trans.setVisibility(View.INVISIBLE);
                card_yearly_trans.setVisibility(View.INVISIBLE);

                // show layout

                all_page.setVisibility(View.VISIBLE);
                today_page.setVisibility(View.GONE);
                weekly_page.setVisibility(View.GONE);
                monthly_page.setVisibility(View.GONE);
                yearly_page.setVisibility(View.GONE);

                try {
                    list.clear();

                    // Fetch income and expense for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
                    Transaction_all_list.setAdapter(adapter1);

                    checkListData();


                    set_All_Data();
                    adapter1.notifyDataSetChanged();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        // xxxxx tran_all_btn Click Data Refresh xxxxx
        transaction_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch income and expense for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListData();

                    adapter1.notifyDataSetChanged();
                    set_All_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx income_all_btn_data_show Click Show Income Data xxxxx
        income_btn_data_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    list.clear();

                    // Fetch income for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE income AND user_email = ? ORDER BY entry_date DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});


                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListData();

                    adapter1.notifyDataSetChanged();
                    income_All_Data();
                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        // xxxxx expense_all_btn_data_show Click Show Expense Data xxxxx
        expense_btn_data_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list.clear();

                    // Fetch income for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE expense AND user_email = ? ORDER BY entry_date DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListData();

                    adapter1.notifyDataSetChanged();
                    expense_All_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx ascending_all_btn_data_show Click Show Income ascendingData xxxxx
        date_ascending_btn_data_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list.clear();

                    // Fetch Ascending for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }

                    cursor.close(); // Close the cursor after use

                    checkListData();

                    adapter1.notifyDataSetChanged();
                    ascending_All_Data();
                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx descending_all_btn_data_show Click Show Income descending Data xxxxx
        date_descending_btn_data_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch Descending for the ALL
                    String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);
                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }

                    cursor.close(); // Close the cursor after use

                    checkListData();

                    adapter1.notifyDataSetChanged();
                    descending_All_Data();
                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Today Data
    private void todayDataShow() {

        // xxxxx tran_today_btn_1 Click Data set xxxxx
        transaction_today_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                transaction_all_1_btn.setVisibility(View.VISIBLE);
                transaction_today_1_btn.setVisibility(View.INVISIBLE);
                transaction_weekly_1_btn.setVisibility(View.VISIBLE);
                transaction_monthly_1_btn.setVisibility(View.VISIBLE);
                transaction_yearly_1_btn.setVisibility(View.VISIBLE);
                //btn-1
                card_all_trans.setVisibility(View.INVISIBLE);
                card_today_trans.setVisibility(View.VISIBLE);
                card_weekly_trans.setVisibility(View.INVISIBLE);
                card_monthly_trans.setVisibility(View.INVISIBLE);
                card_yearly_trans.setVisibility(View.INVISIBLE);

                // show layout
                all_page.setVisibility(View.GONE);
                today_page.setVisibility(View.VISIBLE);
                weekly_page.setVisibility(View.GONE);
                monthly_page.setVisibility(View.GONE);
                yearly_page.setVisibility(View.GONE);

                try {
                    list.clear();

                    // Fetch income and expense for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);

                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
                    Transaction_today_list.setAdapter(adapter1);

                    checkListDataToday();


                    set_Today_Data();
                    adapter1.notifyDataSetChanged();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        // xxxxx tran_today_btn Click Data Refresh xxxxx
        transaction_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    list.clear();

                    // Fetch income and expense for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);

                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
                    Transaction_today_list.setAdapter(adapter1);

                    checkListDataToday();


                    set_Today_Data();
                    adapter1.notifyDataSetChanged();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        // xxxxx income_today_btn_data_show Click Show Income Data xxxxx
        income_btn_data_show_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch income for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND income AND user_email = ? ORDER BY time ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);

                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListDataToday();

                    adapter1.notifyDataSetChanged();
                    income_Today_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx expense_today_btn_data_show Click Show Expense Data xxxxx
        expense_btn_data_show_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch expense for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND expense AND user_email = ? ORDER BY time ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);

                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListDataToday();

                    adapter1.notifyDataSetChanged();
                    expense_Today_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx ascending_today_btn_data_show Click Show today_income ascending Data xxxxx
        date_ascending_btn_data_show_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch expense for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time ASC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);


                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListDataToday();

                    adapter1.notifyDataSetChanged();
                    ascending_Today_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // xxxxx descending_today_btn_data_show Click Show today_income descending Data xxxxx
        date_descending_btn_data_show_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    list.clear();

                    // Fetch expense for the TODAY
                    String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time DESC";
                    Cursor cursor = database.rawQuery(query, new String[]{userEmail});

                    if (cursor.moveToFirst()) {
                        do {
                            int Reg_id = cursor.getInt(0);
                            String Income = cursor.getString(1);
                            String Expense = cursor.getString(2);
                            String Category = cursor.getString(4);
                            String Paymentmethod = cursor.getString(5);
                            String Notes = cursor.getString(6);
                            String Date = cursor.getString(7);
                            String Time = cursor.getString(8);

                            Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                            list.add(model);

                        }
                        while (cursor.moveToNext());
                    }
                    cursor.close(); // Close the cursor after use

                    checkListDataToday();

                    adapter1.notifyDataSetChanged();
                    descending_Today_Data();

                } catch (Exception e) {
                    Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    // Weekly Data
    private void weeklyDataShow() {
        // xxxxx tran_weekly_btn_1 Click Data set xxxxx
        transaction_weekly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();

                //btn-2
                transaction_all_1_btn.setVisibility(View.VISIBLE);
                transaction_today_1_btn.setVisibility(View.VISIBLE);
                transaction_monthly_1_btn.setVisibility(View.VISIBLE);
                transaction_yearly_1_btn.setVisibility(View.VISIBLE);
                transaction_weekly_1_btn.setVisibility(View.INVISIBLE);
                //btn-1

                card_all_trans.setVisibility(View.INVISIBLE);
                card_today_trans.setVisibility(View.INVISIBLE);
                card_weekly_trans.setVisibility(View.VISIBLE);
                card_monthly_trans.setVisibility(View.INVISIBLE);
                card_yearly_trans.setVisibility(View.INVISIBLE);

                // show layout
                all_page.setVisibility(View.GONE);
                today_page.setVisibility(View.GONE);
                weekly_page.setVisibility(View.VISIBLE);
                monthly_page.setVisibility(View.GONE);
                yearly_page.setVisibility(View.GONE);

                fetchWeeklyData();
            }
        });

        // xxxxx tran_weekly_btn Click Data Refresh xxxxx
        transaction_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();

                fetchWeeklyData();
            }
        });

        // xxxxx income_weekly_btn_data_show Click Show Income Data xxxxx
        income_btn_data_show_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchWeeklyIncomeData();
            }
        });

        // xxxxx expense_weekly_btn_data_show Click Show Expense Data xxxxx
        expense_btn_data_show_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchWeeklyExpenseData();
            }
        });

        // xxxxx ascending_weekly_btn_data_show Click Show today_income ascending Data xxxxx
        date_ascending_btn_data_show_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchWeeklyAscendingData();
            }
        });

        // xxxxx descending_weekly_btn_data_show Click Show today_income descending Data xxxxx
        date_descending_btn_data_show_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchWeeklyDescendingData();

            }
        });

        tab_weekly_lessthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousWeekDates();
                fetchWeeklyData();
                fetchWeeklyIncomeData();
                fetchWeeklyExpenseData();
                fetchWeeklyAscendingData();
                fetchWeeklyDescendingData();
            }
        });

        tab_weekly_Greaterthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWeekDates();
                fetchWeeklyData();
                fetchWeeklyIncomeData();
                fetchWeeklyExpenseData();
                fetchWeeklyAscendingData();
                fetchWeeklyDescendingData();
            }
        });

    }

    // Monthly Data

    private void monthlyDataShow() {

        // xxxxx tran_monthly_btn_1 Click Data set xxxxx
        transaction_monthly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();

                //btn-2
                transaction_all_1_btn.setVisibility(View.VISIBLE);
                transaction_today_1_btn.setVisibility(View.VISIBLE);
                transaction_yearly_1_btn.setVisibility(View.VISIBLE);
                transaction_weekly_1_btn.setVisibility(View.VISIBLE);
                transaction_monthly_1_btn.setVisibility(View.INVISIBLE);
                //btn-1
                card_all_trans.setVisibility(View.INVISIBLE);
                card_today_trans.setVisibility(View.INVISIBLE);
                card_monthly_trans.setVisibility(View.VISIBLE);
                card_weekly_trans.setVisibility(View.INVISIBLE);
                card_yearly_trans.setVisibility(View.INVISIBLE);


                // show layout
                all_page.setVisibility(View.GONE);
                today_page.setVisibility(View.GONE);
                weekly_page.setVisibility(View.GONE);
                monthly_page.setVisibility(View.VISIBLE);
                yearly_page.setVisibility(View.GONE);

                fetchMonthlyData();

            }
        });

        // xxxxx tran_monthly_btn Click Data Refresh xxxxx
        transaction_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();

                fetchMonthlyData();

            }
        });

        // xxxxx income_monthly_btn_data_show Click Show Income Data xxxxx
        income_btn_data_show_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchMonthlyIncomeData();
            }
        });

        // xxxxx expense_monthly_btn_data_show Click Show Expense Data xxxxx
        expense_btn_data_show_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchMonthlyExpenseData();

            }
        });

        // xxxxx ascending_monthly_btn_data_show Click Show today_income ascending Data xxxxx
        date_ascending_btn_data_show_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchMonthlyAscendingData();
            }
        });

        // xxxxx descending_monthly_btn_data_show Click Show today_income descending Data xxxxx
        date_descending_btn_data_show_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchMonthlyDescendingData();
            }
        });

        tab_monthly_lessthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousMonthDates();
                fetchMonthlyData();
                fetchMonthlyIncomeData();
                fetchMonthlyExpenseData();
                fetchMonthlyAscendingData();
                fetchMonthlyDescendingData();
            }
        });

        tab_monthly_Greaterthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextMonthDates();
                fetchMonthlyData();
                fetchMonthlyIncomeData();
                fetchMonthlyExpenseData();
                fetchMonthlyAscendingData();
                fetchMonthlyDescendingData();
            }
        });

    }

    // Yearly Data
    private void yearlyDataShow() {

        // xxxxx tran_yearly_btn_1 Click Data set xxxxx
        transaction_yearly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();

                //btn-2
                transaction_all_1_btn.setVisibility(View.VISIBLE);
                transaction_today_1_btn.setVisibility(View.VISIBLE);
                transaction_weekly_1_btn.setVisibility(View.VISIBLE);
                transaction_monthly_1_btn.setVisibility(View.VISIBLE);
                transaction_yearly_1_btn.setVisibility(View.INVISIBLE);
                //btn-1

                card_all_trans.setVisibility(View.INVISIBLE);
                card_today_trans.setVisibility(View.INVISIBLE);
                card_weekly_trans.setVisibility(View.INVISIBLE);
                card_monthly_trans.setVisibility(View.INVISIBLE);
                card_yearly_trans.setVisibility(View.VISIBLE);

                // show layout
                all_page.setVisibility(View.GONE);
                today_page.setVisibility(View.GONE);
                weekly_page.setVisibility(View.GONE);
                monthly_page.setVisibility(View.GONE);
                yearly_page.setVisibility(View.VISIBLE);

                fetchYearlyData();

            }
        });

        // xxxxx tran_yearly_btn Click Data Refresh xxxxx
        transaction_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();

                fetchYearlyData();
            }
        });

        // xxxxx income_yearly_btn_data_show Click Show Income Data xxxxx
        income_btn_data_show_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchYearlyIncomeData();

            }
        });

        // xxxxx expense_yearly_btn_data_show Click Show Expense Data xxxxx
        expense_btn_data_show_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchYearlyExpenseData();
            }
        });

        // xxxxx ascending_yearly_btn_data_show Click Show today_income ascending Data xxxxx
        date_ascending_btn_data_show_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchYearlyAscendingData();
            }
        });

        // xxxxx descending_yearly_btn_data_show Click Show today_income descending Data xxxxx
        date_descending_btn_data_show_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchYearlyDescendingData();

            }
        });

        tab_yearly_lessthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousYearDates();
                fetchYearlyData();
                fetchYearlyIncomeData();
                fetchYearlyExpenseData();
                fetchYearlyAscendingData();
                fetchYearlyDescendingData();
            }
        });

        tab_yearly_Greaterthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextYearDates();
                fetchYearlyData();
                fetchYearlyIncomeData();
                fetchYearlyExpenseData();
                fetchYearlyAscendingData();
                fetchYearlyDescendingData();
            }
        });


    }


    // AllData set Textview
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (ALL) data income,expense and total.
    private void set_All_Data() {
        cursor = database.rawQuery("SELECT * FROM incexp_tbl WHERE user_email = ?", new String[]{userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_all_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_all_transaction, Integer.parseInt(String.valueOf(Expense)));

        all_balance = Income - Expense;

        if (isDarkMode) {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_all_transaction, Integer.parseInt(String.valueOf(all_balance)));

        income_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void income_All_Data() {
        cursor = database.rawQuery("SELECT * FROM incexp_tbl WHERE income AND user_email = ? ", new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_all_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_all_transaction, Integer.parseInt(String.valueOf(Expense)));

        all_balance = Income - Expense;


        if (isDarkMode) {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_all_transaction, Integer.parseInt(String.valueOf(all_balance)));

        income_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void expense_All_Data() {
        cursor = database.rawQuery("SELECT * FROM incexp_tbl WHERE expense AND user_email = ? ", new String[]{userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_all_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_all_transaction, Integer.parseInt(String.valueOf(Expense)));

        all_balance = Income - Expense;

        if (isDarkMode) {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_all_transaction, Integer.parseInt(String.valueOf(all_balance)));

        income_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void ascending_All_Data() {
        cursor = database.rawQuery("SELECT * FROM incexp_tbl WHERE user_email = ? ", new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_all_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_all_transaction, Integer.parseInt(String.valueOf(Expense)));

        all_balance = Income - Expense;

        if (isDarkMode) {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_all_transaction, Integer.parseInt(String.valueOf(all_balance)));

        income_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    private void descending_All_Data() {
        cursor = database.rawQuery("SELECT * FROM incexp_tbl WHERE user_email = ? ", new String[]{userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }


        updateTextViewWithFormattedNumber(income_all_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_all_transaction, Integer.parseInt(String.valueOf(Expense)));


        all_balance = Income - Expense;


        if (isDarkMode) {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (all_balance == 0) {
                balance_all_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (all_balance >= 1) {
                balance_all_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_all_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_all_transaction, Integer.parseInt(String.valueOf(all_balance)));


        income_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_all_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    // TodayData set Textview
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (TODAY) data income,expense and total.

    private void set_Today_Data() {

        String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        cursor.close();

        updateTextViewWithFormattedNumber(income_today_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_today_transaction, Integer.parseInt(String.valueOf(Expense)));

        today_balance = Income - Expense;

        String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND  strftime('%Y-%d', entry_date) < strftime('%Y-%d', 'now') AND user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});


        int inc_2 = 0;
        int exp_2 = 0;

        while (cursor2.moveToNext()) {
            inc_2 += Integer.parseInt(cursor2.getString(1));
            exp_2 += Integer.parseInt(cursor2.getString(2));
        }


        today_pre_bal1 = inc_1 - exp_1;
        today_pre_bal2 = inc_2 - exp_2;
        today_pre_bal = today_pre_bal1 + today_pre_bal2;
        today_total = today_pre_bal + today_balance;

        if (isDarkMode) {


            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


        }


        updateTextViewWithFormattedNumber(balance_today_transaction, Integer.parseInt(String.valueOf(today_balance)));
        updateTextViewWithFormattedNumber(previousbalance_today_transaction, Integer.parseInt(String.valueOf(today_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_today_transaction, Integer.parseInt(String.valueOf(today_total)));

        income_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Income_Activity.class));
            }
        });

        expense_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Expense_Activity.class));
            }
        });

    }

    private void income_Today_Data() {

        String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND income AND user_email = ? ORDER BY time ASC";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_today_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_today_transaction, Integer.parseInt(String.valueOf(Expense)));

        today_balance = Income - Expense;

        String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND strftime('%Y-%d', entry_date) < strftime('%Y-%d', 'now') AND user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});


        int inc_2 = 0;
        int exp_2 = 0;

        while (cursor2.moveToNext()) {
            inc_2 += Integer.parseInt(cursor2.getString(1));
            exp_2 += Integer.parseInt(cursor2.getString(2));
        }


        today_pre_bal1 = inc_1 - exp_1;
        today_pre_bal2 = inc_2 - exp_2;
        today_pre_bal = today_pre_bal1 + today_pre_bal2;
        today_total = today_pre_bal + today_balance;


        if (isDarkMode) {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


        }


        updateTextViewWithFormattedNumber(balance_today_transaction, Integer.parseInt(String.valueOf(today_balance)));
        updateTextViewWithFormattedNumber(previousbalance_today_transaction, Integer.parseInt(String.valueOf(today_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_today_transaction, Integer.parseInt(String.valueOf(today_total)));

        income_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Income_Activity.class));
            }
        });

        expense_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Expense_Activity.class));
            }
        });


    }

    private void expense_Today_Data() {


        String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND expense AND user_email = ? ORDER BY time ASC";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_today_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_today_transaction, Integer.parseInt(String.valueOf(Expense)));

        today_balance = Income - Expense;

        String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }


        String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND strftime('%Y-%d', entry_date) < strftime('%Y-%d', 'now') AND user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});

        int inc_2 = 0;
        int exp_2 = 0;

        while (cursor2.moveToNext()) {
            inc_2 += Integer.parseInt(cursor2.getString(1));
            exp_2 += Integer.parseInt(cursor2.getString(2));
        }


        today_pre_bal1 = inc_1 - exp_1;
        today_pre_bal2 = inc_2 - exp_2;
        today_pre_bal = today_pre_bal1 + today_pre_bal2;
        today_total = today_pre_bal + today_balance;


        if (isDarkMode) {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

        }

        updateTextViewWithFormattedNumber(balance_today_transaction, Integer.parseInt(String.valueOf(today_balance)));
        updateTextViewWithFormattedNumber(previousbalance_today_transaction, Integer.parseInt(String.valueOf(today_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_today_transaction, Integer.parseInt(String.valueOf(today_total)));

        income_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Income_Activity.class));
            }
        });

        expense_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Expense_Activity.class));
            }
        });
    }

    private void ascending_Today_Data() {


        String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time ASC";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_today_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_today_transaction, Integer.parseInt(String.valueOf(Expense)));

        today_balance = Income - Expense;

        String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND strftime('%Y-%d', entry_date) < strftime('%Y-%d', 'now') AND user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});


        int inc_2 = 0;
        int exp_2 = 0;

        while (cursor2.moveToNext()) {
            inc_2 += Integer.parseInt(cursor2.getString(1));
            exp_2 += Integer.parseInt(cursor2.getString(2));
        }


        today_pre_bal1 = inc_1 - exp_1;
        today_pre_bal2 = inc_2 - exp_2;
        today_pre_bal = today_pre_bal1 + today_pre_bal2;
        today_total = today_pre_bal + today_balance;


        if (isDarkMode) {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

        }

        updateTextViewWithFormattedNumber(balance_today_transaction, Integer.parseInt(String.valueOf(today_balance)));
        updateTextViewWithFormattedNumber(previousbalance_today_transaction, Integer.parseInt(String.valueOf(today_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_today_transaction, Integer.parseInt(String.valueOf(today_total)));

        income_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Income_Activity.class));
            }
        });

        expense_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Expense_Activity.class));
            }
        });
    }

    private void descending_Today_Data() {

        String query = "SELECT * FROM incexp_tbl WHERE entry_date = strftime('%Y-%m-%d', 'now') AND user_email = ? ORDER BY time DESC";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }


        updateTextViewWithFormattedNumber(income_today_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_today_transaction, Integer.parseInt(String.valueOf(Expense)));

        today_balance = Income - Expense;

        String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{userEmail});


        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND strftime('%Y-%d', entry_date) < strftime('%Y-%d', 'now') AND user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});


        int inc_2 = 0;
        int exp_2 = 0;

        while (cursor2.moveToNext()) {
            inc_2 += Integer.parseInt(cursor2.getString(1));
            exp_2 += Integer.parseInt(cursor2.getString(2));
        }


        today_pre_bal1 = inc_1 - exp_1;
        today_pre_bal2 = inc_2 - exp_2;
        today_pre_bal = today_pre_bal1 + today_pre_bal2;
        today_total = today_pre_bal + today_balance;


        if (isDarkMode) {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (today_balance == 0) {
                balance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                balance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }


            if (today_pre_bal == 0) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_pre_bal >= 1) {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (today_total == 0) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_total >= 1) {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_today_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

        }


        updateTextViewWithFormattedNumber(balance_today_transaction, Integer.parseInt(String.valueOf(today_balance)));
        updateTextViewWithFormattedNumber(previousbalance_today_transaction, Integer.parseInt(String.valueOf(today_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_today_transaction, Integer.parseInt(String.valueOf(today_total)));

        income_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Income_Activity.class));
            }
        });

        expense_today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Transactions_Activity.this, Expense_Activity.class));
            }
        });
    }

    // WeeklyData set Textview
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (WEEKLY) data income,expense and total.
    private void set_Weekly_Data() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        // Fetch income and expense for the current week
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_weekly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_weekly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current week
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        cursor = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor.moveToNext()) {
            inc_1 += Integer.parseInt(cursor.getString(1));
            exp_1 += Integer.parseInt(cursor.getString(2));
        }

        weekly_balance = Income - Expense;
        weekly_pre_bal = inc_1 - exp_1;
        weekly_total = weekly_pre_bal + weekly_balance;

        if (isDarkMode) {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_total)));

        income_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    private void set_Weekly_Income_Data() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE  income AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_weekly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_weekly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current week
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        weekly_balance = Income - Expense;
        weekly_pre_bal = inc_1 - exp_1;
        weekly_total = weekly_pre_bal + weekly_balance;

        if (isDarkMode) {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_total)));

        income_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    private void set_Weekly_Expense_Data() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE  expense AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_weekly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_weekly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current week
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        weekly_balance = Income - Expense;
        weekly_pre_bal = inc_1 - exp_1;
        weekly_total = weekly_pre_bal + weekly_balance;

        if (isDarkMode) {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_total)));

        income_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    private void set_Weekly_Ascending_Data() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }
        updateTextViewWithFormattedNumber(income_weekly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_weekly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current week
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        weekly_balance = Income - Expense;
        weekly_pre_bal = inc_1 - exp_1;
        weekly_total = weekly_pre_bal + weekly_balance;

        if (isDarkMode) {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_total)));

        income_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    private void set_Weekly_Descending_Data() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }


        updateTextViewWithFormattedNumber(income_weekly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_weekly_transaction, Integer.parseInt(String.valueOf(Expense)));


        // Fetch income and expense before the current week
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        weekly_balance = Income - Expense;
        weekly_pre_bal = inc_1 - exp_1;
        weekly_total = weekly_pre_bal + weekly_balance;

        if (isDarkMode) {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (weekly_balance == 0) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                balance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_pre_bal == 0) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_pre_bal >= 1) {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (weekly_total == 0) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_total >= 1) {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_weekly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_weekly_transaction, Integer.parseInt(String.valueOf(weekly_total)));

        income_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });
    }

    // MonthlyData set Textview
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (MONTHLY) data income,expense and total.

    private void set_Monthly_Data() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_monthly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_monthly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        monthly_balance = Income - Expense;
        monthly_pre_bal = inc_1 - exp_1;
        monthly_total = monthly_pre_bal + monthly_balance;

        if (isDarkMode) {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_total)));

        income_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Monthly_Income_Data() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE income AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_monthly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_monthly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        monthly_balance = Income - Expense;
        monthly_pre_bal = inc_1 - exp_1;
        monthly_total = monthly_pre_bal + monthly_balance;

        if (isDarkMode) {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_total)));

        income_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Monthly_Expense_Data() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE expense AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_monthly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_monthly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        monthly_balance = Income - Expense;
        monthly_pre_bal = inc_1 - exp_1;
        monthly_total = monthly_pre_bal + monthly_balance;

        if (isDarkMode) {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_total)));

        income_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Monthly_Ascending_Data() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_monthly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_monthly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        monthly_balance = Income - Expense;
        monthly_pre_bal = inc_1 - exp_1;
        monthly_total = monthly_pre_bal + monthly_balance;

        if (isDarkMode) {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_total)));

        income_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Monthly_Descending_Data() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_monthly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_monthly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        monthly_balance = Income - Expense;
        monthly_pre_bal = inc_1 - exp_1;
        monthly_total = monthly_pre_bal + monthly_balance;

        if (isDarkMode) {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (monthly_balance == 0) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                balance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_pre_bal == 0) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_pre_bal >= 1) {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (monthly_total == 0) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_total >= 1) {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_monthly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        updateTextViewWithFormattedNumber(balance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_monthly_transaction, Integer.parseInt(String.valueOf(monthly_total)));

        income_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    // YearlyData set Textview
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (YEARLY) data income,expense and total.

    private void set_Yearly_Data() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_yearly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_yearly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        yearly_balance = Income - Expense;
        yearly_pre_bal = inc_1 - exp_1;
        yearly_total = yearly_pre_bal + yearly_balance;

        if (isDarkMode) {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_total)));

        income_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Yearly_Income_Data() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE income AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_yearly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_yearly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        yearly_balance = Income - Expense;
        yearly_pre_bal = inc_1 - exp_1;
        yearly_total = yearly_pre_bal + yearly_balance;

        if (isDarkMode) {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_total)));

        income_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Yearly_Expense_Data() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE expense AND entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_yearly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_yearly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        yearly_balance = Income - Expense;
        yearly_pre_bal = inc_1 - exp_1;
        yearly_total = yearly_pre_bal + yearly_balance;

        if (isDarkMode) {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_total)));

        income_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Yearly_Ascending_Data() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_yearly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_yearly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        yearly_balance = Income - Expense;
        yearly_pre_bal = inc_1 - exp_1;
        yearly_total = yearly_pre_bal + yearly_balance;

        if (isDarkMode) {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_total)));

        income_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }

    private void set_Yearly_Descending_Data() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        // Fetch income and expense for the current month
        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }

        updateTextViewWithFormattedNumber(income_yearly_transaction, Integer.parseInt(String.valueOf(Income)));
        updateTextViewWithFormattedNumber(expense_yearly_transaction, Integer.parseInt(String.valueOf(Expense)));

        // Fetch income and expense before the current month
        String query1 = "SELECT * FROM incexp_tbl WHERE entry_date < ? AND user_email = ?";
        Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, userEmail});

        int inc_1 = 0;
        int exp_1 = 0;

        while (cursor1.moveToNext()) {
            inc_1 += Integer.parseInt(cursor1.getString(1));
            exp_1 += Integer.parseInt(cursor1.getString(2));
        }

        yearly_balance = Income - Expense;
        yearly_pre_bal = inc_1 - exp_1;
        yearly_total = yearly_pre_bal + yearly_balance;

        if (isDarkMode) {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#FF3838"));//Red
            }


        } else {

            if (yearly_balance == 0) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                balance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                balance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_pre_bal == 0) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_pre_bal >= 1) {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                previousbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }

            if (yearly_total == 0) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_total >= 1) {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else {
                totalbalance_yearly_transaction.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        updateTextViewWithFormattedNumber(balance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_balance)));
        updateTextViewWithFormattedNumber(previousbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_pre_bal)));
        updateTextViewWithFormattedNumber(totalbalance_yearly_transaction, Integer.parseInt(String.valueOf(yearly_total)));


        income_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        expense_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Transactions_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Weekly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>


    //Fetching Weekly Data
    private void fetchWeeklyData() {
        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
            Transaction_weekly_list.setAdapter(adapter1);

            checkListDataWeekly();

            adapter1.notifyDataSetChanged();
            set_Weekly_Data();

        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Weekly Income Data
    private void fetchWeeklyIncomeData() {
        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE income AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataWeekly();

            adapter1.notifyDataSetChanged();
            set_Weekly_Income_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Weekly Expense Data
    private void fetchWeeklyExpenseData() {
        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE expense AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataWeekly();

            adapter1.notifyDataSetChanged();
            set_Weekly_Expense_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Weekly Ascending Data
    private void fetchWeeklyAscendingData() {
        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date ASC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataWeekly();

            adapter1.notifyDataSetChanged();
            set_Weekly_Ascending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Weekly DescendingData
    private void fetchWeeklyDescendingData() {
        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataWeekly();

            adapter1.notifyDataSetChanged();
            set_Weekly_Descending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Monthly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Monthy Data
    private void fetchMonthlyData() {
        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
            Transaction_monthly_list.setAdapter(adapter1);

            checkListDataMonthly();

            adapter1.notifyDataSetChanged();
            set_Monthly_Data();

        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Monthly Income Data
    private void fetchMonthlyIncomeData() {
        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE income AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataMonthly();

            adapter1.notifyDataSetChanged();
            set_Monthly_Income_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Monthly Expense Data
    private void fetchMonthlyExpenseData() {
        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE expense AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataMonthly();

            adapter1.notifyDataSetChanged();
            set_Monthly_Expense_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Monthly Ascending Data
    private void fetchMonthlyAscendingData() {
        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date ASC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataMonthly();

            adapter1.notifyDataSetChanged();
            set_Monthly_Ascending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Monthly DescendingData
    private void fetchMonthlyDescendingData() {
        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataMonthly();

            adapter1.notifyDataSetChanged();
            set_Monthly_Descending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Yearly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>

    //Fetching Yearly Data
    private void fetchYearlyData() {
        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            adapter1 = new Incomeexpense_Adapter(Transactions_Activity.this, list, database, userEmail);
            Transaction_yearly_list.setAdapter(adapter1);

            checkListDataYearly();

            adapter1.notifyDataSetChanged();
            set_Yearly_Data();

        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Yearly Income Data
    private void fetchYearlyIncomeData() {
        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE income AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataYearly();

            adapter1.notifyDataSetChanged();
            set_Yearly_Income_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Yearly Expense Data
    private void fetchYearlyExpenseData() {
        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE expense AND entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataYearly();

            adapter1.notifyDataSetChanged();
            set_Yearly_Expense_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Yearly Ascending Data
    private void fetchYearlyAscendingData() {
        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date ASC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataYearly();

            adapter1.notifyDataSetChanged();
            set_Yearly_Ascending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Fetching Yearly DescendingData
    private void fetchYearlyDescendingData() {
        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();
            String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
            Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Notes = cursor.getString(6);
                    String Date = cursor.getString(7);
                    String Time = cursor.getString(8);
                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Notes, Date, Time);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use

            checkListDataYearly();

            adapter1.notifyDataSetChanged();
            set_Yearly_Descending_Data();
        } catch (Exception e) {
            Toast.makeText(Transactions_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //     \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Weekly  ////////////////////////////////////////////////////////////////
    // Week Start Date
    private String getWeekStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return dateFormat.format(calendar.getTime());
    }

    // Week End Date
    private String getWeekEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        return dateFormat.format(calendar.getTime());
    }

    // Update Current Week Date
    private void updateCurrentWeekDates() {
        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();
        String dateRange = startDate + " -> " + endDate;
        textView_weekly.setText(dateRange); // Update your TextView here
    }

    // Reset Current Week Date and Data
    public void resetToCurrentWeek() {
        calendar = Calendar.getInstance();
        updateCurrentWeekDates();
    }

    // Show Previous Week Date
    public void showPreviousWeekDates() {
        calendar.add(Calendar.WEEK_OF_YEAR, -1); // Move calendar to previous week
        updateCurrentWeekDates(); // Update UI with new week dates
    }

    // Show Next Week Date
    public void showNextWeekDates() {
        calendar.add(Calendar.WEEK_OF_YEAR, 1); // Move calendar to next week
        updateCurrentWeekDates(); // Update UI with new week dates
    }

    //     \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Monthly  ////////////////////////////////////////////////////////////////

    //  Month Start Date
    private String getMonthStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    //  Month last Date
    private String getMonthEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(calendar.getTime());
    }

    // Update Current  Month Date
    private void updateCurrentMonthDates() {
        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();
        String dateRange = startDate + " -> " + endDate;
        textView_monthly.setText(dateRange);
    }

    // Reset Current  Month Date and Data
    private void resetToCurrentMonth() {
        calendar = Calendar.getInstance();
        updateCurrentMonthDates();
    }

    // Show Prev Month Date
    private void showPreviousMonthDates() {
        calendar.add(Calendar.MONTH, -1);
        updateCurrentMonthDates();
    }

    // Show Next Month Date
    private void showNextMonthDates() {
        calendar.add(Calendar.MONTH, 1);
        updateCurrentMonthDates();
    }

    //     \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\   Yearly  ////////////////////////////////////////////////////////////////

    // Year Start Date
    private String getYearStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(calendar.getTime());
    }

    // Year End Date
    private String getYearEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return dateFormat.format(calendar.getTime());
    }

    // Update Current Year Dates
    private void updateCurrentYearDates() {
        String startDate = getYearStartDate();
        String endDate = getYearEndDate();
        String dateRange = startDate + " -> " + endDate;
        textView_yearly.setText(dateRange);
    }

    // Reset to Current Year
    private void resetToCurrentYear() {
        calendar = Calendar.getInstance();
        updateCurrentYearDates();
    }

    // Show Previous Year Dates
    private void showPreviousYearDates() {
        calendar.add(Calendar.YEAR, -1);
        updateCurrentYearDates();
    }

    // Show Next Year Dates
    private void showNextYearDates() {
        calendar.add(Calendar.YEAR, 1);
        updateCurrentYearDates();
    }


    // Assuming you have a method to update your TextView with formatted number
    private void updateTextViewWithFormattedNumber(TextView textView, int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        textView.setText(formattedNumber);
    }

    // Check List data isempty
    private void checkListData() {
        if (list.isEmpty()) {
            Transaction_all_list.setVisibility(View.GONE);
            transaction_all_no_data_img.setVisibility(View.VISIBLE);
        } else {
            Transaction_all_list.setVisibility(View.VISIBLE);
            transaction_all_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataToday() {
        if (list.isEmpty()) {
            Transaction_today_list.setVisibility(View.GONE);
            transaction_today_no_data_img.setVisibility(View.VISIBLE);
        } else {
            Transaction_today_list.setVisibility(View.VISIBLE);
            transaction_today_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataWeekly() {
        if (list.isEmpty()) {
            Transaction_weekly_list.setVisibility(View.GONE);
            transaction_weekly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            Transaction_weekly_list.setVisibility(View.VISIBLE);
            transaction_weekly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataMonthly() {
        if (list.isEmpty()) {
            Transaction_monthly_list.setVisibility(View.GONE);
            transaction_monthly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            Transaction_monthly_list.setVisibility(View.VISIBLE);
            transaction_monthly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataYearly() {
        if (list.isEmpty()) {
            Transaction_yearly_list.setVisibility(View.GONE);
            transaction_yearly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            Transaction_yearly_list.setVisibility(View.VISIBLE);
            transaction_yearly_no_data_img.setVisibility(View.GONE);
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
        transaction_all_btn.setText(R.string.all_btn_txt_lay1_transactions);
        transaction_all_1_btn.setText(R.string.all_btn_txt_lay1_transactions);

        transaction_today_btn.setText(R.string.today_btn_txt_lay1_transactions);
        transaction_today_1_btn.setText(R.string.today_btn_txt_lay1_transactions);

        transaction_weekly_btn.setText(R.string.weekly_btn_txt_lay1_transactions);
        transaction_weekly_1_btn.setText(R.string.weekly_btn_txt_lay1_transactions);

        transaction_monthly_btn.setText(R.string.monthly_btn_txt_lay1_transactions);
        transaction_monthly_1_btn.setText(R.string.monthly_btn_txt_lay1_transactions);

        transaction_yearly_btn.setText(R.string.yearly_btn_txt_lay1_transactions);
        transaction_yearly_1_btn.setText(R.string.yearly_btn_txt_lay1_transactions);


        // Layout ---- 2
        income_btn_data_show_all.setText(R.string.income_btn_txt_lay2_transactions);
        expense_btn_data_show_all.setText(R.string.expense_btn_txt_lay2_transactions);
        date_ascending_btn_data_show_all.setText(R.string.date_asc_btn_txt_lay2_transactions);
        date_descending_btn_data_show_all.setText(R.string.date_desc_btn_txt_lay2_transactions);

        income_btn_data_show_today.setText(R.string.income_btn_txt_lay2_transactions);
        expense_btn_data_show_today.setText(R.string.expense_btn_txt_lay2_transactions);
        date_ascending_btn_data_show_today.setText(R.string.date_asc_btn_txt_lay2_transactions);
        date_descending_btn_data_show_today.setText(R.string.date_desc_btn_txt_lay2_transactions);

        income_btn_data_show_weekly.setText(R.string.income_btn_txt_lay2_transactions);
        expense_btn_data_show_weekly.setText(R.string.expense_btn_txt_lay2_transactions);
        date_ascending_btn_data_show_weekly.setText(R.string.date_asc_btn_txt_lay2_transactions);
        date_descending_btn_data_show_weekly.setText(R.string.date_desc_btn_txt_lay2_transactions);


        income_btn_data_show_monthly.setText(R.string.income_btn_txt_lay2_transactions);
        expense_btn_data_show_monthly.setText(R.string.expense_btn_txt_lay2_transactions);
        date_ascending_btn_data_show_monthly.setText(R.string.date_asc_btn_txt_lay2_transactions);
        date_descending_btn_data_show_monthly.setText(R.string.date_desc_btn_txt_lay2_transactions);


        income_btn_data_show_yearly.setText(R.string.income_btn_txt_lay2_transactions);
        expense_btn_data_show_yearly.setText(R.string.expense_btn_txt_lay2_transactions);
        date_ascending_btn_data_show_yearly.setText(R.string.date_asc_btn_txt_lay2_transactions);
        date_descending_btn_data_show_yearly.setText(R.string.date_desc_btn_txt_lay2_transactions);


        // Layout ---- 3
        textView_all.setText(R.string.all_btn_txt_lay3_transactions);
        textView_today.setText(R.string.today_btn_txt_lay3_transactions);
        textView_weekly.setText(R.string.weekly_btn_txt_lay3_transactions);
        textView_monthly.setText(R.string.monthly_btn_txt_lay3_transactions);
        textView_yearly.setText(R.string.yearly_btn_txt_lay3_transactions);

        // Layout ---- 4

        //                                            1
        date_trans_all.setText(R.string.date_txt_lay4_transactions);
        category_trans_all.setText(R.string.category_txt_lay4_transactions);
        inc_trans_all.setText(R.string.income_txt_lay4_transactions);
        exp_trans_all.setText(R.string.expense_txt_lay4_transactions);

        //                                            2
        date_trans_today.setText(R.string.date_txt_lay4_transactions);
        category_trans_today.setText(R.string.category_txt_lay4_transactions);
        inc_trans_today.setText(R.string.income_txt_lay4_transactions);
        exp_trans_today.setText(R.string.expense_txt_lay4_transactions);

        //                                            3
        date_trans_weekly.setText(R.string.date_txt_lay4_transactions);
        category_trans_weekly.setText(R.string.category_txt_lay4_transactions);
        inc_trans_weekly.setText(R.string.income_txt_lay4_transactions);
        exp_trans_weekly.setText(R.string.expense_txt_lay4_transactions);

        //                                            4
        date_trans_monthly.setText(R.string.date_txt_lay4_transactions);
        category_trans_monthly.setText(R.string.category_txt_lay4_transactions);
        inc_trans_monthly.setText(R.string.income_txt_lay4_transactions);
        exp_trans_monthly.setText(R.string.expense_txt_lay4_transactions);

        //                                            5
        date_trans_yearly.setText(R.string.date_txt_lay4_transactions);
        category_trans_yearly.setText(R.string.category_txt_lay4_transactions);
        inc_trans_yearly.setText(R.string.income_txt_lay4_transactions);
        exp_trans_yearly.setText(R.string.expense_txt_lay4_transactions);

        // Layout ---- 5

        income_all_btn.setText(R.string.income_btn_txt_lay5_transactions);
        expense_all_btn.setText(R.string.expense_btn_txt_lay5_transactions);

        income_today_btn.setText(R.string.income_btn_txt_lay5_transactions);
        expense_today_btn.setText(R.string.expense_btn_txt_lay5_transactions);

        income_weekly_btn.setText(R.string.income_btn_txt_lay5_transactions);
        expense_weekly_btn.setText(R.string.expense_btn_txt_lay5_transactions);

        income_monthly_btn.setText(R.string.income_btn_txt_lay5_transactions);
        expense_monthly_btn.setText(R.string.expense_btn_txt_lay5_transactions);

        income_yearly_btn.setText(R.string.income_btn_txt_lay5_transactions);
        expense_yearly_btn.setText(R.string.expense_btn_txt_lay5_transactions);

        // Layout ---- 6
        income_all_transaction_txt.setText(R.string.total_inc_txt_lay6_transactions);
        expense_all_transaction_txt.setText(R.string.total_exp_txt_lay6_transactions);
        balance_all_transaction_txt.setText(R.string.balance_txt_lay6_transactions);

        income_today_transaction_txt.setText(R.string.total_inc_txt_lay6_transactions);
        expense_today_transaction_txt.setText(R.string.total_exp_txt_lay6_transactions);
        balance_today_transaction_txt.setText(R.string.balance_txt_lay6_transactions);
        previousbalance_today_transaction_txt.setText(R.string.pre_bal_txt_lay7_transactions);
        totalbalance_today_transaction_txt.setText(R.string.total_bal_bal_txt_lay7_transactions);


        income_weekly_transaction_txt.setText(R.string.total_inc_txt_lay6_transactions);
        expense_weekly_transaction_txt.setText(R.string.total_exp_txt_lay6_transactions);
        balance_weekly_transaction_txt.setText(R.string.balance_txt_lay6_transactions);
        previousbalance_weekly_transaction_txt.setText(R.string.pre_bal_txt_lay7_transactions);
        totalbalance_weekly_transaction_txt.setText(R.string.total_bal_bal_txt_lay7_transactions);


        income_monthly_transaction_txt.setText(R.string.total_inc_txt_lay6_transactions);
        expense_monthly_transaction_txt.setText(R.string.total_exp_txt_lay6_transactions);
        balance_monthly_transaction_txt.setText(R.string.balance_txt_lay6_transactions);
        previousbalance_monthly_transaction_txt.setText(R.string.pre_bal_txt_lay7_transactions);
        totalbalance_monthly_transaction_txt.setText(R.string.total_bal_bal_txt_lay7_transactions);


        income_yearly_transaction_txt.setText(R.string.total_inc_txt_lay6_transactions);
        expense_yearly_transaction_txt.setText(R.string.total_exp_txt_lay6_transactions);
        balance_yearly_transaction_txt.setText(R.string.balance_txt_lay6_transactions);
        previousbalance_yearly_transaction_txt.setText(R.string.pre_bal_txt_lay7_transactions);
        totalbalance_yearly_transaction_txt.setText(R.string.total_bal_bal_txt_lay7_transactions);

    }


    // <-------------------------------------------------------------------- On BackPress ------------------------------------------------------------------------------>

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Transactions_Activity.this, Home_Activity.class);
        intent.putExtra("USER_EMAIL", userEmail); // Pass any necessary data
        startActivity(intent);
    }

    //   <- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ->

}