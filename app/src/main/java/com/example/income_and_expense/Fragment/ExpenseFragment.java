package com.example.income_and_expense.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.income_and_expense.Adapter.Category_Adapter;
import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ExpenseFragment extends Fragment {

    //                                          -----  ALL  -----
    PieChart cat_chart_pie_chart_all_expense;

    TextView cat_chart_expense_all_transaction_txt_expense, cat_1_chart_expense_all_transaction_txt_expense;
    TextView cat_chart_textView_all_expense;

    Button cat_chart_all_btn_expense, cat_chart_all_1_btn_expense;
    RelativeLayout cat_chart_all_page_expense;

    int Income_all = 0, Expense_all = 0;

    CardView card_all_category_chart_expense;
    ListView cat_all_list_expense;

    TextView cat_txt_exp_all, amount_txt_exp_all;

    ImageView expense_fragment_all_no_data_img;
    LinearLayout linear_all_expense_fragment;


    //                                           -----  TODAY  -----
    PieChart cat_chart_pie_chart_today_expense;

    TextView cat_chart_expense_today_transaction_txt_expense, cat_1_chart_expense_today_transaction_txt_expense;
    TextView cat_chart_textView_today_expense;

    Button cat_chart_today_btn_expense, cat_chart_today_1_btn_expense;
    RelativeLayout cat_chart_today_page_expense;

    int Income_today = 0, Expense_today = 0;

    CardView card_today_category_chart_expense;

    ListView cat_today_list_expense;

    TextView cat_txt_exp_today, amount_txt_exp_today;

    ImageView expense_fragment_today_no_data_img;
    LinearLayout linear_today_expense_fragment;


    //                                           -----  WEEKLY  -----
    PieChart cat_chart_pie_chart_weekly_expense;

    TextView cat_chart_expense_weekly_transaction_txt_expense, cat_1_chart_expense_weekly_transaction_txt_expense;

    TableLayout expense_less_greater_weekly_cat_chart;
    TextView cat_chart_textView_weekly_expense;
    ImageView less_cat_chart_weekly_expense, greater_cat_chart_weekly_expense;

    Button cat_chart_weekly_btn_expense, cat_chart_weekly_1_btn_expense;
    RelativeLayout cat_chart_weekly_page_expense;

    int Income_weekly = 0, Expense_weekly = 0;
    CardView card_weekly_category_chart_expense;

    ListView cat_weekly_list_expense;

    TextView cat_txt_exp_weekly, amount_txt_exp_weekly;

    ImageView expense_fragment_weekly_no_data_img;
    LinearLayout linear_weekly_expense_fragment;


    //                                           -----  MONTHLY  -----

    PieChart cat_chart_pie_chart_monthly_expense;

    TextView cat_chart_expense_monthly_transaction_txt_expense, cat_1_chart_expense_monthly_transaction_txt_expense;

    TableLayout expense_less_greater_monthly_cat_chart;
    TextView cat_chart_textView_monthly_expense;

    ImageView less_cat_chart_monthly_expense, greater_cat_chart_monthly_expense;


    Button cat_chart_monthly_btn_expense, cat_chart_monthly_1_btn_expense;
    RelativeLayout cat_chart_monthly_page_expense;

    int Income_monthly = 0, Expense_monthly = 0;

    CardView card_monthly_category_chart_expense;

    ListView cat_monthly_list_expense;

    TextView cat_txt_exp_monthly, amount_txt_exp_monthly;

    ImageView expense_fragment_monthly_no_data_img;
    LinearLayout linear_monthly_expense_fragment;



    //                                           -----  YEARLY  -----

    PieChart cat_chart_pie_chart_yearly_expense;

    TextView cat_chart_expense_yearly_transaction_txt_expense, cat_1_chart_expense_yearly_transaction_txt_expense;

    TableLayout expense_less_greater_yearly_cat_chart;
    TextView cat_chart_textView_yearly_expense;

    ImageView less_cat_chart_yearly_expense, greater_cat_chart_yearly_expense;


    Button cat_chart_yearly_btn_expense, cat_chart_yearly_1_btn_expense;
    RelativeLayout cat_chart_yearly_page_expense;

    int Income_yearly = 0, Expense_yearly = 0;
    CardView card_yearly_category_chart_expense;

    ListView cat_yearly_list_expense;

    TextView cat_txt_exp_yearly, amount_txt_exp_yearly;

    ImageView expense_fragment_yearly_no_data_img;
    LinearLayout linear_yearly_expense_fragment;


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    int[] color;
    int all_balance = 0, today_balance = 0, weekly_balance = 0, monthly_balance = 0, yearly_balance = 0;

    private List<Income_Expense_Model> list = new ArrayList<>();
    Category_Adapter cat_adapter;

    RelativeLayout exp_frg;

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
    SQLiteDatabase db;

    private SQLiteDatabase CreateDatabase() {
        return getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (isDarkMode) {
            color = new int[]{Color.rgb(255, 56, 56)};
        } else {
            color = new int[]{Color.rgb(230, 49, 49)};

        }
        // Language default
        setLocal(getSavedLanguage());

        exp_frg = view.findViewById(R.id.exp_frg);

        userEmail = getArguments().getString("USER_EMAIL");


        currentMonth = Calendar.getInstance();
        calendar = Calendar.getInstance();

        db = CreateDatabase();

        // Initialize views
        initializeViews(view);

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

        return view;
    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


    // Initialize views
    private void initializeViews(View view) {

        //                                       --------   All   -----

        cat_chart_expense_all_transaction_txt_expense = view.findViewById(R.id.cat_chart_expense_all_transaction_txt_expense);
        cat_1_chart_expense_all_transaction_txt_expense = view.findViewById(R.id.cat_1_chart_expense_all_transaction_txt_expense);

        cat_chart_textView_all_expense = view.findViewById(R.id.cat_chart_textView_all_expense);
        //All Pie chart
        cat_chart_pie_chart_all_expense = view.findViewById(R.id.cat_chart_piechart_all_expense);
        //Top (All) button assign
        cat_chart_all_btn_expense = view.findViewById(R.id.cat_chart_all_btn_expense);
        cat_chart_all_1_btn_expense = view.findViewById(R.id.cat_chart_all_1_btn_expense);
        cat_chart_all_page_expense = view.findViewById(R.id.cat_chart_all_page_expense);

        card_all_category_chart_expense = view.findViewById(R.id.card_all_category_chart_expense);
        cat_all_list_expense = view.findViewById(R.id.cat_all_list_expense);

        cat_txt_exp_all = view.findViewById(R.id.cat_txt_exp_all);
        amount_txt_exp_all = view.findViewById(R.id.amount_txt_exp_all);


        expense_fragment_all_no_data_img = view.findViewById(R.id.expense_fragment_all_no_data_img);
        linear_all_expense_fragment = view.findViewById(R.id.linear_all_expense_fragment);


        //                                       --------   TODAY   -----

        // Bottom Textview

        cat_chart_expense_today_transaction_txt_expense = view.findViewById(R.id.cat_chart_expense_today_transaction_txt_expense);
        cat_1_chart_expense_today_transaction_txt_expense = view.findViewById(R.id.cat_1_chart_expense_today_transaction_txt_expense);

        cat_chart_textView_today_expense = view.findViewById(R.id.cat_chart_textView_today_expense);
        //today Pie chart
        cat_chart_pie_chart_today_expense = view.findViewById(R.id.cat_chart_piechart_today_expense);
        //Top (today) button assign
        cat_chart_today_btn_expense = view.findViewById(R.id.cat_chart_today_btn_expense);
        cat_chart_today_1_btn_expense = view.findViewById(R.id.cat_chart_today_1_btn_expense);
        cat_chart_today_page_expense = view.findViewById(R.id.cat_chart_today_page_expense);

        card_today_category_chart_expense = view.findViewById(R.id.card_today_category_chart_expense);
        cat_today_list_expense = view.findViewById(R.id.cat_today_list_expense);

        cat_txt_exp_today = view.findViewById(R.id.cat_txt_exp_today);
        amount_txt_exp_today = view.findViewById(R.id.amount_txt_exp_today);

        expense_fragment_today_no_data_img = view.findViewById(R.id.expense_fragment_today_no_data_img);
        linear_today_expense_fragment = view.findViewById(R.id.linear_today_expense_fragment);

        //                                       --------   WEEKLY   -----

        cat_chart_expense_weekly_transaction_txt_expense = view.findViewById(R.id.cat_chart_expense_weekly_transaction_txt_expense);
        cat_1_chart_expense_weekly_transaction_txt_expense = view.findViewById(R.id.cat_1_chart_expense_weekly_transaction_txt_expense);


        expense_less_greater_weekly_cat_chart = view.findViewById(R.id.expense_less_greater_weekly_cat_chart);
        cat_chart_textView_weekly_expense = view.findViewById(R.id.cat_chart_textView_weekly_expense);
        less_cat_chart_weekly_expense = view.findViewById(R.id.less_cat_chart_weekly_expense);
        greater_cat_chart_weekly_expense = view.findViewById(R.id.greater_cat_chart_weekly_expense);


        //weekly Pie chart
        cat_chart_pie_chart_weekly_expense = view.findViewById(R.id.cat_chart_piechart_weekly_expense);
        //Top (weekly) button assign
        cat_chart_weekly_btn_expense = view.findViewById(R.id.cat_chart_weekly_btn_expense);
        cat_chart_weekly_1_btn_expense = view.findViewById(R.id.cat_chart_weekly_1_btn_expense);
        cat_chart_weekly_page_expense = view.findViewById(R.id.cat_chart_weekly_page_expense);

        card_weekly_category_chart_expense = view.findViewById(R.id.card_weekly_category_chart_expense);
        cat_weekly_list_expense = view.findViewById(R.id.cat_weekly_list_expense);

        cat_txt_exp_weekly = view.findViewById(R.id.cat_txt_exp_weekly);
        amount_txt_exp_weekly = view.findViewById(R.id.amount_txt_exp_weekly);

        expense_fragment_weekly_no_data_img = view.findViewById(R.id.expense_fragment_weekly_no_data_img);
        linear_weekly_expense_fragment = view.findViewById(R.id.linear_weekly_expense_fragment);


        //                                       --------   MONTHLY   -----

        cat_chart_expense_monthly_transaction_txt_expense = view.findViewById(R.id.cat_chart_expense_monthly_transaction_txt_expense);
        cat_1_chart_expense_monthly_transaction_txt_expense = view.findViewById(R.id.cat_1_chart_expense_monthly_transaction_txt_expense);


        expense_less_greater_monthly_cat_chart = view.findViewById(R.id.expense_less_greater_monthly_cat_chart);
        cat_chart_textView_monthly_expense = view.findViewById(R.id.cat_chart_textView_monthly_expense);
        less_cat_chart_monthly_expense = view.findViewById(R.id.less_cat_chart_monthly_expense);
        greater_cat_chart_monthly_expense = view.findViewById(R.id.greater_cat_chart_monthly_expense);

        //monthly Pie chart
        cat_chart_pie_chart_monthly_expense = view.findViewById(R.id.cat_chart_piechart_monthly_expense);
        //Top (monthly) button assign
        cat_chart_monthly_btn_expense = view.findViewById(R.id.cat_chart_monthly_btn_expense);
        cat_chart_monthly_1_btn_expense = view.findViewById(R.id.cat_chart_monthly_1_btn_expense);
        cat_chart_monthly_page_expense = view.findViewById(R.id.cat_chart_monthly_page_expense);

        card_monthly_category_chart_expense = view.findViewById(R.id.card_monthly_category_chart_expense);
        cat_monthly_list_expense = view.findViewById(R.id.cat_monthly_list_expense);

        cat_txt_exp_monthly = view.findViewById(R.id.cat_txt_exp_monthly);
        amount_txt_exp_monthly = view.findViewById(R.id.amount_txt_exp_monthly);

        expense_fragment_monthly_no_data_img = view.findViewById(R.id.expense_fragment_monthly_no_data_img);
        linear_monthly_expense_fragment = view.findViewById(R.id.linear_monthly_expense_fragment);


        //                                       --------   YEARLY   -----

        // Bottom Textview

        cat_chart_expense_yearly_transaction_txt_expense = view.findViewById(R.id.cat_chart_expense_yearly_transaction_txt_expense);
        cat_1_chart_expense_yearly_transaction_txt_expense = view.findViewById(R.id.cat_1_chart_expense_yearly_transaction_txt_expense);


        expense_less_greater_yearly_cat_chart = view.findViewById(R.id.expense_less_greater_yearly_cat_chart);
        cat_chart_textView_yearly_expense = view.findViewById(R.id.cat_chart_textView_yearly_expense);
        less_cat_chart_yearly_expense = view.findViewById(R.id.less_cat_chart_yearly_expense);
        greater_cat_chart_yearly_expense = view.findViewById(R.id.greater_cat_chart_yearly_expense);

        //yearly Pie chart
        cat_chart_pie_chart_yearly_expense = view.findViewById(R.id.cat_chart_piechart_yearly_expense);
        //Top (yearly) button assign
        cat_chart_yearly_btn_expense = view.findViewById(R.id.cat_chart_yearly_btn_expense);
        cat_chart_yearly_1_btn_expense = view.findViewById(R.id.cat_chart_yearly_1_btn_expense);
        cat_chart_yearly_page_expense = view.findViewById(R.id.cat_chart_yearly_page_expense);

        card_yearly_category_chart_expense = view.findViewById(R.id.card_yearly_category_chart_expense);
        cat_yearly_list_expense = view.findViewById(R.id.cat_yearly_list_expense);

        cat_txt_exp_yearly = view.findViewById(R.id.cat_txt_exp_yearly);
        amount_txt_exp_yearly = view.findViewById(R.id.amount_txt_exp_yearly);

        expense_fragment_yearly_no_data_img = view.findViewById(R.id.expense_fragment_yearly_no_data_img);
        linear_yearly_expense_fragment = view.findViewById(R.id.linear_yearly_expense_fragment);

    }

    // Dark/ Light set textview
    private void darkLightSet() {
        if (isDarkMode) {
            //                                                            all
            cat_chart_expense_all_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            cat_1_chart_expense_all_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            //                                                           today
            cat_chart_expense_today_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            cat_1_chart_expense_today_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));

            //                                                          weekly
            cat_chart_expense_weekly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            cat_1_chart_expense_weekly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_weekly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_weekly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

            //                                                          monthly
            cat_chart_expense_monthly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            cat_1_chart_expense_monthly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_monthly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_monthly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

            //                                                           yearly
            cat_chart_expense_yearly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));
            cat_1_chart_expense_yearly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_yearly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_yearly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

        } else {
            //                                                         all
            cat_chart_expense_all_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            cat_1_chart_expense_all_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));

            //                                                        today
            cat_chart_expense_today_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            cat_1_chart_expense_today_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            //                                                       weekly
            cat_chart_expense_weekly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            cat_1_chart_expense_weekly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));

            less_cat_chart_weekly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_weekly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));

            //                                                       monthly
            cat_chart_expense_monthly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            cat_1_chart_expense_monthly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));

            less_cat_chart_monthly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_monthly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));

            //                                                         yearly
            cat_chart_expense_yearly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));
            cat_1_chart_expense_yearly_transaction_txt_expense.setTextColor(getResources().getColor(R.color.expense1));

            less_cat_chart_yearly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_yearly_expense.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));
        }
    }

    // ALL Data
    private void allDataShow() {

        fetchAllData();
        // xxxxx cat_chart_all_btn Click Data Refresh xxxxx
        cat_chart_all_1_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                cat_chart_all_1_btn_expense.setVisibility(View.INVISIBLE);
                cat_chart_today_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_expense.setVisibility(View.VISIBLE);


                card_all_category_chart_expense.setVisibility(View.VISIBLE);
                card_today_category_chart_expense.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_expense.setVisibility(View.INVISIBLE);

                // show layout
                cat_chart_all_page_expense.setVisibility(View.VISIBLE);
                cat_chart_today_page_expense.setVisibility(View.GONE);
                cat_chart_weekly_page_expense.setVisibility(View.GONE);
                cat_chart_monthly_page_expense.setVisibility(View.GONE);
                cat_chart_yearly_page_expense.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_expense.setVisibility(View.VISIBLE);
                cat_chart_textView_today_expense.setVisibility(View.GONE);
                expense_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchAllData();

            }
        });

    }

    // Today Data
    private void todayDataShow() {

        // xxxxx cat_chart_today_btn Click Data Refresh xxxxx
        cat_chart_today_1_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                cat_chart_all_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_expense.setVisibility(View.INVISIBLE);
                cat_chart_weekly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_expense.setVisibility(View.VISIBLE);

                card_all_category_chart_expense.setVisibility(View.INVISIBLE);
                card_today_category_chart_expense.setVisibility(View.VISIBLE);
                card_weekly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_expense.setVisibility(View.INVISIBLE);


                // show layout
                cat_chart_all_page_expense.setVisibility(View.GONE);
                cat_chart_today_page_expense.setVisibility(View.VISIBLE);
                cat_chart_weekly_page_expense.setVisibility(View.GONE);
                cat_chart_monthly_page_expense.setVisibility(View.GONE);
                cat_chart_yearly_page_expense.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_expense.setVisibility(View.GONE);
                cat_chart_textView_today_expense.setVisibility(View.VISIBLE);
                expense_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchTodayData();

            }
        });

    }

    // Weekly Data
    private void weeklyDataShow() {

        // xxxxx cat_chart_weekly_btn_1 Click Data Refresh xxxxx
        cat_chart_weekly_1_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();

                //btn-2
                cat_chart_all_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_expense.setVisibility(View.INVISIBLE);
                cat_chart_monthly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_expense.setVisibility(View.VISIBLE);

                card_all_category_chart_expense.setVisibility(View.INVISIBLE);
                card_today_category_chart_expense.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_expense.setVisibility(View.VISIBLE);
                card_monthly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_expense.setVisibility(View.INVISIBLE);


                // show layout
                cat_chart_all_page_expense.setVisibility(View.GONE);
                cat_chart_today_page_expense.setVisibility(View.GONE);
                cat_chart_weekly_page_expense.setVisibility(View.VISIBLE);
                cat_chart_monthly_page_expense.setVisibility(View.GONE);
                cat_chart_yearly_page_expense.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_expense.setVisibility(View.GONE);
                cat_chart_textView_today_expense.setVisibility(View.GONE);
                expense_less_greater_weekly_cat_chart.setVisibility(View.VISIBLE);
                expense_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchWeeklyData();

            }
        });

        // xxxxx cat_chart_weekly_btn Click Data Refresh xxxxx
        cat_chart_weekly_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();
                fetchWeeklyData();

            }
        });

        less_cat_chart_weekly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousWeekDates();
                fetchWeeklyData();
            }
        });

        greater_cat_chart_weekly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWeekDates();
                fetchWeeklyData();
            }
        });

    }

    // Monthly Data
    private void monthlyDataShow() {
        // xxxxx cat_chart_monthly_btn_1 Click Data Refresh xxxxx
        cat_chart_monthly_1_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();

                //btn-2
                cat_chart_all_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_expense.setVisibility(View.INVISIBLE);
                cat_chart_yearly_1_btn_expense.setVisibility(View.VISIBLE);

                card_all_category_chart_expense.setVisibility(View.INVISIBLE);
                card_today_category_chart_expense.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_expense.setVisibility(View.VISIBLE);
                card_yearly_category_chart_expense.setVisibility(View.INVISIBLE);


                // show layout
                cat_chart_all_page_expense.setVisibility(View.GONE);
                cat_chart_today_page_expense.setVisibility(View.GONE);
                cat_chart_weekly_page_expense.setVisibility(View.GONE);
                cat_chart_monthly_page_expense.setVisibility(View.VISIBLE);
                cat_chart_yearly_page_expense.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_expense.setVisibility(View.GONE);
                cat_chart_textView_today_expense.setVisibility(View.GONE);
                expense_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_monthly_cat_chart.setVisibility(View.VISIBLE);
                expense_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchMonthlyData();

            }
        });

        // xxxxx cat_chart_monthly_btn Click Data Refresh xxxxx
        cat_chart_monthly_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();
                fetchMonthlyData();

            }
        });

        less_cat_chart_monthly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousMonthDates();
                fetchMonthlyData();
            }
        });

        greater_cat_chart_monthly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextMonthDates();
                fetchMonthlyData();
            }
        });

    }

    // Yearly Data
    private void yearlyDataShow() {

        // xxxxx cat_chart_yearly_btn_1 Click Data Refresh xxxxx
        cat_chart_yearly_1_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();

                //btn-2
                cat_chart_yearly_1_btn_expense.setVisibility(View.INVISIBLE);
                cat_chart_all_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_expense.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_expense.setVisibility(View.VISIBLE);

                card_all_category_chart_expense.setVisibility(View.INVISIBLE);
                card_today_category_chart_expense.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_expense.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_expense.setVisibility(View.VISIBLE);

                // show layout
                cat_chart_all_page_expense.setVisibility(View.GONE);
                cat_chart_today_page_expense.setVisibility(View.GONE);
                cat_chart_weekly_page_expense.setVisibility(View.GONE);
                cat_chart_monthly_page_expense.setVisibility(View.GONE);
                cat_chart_yearly_page_expense.setVisibility(View.VISIBLE);

                //Textview
                cat_chart_textView_all_expense.setVisibility(View.GONE);
                cat_chart_textView_today_expense.setVisibility(View.GONE);
                expense_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                expense_less_greater_yearly_cat_chart.setVisibility(View.VISIBLE);

                fetchYearlyData();

            }
        });

        // xxxxx cat_chart_yearly_btn Click Data Refresh xxxxx
        cat_chart_yearly_btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();
                fetchYearlyData();

            }
        });

        less_cat_chart_yearly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousYearDates();
                fetchYearlyData();
            }
        });

        greater_cat_chart_yearly_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextYearDates();
                fetchYearlyData();
            }
        });

    }

    //scroll prev screen hide
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (exp_frg != null) {
            exp_frg.setVisibility(isVisibleToUser ? View.VISIBLE : View.GONE);
        }
    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching All Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching All Data
    @SuppressLint("Range")
    private void fetchAllData() {

        try {
            list.clear();

            String query1 = "SELECT category, SUM(expense) AS total_incomeexpense, ROUND((SUM(expense) * 100.0 / (SELECT SUM(expense) FROM incexp_tbl WHERE user_email = ?)), 2) AS percentage FROM incexp_tbl WHERE user_email = ? GROUP BY category HAVING total_incomeexpense > 0 ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query1, new String[]{userEmail, userEmail});


            if (cursor1.moveToFirst()) {
                do {
                    String category = cursor1.getString(cursor1.getColumnIndex("category"));
                    String totalIncome = cursor1.getString(cursor1.getColumnIndex("total_incomeexpense"));
                    String percentage = cursor1.getString(cursor1.getColumnIndex("percentage"));

                    Income_Expense_Model model = new Income_Expense_Model(category, totalIncome, percentage);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            cat_adapter = new Category_Adapter(getContext(), list, db, userEmail);
            cat_all_list_expense.setAdapter(cat_adapter);

            checkListDataAll();

            cat_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //---------

        String query2 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE user_email = ? GROUP BY category";
        Cursor cursor2 = db.rawQuery(query2, new String[]{userEmail});

        int totalExpense = 0;
        if (cursor2.moveToFirst()) {
            do {
                double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                totalExpense += categoryExpense;
            } while (cursor2.moveToNext());
        }
        int all_total = totalExpense;
        cat_1_chart_expense_all_transaction_txt_expense.setText(String.valueOf(all_total));

        //----------

        List<PieEntry> entries = new ArrayList<>();
        if (cursor2.moveToFirst()) {
            do {
                String category = cursor2.getString(cursor2.getColumnIndex("category"));
                double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                float incomePercentage = (float) ((categoryExpense / totalExpense) * 100);
                float percentage = Math.round(incomePercentage);
                if (categoryExpense != 0) {
                    entries.add(new PieEntry(percentage, category));
                }
            } while (cursor2.moveToNext());
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(color);
        PieData data = new PieData(dataSet);
        cat_chart_pie_chart_all_expense.setData(data);
        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Set data to PieChart
        cat_chart_pie_chart_all_expense.setData(data);

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface


//        cat_chart_pie_chart_all_expense.setCenterText("Total: " + totalExpense);

        cat_chart_pie_chart_all_expense.getLegend().setEnabled(false);
        cat_chart_pie_chart_all_expense.getDescription().setEnabled(false);
        cat_chart_pie_chart_all_expense.setEntryLabelColor(Color.BLACK);

        cat_chart_pie_chart_all_expense.setDrawHoleEnabled(true); // Enable the hole in the center
        cat_chart_pie_chart_all_expense.setHoleRadius(65f); // Set the radius of the hole
        cat_chart_pie_chart_all_expense.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
        cat_chart_pie_chart_all_expense.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
        cat_chart_pie_chart_all_expense.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

        String balanceText = getResources().getString(R.string.pie_cat_chart_total);
        String centerText = balanceText + " " + all_total;
        cat_chart_pie_chart_all_expense.setCenterText(centerText); // Set the text in the center of the hole
        cat_chart_pie_chart_all_expense.setCenterTextColor(Color.RED);

        cat_chart_pie_chart_all_expense.setCenterTextSize(15f); // Set the size of the center text
        cat_chart_pie_chart_all_expense.animateXY(800, 800);

        cat_chart_pie_chart_all_expense.invalidate(); // Refresh the chart

        //click to BAR CHART
        cat_chart_pie_chart_all_expense.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Map<String, Integer> categoryExpenseMap = new HashMap<>();

                if (e instanceof PieEntry) {
                    PieEntry pieEntry = (PieEntry) e;
                    String category = pieEntry.getLabel();

                    String query3 = "SELECT category, SUM(expense) AS totalexpense FROM incexp_tbl WHERE user_email = ? GROUP BY category";
                    Cursor cursor3 = db.rawQuery(query3, new String[]{userEmail});

                    if (cursor3.moveToFirst()) {
                        do {
                            String fetchedCategory = cursor3.getString(cursor3.getColumnIndex("category"));
                            int totalExpense = cursor3.getInt(cursor3.getColumnIndex("totalexpense"));
                            categoryExpenseMap.put(fetchedCategory, totalExpense);
                        } while (cursor3.moveToNext());
                    }
                    cursor3.close();

                    // Get the total expense for the selected category
                    int categoryExpense = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        categoryExpense = categoryExpenseMap.getOrDefault(category, 0);
                    }

                    // Display the result
                    String message = category + " : " + categoryExpense;
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

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
            String query1 = "SELECT category, SUM(expense) AS total_incomeexpense, ROUND((SUM(expense) * 100.0 / (SELECT SUM(expense) FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now'))), 2) AS percentage FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') GROUP BY category HAVING total_incomeexpense > 0 ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query1, new String[]{userEmail, userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String category = cursor1.getString(cursor1.getColumnIndex("category"));
                    String totalIncome = cursor1.getString(cursor1.getColumnIndex("total_incomeexpense"));
                    String percentage = cursor1.getString(cursor1.getColumnIndex("percentage"));

                    Income_Expense_Model model = new Income_Expense_Model(category, totalIncome, percentage);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            cat_adapter = new Category_Adapter(getContext(), list, db, userEmail);
            cat_today_list_expense.setAdapter(cat_adapter);

            checkListDataToday();

            cat_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //---------

        String query2 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') GROUP BY category";
        Cursor cursor2 = db.rawQuery(query2, new String[]{userEmail});

        int totalExpense = 0;
        if (cursor2.moveToFirst()) {
            do {
                double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                totalExpense += categoryExpense;
            } while (cursor2.moveToNext());
        }
        int today_total = totalExpense;
        cat_1_chart_expense_today_transaction_txt_expense.setText(String.valueOf(today_total));

        //----------

        List<PieEntry> entries = new ArrayList<>();
        if (cursor2.moveToFirst()) {
            do {
                String category = cursor2.getString(cursor2.getColumnIndex("category"));
                double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                float incomePercentage = (float) ((categoryExpense / totalExpense) * 100);
                float percentage = Math.round(incomePercentage);
                if (categoryExpense != 0) {
                    entries.add(new PieEntry(percentage, category));
                }
            } while (cursor2.moveToNext());
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors(color);
        PieData data = new PieData(dataSet);
        cat_chart_pie_chart_today_expense.setData(data);
        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Set data to PieChart
        cat_chart_pie_chart_today_expense.setData(data);

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface


//        cat_chart_pie_chart_today_expense.setCenterText("Total: " + totalExpense);

        cat_chart_pie_chart_today_expense.getLegend().setEnabled(false);
        cat_chart_pie_chart_today_expense.getDescription().setEnabled(false);
        cat_chart_pie_chart_today_expense.setEntryLabelColor(Color.BLACK);

        cat_chart_pie_chart_today_expense.setDrawHoleEnabled(true); // Enable the hole in the center
        cat_chart_pie_chart_today_expense.setHoleRadius(65f); // Set the radius of the hole
        cat_chart_pie_chart_today_expense.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
        cat_chart_pie_chart_today_expense.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
        cat_chart_pie_chart_today_expense.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

        String balanceText = getResources().getString(R.string.pie_cat_chart_total);
        String centerText = balanceText + " " + today_total;
        cat_chart_pie_chart_today_expense.setCenterText(centerText); // Set the text in the center of the hole
        cat_chart_pie_chart_today_expense.setCenterTextColor(Color.RED);

        cat_chart_pie_chart_today_expense.setCenterTextSize(15f); // Set the size of the center text
        cat_chart_pie_chart_today_expense.animateXY(800, 800);

        cat_chart_pie_chart_today_expense.invalidate(); // Refresh the chart

        //click to BAR CHART
        cat_chart_pie_chart_today_expense.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {


                Map<String, Integer> categoryExpenseMap = new HashMap<>();

                if (e instanceof PieEntry) {
                    PieEntry pieEntry = (PieEntry) e;
                    String category = pieEntry.getLabel();

                    String query3 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now') GROUP BY category";
                    Cursor cursor3 = db.rawQuery(query3, new String[]{userEmail});
                    if (cursor3.moveToFirst()) {
                        do {
                            String fetchedCategory = cursor3.getString(cursor3.getColumnIndex("category"));
                            int totalExpense = cursor3.getInt(cursor3.getColumnIndex("totalExpense"));
                            categoryExpenseMap.put(fetchedCategory, totalExpense);
                        } while (cursor3.moveToNext());
                    }
                    cursor3.close();

                    // Get the total expense for the selected category
                    int categoryExpense = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        categoryExpense = categoryExpenseMap.getOrDefault(category, 0);
                    }

                    // Display the result
                    String message = category + " : " + categoryExpense;
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

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

            String startDate = getWeekStartDate();
            String endDate = getWeekEndDate();

            list.clear();
            String query1 = "SELECT category, SUM(expense) AS total_incomeexpense, ROUND((SUM(expense) * 100.0 / (SELECT SUM(expense) FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?)), 2) AS percentage FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY category HAVING total_incomeexpense > 0 ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query1, new String[]{startDate, endDate, userEmail, startDate, endDate, userEmail});


            if (cursor1.moveToFirst()) {
                do {
                    String category = cursor1.getString(cursor1.getColumnIndex("category"));
                    String totalIncome = cursor1.getString(cursor1.getColumnIndex("total_incomeexpense"));
                    String percentage = cursor1.getString(cursor1.getColumnIndex("percentage"));

                    Income_Expense_Model model = new Income_Expense_Model(category, totalIncome, percentage);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            cat_adapter = new Category_Adapter(getContext(), list, db,userEmail);
            cat_weekly_list_expense.setAdapter(cat_adapter);

            checkListDataWeekly();

            cat_adapter.notifyDataSetChanged();



        //---------

        String query2 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
        Cursor cursor2 = db.rawQuery(query2, new String[]{startDate, endDate, userEmail});

        int totalExpense = 0;
        if (cursor2.moveToFirst()) {
            do {
                double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                totalExpense += categoryExpense;
            } while (cursor2.moveToNext());
        }
        int weekly_total = totalExpense;
        cat_1_chart_expense_weekly_transaction_txt_expense.setText(String.valueOf(weekly_total));

        //----------

            List<PieEntry> entries = new ArrayList<>();
            if (cursor2.moveToFirst()) {
                do {
                    String category = cursor2.getString(cursor2.getColumnIndex("category"));
                    double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                    float incomePercentage = (float) ((categoryExpense / totalExpense) * 100);
                    float percentage = Math.round(incomePercentage);
                    if (categoryExpense != 0) {
                        entries.add(new PieEntry(percentage, category));
                    }
                } while (cursor2.moveToNext());
            }

            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(color);
            PieData data = new PieData(dataSet);
            cat_chart_pie_chart_weekly_expense.setData(data);
            // Percentage Value Formatter
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format(Locale.getDefault(), "%.0f%%", value);
                }
            });

            // Set data to PieChart
            cat_chart_pie_chart_weekly_expense.setData(data);

            // Customize value and label colors
            dataSet.setValueTextColor(Color.BLACK); // Value color
            dataSet.setValueTextSize(12f); // Value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface


//        cat_chart_pie_chart_weekly_expense.setCenterText("Total: " + totalExpense);

            cat_chart_pie_chart_weekly_expense.getLegend().setEnabled(false);
            cat_chart_pie_chart_weekly_expense.getDescription().setEnabled(false);
            cat_chart_pie_chart_weekly_expense.setEntryLabelColor(Color.BLACK);

            cat_chart_pie_chart_weekly_expense.setDrawHoleEnabled(true); // Enable the hole in the center
            cat_chart_pie_chart_weekly_expense.setHoleRadius(65f); // Set the radius of the hole
            cat_chart_pie_chart_weekly_expense.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
            cat_chart_pie_chart_weekly_expense.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
            cat_chart_pie_chart_weekly_expense.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

            String balanceText = getResources().getString(R.string.pie_cat_chart_total);
            String centerText = balanceText + " " + weekly_total;
            cat_chart_pie_chart_weekly_expense.setCenterText(centerText); // Set the text in the center of the hole
            cat_chart_pie_chart_weekly_expense.setCenterTextColor(Color.RED);

            cat_chart_pie_chart_weekly_expense.setCenterTextSize(15f); // Set the size of the center text
            cat_chart_pie_chart_weekly_expense.animateXY(800, 800);

            cat_chart_pie_chart_weekly_expense.invalidate(); // Refresh the chart

            //click to BAR CHART
            cat_chart_pie_chart_weekly_expense.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                    Map<String, Integer> categoryExpenseMap = new HashMap<>();

                    if (e instanceof PieEntry) {
                        PieEntry pieEntry = (PieEntry) e;
                        String category = pieEntry.getLabel();

                        String query3 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
                        Cursor cursor3 = db.rawQuery(query3, new String[]{startDate, endDate, userEmail});

                        if (cursor3.moveToFirst()) {
                            do {
                                String fetchedCategory = cursor3.getString(cursor3.getColumnIndex("category"));
                                int totalExpense = cursor3.getInt(cursor3.getColumnIndex("totalExpense"));
                                categoryExpenseMap.put(fetchedCategory, totalExpense);
                            } while (cursor3.moveToNext());
                        }
                        cursor3.close();

                        // Get the total expense for the selected category
                        int categoryExpense = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            categoryExpense = categoryExpenseMap.getOrDefault(category, 0);
                        }

                        // Display the result
                        String message = category + " : " + categoryExpense;
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onNothingSelected() {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Monthly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Monthly Data
    @SuppressLint("Range")
    private void fetchMonthlyData() {
        try {

            String startDate = getMonthStartDate();
            String endDate = getMonthEndDate();

            list.clear();

            String query1 = "SELECT category, SUM(expense) AS total_incomeexpense, ROUND((SUM(expense) * 100.0 / (SELECT SUM(expense) FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?)), 2) AS percentage FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY category HAVING total_incomeexpense > 0 ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query1, new String[]{startDate, endDate, userEmail, startDate, endDate, userEmail});

            if (cursor1.moveToFirst()) {
                do {
                    String category = cursor1.getString(cursor1.getColumnIndex("category"));
                    String totalIncome = cursor1.getString(cursor1.getColumnIndex("total_incomeexpense"));
                    String percentage = cursor1.getString(cursor1.getColumnIndex("percentage"));

                    Income_Expense_Model model = new Income_Expense_Model(category, totalIncome, percentage);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            cat_adapter = new Category_Adapter(getContext(), list, db,userEmail);
            cat_monthly_list_expense.setAdapter(cat_adapter);

            checkListDataMonthly();

            cat_adapter.notifyDataSetChanged();

            //---------
            String query2 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
            Cursor cursor2 = db.rawQuery(query2, new String[]{startDate, endDate, userEmail});

            int totalExpense = 0;
            if (cursor2.moveToFirst()) {
                do {
                    double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                    totalExpense += categoryExpense;
                } while (cursor2.moveToNext());
            }
            int monthly_total = totalExpense;
            cat_1_chart_expense_monthly_transaction_txt_expense.setText(String.valueOf(monthly_total));

            //----------

            ArrayList<PieEntry> entries = new ArrayList<>();

            if (cursor2.moveToFirst()) {
                do {
                    String category = cursor2.getString(cursor2.getColumnIndex("category"));
                    float categoryExpense = cursor2.getFloat(cursor2.getColumnIndex("totalExpense"));
                    float incomePercentage = (float) ((categoryExpense / totalExpense) * 100);
                    float percentage = Math.round(incomePercentage);
                    if (categoryExpense != 0) {
                        entries.add(new PieEntry(percentage, category));
                    }
                } while (cursor2.moveToNext());

            }

            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(color);
            PieData data = new PieData(dataSet);
            cat_chart_pie_chart_monthly_expense.setData(data);
            // Percentage Value Formatter
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format(Locale.getDefault(), "%.0f%%", value);
                }
            });

            // Set data to PieChart
            cat_chart_pie_chart_monthly_expense.setData(data);

            // Customize value and label colors
            dataSet.setValueTextColor(Color.BLACK); // Value color
            dataSet.setValueTextSize(12f); // Value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface


//        cat_chart_pie_chart_monthly_expense.setCenterText("Total: " + totalExpense);

            cat_chart_pie_chart_monthly_expense.getLegend().setEnabled(false);
            cat_chart_pie_chart_monthly_expense.getDescription().setEnabled(false);
            cat_chart_pie_chart_monthly_expense.setEntryLabelColor(Color.BLACK);

            cat_chart_pie_chart_monthly_expense.setDrawHoleEnabled(true); // Enable the hole in the center
            cat_chart_pie_chart_monthly_expense.setHoleRadius(65f); // Set the radius of the hole
            cat_chart_pie_chart_monthly_expense.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
            cat_chart_pie_chart_monthly_expense.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
            cat_chart_pie_chart_monthly_expense.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

            String balanceText = getResources().getString(R.string.pie_cat_chart_total);
            String centerText = balanceText + " " + monthly_total;
            cat_chart_pie_chart_monthly_expense.setCenterText(centerText); // Set the text in the center of the hole
            cat_chart_pie_chart_monthly_expense.setCenterTextColor(Color.RED);

            cat_chart_pie_chart_monthly_expense.setCenterTextSize(15f); // Set the size of the center text
            cat_chart_pie_chart_monthly_expense.animateXY(800, 800);

            cat_chart_pie_chart_monthly_expense.invalidate(); // Refresh the chart


            //click to BAR CHART
            cat_chart_pie_chart_monthly_expense.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    Map<String, Integer> categoryExpenseMap = new HashMap<>();

                    if (e instanceof PieEntry) {
                        PieEntry pieEntry = (PieEntry) e;
                        String category = pieEntry.getLabel();

                        String query3 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
                        Cursor cursor3 = db.rawQuery(query3, new String[]{startDate, endDate, userEmail});

                        if (cursor3.moveToFirst()) {
                            do {
                                String fetchedCategory = cursor3.getString(cursor3.getColumnIndex("category"));
                                int totalExpense = cursor3.getInt(cursor3.getColumnIndex("totalExpense"));
                                categoryExpenseMap.put(fetchedCategory, totalExpense);
                            } while (cursor3.moveToNext());
                        }
                        cursor3.close();

                        // Get the total expense for the selected category
                        int categoryExpense = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            categoryExpense = categoryExpenseMap.getOrDefault(category, 0);
                        }

                        // Display the result
                        String message = category + " : " + categoryExpense;
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onNothingSelected() {

                }
            });


        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Yearly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Yearly Data
    @SuppressLint("Range")
    private void fetchYearlyData() {

        try {

            String startDate = getYearStartDate();
            String endDate = getYearEndDate();

            list.clear();

            String query1 = "SELECT category, SUM(expense) AS total_incomeexpense, ROUND((SUM(expense) * 100.0 / (SELECT SUM(expense) FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?)), 2) AS percentage FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? GROUP BY category HAVING total_incomeexpense > 0 ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query1, new String[]{startDate, endDate, userEmail, startDate, endDate, userEmail});
            if (cursor1.moveToFirst()) {
                do {
                    String category = cursor1.getString(cursor1.getColumnIndex("category"));
                    String totalIncome = cursor1.getString(cursor1.getColumnIndex("total_incomeexpense"));
                    String percentage = cursor1.getString(cursor1.getColumnIndex("percentage"));

                    Income_Expense_Model model = new Income_Expense_Model(category, totalIncome, percentage);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            cat_adapter = new Category_Adapter(getContext(), list, db,userEmail);
            cat_yearly_list_expense.setAdapter(cat_adapter);

            checkListDataYearly();

            cat_adapter.notifyDataSetChanged();

            //---------

            String query2 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
            Cursor cursor2 = db.rawQuery(query2, new String[]{startDate, endDate, userEmail});

            int totalExpense = 0;
            if (cursor2.moveToFirst()) {
                do {
                    double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                    totalExpense += categoryExpense;
                } while (cursor2.moveToNext());
            }
            int yearly_total = totalExpense;
            cat_1_chart_expense_yearly_transaction_txt_expense.setText(String.valueOf(yearly_total));

            //----------

            List<PieEntry> entries = new ArrayList<>();
            if (cursor2.moveToFirst()) {
                do {
                    String category = cursor2.getString(cursor2.getColumnIndex("category"));
                    double categoryExpense = cursor2.getDouble(cursor2.getColumnIndex("totalExpense"));
                    float incomePercentage = (float) ((categoryExpense / totalExpense) * 100);
                    float percentage = Math.round(incomePercentage);
                    if (categoryExpense != 0) {
                        entries.add(new PieEntry(percentage, category));
                    }
                } while (cursor2.moveToNext());
            }

            PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
            dataSet.setColors(color);
            PieData data = new PieData(dataSet);
            cat_chart_pie_chart_yearly_expense.setData(data);
            // Percentage Value Formatter
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format(Locale.getDefault(), "%.0f%%", value);
                }
            });

            // Set data to PieChart
            cat_chart_pie_chart_yearly_expense.setData(data);

            // Customize value and label colors
            dataSet.setValueTextColor(Color.BLACK); // Value color
            dataSet.setValueTextSize(12f); // Value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface


//        cat_chart_pie_chart_yearly_expense.setCenterText("Total: " + totalExpense);

            cat_chart_pie_chart_yearly_expense.getLegend().setEnabled(false);
            cat_chart_pie_chart_yearly_expense.getDescription().setEnabled(false);
            cat_chart_pie_chart_yearly_expense.setEntryLabelColor(Color.BLACK);

            cat_chart_pie_chart_yearly_expense.setDrawHoleEnabled(true); // Enable the hole in the center
            cat_chart_pie_chart_yearly_expense.setHoleRadius(65f); // Set the radius of the hole
            cat_chart_pie_chart_yearly_expense.setTransparentCircleRadius(70f); // Set the radius of the transparent circle around the hole
            cat_chart_pie_chart_yearly_expense.setTransparentCircleColor(Color.WHITE); // Set the color of the transparent circle
            cat_chart_pie_chart_yearly_expense.setTransparentCircleAlpha(190); // Set the transparency of the transparent circle

            String balanceText = getResources().getString(R.string.pie_cat_chart_total);
            String centerText = balanceText + " " + yearly_total;
            cat_chart_pie_chart_yearly_expense.setCenterText(centerText); // Set the text in the center of the hole
            cat_chart_pie_chart_yearly_expense.setCenterTextColor(Color.RED);

            cat_chart_pie_chart_yearly_expense.setCenterTextSize(15f); // Set the size of the center text
            cat_chart_pie_chart_yearly_expense.animateXY(800, 800);

            cat_chart_pie_chart_yearly_expense.invalidate(); // Refresh the chart

            //click to BAR CHART
            cat_chart_pie_chart_yearly_expense.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                    Map<String, Integer> categoryExpenseMap = new HashMap<>();

                    if (e instanceof PieEntry) {
                        PieEntry pieEntry = (PieEntry) e;
                        String category = pieEntry.getLabel();

                        String query3 = "SELECT category, SUM(expense) AS totalExpense FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ?  GROUP BY category";
                        Cursor cursor3 = db.rawQuery(query3, new String[]{startDate, endDate, userEmail});


                        if (cursor3.moveToFirst()) {
                            do {
                                String fetchedCategory = cursor3.getString(cursor3.getColumnIndex("category"));
                                int totalExpense = cursor3.getInt(cursor3.getColumnIndex("totalExpense"));
                                categoryExpenseMap.put(fetchedCategory, totalExpense);
                            } while (cursor3.moveToNext());
                        }
                        cursor3.close();

                        // Get the total expense for the selected category
                        int categoryExpense = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            categoryExpense = categoryExpenseMap.getOrDefault(category, 0);
                        }

                        // Display the result
                        String message = category + " : " + categoryExpense;
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onNothingSelected() {

                }
            });

        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        cat_chart_textView_weekly_expense.setText(dateRange); // Update your TextView here
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
        cat_chart_textView_monthly_expense.setText(dateRange);
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
        cat_chart_textView_yearly_expense.setText(dateRange);
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


    // Check List data isempty
    private void checkListDataAll() {
        if (list.isEmpty()) {
            linear_all_expense_fragment.setVisibility(View.GONE);
            expense_fragment_all_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_all_expense_fragment.setVisibility(View.VISIBLE);
            expense_fragment_all_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataToday() {
        if (list.isEmpty()) {
            linear_today_expense_fragment.setVisibility(View.GONE);
            expense_fragment_today_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_today_expense_fragment.setVisibility(View.VISIBLE);
            expense_fragment_today_no_data_img.setVisibility(View.GONE);
        }
    }


    private void checkListDataWeekly() {
        if (list.isEmpty()) {
            linear_weekly_expense_fragment.setVisibility(View.GONE);
            expense_fragment_weekly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_weekly_expense_fragment.setVisibility(View.VISIBLE);
            expense_fragment_weekly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataMonthly() {
        if (list.isEmpty()) {
            linear_monthly_expense_fragment.setVisibility(View.GONE);
            expense_fragment_monthly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_monthly_expense_fragment.setVisibility(View.VISIBLE);
            expense_fragment_monthly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataYearly() {
        if (list.isEmpty()) {
            linear_yearly_expense_fragment.setVisibility(View.GONE);
            expense_fragment_yearly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_yearly_expense_fragment.setVisibility(View.VISIBLE);
            expense_fragment_yearly_no_data_img.setVisibility(View.GONE);
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("selected_language", lang);
        editor.apply();
    }

    private String getSavedLanguage() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        return prefs.getString("selected_language", "en"); // Default to English
    }


    // <-------------------------------------------------------------------- Update Text ------------------------------------------------------------------------------>
    private void updateText() {
        // Layout ---- 1
        cat_chart_all_btn_expense.setText(R.string.all_btn_txt_lay2_cat_chart);
        cat_chart_all_1_btn_expense.setText(R.string.all_btn_txt_lay2_cat_chart);

        cat_chart_today_btn_expense.setText(R.string.today_btn_txt_lay2_cat_chart);
        cat_chart_today_1_btn_expense.setText(R.string.today_btn_txt_lay2_cat_chart);

        cat_chart_weekly_btn_expense.setText(R.string.weekly_btn_txt_lay2_cat_chart);
        cat_chart_weekly_1_btn_expense.setText(R.string.weekly_btn_txt_lay2_cat_chart);

        cat_chart_monthly_btn_expense.setText(R.string.monthly_btn_txt_lay2_cat_chart);
        cat_chart_monthly_1_btn_expense.setText(R.string.monthly_btn_txt_lay2_cat_chart);

        cat_chart_yearly_btn_expense.setText(R.string.yearly_btn_txt_lay2_cat_chart);
        cat_chart_yearly_1_btn_expense.setText(R.string.yearly_btn_txt_lay2_cat_chart);

//        // Layout ---- 2
        cat_chart_textView_all_expense.setText(R.string.all_btn_txt_lay3_cat_chart);
        cat_chart_textView_today_expense.setText(R.string.today_btn_txt_lay3_cat_chart);
        cat_chart_textView_weekly_expense.setText(R.string.weekly_btn_txt_lay3_cat_chart);
        cat_chart_textView_monthly_expense.setText(R.string.monthly_btn_txt_lay3_cat_chart);
        cat_chart_textView_yearly_expense.setText(R.string.yearly_btn_txt_lay3_cat_chart);


//        // Layout ---- 3
        cat_chart_expense_all_transaction_txt_expense.setText(R.string.total_exp_txt_lay4_cat_chart);

        cat_chart_expense_today_transaction_txt_expense.setText(R.string.total_exp_txt_lay4_cat_chart);

        cat_chart_expense_weekly_transaction_txt_expense.setText(R.string.total_exp_txt_lay4_cat_chart);

        cat_chart_expense_monthly_transaction_txt_expense.setText(R.string.total_exp_txt_lay4_cat_chart);

        cat_chart_expense_yearly_transaction_txt_expense.setText(R.string.total_exp_txt_lay4_cat_chart);

        //        // Layout ---- 4
        cat_txt_exp_all.setText(R.string.category_txt_cat_chart);
        amount_txt_exp_all.setText(R.string.amount_txt_cat_chart);


        cat_txt_exp_today.setText(R.string.category_txt_cat_chart);
        amount_txt_exp_today.setText(R.string.amount_txt_cat_chart);


        cat_txt_exp_weekly.setText(R.string.category_txt_cat_chart);
        amount_txt_exp_weekly.setText(R.string.amount_txt_cat_chart);


        cat_txt_exp_monthly.setText(R.string.category_txt_cat_chart);
        amount_txt_exp_monthly.setText(R.string.amount_txt_cat_chart);


        cat_txt_exp_yearly.setText(R.string.category_txt_cat_chart);
        amount_txt_exp_yearly.setText(R.string.amount_txt_cat_chart);


    }


    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
}