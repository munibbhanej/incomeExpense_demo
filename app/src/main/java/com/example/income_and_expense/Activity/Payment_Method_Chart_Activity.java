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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.income_and_expense.Adapter.Incomeexpense_Adapter;
import com.example.income_and_expense.Adapter.Payment_method_Adapter;
import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Payment_Method_Chart_Activity extends AppCompatActivity {


    // ----- All ------
    BarChart pay_meth_bar_chart_all;
    TextView amount_total_all;

    TextView pay_chart_textView_all;
    Button pay_meth_all_btn, pay_meth_all_1_btn;
    RelativeLayout pay_meth_all_page;

    private ListView pay_meth_all_list;

    TextView pay_meth_txt_all_l1, amount_txt_all_l1;

    CardView card_all_pay_meth_chart;

    ImageView pay_meth_all_no_data_img;
    LinearLayout  linear_all_pay_meth;

    // ----- Today ------
    BarChart pay_meth_bar_chart_today;
    TextView amount_total_today;

    TextView pay_chart_textView_today;
    Button pay_meth_today_btn, pay_meth_today_1_btn;
    RelativeLayout pay_meth_today_page;

    CardView card_today_pay_meth_chart;

    private ListView pay_meth_today_list;

    TextView pay_meth_txt_today_l1, amount_txt_today_l1;

    ImageView pay_meth_today_no_data_img;
    LinearLayout  linear_today_pay_meth;


    // ----- Weekly ------
    BarChart pay_meth_bar_chart_weekly;
    TextView amount_total_weekly;
    TableLayout tableLayout_less_greater_weekly_pay_chart;

    ImageView less_pay_chart_weekly, greater_pay_chart_weekly;

    TextView pay_chart_textView_weekly;
    Button pay_meth_weekly_btn, pay_meth_weekly_1_btn;
    RelativeLayout pay_meth_weekly_page;

    CardView card_weekly_pay_meth_chart;

    ListView pay_meth_weekly_list;

    TextView pay_meth_txt_weekly_l1, amount_txt_weekly_l1;

    ImageView pay_meth_weekly_no_data_img;
    LinearLayout  linear_weekly_pay_meth;


    // ----- Monthly ------
    BarChart pay_meth_bar_chart_monthly;
    TextView amount_total_monthly;

    TableLayout tableLayout_less_greater_monthly_pay_chart;
    ImageView less_pay_chart_monthly, greater_pay_chart_monthly;

    TextView pay_chart_textView_monthly;
    Button pay_meth_monthly_btn, pay_meth_monthly_1_btn;
    RelativeLayout pay_meth_monthly_page;
    CardView card_monthly_pay_meth_chart;

    private ListView pay_meth_monthly_list;

    TextView pay_meth_txt_monthly_l1, amount_txt_monthly_l1;

    ImageView pay_meth_monthly_no_data_img;
    LinearLayout  linear_monthly_pay_meth;



    // ----- Yearly ------

    BarChart pay_meth_bar_chart_yearly;
    TextView amount_total_yearly;

    TableLayout tableLayout_less_greater_yearly_pay_chart;
    ImageView less_pay_chart_yearly, greater_pay_chart_yearly;

    TextView pay_chart_textView_yearly;
    Button pay_meth_yearly_btn, pay_meth_yearly_1_btn;
    RelativeLayout pay_meth_yearly_page;

    CardView card_yearly_pay_meth_chart;
    ListView pay_meth_yearly_list;
    TextView pay_meth_txt_yearly_l1, amount_txt_yearly_l1;

    ImageView pay_meth_yearly_no_data_img;

    LinearLayout  linear_yearly_pay_meth;


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    Cursor cursor;
    int all_total = 0, today_total = 0, weekly_total = 0, monthly_total = 0, yearly_total = 0;

    Payment_method_Adapter pay_adapter;
    private List<Income_Expense_Model> list = new ArrayList<>();

    TextView text_toolbar;
    ImageView back_icon_toolbar;

    private String userEmail;

    private Calendar currentMonth;
    private Calendar calendar;

    //                          Dark and Light Mode

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    private SharedPreferences sharedPreferences;
    boolean isDarkMode;
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

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

        setContentView(R.layout.activity_payment_method_chart);


        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        currentMonth = Calendar.getInstance();
        calendar = Calendar.getInstance();

        database = CreateDatabase();


        // Toolbar
        toolbar();

        // Initialize views
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
                Intent intent = new Intent(Payment_Method_Chart_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);

            }
        });
    }

    // Initialize views
    private void initializeViews() {

        // All
        pay_meth_bar_chart_all = findViewById(R.id.pay_meth_barchart_all);
        amount_total_all = findViewById(R.id.amount_total_all);

        pay_chart_textView_all = findViewById(R.id.pay_chart_textView_all);

        card_all_pay_meth_chart = findViewById(R.id.card_all_pay_meth_chart);
        pay_meth_all_list = findViewById(R.id.pay_meth_all_list);

        pay_meth_txt_all_l1 = findViewById(R.id.pay_meth_txt_all_l1);
        amount_txt_all_l1 = findViewById(R.id.amount_txt_all_l1);

        pay_meth_all_no_data_img = findViewById(R.id.pay_meth_all_no_data_img);
        linear_all_pay_meth = findViewById(R.id.linear_all_pay_meth);

        // TODAY
        pay_meth_bar_chart_today = findViewById(R.id.pay_meth_barchart_today);
        amount_total_today = findViewById(R.id.amount_total_today);

        pay_chart_textView_today = findViewById(R.id.pay_chart_textView_today);
        card_today_pay_meth_chart = findViewById(R.id.card_today_pay_meth_chart);
        pay_meth_today_list = findViewById(R.id.pay_meth_today_list);

        pay_meth_txt_today_l1 = findViewById(R.id.pay_meth_txt_today_l1);
        amount_txt_today_l1 = findViewById(R.id.amount_txt_today_l1);

        pay_meth_today_no_data_img = findViewById(R.id.pay_meth_today_no_data_img);
        linear_today_pay_meth = findViewById(R.id.linear_today_pay_meth);


        // WEEKLY
        pay_meth_bar_chart_weekly = findViewById(R.id.pay_meth_barchart_weekly);
        amount_total_weekly = findViewById(R.id.amount_total_weekly);

        tableLayout_less_greater_weekly_pay_chart = findViewById(R.id.tableLayout_less_greater_weekly_pay_chart);
        less_pay_chart_weekly = findViewById(R.id.less_pay_chart_weekly);
        greater_pay_chart_weekly = findViewById(R.id.greater_pay_chart_weekly);
        pay_chart_textView_weekly = findViewById(R.id.pay_chart_textView_weekly);

        card_weekly_pay_meth_chart = findViewById(R.id.card_weekly_pay_meth_chart);
        pay_meth_weekly_list = findViewById(R.id.pay_meth_weekly_list);

        pay_meth_txt_weekly_l1 = findViewById(R.id.pay_meth_txt_weekly_l1);
        amount_txt_weekly_l1 = findViewById(R.id.amount_txt_weekly_l1);

        pay_meth_weekly_no_data_img = findViewById(R.id.pay_meth_weekly_no_data_img);
        linear_weekly_pay_meth = findViewById(R.id.linear_weekly_pay_meth);


        // MONTHLY
        pay_meth_bar_chart_monthly = findViewById(R.id.pay_meth_barchart_monthly);
        amount_total_monthly = findViewById(R.id.amount_total_monthly);

        tableLayout_less_greater_monthly_pay_chart = findViewById(R.id.tableLayout_less_greater_monthly_pay_chart);
        less_pay_chart_monthly = findViewById(R.id.less_pay_chart_monthly);
        greater_pay_chart_monthly = findViewById(R.id.greater_pay_chart_monthly);
        pay_chart_textView_monthly = findViewById(R.id.pay_chart_textView_monthly);

        card_monthly_pay_meth_chart = findViewById(R.id.card_monthly_pay_meth_chart);
        pay_meth_monthly_list = findViewById(R.id.pay_meth_monthly_list);

        pay_meth_txt_monthly_l1 = findViewById(R.id.pay_meth_txt_monthly_l1);
        amount_txt_monthly_l1 = findViewById(R.id.amount_txt_monthly_l1);

        pay_meth_monthly_no_data_img = findViewById(R.id.pay_meth_monthly_no_data_img);
        linear_monthly_pay_meth = findViewById(R.id.linear_monthly_pay_meth);


        // YEARLY
        pay_meth_bar_chart_yearly = findViewById(R.id.pay_meth_barchart_yearly);
        amount_total_yearly = findViewById(R.id.amount_total_yearly);

        tableLayout_less_greater_yearly_pay_chart = findViewById(R.id.tableLayout_less_greater_yearly_pay_chart);
        less_pay_chart_yearly = findViewById(R.id.less_pay_chart_yearly);
        greater_pay_chart_yearly = findViewById(R.id.greater_pay_chart_yearly);
        pay_chart_textView_yearly = findViewById(R.id.pay_chart_textView_yearly);

        card_yearly_pay_meth_chart = findViewById(R.id.card_yearly_pay_meth_chart);
        pay_meth_yearly_list = findViewById(R.id.pay_meth_yearly_list);

        pay_meth_txt_yearly_l1 = findViewById(R.id.pay_meth_txt_yearly_l1);
        amount_txt_yearly_l1 = findViewById(R.id.amount_txt_yearly_l1);

        pay_meth_yearly_no_data_img = findViewById(R.id.pay_meth_yearly_no_data_img);
        linear_yearly_pay_meth = findViewById(R.id.linear_yearly_pay_meth);


        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

        //--------   All   -----
        pay_meth_all_btn = findViewById(R.id.pay_meth_all_btn);
        pay_meth_all_1_btn = findViewById(R.id.pay_meth_all_1_btn);
        pay_meth_all_page = findViewById(R.id.pay_meth_all_page);


        //--------   Today   -----
        pay_meth_today_btn = findViewById(R.id.pay_meth_today_btn);
        pay_meth_today_1_btn = findViewById(R.id.pay_meth_today_1_btn);
        pay_meth_today_page = findViewById(R.id.pay_meth_today_page);


        //--------   Weekly   -----
        pay_meth_weekly_btn = findViewById(R.id.pay_meth_weekly_btn);
        pay_meth_weekly_1_btn = findViewById(R.id.pay_meth_weekly_1_btn);
        pay_meth_weekly_page = findViewById(R.id.pay_meth_weekly_page);


        //--------   Monthly   -----
        pay_meth_monthly_btn = findViewById(R.id.pay_meth_monthly_btn);
        pay_meth_monthly_1_btn = findViewById(R.id.pay_meth_monthly_1_btn);
        pay_meth_monthly_page = findViewById(R.id.pay_meth_monthly_page);


        //--------   Yearly   -----
        pay_meth_yearly_btn = findViewById(R.id.pay_meth_yearly_btn);
        pay_meth_yearly_1_btn = findViewById(R.id.pay_meth_yearly_1_btn);
        pay_meth_yearly_page = findViewById(R.id.pay_meth_yearly_page);

        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

        list = new ArrayList<>();

    }

    // Dark/ Light set textview
    private void darkLightSet() {
        //Dark and Light Mode
        if (isDarkMode) {
            // weekly
            less_pay_chart_weekly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_pay_chart_weekly.setImageDrawable(getDrawable(R.drawable.greater_then_light));

            // monthly
            less_pay_chart_monthly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_pay_chart_monthly.setImageDrawable(getDrawable(R.drawable.greater_then_light));

            // yearly
            less_pay_chart_yearly.setImageDrawable(getDrawable(R.drawable.less_than_light));
            greater_pay_chart_yearly.setImageDrawable(getDrawable(R.drawable.greater_then_light));

        }
        else{

            // weekly
            less_pay_chart_weekly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_pay_chart_weekly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));

            // monthly
            less_pay_chart_monthly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_pay_chart_monthly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));

            // yearly
            less_pay_chart_yearly.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            greater_pay_chart_yearly.setImageDrawable(getDrawable(R.drawable.greater_then_dark));

        }

    }

    // All Data
    private void allDataShow() {

        // xxxxx Default Show List Data xxxxx
        fetchAllData();

        // xxxxx pay_meth_all_btn Click Data Refresh xxxxx
        pay_meth_all_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                pay_meth_today_1_btn.setVisibility(View.VISIBLE);
                pay_meth_weekly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_monthly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_yearly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_all_1_btn.setVisibility(View.INVISIBLE);

                //btn-1
                card_all_pay_meth_chart.setVisibility(View.VISIBLE);
                card_today_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_weekly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_monthly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_yearly_pay_meth_chart.setVisibility(View.INVISIBLE);

                // show layout
                pay_meth_all_page.setVisibility(View.VISIBLE);
                pay_meth_today_page.setVisibility(View.GONE);
                pay_meth_weekly_page.setVisibility(View.GONE);
                pay_meth_monthly_page.setVisibility(View.GONE);
                pay_meth_yearly_page.setVisibility(View.GONE);

                //Textview
                pay_chart_textView_all.setVisibility(View.VISIBLE);
                pay_chart_textView_today.setVisibility(View.GONE);
                tableLayout_less_greater_weekly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_monthly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_yearly_pay_chart.setVisibility(View.GONE);

                fetchAllData();

            }
        });

    }

    // Today Data
    private void todayDataShow() {

        // xxxxx pay_meth_today_btn  Click Data Refresh xxxxx
        pay_meth_today_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                pay_meth_weekly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_monthly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_yearly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_all_1_btn.setVisibility(View.VISIBLE);
                pay_meth_today_1_btn.setVisibility(View.INVISIBLE);

                //btn-1
                card_all_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_today_pay_meth_chart.setVisibility(View.VISIBLE);
                card_weekly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_monthly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_yearly_pay_meth_chart.setVisibility(View.INVISIBLE);

                // show layout
                pay_meth_all_page.setVisibility(View.GONE);
                pay_meth_today_page.setVisibility(View.VISIBLE);
                pay_meth_weekly_page.setVisibility(View.GONE);
                pay_meth_monthly_page.setVisibility(View.GONE);
                pay_meth_yearly_page.setVisibility(View.GONE);

                //Textview
                pay_chart_textView_all.setVisibility(View.GONE);
                pay_chart_textView_today.setVisibility(View.VISIBLE);
                tableLayout_less_greater_weekly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_monthly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_yearly_pay_chart.setVisibility(View.GONE);


                fetchTodayData();

            }
        });

    }

    // Weekly Data
    private void weeklyDataShow() {

        // xxxxx pay_meth_weekly_btn_1  Click Data Refresh xxxxx
        pay_meth_weekly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();

                //btn-2
                pay_meth_monthly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_yearly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_all_1_btn.setVisibility(View.VISIBLE);
                pay_meth_today_1_btn.setVisibility(View.VISIBLE);
                pay_meth_weekly_1_btn.setVisibility(View.INVISIBLE);

                //btn-1
                card_all_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_today_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_weekly_pay_meth_chart.setVisibility(View.VISIBLE);
                card_monthly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_yearly_pay_meth_chart.setVisibility(View.INVISIBLE);


                // show layout
                pay_meth_all_page.setVisibility(View.GONE);
                pay_meth_today_page.setVisibility(View.GONE);
                pay_meth_weekly_page.setVisibility(View.VISIBLE);
                pay_meth_monthly_page.setVisibility(View.GONE);
                pay_meth_yearly_page.setVisibility(View.GONE);

                //Textview
                pay_chart_textView_all.setVisibility(View.GONE);
                pay_chart_textView_today.setVisibility(View.GONE);
                tableLayout_less_greater_weekly_pay_chart.setVisibility(View.VISIBLE);
                tableLayout_less_greater_monthly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_yearly_pay_chart.setVisibility(View.GONE);

                fetchWeeklyData();

            }
        });

        // xxxxx pay_meth_weekly_btn  Click Data Refresh xxxxx
        pay_meth_weekly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetToCurrentWeek();
                updateCurrentWeekDates();

                fetchWeeklyData();

            }
        });

        less_pay_chart_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousWeekDates();
                fetchWeeklyData();
            }
        });

        greater_pay_chart_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWeekDates();
                fetchWeeklyData();
            }
        });

    }

    // Monthly Data
    private void monthlyDataShow() {

        // xxxxx pay_meth_monthly_btn_1  Click Data Refresh xxxxx
        pay_meth_monthly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();

                //btn-2
                pay_meth_yearly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_all_1_btn.setVisibility(View.VISIBLE);
                pay_meth_today_1_btn.setVisibility(View.VISIBLE);
                pay_meth_weekly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_monthly_1_btn.setVisibility(View.INVISIBLE);

                //btn-1
                card_all_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_today_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_weekly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_monthly_pay_meth_chart.setVisibility(View.VISIBLE);
                card_yearly_pay_meth_chart.setVisibility(View.INVISIBLE);

                // show layout
                pay_meth_all_page.setVisibility(View.GONE);
                pay_meth_today_page.setVisibility(View.GONE);
                pay_meth_weekly_page.setVisibility(View.GONE);
                pay_meth_monthly_page.setVisibility(View.VISIBLE);
                pay_meth_yearly_page.setVisibility(View.GONE);

                //Textview
                pay_chart_textView_all.setVisibility(View.GONE);
                pay_chart_textView_today.setVisibility(View.GONE);
                tableLayout_less_greater_weekly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_monthly_pay_chart.setVisibility(View.VISIBLE);
                tableLayout_less_greater_yearly_pay_chart.setVisibility(View.GONE);

                fetchMonthlyData();

            }
        });


        // xxxxx pay_meth_weekly_btn  Click Data Refresh xxxxx
        pay_meth_monthly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetToCurrentMonth();
                updateCurrentMonthDates();

                fetchMonthlyData();

            }
        });

        less_pay_chart_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousMonthDates();
                fetchMonthlyData();
            }
        });

        greater_pay_chart_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextMonthDates();
                fetchMonthlyData();
            }
        });

    }

    // Yearly Data
    private void yearlyDataShow() {

        // xxxxx pay_meth_yearly_btn_1  Click Data Refresh xxxxx
        pay_meth_yearly_1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();

                //btn-2
                pay_meth_yearly_1_btn.setVisibility(View.INVISIBLE);
                pay_meth_all_1_btn.setVisibility(View.VISIBLE);
                pay_meth_today_1_btn.setVisibility(View.VISIBLE);
                pay_meth_weekly_1_btn.setVisibility(View.VISIBLE);
                pay_meth_monthly_1_btn.setVisibility(View.VISIBLE);

                //btn-1
                card_all_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_today_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_weekly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_monthly_pay_meth_chart.setVisibility(View.INVISIBLE);
                card_yearly_pay_meth_chart.setVisibility(View.VISIBLE);

                // show layout
                pay_meth_all_page.setVisibility(View.GONE);
                pay_meth_today_page.setVisibility(View.GONE);
                pay_meth_weekly_page.setVisibility(View.GONE);
                pay_meth_monthly_page.setVisibility(View.GONE);
                pay_meth_yearly_page.setVisibility(View.VISIBLE);

                //Textview
                pay_chart_textView_all.setVisibility(View.GONE);
                pay_chart_textView_today.setVisibility(View.GONE);
                tableLayout_less_greater_weekly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_monthly_pay_chart.setVisibility(View.GONE);
                tableLayout_less_greater_yearly_pay_chart.setVisibility(View.VISIBLE);

                fetchYearlyData();

            }
        });

        // xxxxx pay_meth_weekly_btn  Click Data Refresh xxxxx
        pay_meth_yearly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetToCurrentYear();
                updateCurrentYearDates();

                fetchYearlyData();

            }
        });

        less_pay_chart_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousYearDates();
                fetchYearlyData();
            }
        });

        greater_pay_chart_yearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextYearDates();
                fetchYearlyData();
            }
        });

    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching All Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching All Data
    @SuppressLint("Range")
    private void fetchAllData() {

        try {
            list.clear();

            String query = "SELECT paymentmethod, SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE user_email = ? GROUP BY paymentmethod ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = database.rawQuery(query, new String[]{userEmail});

            if (cursor1.moveToFirst()) {
                do {

                    String paymentMethod = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("total_incomeexpense"));
                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, paymentMethod);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            pay_adapter = new Payment_method_Adapter(Payment_Method_Chart_Activity.this, list, database, userEmail);
            pay_meth_all_list.setAdapter(pay_adapter);
            setListViewHeightBasedOnChildren(pay_meth_all_list);  // Adjust ListView height based on its content

            checkListDataAll();

            pay_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Calculate sum of income where payment method is "Cash"
        String query2 = "SELECT 'Total' AS paymentmethod, SUM(income - expense) AS net_income FROM incexp_tbl WHERE user_email = ?";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});
        int totalIncome = 0;
        if (cursor2.moveToFirst()) {
            totalIncome = cursor2.getInt(cursor2.getColumnIndex("net_income"));
        }

        all_total = totalIncome;

        String balanceText = getResources().getString(R.string.bar_cat_chart_balance);
        String centerText = balanceText + " " + all_total;
        amount_total_all.setText(String.valueOf(centerText));


        // add data in Barchart
        String query3 = "SELECT paymentmethod , SUM(income - expense) total_incomeexpense FROM incexp_tbl  WHERE user_email = ? GROUP BY paymentmethod ORDER BY total_incomeexpense DESC ";
        Cursor cursor3 = database.rawQuery(query3, new String[]{userEmail});
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        int index = 0;

        while (cursor3.moveToNext()) {
            String category = cursor3.getString(0);
            int expense;
            expense = cursor3.getInt(1);
            if (expense != 0) {
                entries.add(new BarEntry(index, expense));
            }
            categories.add(category);
            index++;

        }
        BarDataSet dataSet = new BarDataSet(entries, "Report");
        if (isDarkMode) {
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            dataSet.setValueTextColor(Color.WHITE);
        } else {
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            dataSet.setValueTextColor(Color.BLACK);
        }
        dataSet.setValueTextSize(8f);


        BarData barData = new BarData(dataSet);
        pay_meth_bar_chart_all.setFitBars(true);
        pay_meth_bar_chart_all.setData(barData);
        pay_meth_bar_chart_all.animateY(1000);

        // Customize YAxis
        YAxis leftYAxis = pay_meth_bar_chart_all.getAxisLeft();
        leftYAxis.setAxisLineWidth(0.5f);
        if (isDarkMode) {
            leftYAxis.setAxisLineColor(Color.GRAY);
            leftYAxis.setTextColor(Color.LTGRAY);

        } else {
            leftYAxis.setAxisLineColor(Color.BLACK);
            leftYAxis.setTextColor(Color.BLACK);

        }

        // Customize YAxis
        YAxis rightYAxis = pay_meth_bar_chart_all.getAxisRight();
        rightYAxis.setAxisLineWidth(0.5f);

        if (isDarkMode) {
            rightYAxis.setAxisLineColor(Color.GRAY);
            rightYAxis.setTextColor(Color.LTGRAY);

        } else {
            rightYAxis.setAxisLineColor(Color.BLACK);
            rightYAxis.setTextColor(Color.BLACK);
        }
        // Customize XAxis
        XAxis xAxis = pay_meth_bar_chart_all.getXAxis();
        xAxis.setAxisLineWidth(0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        if (isDarkMode) {
            xAxis.setAxisLineColor(Color.GRAY);
        } else {
            xAxis.setAxisLineColor(Color.BLACK);

        }
        xAxis.setDrawGridLines(false);


        pay_meth_bar_chart_all.getDescription().setEnabled(false);
        pay_meth_bar_chart_all.getLegend().setEnabled(false);

        //click to BAR CHART
        pay_meth_bar_chart_all.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int value1 = Math.round(e.getY());

                int index = (int) e.getX();
                String category = categories.get(index);
                if (e instanceof BarEntry) {
                    Toast.makeText(Payment_Method_Chart_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Today Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Today Data
    @SuppressLint("Range")
    private void fetchTodayData() {

        try {

            list.clear();

            String query = "SELECT paymentmethod, SUM(income - expense) AS incomeexpense FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') GROUP BY paymentmethod ORDER BY incomeexpense DESC";
            Cursor cursor1 = database.rawQuery(query, new String[]{userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String payment = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("incomeexpense"));

                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, payment);
                    list.add(model);
                } while (cursor1.moveToNext());
            }


            pay_adapter = new Payment_method_Adapter(Payment_Method_Chart_Activity.this, list, database, userEmail);
            pay_meth_today_list.setAdapter(pay_adapter);

            checkListDataToday();

            pay_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Calculate sum of income where payment method is "Cash"
        String query2 = "SELECT 'Total' AS paymentmethod, SUM(income - expense) AS net_income FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') ";
        Cursor cursor2 = database.rawQuery(query2, new String[]{userEmail});

        int totalIncome = 0;

        if (cursor2.moveToFirst()) {
            totalIncome = cursor2.getInt(cursor2.getColumnIndex("net_income"));
        }

        today_total = totalIncome;
        String balanceText = getResources().getString(R.string.bar_cat_chart_balance);
        String centerText = balanceText + " " + today_total;
        amount_total_today.setText(String.valueOf(centerText));

        // add data in Barchart
        String query3 = "SELECT paymentmethod, SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') GROUP BY paymentmethod ORDER BY total_incomeexpense DESC";
        Cursor cursor3 = database.rawQuery(query3, new String[]{userEmail});

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        int index = 0;

        while (cursor3.moveToNext()) {
            String category = cursor3.getString(0);
            int expense;
            expense = cursor3.getInt(1);
            if (expense != 0) {
                entries.add(new BarEntry(index, expense));
            }
            categories.add(category);
            index++;

        }

        BarDataSet dataSet = new BarDataSet(entries, "Report");
        if (isDarkMode) {
            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            dataSet.setValueTextColor(Color.WHITE);
        } else {
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            dataSet.setValueTextColor(Color.BLACK);
        }
        dataSet.setValueTextSize(8f);


        BarData barData = new BarData(dataSet);
        pay_meth_bar_chart_today.setFitBars(true);
        pay_meth_bar_chart_today.setData(barData);
        pay_meth_bar_chart_today.animateY(1000);

        // Customize YAxis
        YAxis leftYAxis = pay_meth_bar_chart_today.getAxisLeft();
        leftYAxis.setAxisLineWidth(0.5f);
        if (isDarkMode) {
            leftYAxis.setAxisLineColor(Color.GRAY);
            leftYAxis.setTextColor(Color.LTGRAY);

        } else {
            leftYAxis.setAxisLineColor(Color.BLACK);
            leftYAxis.setTextColor(Color.BLACK);

        }

        // Customize YAxis
        YAxis rightYAxis = pay_meth_bar_chart_today.getAxisRight();
        rightYAxis.setAxisLineWidth(0.5f);

        if (isDarkMode) {
            rightYAxis.setAxisLineColor(Color.GRAY);
            rightYAxis.setTextColor(Color.LTGRAY);

        } else {
            rightYAxis.setAxisLineColor(Color.BLACK);
            rightYAxis.setTextColor(Color.BLACK);
        }

        // Customize XAxis
        XAxis xAxis = pay_meth_bar_chart_today.getXAxis();
        xAxis.setAxisLineWidth(0.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        if (isDarkMode) {
            xAxis.setAxisLineColor(Color.GRAY);
        } else {
            xAxis.setAxisLineColor(Color.BLACK);

        }
        xAxis.setDrawGridLines(false);


        pay_meth_bar_chart_today.getDescription().setEnabled(false);
        pay_meth_bar_chart_today.getLegend().setEnabled(false);

        //click to BAR CHART
        pay_meth_bar_chart_today.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int value1 = Math.round(e.getY());

                int index = (int) e.getX();
                String category = categories.get(index);
                if (e instanceof BarEntry) {
                    Toast.makeText(Payment_Method_Chart_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Weekly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Weekly Data
    @SuppressLint("Range")
    private void fetchWeeklyData() {

        try {
            list.clear();
            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();


            String query1 = "SELECT  paymentmethod,SUM(income - expense) AS total_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_income DESC";
            Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, endDate, userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String payments = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("total_income"));

                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, payments);
                    list.add(model);
                } while (cursor1.moveToNext());
            }
            cursor1.close();

            pay_adapter = new Payment_method_Adapter(Payment_Method_Chart_Activity.this, list, database, userEmail);
            pay_meth_weekly_list.setAdapter(pay_adapter);

            checkListDataWeekly();

            pay_adapter.notifyDataSetChanged();


            // Calculate sum of income where payment method is "Cash"

            String query2 = "SELECT  'Total' AS paymentmethod, SUM(income - expense) AS net_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
            Cursor cursor2 = database.rawQuery(query2, new String[]{startDate, endDate, userEmail});

            int totalIncome = 0;
            if (cursor2.moveToFirst()) {
                totalIncome = cursor2.getInt(cursor2.getColumnIndex("net_income"));
            }

            weekly_total = totalIncome;
            String balanceText = getResources().getString(R.string.bar_cat_chart_balance);
            String centerText = balanceText + " " + weekly_total;
            amount_total_weekly.setText(String.valueOf(centerText));

            //add data in Barchart

            String query3 = "SELECT  paymentmethod,SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_incomeexpense DESC";
            Cursor cursor3 = database.rawQuery(query3, new String[]{startDate, endDate, userEmail});

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> categories = new ArrayList<>();
            int index = 0;

            while (cursor3.moveToNext()) {
                String category = cursor3.getString(0);
                int expense;
                expense = cursor3.getInt(1);
                if (expense != 0) {
                    entries.add(new BarEntry(index, expense));
                }
                categories.add(category);
                index++;

            }

            BarDataSet dataSet = new BarDataSet(entries, "Report");
            if (isDarkMode) {
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                dataSet.setValueTextColor(Color.WHITE);
            } else {
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
            }
            dataSet.setValueTextSize(8f);


            BarData barData = new BarData(dataSet);
            pay_meth_bar_chart_weekly.setFitBars(true);
            pay_meth_bar_chart_weekly.setData(barData);
            pay_meth_bar_chart_weekly.animateY(1000);

            // Customize YAxis
            YAxis leftYAxis = pay_meth_bar_chart_weekly.getAxisLeft();
            leftYAxis.setAxisLineWidth(0.5f);
            if (isDarkMode) {
                leftYAxis.setAxisLineColor(Color.GRAY);
                leftYAxis.setTextColor(Color.LTGRAY);

            } else {
                leftYAxis.setAxisLineColor(Color.BLACK);
                leftYAxis.setTextColor(Color.BLACK);

            }

            // Customize YAxis
            YAxis rightYAxis = pay_meth_bar_chart_weekly.getAxisRight();
            rightYAxis.setAxisLineWidth(0.5f);

            if (isDarkMode) {
                rightYAxis.setAxisLineColor(Color.GRAY);
                rightYAxis.setTextColor(Color.LTGRAY);

            } else {
                rightYAxis.setAxisLineColor(Color.BLACK);
                rightYAxis.setTextColor(Color.BLACK);
            }

            // Customize XAxis
            XAxis xAxis = pay_meth_bar_chart_weekly.getXAxis();
            xAxis.setAxisLineWidth(0.5f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(false);
            if (isDarkMode) {
                xAxis.setAxisLineColor(Color.GRAY);
            } else {
                xAxis.setAxisLineColor(Color.BLACK);

            }
            xAxis.setDrawGridLines(false);


            pay_meth_bar_chart_weekly.getDescription().setEnabled(false);
            pay_meth_bar_chart_weekly.getLegend().setEnabled(false);

            //click to BAR CHART
            pay_meth_bar_chart_weekly.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    int value1 = Math.round(e.getY());

                    int index = (int) e.getX();
                    String category = categories.get(index);
                    if (e instanceof BarEntry) {
                        Toast.makeText(Payment_Method_Chart_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onNothingSelected() {

                }
            });


        } catch (Exception e) {
            Toast.makeText(Payment_Method_Chart_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Monthly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Monthly Data
    @SuppressLint("Range")
    private void fetchMonthlyData() {

        try {
            list.clear();
            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();


            String query1 = "SELECT  paymentmethod,SUM(income - expense) AS total_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_income DESC";
            Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, endDate, userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String payments = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("total_income"));

                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, payments);
                    list.add(model);
                } while (cursor1.moveToNext());
            }
            cursor1.close();
            pay_adapter = new Payment_method_Adapter(Payment_Method_Chart_Activity.this, list, database, userEmail);
            pay_meth_monthly_list.setAdapter(pay_adapter);

            checkListDataMonthly();

            pay_adapter.notifyDataSetChanged();

            // Calculate sum of income where payment method is "Cash"
            String query2 = "SELECT  'Total' AS paymentmethod, SUM(income - expense) AS net_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
            Cursor cursor2 = database.rawQuery(query2, new String[]{startDate, endDate, userEmail});

            int totalIncome = 0;
            if (cursor2.moveToFirst()) {
                totalIncome = cursor2.getInt(cursor2.getColumnIndex("net_income"));
            }

            monthly_total = totalIncome;
            String balanceText = getResources().getString(R.string.bar_cat_chart_balance);
            String centerText = balanceText + " " + monthly_total;
            amount_total_monthly.setText(String.valueOf(centerText));

            //add data in Barchart

            String query3 = "SELECT  paymentmethod,SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_incomeexpense DESC";
            Cursor cursor3 = database.rawQuery(query3, new String[]{startDate, endDate, userEmail});

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> categories = new ArrayList<>();
            int index = 0;

            while (cursor3.moveToNext()) {
                String category = cursor3.getString(0);
                int expense;
                expense = cursor3.getInt(1);
                if (expense != 0) {
                    entries.add(new BarEntry(index, expense));
                }
                categories.add(category);
                index++;

            }

            BarDataSet dataSet = new BarDataSet(entries, "Report");
            if (isDarkMode) {
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                dataSet.setValueTextColor(Color.WHITE);
            } else {
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
            }
            dataSet.setValueTextSize(8f);


            BarData barData = new BarData(dataSet);
            pay_meth_bar_chart_monthly.setFitBars(true);
            pay_meth_bar_chart_monthly.setData(barData);
            pay_meth_bar_chart_monthly.animateY(1000);

            // Customize YAxis
            YAxis leftYAxis = pay_meth_bar_chart_monthly.getAxisLeft();
            leftYAxis.setAxisLineWidth(0.5f);
            if (isDarkMode) {
                leftYAxis.setAxisLineColor(Color.GRAY);
                leftYAxis.setTextColor(Color.LTGRAY);

            } else {
                leftYAxis.setAxisLineColor(Color.BLACK);
                leftYAxis.setTextColor(Color.BLACK);

            }

            // Customize YAxis
            YAxis rightYAxis = pay_meth_bar_chart_monthly.getAxisRight();
            rightYAxis.setAxisLineWidth(0.5f);

            if (isDarkMode) {
                rightYAxis.setAxisLineColor(Color.GRAY);
                rightYAxis.setTextColor(Color.LTGRAY);

            } else {
                rightYAxis.setAxisLineColor(Color.BLACK);
                rightYAxis.setTextColor(Color.BLACK);
            }

            // Customize XAxis
            XAxis xAxis = pay_meth_bar_chart_monthly.getXAxis();
            xAxis.setAxisLineWidth(0.5f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(false);
            if (isDarkMode) {
                xAxis.setAxisLineColor(Color.GRAY);
            } else {
                xAxis.setAxisLineColor(Color.BLACK);

            }
            xAxis.setDrawGridLines(false);


            pay_meth_bar_chart_monthly.getDescription().setEnabled(false);
            pay_meth_bar_chart_monthly.getLegend().setEnabled(false);

            //click to BAR CHART
            pay_meth_bar_chart_monthly.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    int value1 = Math.round(e.getY());

                    int index = (int) e.getX();
                    String category = categories.get(index);
                    if (e instanceof BarEntry) {
                        Toast.makeText(Payment_Method_Chart_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onNothingSelected() {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Yearly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Yearly Data
    @SuppressLint("Range")
    private void fetchYearlyData() {

        try {
            list.clear();
            String startDate = getYearStartDate();
            String endDate = getYearEndDate();


            String query1 = "SELECT  paymentmethod,SUM(income - expense) AS total_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_income DESC";
            Cursor cursor1 = database.rawQuery(query1, new String[]{startDate, endDate, userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String payments = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("total_income"));

                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, payments);
                    list.add(model);
                } while (cursor1.moveToNext());
            }
            cursor1.close();
            pay_adapter = new Payment_method_Adapter(Payment_Method_Chart_Activity.this, list, database, userEmail);
            pay_meth_yearly_list.setAdapter(pay_adapter);

            checkListDataYearly();

            pay_adapter.notifyDataSetChanged();

            // Calculate sum of income where payment method is "Cash"
            String query2 = "SELECT  'Total' AS paymentmethod, SUM(income - expense) AS net_income FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?";
            Cursor cursor2 = database.rawQuery(query2, new String[]{startDate, endDate, userEmail});

            int totalIncome = 0;
            if (cursor2.moveToFirst()) {
                totalIncome = cursor2.getInt(cursor2.getColumnIndex("net_income"));
            }

            yearly_total = totalIncome;
            String balanceText = getResources().getString(R.string.bar_cat_chart_balance);
            String centerText = balanceText + " " + yearly_total;
            amount_total_yearly.setText(String.valueOf("Total : " + centerText));

            //add data in Barchart

            String query3 = "SELECT  paymentmethod,SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY paymentmethod ORDER By total_incomeexpense DESC";
            Cursor cursor3 = database.rawQuery(query3, new String[]{startDate, endDate, userEmail});

            ArrayList<BarEntry> entries = new ArrayList<>();
            ArrayList<String> categories = new ArrayList<>();
            int index = 0;

            while (cursor3.moveToNext()) {
                String category = cursor3.getString(0);
                int expense;
                expense = cursor3.getInt(1);
                if (expense != 0) {
                    entries.add(new BarEntry(index, expense));
                }
                categories.add(category);
                index++;

            }

            BarDataSet dataSet = new BarDataSet(entries, "Report");
            if (isDarkMode) {
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                dataSet.setValueTextColor(Color.WHITE);
            } else {
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
            }
            dataSet.setValueTextSize(8f);


            BarData barData = new BarData(dataSet);
            pay_meth_bar_chart_yearly.setFitBars(true);
            pay_meth_bar_chart_yearly.setData(barData);
            pay_meth_bar_chart_yearly.animateY(1000);

            // Customize YAxis
            YAxis leftYAxis = pay_meth_bar_chart_yearly.getAxisLeft();
            leftYAxis.setAxisLineWidth(0.5f);
            if (isDarkMode) {
                leftYAxis.setAxisLineColor(Color.GRAY);
                leftYAxis.setTextColor(Color.LTGRAY);

            } else {
                leftYAxis.setAxisLineColor(Color.BLACK);
                leftYAxis.setTextColor(Color.BLACK);

            }

            // Customize YAxis
            YAxis rightYAxis = pay_meth_bar_chart_yearly.getAxisRight();
            rightYAxis.setAxisLineWidth(0.5f);

            if (isDarkMode) {
                rightYAxis.setAxisLineColor(Color.GRAY);
                rightYAxis.setTextColor(Color.LTGRAY);

            } else {
                rightYAxis.setAxisLineColor(Color.BLACK);
                rightYAxis.setTextColor(Color.BLACK);
            }

            // Customize XAxis
            XAxis xAxis = pay_meth_bar_chart_yearly.getXAxis();
            xAxis.setAxisLineWidth(0.5f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawLabels(false);
            if (isDarkMode) {
                xAxis.setAxisLineColor(Color.GRAY);
            } else {
                xAxis.setAxisLineColor(Color.BLACK);

            }
            xAxis.setDrawGridLines(false);


            pay_meth_bar_chart_yearly.getDescription().setEnabled(false);
            pay_meth_bar_chart_yearly.getLegend().setEnabled(false);

            //click to BAR CHART
            pay_meth_bar_chart_yearly.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    int value1 = Math.round(e.getY());

                    int index = (int) e.getX();
                    String category = categories.get(index);
                    if (e instanceof BarEntry) {
                        Toast.makeText(Payment_Method_Chart_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onNothingSelected() {

                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        pay_chart_textView_weekly.setText(dateRange); // Update your TextView here
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
        pay_chart_textView_monthly.setText(dateRange);
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
        pay_chart_textView_yearly.setText(dateRange);
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


    // Listview Height set
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // Pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // Check List data isempty
    private void checkListDataAll() {
        if (list.isEmpty()) {
            linear_all_pay_meth.setVisibility(View.GONE);
            pay_meth_all_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_all_pay_meth.setVisibility(View.VISIBLE);
            pay_meth_all_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataToday() {
        if (list.isEmpty()) {
            linear_today_pay_meth.setVisibility(View.GONE);
            pay_meth_today_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_today_pay_meth.setVisibility(View.VISIBLE);
            pay_meth_today_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataWeekly() {
        if (list.isEmpty()) {
            linear_weekly_pay_meth.setVisibility(View.GONE);
            pay_meth_weekly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_weekly_pay_meth.setVisibility(View.VISIBLE);
            pay_meth_weekly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataMonthly() {
        if (list.isEmpty()) {
            linear_monthly_pay_meth.setVisibility(View.GONE);
            pay_meth_monthly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_monthly_pay_meth.setVisibility(View.VISIBLE);
            pay_meth_monthly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataYearly() {
        if (list.isEmpty()) {
            linear_yearly_pay_meth.setVisibility(View.GONE);
            pay_meth_yearly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_yearly_pay_meth.setVisibility(View.VISIBLE);
            pay_meth_yearly_no_data_img.setVisibility(View.GONE);
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
        pay_meth_all_btn.setText(R.string.all_btn_txt_lay2_cat_chart);
        pay_meth_all_1_btn.setText(R.string.all_btn_txt_lay2_cat_chart);

        pay_meth_today_btn.setText(R.string.today_btn_txt_lay2_cat_chart);
        pay_meth_today_1_btn.setText(R.string.today_btn_txt_lay2_cat_chart);

        pay_meth_weekly_btn.setText(R.string.weekly_btn_txt_lay2_cat_chart);
        pay_meth_weekly_1_btn.setText(R.string.weekly_btn_txt_lay2_cat_chart);

        pay_meth_monthly_btn.setText(R.string.monthly_btn_txt_lay2_cat_chart);
        pay_meth_monthly_1_btn.setText(R.string.monthly_btn_txt_lay2_cat_chart);

        pay_meth_yearly_btn.setText(R.string.yearly_btn_txt_lay2_cat_chart);
        pay_meth_yearly_1_btn.setText(R.string.yearly_btn_txt_lay2_cat_chart);

//        // Layout ---- 2
        pay_chart_textView_all.setText(R.string.all_btn_txt_lay3_cat_chart);
        pay_chart_textView_today.setText(R.string.today_btn_txt_lay3_cat_chart);
        pay_chart_textView_weekly.setText(R.string.weekly_btn_txt_lay3_cat_chart);
        pay_chart_textView_monthly.setText(R.string.monthly_btn_txt_lay3_cat_chart);
        pay_chart_textView_yearly.setText(R.string.yearly_btn_txt_lay3_cat_chart);


//        // Layout ---- 3
        pay_meth_txt_all_l1.setText(R.string.pay_meth_txt_lay4_all_pm);
        amount_txt_all_l1.setText(R.string.amount_txt_lay4_all_pm);

        pay_meth_txt_today_l1.setText(R.string.pay_meth_txt_lay4_all_pm);
        amount_txt_today_l1.setText(R.string.amount_txt_lay4_all_pm);

        pay_meth_txt_weekly_l1.setText(R.string.pay_meth_txt_lay4_all_pm);
        amount_txt_weekly_l1.setText(R.string.amount_txt_lay4_all_pm);

        pay_meth_txt_monthly_l1.setText(R.string.pay_meth_txt_lay4_all_pm);
        amount_txt_monthly_l1.setText(R.string.amount_txt_lay4_all_pm);

        pay_meth_txt_yearly_l1.setText(R.string.pay_meth_txt_lay4_all_pm);
        amount_txt_yearly_l1.setText(R.string.amount_txt_lay4_all_pm);


    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


}