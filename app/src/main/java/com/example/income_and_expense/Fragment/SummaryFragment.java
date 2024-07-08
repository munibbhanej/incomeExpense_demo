package com.example.income_and_expense.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.income_and_expense.Activity.Home_Activity;
import com.example.income_and_expense.Activity.Payment_Method_Chart_Activity;
import com.example.income_and_expense.Model.Income_Expense_Model;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class SummaryFragment extends Fragment {

    //                                          -----  ALL  -----
    PieChart cat_chart_pie_chart_all_summary;

    TextView cat_chart_income_all_transaction_summary, cat_chart_expense_all_transaction_summary, cat_chart_balance_all_transaction_summary;
    TextView cat_chart_income_all_transaction_txt_summary, cat_chart_expense_all_transaction_txt_summary, cat_chart_balance_all_transaction_txt_summary;
    TextView cat_chart_textView_all_summary;

    Button cat_chart_all_btn_summary, cat_chart_all_1_btn_summary;
    RelativeLayout cat_chart_all_page_summary;

    int Income_all = 0, Expense_all = 0;

    CardView card_all_category_chart_summary;

    ImageView summary_fragment_all_no_data_img;
    LinearLayout linear_all_summary_fragment;


    //                                           -----  TODAY  -----
    PieChart cat_chart_pie_chart_today_summary;

    TextView cat_chart_income_today_transaction_summary, cat_chart_expense_today_transaction_summary, cat_chart_balance_today_transaction_summary;
    TextView cat_chart_income_today_transaction_txt_summary, cat_chart_expense_today_transaction_txt_summary, cat_chart_balance_today_transaction_txt_summary;
    TextView cat_chart_textView_today_summary;

    Button cat_chart_today_btn_summary, cat_chart_today_1_btn_summary;
    RelativeLayout cat_chart_today_page_summary;

    int Income_today = 0, Expense_today = 0;

    CardView card_today_category_chart_summary;

    ImageView summary_fragment_today_no_data_img;
    LinearLayout linear_today_summary_fragment;


    //                                           -----  WEEKLY  -----
    PieChart cat_chart_pie_chart_weekly_summary;

    TextView cat_chart_income_weekly_transaction_summary, cat_chart_expense_weekly_transaction_summary, cat_chart_balance_weekly_transaction_summary;
    TextView cat_chart_income_weekly_transaction_txt_summary, cat_chart_expense_weekly_transaction_txt_summary, cat_chart_balance_weekly_transaction_txt_summary;

    TableLayout summary_less_greater_weekly_cat_chart;
    TextView cat_chart_textView_weekly_summary;

    ImageView less_cat_chart_weekly_summary, greater_cat_chart_weekly_summary;

    Button cat_chart_weekly_btn_summary, cat_chart_weekly_1_btn_summary;
    RelativeLayout cat_chart_weekly_page_summary;

    int Income_weekly = 0, Expense_weekly = 0;
    CardView card_weekly_category_chart_summary;

    ImageView summary_fragment_weekly_no_data_img;
    LinearLayout linear_weekly_summary_fragment;


    //                                           -----  MONTHLY  -----

    PieChart cat_chart_pie_chart_monthly_summary;

    TextView cat_chart_income_monthly_transaction_summary, cat_chart_expense_monthly_transaction_summary, cat_chart_balance_monthly_transaction_summary;
    TextView cat_chart_income_monthly_transaction_txt_summary, cat_chart_expense_monthly_transaction_txt_summary, cat_chart_balance_monthly_transaction_txt_summary;

    TableLayout summary_less_greater_monthly_cat_chart;
    TextView cat_chart_textView_monthly_summary;

    ImageView less_cat_chart_monthly_summary, greater_cat_chart_monthly_summary;

    Button cat_chart_monthly_btn_summary, cat_chart_monthly_1_btn_summary;
    RelativeLayout cat_chart_monthly_page_summary;

    int Income_monthly = 0, Expense_monthly = 0;

    CardView card_monthly_category_chart_summary;

    ImageView summary_fragment_monthly_no_data_img;
    LinearLayout linear_monthly_summary_fragment;


    //                                           -----  YEARLY  -----

    PieChart cat_chart_pie_chart_yearly_summary;

    TextView cat_chart_income_yearly_transaction_summary, cat_chart_expense_yearly_transaction_summary, cat_chart_balance_yearly_transaction_summary;
    TextView cat_chart_income_yearly_transaction_txt_summary, cat_chart_expense_yearly_transaction_txt_summary, cat_chart_balance_yearly_transaction_txt_summary;

    TableLayout summary_less_greater_yearly_cat_chart;
    TextView cat_chart_textView_yearly_summary;

    ImageView less_cat_chart_yearly_summary, greater_cat_chart_yearly_summary;

    Button cat_chart_yearly_btn_summary, cat_chart_yearly_1_btn_summary;
    RelativeLayout cat_chart_yearly_page_summary;

    int Income_yearly = 0, Expense_yearly = 0;
    CardView card_yearly_category_chart_summary;

    ImageView summary_fragment_yearly_no_data_img;
    LinearLayout linear_yearly_summary_fragment;

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    int[] color;
    int all_balance = 0, today_balance = 0, weekly_balance = 0, monthly_balance = 0, yearly_balance = 0;

    private List<Income_Expense_Model> list = new ArrayList<>();

    RelativeLayout sum_frg;

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
        return getActivity().openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (isDarkMode) {
            color = new int[]{Color.rgb(77, 218, 84), Color.rgb(255, 56, 56)};
        } else {
            color = new int[]{Color.rgb(29, 90, 32), Color.rgb(230, 49, 49)};

        }

        // Language default
        setLocal(getSavedLanguage());

        sum_frg = view.findViewById(R.id.sum_frg);

        userEmail = getArguments().getString("USER_EMAIL");


        currentMonth = Calendar.getInstance();
        calendar = Calendar.getInstance();

        database = CreateDatabase();

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

        // Bottom Textview
        cat_chart_income_all_transaction_summary = view.findViewById(R.id.cat_chart_income_all_transaction_summary);
        cat_chart_expense_all_transaction_summary = view.findViewById(R.id.cat_chart_expense_all_transaction_summary);
        cat_chart_balance_all_transaction_summary = view.findViewById(R.id.cat_chart_balance_all_transaction_summary);

        cat_chart_income_all_transaction_txt_summary = view.findViewById(R.id.cat_chart_income_all_transaction_txt_summary);
        cat_chart_expense_all_transaction_txt_summary = view.findViewById(R.id.cat_chart_expense_all_transaction_txt_summary);
        cat_chart_balance_all_transaction_txt_summary = view.findViewById(R.id.cat_chart_balance_all_transaction_txt_summary);

        cat_chart_textView_all_summary = view.findViewById(R.id.cat_chart_textView_all_summary);
        //All Pie chart
        cat_chart_pie_chart_all_summary = view.findViewById(R.id.cat_chart_piechart_all_summary);
        //Top (All) button assign
        cat_chart_all_btn_summary = view.findViewById(R.id.cat_chart_all_btn_summary);
        cat_chart_all_1_btn_summary = view.findViewById(R.id.cat_chart_all_1_btn_summary);
        cat_chart_all_page_summary = view.findViewById(R.id.cat_chart_all_page_summary);

        card_all_category_chart_summary = view.findViewById(R.id.card_all_category_chart_summary);

        summary_fragment_all_no_data_img = view.findViewById(R.id.summary_fragment_all_no_data_img);
        linear_all_summary_fragment = view.findViewById(R.id.linear_all_summary_fragment);

        //                                       --------   TODAY   -----

        // Bottom Textview
        cat_chart_income_today_transaction_summary = view.findViewById(R.id.cat_chart_income_today_transaction_summary);
        cat_chart_expense_today_transaction_summary = view.findViewById(R.id.cat_chart_expense_today_transaction_summary);
        cat_chart_balance_today_transaction_summary = view.findViewById(R.id.cat_chart_balance_today_transaction_summary);

        cat_chart_income_today_transaction_txt_summary = view.findViewById(R.id.cat_chart_income_today_transaction_txt_summary);
        cat_chart_expense_today_transaction_txt_summary = view.findViewById(R.id.cat_chart_expense_today_transaction_txt_summary);
        cat_chart_balance_today_transaction_txt_summary = view.findViewById(R.id.cat_chart_balance_today_transaction_txt_summary);

        cat_chart_textView_today_summary = view.findViewById(R.id.cat_chart_textView_today_summary);
        //today Pie chart
        cat_chart_pie_chart_today_summary = view.findViewById(R.id.cat_chart_piechart_today_summary);
        //Top (today) button assign
        cat_chart_today_btn_summary = view.findViewById(R.id.cat_chart_today_btn_summary);
        cat_chart_today_1_btn_summary = view.findViewById(R.id.cat_chart_today_1_btn_summary);
        cat_chart_today_page_summary = view.findViewById(R.id.cat_chart_today_page_summary);

        card_today_category_chart_summary = view.findViewById(R.id.card_today_category_chart_summary);

        summary_fragment_today_no_data_img = view.findViewById(R.id.summary_fragment_today_no_data_img);
        linear_today_summary_fragment = view.findViewById(R.id.linear_today_summary_fragment);


        //                                       --------   WEEKLY   -----

        // Bottom Textview
        cat_chart_income_weekly_transaction_summary = view.findViewById(R.id.cat_chart_income_weekly_transaction_summary);
        cat_chart_expense_weekly_transaction_summary = view.findViewById(R.id.cat_chart_expense_weekly_transaction_summary);
        cat_chart_balance_weekly_transaction_summary = view.findViewById(R.id.cat_chart_balance_weekly_transaction_summary);

        cat_chart_income_weekly_transaction_txt_summary = view.findViewById(R.id.cat_chart_income_weekly_transaction_txt_summary);
        cat_chart_expense_weekly_transaction_txt_summary = view.findViewById(R.id.cat_chart_expense_weekly_transaction_txt_summary);
        cat_chart_balance_weekly_transaction_txt_summary = view.findViewById(R.id.cat_chart_balance_weekly_transaction_txt_summary);


        summary_less_greater_weekly_cat_chart = view.findViewById(R.id.summary_less_greater_weekly_cat_chart);
        cat_chart_textView_weekly_summary = view.findViewById(R.id.cat_chart_textView_weekly_summary);
        less_cat_chart_weekly_summary = view.findViewById(R.id.less_cat_chart_weekly_summary);
        greater_cat_chart_weekly_summary = view.findViewById(R.id.greater_cat_chart_weekly_summary);

        //weekly Pie chart
        cat_chart_pie_chart_weekly_summary = view.findViewById(R.id.cat_chart_piechart_weekly_summary);
        //Top (weekly) button assign
        cat_chart_weekly_btn_summary = view.findViewById(R.id.cat_chart_weekly_btn_summary);
        cat_chart_weekly_1_btn_summary = view.findViewById(R.id.cat_chart_weekly_1_btn_summary);
        cat_chart_weekly_page_summary = view.findViewById(R.id.cat_chart_weekly_page_summary);

        card_weekly_category_chart_summary = view.findViewById(R.id.card_weekly_category_chart_summary);

        summary_fragment_weekly_no_data_img = view.findViewById(R.id.summary_fragment_weekly_no_data_img);
        linear_weekly_summary_fragment = view.findViewById(R.id.linear_weekly_summary_fragment);


        //                                       --------   MONTHLY   -----

        // Bottom Textview
        cat_chart_income_monthly_transaction_summary = view.findViewById(R.id.cat_chart_income_monthly_transaction_summary);
        cat_chart_expense_monthly_transaction_summary = view.findViewById(R.id.cat_chart_expense_monthly_transaction_summary);
        cat_chart_balance_monthly_transaction_summary = view.findViewById(R.id.cat_chart_balance_monthly_transaction_summary);

        cat_chart_income_monthly_transaction_txt_summary = view.findViewById(R.id.cat_chart_income_monthly_transaction_txt_summary);
        cat_chart_expense_monthly_transaction_txt_summary = view.findViewById(R.id.cat_chart_expense_monthly_transaction_txt_summary);
        cat_chart_balance_monthly_transaction_txt_summary = view.findViewById(R.id.cat_chart_balance_monthly_transaction_txt_summary);


        summary_less_greater_monthly_cat_chart = view.findViewById(R.id.summary_less_greater_monthly_cat_chart);
        cat_chart_textView_monthly_summary = view.findViewById(R.id.cat_chart_textView_monthly_summary);
        less_cat_chart_monthly_summary = view.findViewById(R.id.less_cat_chart_monthly_summary);
        greater_cat_chart_monthly_summary = view.findViewById(R.id.greater_cat_chart_monthly_summary);

        //monthly Pie chart
        cat_chart_pie_chart_monthly_summary = view.findViewById(R.id.cat_chart_piechart_monthly_summary);
        //Top (monthly) button assign
        cat_chart_monthly_btn_summary = view.findViewById(R.id.cat_chart_monthly_btn_summary);
        cat_chart_monthly_1_btn_summary = view.findViewById(R.id.cat_chart_monthly_1_btn_summary);
        cat_chart_monthly_page_summary = view.findViewById(R.id.cat_chart_monthly_page_summary);

        card_monthly_category_chart_summary = view.findViewById(R.id.card_monthly_category_chart_summary);

        summary_fragment_monthly_no_data_img = view.findViewById(R.id.summary_fragment_monthly_no_data_img);
        linear_monthly_summary_fragment = view.findViewById(R.id.linear_monthly_summary_fragment);


        //                                       --------   YEARLY   -----

        // Bottom Textview
        cat_chart_income_yearly_transaction_summary = view.findViewById(R.id.cat_chart_income_yearly_transaction_summary);
        cat_chart_expense_yearly_transaction_summary = view.findViewById(R.id.cat_chart_expense_yearly_transaction_summary);
        cat_chart_balance_yearly_transaction_summary = view.findViewById(R.id.cat_chart_balance_yearly_transaction_summary);

        cat_chart_income_yearly_transaction_txt_summary = view.findViewById(R.id.cat_chart_income_yearly_transaction_txt_summary);
        cat_chart_expense_yearly_transaction_txt_summary = view.findViewById(R.id.cat_chart_expense_yearly_transaction_txt_summary);
        cat_chart_balance_yearly_transaction_txt_summary = view.findViewById(R.id.cat_chart_balance_yearly_transaction_txt_summary);

        summary_less_greater_yearly_cat_chart = view.findViewById(R.id.summary_less_greater_yearly_cat_chart);
        cat_chart_textView_yearly_summary = view.findViewById(R.id.cat_chart_textView_yearly_summary);
        less_cat_chart_yearly_summary = view.findViewById(R.id.less_cat_chart_yearly_summary);
        greater_cat_chart_yearly_summary = view.findViewById(R.id.greater_cat_chart_yearly_summary);


        //yearly Pie chart
        cat_chart_pie_chart_yearly_summary = view.findViewById(R.id.cat_chart_piechart_yearly_summary);
        //Top (yearly) button assign
        cat_chart_yearly_btn_summary = view.findViewById(R.id.cat_chart_yearly_btn_summary);
        cat_chart_yearly_1_btn_summary = view.findViewById(R.id.cat_chart_yearly_1_btn_summary);
        cat_chart_yearly_page_summary = view.findViewById(R.id.cat_chart_yearly_page_summary);

        card_yearly_category_chart_summary = view.findViewById(R.id.card_yearly_category_chart_summary);

        summary_fragment_yearly_no_data_img = view.findViewById(R.id.summary_fragment_yearly_no_data_img);
        linear_yearly_summary_fragment = view.findViewById(R.id.linear_yearly_summary_fragment);

    }

    // Dark/ Light set textview
    private void darkLightSet() {

        if (isDarkMode) {
            //                                                            all
            cat_chart_income_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense2));
            cat_chart_balance_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total2));
            cat_chart_income_all_transaction_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_all_transaction_summary.setTextColor(getResources().getColor(R.color.expense2));
            //                                                           today
            cat_chart_income_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense2));
            cat_chart_balance_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total2));
            cat_chart_income_today_transaction_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_today_transaction_summary.setTextColor(getResources().getColor(R.color.expense2));
            //                                                          weekly
            cat_chart_income_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense2));
            cat_chart_balance_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total2));
            cat_chart_income_weekly_transaction_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_weekly_transaction_summary.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_weekly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_weekly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

            //                                                          monthly
            cat_chart_income_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense2));
            cat_chart_balance_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total2));
            cat_chart_income_monthly_transaction_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_monthly_transaction_summary.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_monthly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_monthly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

            //                                                           yearly
            cat_chart_income_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense2));
            cat_chart_balance_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total2));
            cat_chart_income_yearly_transaction_summary.setTextColor(getResources().getColor(R.color.income2));
            cat_chart_expense_yearly_transaction_summary.setTextColor(getResources().getColor(R.color.expense2));

            less_cat_chart_yearly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_light));
            greater_cat_chart_yearly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_light));

        } else {
            //                                                         all
            cat_chart_income_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense1));
            cat_chart_balance_all_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total1));
            cat_chart_income_all_transaction_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_all_transaction_summary.setTextColor(getResources().getColor(R.color.expense1));
            //                                                        today
            cat_chart_income_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense1));
            cat_chart_balance_today_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total1));
            cat_chart_income_today_transaction_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_today_transaction_summary.setTextColor(getResources().getColor(R.color.expense1));
            //                                                       weekly
            cat_chart_income_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense1));
            cat_chart_balance_weekly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total1));
            cat_chart_income_weekly_transaction_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_weekly_transaction_summary.setTextColor(getResources().getColor(R.color.expense1));


            less_cat_chart_weekly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_weekly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));

            //                                                       monthly
            cat_chart_income_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense1));
            cat_chart_balance_monthly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total1));
            cat_chart_income_monthly_transaction_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_monthly_transaction_summary.setTextColor(getResources().getColor(R.color.expense1));

            less_cat_chart_monthly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_monthly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));
            //                                                         yearly
            cat_chart_income_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.expense1));
            cat_chart_balance_yearly_transaction_txt_summary.setTextColor(getResources().getColor(R.color.total1));
            cat_chart_income_yearly_transaction_summary.setTextColor(getResources().getColor(R.color.income1));
            cat_chart_expense_yearly_transaction_summary.setTextColor(getResources().getColor(R.color.expense1));

            less_cat_chart_yearly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.less_than_dark));
            greater_cat_chart_yearly_summary.setImageDrawable(requireContext().getDrawable(R.drawable.greater_then_dark));
        }


    }

    // ALL Data
    private void allDataShow() {

        // xxxxx Default Show List Data xxxxx
        fetchAllData();

        // xxxxx cat_chart_all_btn Click Data Refresh xxxxx
        cat_chart_all_1_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                cat_chart_all_1_btn_summary.setVisibility(View.INVISIBLE);
                cat_chart_today_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_summary.setVisibility(View.VISIBLE);


                //btn-1
                card_all_category_chart_summary.setVisibility(View.VISIBLE);
                card_today_category_chart_summary.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_summary.setVisibility(View.INVISIBLE);

                // show layout
                cat_chart_all_page_summary.setVisibility(View.VISIBLE);
                cat_chart_today_page_summary.setVisibility(View.GONE);
                cat_chart_weekly_page_summary.setVisibility(View.GONE);
                cat_chart_monthly_page_summary.setVisibility(View.GONE);
                cat_chart_yearly_page_summary.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_summary.setVisibility(View.VISIBLE);
                cat_chart_textView_today_summary.setVisibility(View.GONE);
                summary_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchAllData();

            }
        });

    }

    // Today Data
    private void todayDataShow() {

        // xxxxx cat_chart_today_btn Click Data Refresh xxxxx
        cat_chart_today_1_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btn-2
                cat_chart_all_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_summary.setVisibility(View.INVISIBLE);
                cat_chart_weekly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_summary.setVisibility(View.VISIBLE);

                //btn-1
                card_all_category_chart_summary.setVisibility(View.INVISIBLE);
                card_today_category_chart_summary.setVisibility(View.VISIBLE);
                card_weekly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_summary.setVisibility(View.INVISIBLE);

                // show layout
                cat_chart_all_page_summary.setVisibility(View.GONE);
                cat_chart_today_page_summary.setVisibility(View.VISIBLE);
                cat_chart_weekly_page_summary.setVisibility(View.GONE);
                cat_chart_monthly_page_summary.setVisibility(View.GONE);
                cat_chart_yearly_page_summary.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_summary.setVisibility(View.GONE);
                cat_chart_textView_today_summary.setVisibility(View.VISIBLE);
                summary_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchTodayData();

            }
        });

    }

    // Weekly Data
    private void weeklyDataShow() {

        // xxxxx cat_chart_weekly_btn_1 Click Data Refresh xxxxx
        cat_chart_weekly_1_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentWeek();
                updateCurrentWeekDates();

                //btn-2
                cat_chart_all_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_summary.setVisibility(View.INVISIBLE);
                cat_chart_monthly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_yearly_1_btn_summary.setVisibility(View.VISIBLE);

                //btn-1
                card_all_category_chart_summary.setVisibility(View.INVISIBLE);
                card_today_category_chart_summary.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_summary.setVisibility(View.VISIBLE);
                card_monthly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_summary.setVisibility(View.INVISIBLE);

                // show layout
                cat_chart_all_page_summary.setVisibility(View.GONE);
                cat_chart_today_page_summary.setVisibility(View.GONE);
                cat_chart_weekly_page_summary.setVisibility(View.VISIBLE);
                cat_chart_monthly_page_summary.setVisibility(View.GONE);
                cat_chart_yearly_page_summary.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_summary.setVisibility(View.GONE);
                cat_chart_textView_today_summary.setVisibility(View.GONE);
                summary_less_greater_weekly_cat_chart.setVisibility(View.VISIBLE);
                summary_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchWeeklyData();

            }
        });


        // xxxxx cat_chart_weekly_btn Click Data Refresh xxxxx
        cat_chart_weekly_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetToCurrentWeek();
                updateCurrentWeekDates();
                fetchWeeklyData();
            }
        });

        less_cat_chart_weekly_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousWeekDates();
                fetchWeeklyData();
            }
        });

        greater_cat_chart_weekly_summary.setOnClickListener(new View.OnClickListener() {
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
        cat_chart_monthly_1_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();

                //btn-2
                cat_chart_all_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_summary.setVisibility(View.INVISIBLE);
                cat_chart_yearly_1_btn_summary.setVisibility(View.VISIBLE);

                //btn-1
                card_all_category_chart_summary.setVisibility(View.INVISIBLE);
                card_today_category_chart_summary.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_summary.setVisibility(View.VISIBLE);
                card_yearly_category_chart_summary.setVisibility(View.INVISIBLE);

                // show layout
                cat_chart_all_page_summary.setVisibility(View.GONE);
                cat_chart_today_page_summary.setVisibility(View.GONE);
                cat_chart_weekly_page_summary.setVisibility(View.GONE);
                cat_chart_monthly_page_summary.setVisibility(View.VISIBLE);
                cat_chart_yearly_page_summary.setVisibility(View.GONE);

                //Textview
                cat_chart_textView_all_summary.setVisibility(View.GONE);
                cat_chart_textView_today_summary.setVisibility(View.GONE);
                summary_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_monthly_cat_chart.setVisibility(View.VISIBLE);
                summary_less_greater_yearly_cat_chart.setVisibility(View.GONE);

                fetchMonthlyData();

            }
        });

        // xxxxx cat_chart_monthly_btn Click Data Refresh xxxxx
        cat_chart_monthly_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentMonth();
                updateCurrentMonthDates();
                fetchMonthlyData();

            }
        });

        less_cat_chart_monthly_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousMonthDates();
                fetchMonthlyData();
            }
        });

        greater_cat_chart_monthly_summary.setOnClickListener(new View.OnClickListener() {
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
        cat_chart_yearly_1_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();

                //btn-2
                cat_chart_yearly_1_btn_summary.setVisibility(View.INVISIBLE);
                cat_chart_all_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_today_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_weekly_1_btn_summary.setVisibility(View.VISIBLE);
                cat_chart_monthly_1_btn_summary.setVisibility(View.VISIBLE);

                //btn-1
                card_all_category_chart_summary.setVisibility(View.INVISIBLE);
                card_today_category_chart_summary.setVisibility(View.INVISIBLE);
                card_weekly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_monthly_category_chart_summary.setVisibility(View.INVISIBLE);
                card_yearly_category_chart_summary.setVisibility(View.VISIBLE);

                // show layout
                cat_chart_all_page_summary.setVisibility(View.GONE);
                cat_chart_today_page_summary.setVisibility(View.GONE);
                cat_chart_weekly_page_summary.setVisibility(View.GONE);
                cat_chart_monthly_page_summary.setVisibility(View.GONE);
                cat_chart_yearly_page_summary.setVisibility(View.VISIBLE);

                //Textview
                cat_chart_textView_all_summary.setVisibility(View.GONE);
                cat_chart_textView_today_summary.setVisibility(View.GONE);
                summary_less_greater_weekly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_monthly_cat_chart.setVisibility(View.GONE);
                summary_less_greater_yearly_cat_chart.setVisibility(View.VISIBLE);

                fetchYearlyData();

            }
        });

        // xxxxx cat_chart_yearly_btn Click Data Refresh xxxxx
        cat_chart_yearly_btn_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetToCurrentYear();
                updateCurrentYearDates();
                fetchYearlyData();

            }
        });

        less_cat_chart_yearly_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousYearDates();
                fetchYearlyData();
            }
        });

        greater_cat_chart_yearly_summary.setOnClickListener(new View.OnClickListener() {
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
        if (sum_frg != null) {
            sum_frg.setVisibility(isVisibleToUser ? View.VISIBLE : View.GONE);
        }
    }

    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching All Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching All Data
    @SuppressLint("Range")
    private void fetchAllData() {

        try {

            String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date ASC";
            Cursor cursor = database.rawQuery(query, new String[]{userEmail});

            Income_all = 0;
            Expense_all = 0;

            while (cursor.moveToNext()) {
                Income_all += Integer.parseInt(cursor.getString(1));
                Expense_all += Integer.parseInt(cursor.getString(2));
            }

            cat_chart_income_all_transaction_summary.setText(String.valueOf(Income_all));
            cat_chart_expense_all_transaction_summary.setText(String.valueOf(Expense_all));

            all_balance = Income_all - Expense_all;

            checkListDataAll();

            if (isDarkMode) {

                if (all_balance == 0) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#B2B2B2"));//Black
                } else if (all_balance >= 1) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#4DDA54"));//Green
                } else if (all_balance <= 0) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#FF3838"));//Red
                }

            } else {

                if (all_balance == 0) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#000000"));//Black
                } else if (all_balance >= 1) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#1D5A20"));//Green
                } else if (all_balance <= 0) {
                    cat_chart_balance_all_transaction_summary.setTextColor(Color.parseColor("#E63131"));//Red
                }
            }


            cat_chart_balance_all_transaction_summary.setText(String.valueOf(all_balance));

            // Calculate total income and total expense
            double totalIncome = Income_all; // Replace with your method to calculate total income
            double totalExpense = Expense_all; // Replace with your method to calculate total expense
            double total = totalIncome + totalExpense;
            int total1 = (int) (totalIncome - totalExpense);

            // Calculate percentage of income and expense
            float incomePercentage = (float) (totalIncome / total) * 100;
            float expensePercentage = (float) ((totalExpense / total) * 100);
            int roundedValue1 = Math.round(incomePercentage);
            int roundedValue2 = Math.round(expensePercentage);

            String income_Text = getResources().getString(R.string.income_cat_chart_txt);
            String expense_Text = getResources().getString(R.string.expense_cat_chart_txt);

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(roundedValue1, income_Text)); // Example value and label
            entries.add(new PieEntry(roundedValue2, expense_Text)); // Example value and label

            // Create PieDataSet
            PieDataSet dataSet = new PieDataSet(entries, "");
            dataSet.setColors(color); // Set colors for each slice

            // Percentage Value Formatter
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.format(Locale.getDefault(), "%.0f%%", value);
                }
            });

            // Customize value and label colors
            dataSet.setValueTextColor(Color.BLACK); // Value color
            dataSet.setValueTextSize(12f); // Value text size
            dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

            // Customize label colors
            dataSet.setSliceSpace(5f); // Spacing between slices

            // Create PieData
            PieData data = new PieData(dataSet);
            Legend legend = cat_chart_pie_chart_all_summary.getLegend();
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


            // Set data to PieChart
            cat_chart_pie_chart_all_summary.setData(data);
            cat_chart_pie_chart_all_summary.getDescription().setEnabled(false);
            cat_chart_pie_chart_all_summary.setEntryLabelColor(Color.BLACK);

            if (isDarkMode) {
                cat_chart_pie_chart_all_summary.getLegend().setTextColor(Color.WHITE);

            } else {
                cat_chart_pie_chart_all_summary.getLegend().setTextColor(Color.BLACK);
            }

            String balanceText = getResources().getString(R.string.pie_cat_chart_balance);
            String centerText = balanceText + " " + total1;
            cat_chart_pie_chart_all_summary.setCenterText(centerText);
            cat_chart_pie_chart_all_summary.setCenterTextSize(12f);
            cat_chart_pie_chart_all_summary.setCenterTextColor(Color.BLUE);
            cat_chart_pie_chart_all_summary.setHoleRadius(45f);
            cat_chart_pie_chart_all_summary.animateXY(800, 800);
            cat_chart_pie_chart_all_summary.invalidate(); // Refresh chart

            //cat_pie chart_click event
            int inc_all = Income_all;
            int exp_all = Expense_all;
            cat_chart_pie_chart_all_summary.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                    if (e == null) return;
                    int selectedIndex = (int) h.getX();

                    String message;
                    switch (selectedIndex) {
                        case 0:
                            message = "Income: " + inc_all;
                            break;
                        case 1:
                            message = "Expense: " + exp_all;
                            break;
                        default:
                            message = "Unknown item clicked";
                            break;

                    }
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected() {

                }
            });


        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Today Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Today Data
    @SuppressLint("Range")
    private void fetchTodayData() {

        String query = "SELECT * FROM incexp_tbl WHERE user_email = ? AND entry_date = Strftime('%Y-%m-%d','now')";
        Cursor cursor = database.rawQuery(query, new String[]{userEmail});

        Income_today = 0;
        Expense_today = 0;
        while (cursor.moveToNext()) {
            Income_today += Integer.parseInt(cursor.getString(1));
            Expense_today += Integer.parseInt(cursor.getString(2));
        }

        cat_chart_income_today_transaction_summary.setText(String.valueOf(Income_today));
        cat_chart_expense_today_transaction_summary.setText(String.valueOf(Expense_today));

        today_balance = Income_today - Expense_today;

        checkListDataToday();


        if (isDarkMode) {

            if (today_balance == 0) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (today_balance >= 1) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else if (today_balance <= 0) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (today_balance == 0) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#000000"));//Black
            } else if (today_balance >= 1) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else if (today_balance <= 0) {
                cat_chart_balance_today_transaction_summary.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        cat_chart_balance_today_transaction_summary.setText(String.valueOf(today_balance));

        // Calculate total income and total expense
        double totalIncome = Income_today; // Replace with your method to calculate total income
        double totalExpense = Expense_today; // Replace with your method to calculate total expense
        double total = totalIncome + totalExpense;
        int total1 = (int) (totalIncome - totalExpense);

        // Calculate percentage of income and expense
        float incomePercentage = (float) (totalIncome / total) * 100;
        float expensePercentage = (float) ((totalExpense / total) * 100);
        int roundedValue1 = Math.round(incomePercentage);
        int roundedValue2 = Math.round(expensePercentage);

        String income_Text = getResources().getString(R.string.income_cat_chart_txt);
        String expense_Text = getResources().getString(R.string.expense_cat_chart_txt);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(roundedValue1, income_Text)); // Example value and label
        entries.add(new PieEntry(roundedValue2, expense_Text)); // Example value and label

        // Create PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(color); // Set colors for each slice

        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

// Customize label colors
        dataSet.setSliceSpace(5f); // Spacing between slices

// Create PieData
        PieData data = new PieData(dataSet);
        Legend legend = cat_chart_pie_chart_today_summary.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


// Set data to PieChart
        cat_chart_pie_chart_today_summary.setData(data);
        cat_chart_pie_chart_today_summary.getDescription().setEnabled(false);
        cat_chart_pie_chart_today_summary.setEntryLabelColor(Color.BLACK);
        if (isDarkMode) {
            cat_chart_pie_chart_today_summary.getLegend().setTextColor(Color.WHITE);

        } else {
            cat_chart_pie_chart_today_summary.getLegend().setTextColor(Color.BLACK);
        }

        String balanceText = getResources().getString(R.string.pie_cat_chart_balance);
        String centerText = balanceText + " " + total1;
        cat_chart_pie_chart_today_summary.setCenterText(centerText);
        cat_chart_pie_chart_today_summary.setCenterTextSize(12f);
        cat_chart_pie_chart_today_summary.setCenterTextColor(Color.BLUE);
        cat_chart_pie_chart_today_summary.setHoleRadius(45f);
        cat_chart_pie_chart_today_summary.animateXY(800, 800);
        cat_chart_pie_chart_today_summary.invalidate(); // Refresh chart

        //cat_pie chart_click event
        int inc_today = Income_today;
        int exp_today = Expense_today;
        cat_chart_pie_chart_today_summary.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (e == null) return;
                int selectedIndex = (int) h.getX();

                String message;
                switch (selectedIndex) {
                    case 0:
                        message = "Income: " + inc_today;
                        break;
                    case 1:
                        message = "Expense: " + exp_today;
                        break;
                    default:
                        message = "Unknown item clicked";
                        break;

                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

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

        String startDate = getWeekStartDate();
        String endDate = getWeekEndDate();

        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

        Income_weekly = 0;
        Expense_weekly = 0;

        while (cursor.moveToNext()) {
            Income_weekly += Integer.parseInt(cursor.getString(1));
            Expense_weekly += Integer.parseInt(cursor.getString(2));
        }

        cat_chart_income_weekly_transaction_summary.setText(String.valueOf(Income_weekly));
        cat_chart_expense_weekly_transaction_summary.setText(String.valueOf(Expense_weekly));

        weekly_balance = Income_weekly - Expense_weekly;

        checkListDataWeekly();

        if (isDarkMode) {

            if (weekly_balance == 0) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (weekly_balance >= 1) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else if (weekly_balance <= 0) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (weekly_balance == 0) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#000000"));//Black
            } else if (weekly_balance >= 1) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else if (weekly_balance <= 0) {
                cat_chart_balance_weekly_transaction_summary.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }


        cat_chart_balance_weekly_transaction_summary.setText(String.valueOf(weekly_balance));

        // Calculate total income and total expense
        double totalIncome = Income_weekly; // Replace with your method to calculate total income
        double totalExpense = Expense_weekly; // Replace with your method to calculate total expense
        double total = totalIncome + totalExpense;
        int total1 = (int) (totalIncome - totalExpense);

        // Calculate percentage of income and expense
        float incomePercentage = (float) (totalIncome / total) * 100;
        float expensePercentage = (float) ((totalExpense / total) * 100);
        int roundedValue1 = Math.round(incomePercentage);
        int roundedValue2 = Math.round(expensePercentage);

        String income_Text = getResources().getString(R.string.income_cat_chart_txt);
        String expense_Text = getResources().getString(R.string.expense_cat_chart_txt);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(roundedValue1, income_Text)); // Example value and label
        entries.add(new PieEntry(roundedValue2, expense_Text)); // Example value and label

        // Create PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(color); // Set colors for each slice

        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

// Customize label colors
        dataSet.setSliceSpace(5f); // Spacing between slices

// Create PieData
        PieData data = new PieData(dataSet);
        Legend legend = cat_chart_pie_chart_weekly_summary.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


// Set data to PieChart
        cat_chart_pie_chart_weekly_summary.setData(data);
        cat_chart_pie_chart_weekly_summary.getDescription().setEnabled(false);
        cat_chart_pie_chart_weekly_summary.setEntryLabelColor(Color.BLACK);
        if (isDarkMode) {
            cat_chart_pie_chart_weekly_summary.getLegend().setTextColor(Color.WHITE);

        } else {
            cat_chart_pie_chart_weekly_summary.getLegend().setTextColor(Color.BLACK);
        }
        String balanceText = getResources().getString(R.string.pie_cat_chart_balance);
        String centerText = balanceText + " " + total1;
        cat_chart_pie_chart_weekly_summary.setCenterText(centerText);
        cat_chart_pie_chart_weekly_summary.setCenterTextSize(12f);
        cat_chart_pie_chart_weekly_summary.setCenterTextColor(Color.BLUE);
        cat_chart_pie_chart_weekly_summary.setHoleRadius(45f);
        cat_chart_pie_chart_weekly_summary.animateXY(800, 800);
        cat_chart_pie_chart_weekly_summary.invalidate(); // Refresh chart

        //cat_pie chart_click event
        int inc_weekly = Income_weekly;
        int exp_weekly = Expense_weekly;
        cat_chart_pie_chart_weekly_summary.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (e == null) return;
                int selectedIndex = (int) h.getX();

                String message;
                switch (selectedIndex) {
                    case 0:
                        message = "Income: " + inc_weekly;
                        break;
                    case 1:
                        message = "Expense: " + exp_weekly;
                        break;
                    default:
                        message = "Unknown item clicked";
                        break;

                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Monthly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Monthly Data
    @SuppressLint("Range")
    private void fetchMonthlyData() {

        String startDate = getMonthStartDate();
        String endDate = getMonthEndDate();


        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

        Income_monthly = 0;
        Expense_monthly = 0;

        while (cursor.moveToNext()) {
            Income_monthly += Integer.parseInt(cursor.getString(1));
            Expense_monthly += Integer.parseInt(cursor.getString(2));
        }

        cat_chart_income_monthly_transaction_summary.setText(String.valueOf(Income_monthly));
        cat_chart_expense_monthly_transaction_summary.setText(String.valueOf(Expense_monthly));

        monthly_balance = Income_monthly - Expense_monthly;

        checkListDataMonthly();

        if (isDarkMode) {

            if (monthly_balance == 0) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (monthly_balance >= 1) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else if (monthly_balance <= 0) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (monthly_balance == 0) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#000000"));//Black
            } else if (monthly_balance >= 1) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else if (monthly_balance <= 0) {
                cat_chart_balance_monthly_transaction_summary.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        cat_chart_balance_monthly_transaction_summary.setText(String.valueOf(monthly_balance));

        // Calculate total income and total expense
        double totalIncome = Income_monthly; // Replace with your method to calculate total income
        double totalExpense = Expense_monthly; // Replace with your method to calculate total expense
        double total = totalIncome + totalExpense;
        int total1 = (int) (totalIncome - totalExpense);

        // Calculate percentage of income and expense
        float incomePercentage = (float) (totalIncome / total) * 100;
        float expensePercentage = (float) ((totalExpense / total) * 100);
        int roundedValue1 = Math.round(incomePercentage);
        int roundedValue2 = Math.round(expensePercentage);

        String income_Text = getResources().getString(R.string.income_cat_chart_txt);
        String expense_Text = getResources().getString(R.string.expense_cat_chart_txt);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(roundedValue1, income_Text)); // Example value and label
        entries.add(new PieEntry(roundedValue2, expense_Text)); // Example value and label

        // Create PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(color); // Set colors for each slice

        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

// Customize label colors
        dataSet.setSliceSpace(5f); // Spacing between slices

// Create PieData
        PieData data = new PieData(dataSet);
        Legend legend = cat_chart_pie_chart_monthly_summary.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


// Set data to PieChart
        cat_chart_pie_chart_monthly_summary.setData(data);
        cat_chart_pie_chart_monthly_summary.getDescription().setEnabled(false);
        cat_chart_pie_chart_monthly_summary.setEntryLabelColor(Color.BLACK);
        if (isDarkMode) {
            cat_chart_pie_chart_monthly_summary.getLegend().setTextColor(Color.WHITE);

        } else {
            cat_chart_pie_chart_monthly_summary.getLegend().setTextColor(Color.BLACK);
        }
        String balanceText = getResources().getString(R.string.pie_cat_chart_balance);
        String centerText = balanceText + " " + total1;
        cat_chart_pie_chart_monthly_summary.setCenterText(centerText);
        cat_chart_pie_chart_monthly_summary.setCenterTextSize(12f);
        cat_chart_pie_chart_monthly_summary.setCenterTextColor(Color.BLUE);
        cat_chart_pie_chart_monthly_summary.setHoleRadius(45f);
        cat_chart_pie_chart_monthly_summary.animateXY(800, 800);
        cat_chart_pie_chart_monthly_summary.invalidate(); // Refresh chart

        //cat_pie chart_click event
        int inc_monthly = Income_monthly;
        int exp_monthly = Expense_monthly;
        cat_chart_pie_chart_monthly_summary.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (e == null) return;
                int selectedIndex = (int) h.getX();

                String message;
                switch (selectedIndex) {
                    case 0:
                        message = "Income: " + inc_monthly;
                        break;
                    case 1:
                        message = "Expense: " + exp_monthly;
                        break;
                    default:
                        message = "Unknown item clicked";
                        break;

                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });


    }


    //                          <<<----------------------------|||||||||||||||||||||||||||||||||||   Fetching Yearly Data  |||||||||||||||||||||||||||||||||||||||--------------------------------->>>
    //Fetching Yearly Data
    @SuppressLint("Range")
    private void fetchYearlyData() {

        String startDate = getYearStartDate();
        String endDate = getYearEndDate();

        String query = "SELECT * FROM incexp_tbl WHERE entry_date BETWEEN ? AND ? AND user_email = ? ORDER By entry_date DESC";
        Cursor cursor = database.rawQuery(query, new String[]{startDate, endDate, userEmail});

        Income_yearly = 0;
        Expense_yearly = 0;
        while (cursor.moveToNext()) {
            Income_yearly += Integer.parseInt(cursor.getString(1));
            Expense_yearly += Integer.parseInt(cursor.getString(2));
        }

        cat_chart_income_yearly_transaction_summary.setText(String.valueOf(Income_yearly));
        cat_chart_expense_yearly_transaction_summary.setText(String.valueOf(Expense_yearly));

        yearly_balance = Income_yearly - Expense_yearly;

        checkListDataYearly();

        if (isDarkMode) {

            if (yearly_balance == 0) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#B2B2B2"));//Black
            } else if (yearly_balance >= 1) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#4DDA54"));//Green
            } else if (yearly_balance <= 0) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#FF3838"));//Red
            }

        } else {

            if (yearly_balance == 0) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#000000"));//Black
            } else if (yearly_balance >= 1) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#1D5A20"));//Green
            } else if (yearly_balance <= 0) {
                cat_chart_balance_yearly_transaction_summary.setTextColor(Color.parseColor("#E63131"));//Red
            }
        }

        cat_chart_balance_yearly_transaction_summary.setText(String.valueOf(yearly_balance));

        // Calculate total income and total expense
        double totalIncome = Income_yearly; // Replace with your method to calculate total income
        double totalExpense = Expense_yearly; // Replace with your method to calculate total expense
        double total = totalIncome + totalExpense;
        int total1 = (int) (totalIncome - totalExpense);

        // Calculate percentage of income and expense
        float incomePercentage = (float) (totalIncome / total) * 100;
        float expensePercentage = (float) ((totalExpense / total) * 100);
        int roundedValue1 = Math.round(incomePercentage);
        int roundedValue2 = Math.round(expensePercentage);

        String income_Text = getResources().getString(R.string.income_cat_chart_txt);
        String expense_Text = getResources().getString(R.string.expense_cat_chart_txt);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(roundedValue1, income_Text)); // Example value and label
        entries.add(new PieEntry(roundedValue2, expense_Text)); // Example value and label

        // Create PieDataSet
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(color); // Set colors for each slice

        // Percentage Value Formatter
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.0f%%", value);
            }
        });

        // Customize value and label colors
        dataSet.setValueTextColor(Color.BLACK); // Value color
        dataSet.setValueTextSize(12f); // Value text size
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD); // Value typeface

// Customize label colors
        dataSet.setSliceSpace(5f); // Spacing between slices

// Create PieData
        PieData data = new PieData(dataSet);
        Legend legend = cat_chart_pie_chart_yearly_summary.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);


// Set data to PieChart
        cat_chart_pie_chart_yearly_summary.setData(data);
        cat_chart_pie_chart_yearly_summary.getDescription().setEnabled(false);
        cat_chart_pie_chart_yearly_summary.setEntryLabelColor(Color.BLACK);
        if (isDarkMode) {
            cat_chart_pie_chart_yearly_summary.getLegend().setTextColor(Color.WHITE);

        } else {
            cat_chart_pie_chart_yearly_summary.getLegend().setTextColor(Color.BLACK);
        }

        String balanceText = getResources().getString(R.string.pie_cat_chart_balance);
        String centerText = balanceText + " " + total1;
        cat_chart_pie_chart_yearly_summary.setCenterText(centerText);
        cat_chart_pie_chart_yearly_summary.setCenterTextSize(12f);
        cat_chart_pie_chart_yearly_summary.setCenterTextColor(Color.BLUE);
        cat_chart_pie_chart_yearly_summary.setHoleRadius(45f);
        cat_chart_pie_chart_yearly_summary.animateXY(800, 800);
        cat_chart_pie_chart_yearly_summary.invalidate(); // Refresh chart

        //cat_pie chart_click event
        int inc_yearly = Income_yearly;
        int exp_yearly = Expense_yearly;
        cat_chart_pie_chart_yearly_summary.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                if (e == null) return;
                int selectedIndex = (int) h.getX();

                String message;
                switch (selectedIndex) {
                    case 0:
                        message = "Income: " + inc_yearly;
                        break;
                    case 1:
                        message = "Expense: " + exp_yearly;
                        break;
                    default:
                        message = "Unknown item clicked";
                        break;

                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

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
        cat_chart_textView_weekly_summary.setText(dateRange); // Update your TextView here
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
        cat_chart_textView_monthly_summary.setText(dateRange);
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
        cat_chart_textView_yearly_summary.setText(dateRange);
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
        if (all_balance == 0) {
            linear_all_summary_fragment.setVisibility(View.GONE);
            summary_fragment_all_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_all_summary_fragment.setVisibility(View.VISIBLE);
            summary_fragment_all_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataToday() {
        if (today_balance == 0) {
            linear_today_summary_fragment.setVisibility(View.GONE);
            summary_fragment_today_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_today_summary_fragment.setVisibility(View.VISIBLE);
            summary_fragment_today_no_data_img.setVisibility(View.GONE);
        }
    }


    private void checkListDataWeekly() {
        if (weekly_balance == 0) {
            linear_weekly_summary_fragment.setVisibility(View.GONE);
            summary_fragment_weekly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_weekly_summary_fragment.setVisibility(View.VISIBLE);
            summary_fragment_weekly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataMonthly() {
        if (monthly_balance == 0) {
            linear_monthly_summary_fragment.setVisibility(View.GONE);
            summary_fragment_monthly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_monthly_summary_fragment.setVisibility(View.VISIBLE);
            summary_fragment_monthly_no_data_img.setVisibility(View.GONE);
        }
    }

    private void checkListDataYearly() {
        if (yearly_balance == 0) {
            linear_yearly_summary_fragment.setVisibility(View.GONE);
            summary_fragment_yearly_no_data_img.setVisibility(View.VISIBLE);
        } else {
            linear_yearly_summary_fragment.setVisibility(View.VISIBLE);
            summary_fragment_yearly_no_data_img.setVisibility(View.GONE);
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
        cat_chart_all_btn_summary.setText(R.string.all_btn_txt_lay2_cat_chart);
        cat_chart_all_1_btn_summary.setText(R.string.all_btn_txt_lay2_cat_chart);

        cat_chart_today_btn_summary.setText(R.string.today_btn_txt_lay2_cat_chart);
        cat_chart_today_1_btn_summary.setText(R.string.today_btn_txt_lay2_cat_chart);

        cat_chart_weekly_btn_summary.setText(R.string.weekly_btn_txt_lay2_cat_chart);
        cat_chart_weekly_1_btn_summary.setText(R.string.weekly_btn_txt_lay2_cat_chart);

        cat_chart_monthly_btn_summary.setText(R.string.monthly_btn_txt_lay2_cat_chart);
        cat_chart_monthly_1_btn_summary.setText(R.string.monthly_btn_txt_lay2_cat_chart);

        cat_chart_yearly_btn_summary.setText(R.string.yearly_btn_txt_lay2_cat_chart);
        cat_chart_yearly_1_btn_summary.setText(R.string.yearly_btn_txt_lay2_cat_chart);

//        // Layout ---- 2
        cat_chart_textView_all_summary.setText(R.string.all_btn_txt_lay3_cat_chart);
        cat_chart_textView_today_summary.setText(R.string.today_btn_txt_lay3_cat_chart);
        cat_chart_textView_weekly_summary.setText(R.string.weekly_btn_txt_lay3_cat_chart);
        cat_chart_textView_monthly_summary.setText(R.string.monthly_btn_txt_lay3_cat_chart);
        cat_chart_textView_yearly_summary.setText(R.string.yearly_btn_txt_lay3_cat_chart);


//        // Layout ---- 3
        cat_chart_income_all_transaction_txt_summary.setText(R.string.total_inc_txt_lay4_cat_chart);
        cat_chart_expense_all_transaction_txt_summary.setText(R.string.total_exp_txt_lay4_cat_chart);
        cat_chart_balance_all_transaction_txt_summary.setText(R.string.balance_txt_lay4_cat_chart);

        cat_chart_income_today_transaction_txt_summary.setText(R.string.total_inc_txt_lay4_cat_chart);
        cat_chart_expense_today_transaction_txt_summary.setText(R.string.total_exp_txt_lay4_cat_chart);
        cat_chart_balance_today_transaction_txt_summary.setText(R.string.balance_txt_lay4_cat_chart);

        cat_chart_income_weekly_transaction_txt_summary.setText(R.string.total_inc_txt_lay4_cat_chart);
        cat_chart_expense_weekly_transaction_txt_summary.setText(R.string.total_exp_txt_lay4_cat_chart);
        cat_chart_balance_weekly_transaction_txt_summary.setText(R.string.balance_txt_lay4_cat_chart);

        cat_chart_income_monthly_transaction_txt_summary.setText(R.string.total_inc_txt_lay4_cat_chart);
        cat_chart_expense_monthly_transaction_txt_summary.setText(R.string.total_exp_txt_lay4_cat_chart);
        cat_chart_balance_monthly_transaction_txt_summary.setText(R.string.balance_txt_lay4_cat_chart);

        cat_chart_income_yearly_transaction_txt_summary.setText(R.string.total_inc_txt_lay4_cat_chart);
        cat_chart_expense_yearly_transaction_txt_summary.setText(R.string.total_exp_txt_lay4_cat_chart);
        cat_chart_balance_yearly_transaction_txt_summary.setText(R.string.balance_txt_lay4_cat_chart);

    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

}