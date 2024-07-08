package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.income_and_expense.Adapter.EmailAndName_Adapter;
import com.example.income_and_expense.Adapter.Email_Adapter;
import com.example.income_and_expense.Adapter.Incomeexpense_1_Adapter;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Home_Activity extends AppCompatActivity {

    // Layout 1
    TextView add_income_txt_lay1_home, add_expense_txt_lay1_home, transfer_txt_lay1_home, transaction_txt_lay1_home;
    CardView addincome_cardview_lay1_home, addexpense_cardview_lay1_home, transfer_cardview_lay1_home, transactions_cardview_lay1_home;

    // Layout 2
    TextView date_txt1_lay2_home, date_txt2_lay2_home, date_txt3_lay2_home;
    TextView income_lay2_home, income_lay2_home_1, expense_lay2_home, expense_lay2_home_1, total_balance_lay2_home, total_balance_lay2_home_1;
    TextView prev_balance_lay2_home, prev_balance_lay2_home_1, balance_lay2_home, balance_lay2_home_1;

    // Layout 3
    CardView cardview_lay3_home;
    TextView recent_transaction_txt_lay3_home;
    ImageView recent_transaction_close_btn_lay3_home;
    TextView recent_transaction_more_btn_lay3_home;
    ListView recent_transactions_list_lay3_home;


    // Layout 4
    CardView cardview_lay4_home;
    TextView pay_meth_txt_lay4_home;
    ImageView pay_meth_close_btn_lay4_home;
    TextView pay_meth_more_btn_lay4_home;
    ListView pay_meth_list_lay4_home;


    // Layout 5
    CardView cardview_lay5_home;
    TextView monthly_expense_txt_lay5_home;
    ImageView monthly_expense_close_btn_lay5_home;
    TextView monthly_expense_more_btn_lay5_home;
    BarChart monthly_expense_Barchart_lay5_home;


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private String userEmail;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView pra_txt_home;
    List<Income_Expense_Model> list;
    EmailAndName_Adapter emailAndName_adapter;

    Incomeexpense_1_Adapter inc_exp_adapter;
    Payment_method_Adapter pay_adapter;

    Date currentDate = new Date();

    Calendar calendar = Calendar.getInstance();
    Calendar calendar1 = Calendar.getInstance();

    ImageView imageViewNav;

    TextView txtViewNameNav;

    // Dark and Light Mode
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";
    boolean isDarkMode;

    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    SQLiteDatabase db;
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";

    private SQLiteDatabase createDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    private void createTable1() {
        String income_Table = "CREATE TABLE IF NOT EXISTS incexp_tbl (" +
                "regid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "income VARCHAR(50) NOT NULL," +
                "expense VARCHAR(50) NOT NULL," +
                "cat_img BLOB," +
                "category VARCHAR(50) NOT NULL," +
                "paymentmethod VARCHAR(50) NOT NULL," +
                "notes VARCHAR(50) NOT NULL," +
                "entry_date DATE NOT NULL," +
                "time VARCHAR(50) NOT NULL," +
                "user_email VARCHAR(50) NOT NULL" +
                ")";
        db.execSQL(income_Table);
    }

    private void createTable2() {
        String income_Table = "CREATE TABLE IF NOT EXISTS incexp_tbl (" +
                "regid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "income VARCHAR(50) NOT NULL," +
                "expense VARCHAR(50) NOT NULL," +
                "cat_img BLOB," +
                "category VARCHAR(50) NOT NULL," +
                "paymentmethod VARCHAR(50) NOT NULL," +
                "notes VARCHAR(50) NOT NULL," +
                "entry_date DATE NOT NULL," +
                "time VARCHAR(50) NOT NULL," +
                "user_email VARCHAR(50) NOT NULL" +
                ")";
        db.execSQL(income_Table);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dark and Light  Mode
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Language default
        setLocal(getSavedLanguage());

        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");


        // Create Database
        db = createDatabase();
        createTable1();
        createTable2();

        // Initialize views
        initializeViews();

        // Dark/ Light set Textview
        set_DarkLight_txtview();

        // set Image and Text navigationview
        setImageView_and_Textview_nav();

        //Update Date 1st Layout
        updateDate();

        // nav-drawer and toolbar
        navigationDrawer_And_Toolbar();

        // Navigation View click
        NavigationView_click_event();


        // All button click events
        all_button_click_event();

        // ALL Cardview Event
        allCardClickEvent();


        // Update Dark / Light mode Title
        updateMenuItemTitle(isDarkMode);


    }

    //x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    // nav-drawer and toolbar
    private void navigationDrawer_And_Toolbar() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load selected account from SharedPreferences
        String selectedEmail = loadSelectedAccount();
        if (selectedEmail != null) {
            userEmail = selectedEmail; // Set the userEmail to the selected email
            fetchAndSetEmail(selectedEmail);
        } else {
            showAccount_From_Dialog(userEmail);
        }

        // Set the toolbar title click listener
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Toolbar_Dialog();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Initialize views
    private void initializeViews() {

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.left_nav_view);


        // Inflate the header view
        View headerView = navigationView.getHeaderView(0);

        // Find the ImageView and TextView in the header view
         imageViewNav = headerView.findViewById(R.id.imageView_nav);
         txtViewNameNav = headerView.findViewById(R.id.txtview_name_nav);
//                                                         #1
        add_income_txt_lay1_home = findViewById(R.id.add_income_txt_lay1_home);
        add_expense_txt_lay1_home = findViewById(R.id.add_expense_txt_lay1_home);
        transfer_txt_lay1_home = findViewById(R.id.transfer_txt_lay1_home);
        transaction_txt_lay1_home = findViewById(R.id.transaction_txt_lay1_home);

        //                               Layout1 --> 4 BUTTON
        addincome_cardview_lay1_home = findViewById(R.id.addincome_cardview_lay1_home);
        addexpense_cardview_lay1_home = findViewById(R.id.addexpense_cardview_lay1_home);
        transfer_cardview_lay1_home = findViewById(R.id.transfer_cardview_lay1_home);
        transactions_cardview_lay1_home = findViewById(R.id.transactions_cardview_lay1_home);

        //                                                #2
        //  Layout2 --> DATE
        date_txt1_lay2_home = findViewById(R.id.date_txt1_lay2_home);
        date_txt2_lay2_home = findViewById(R.id.date_txt2_lay2_home);
        date_txt3_lay2_home = findViewById(R.id.date_txt3_lay2_home);
        //                               Layout2 --> TEXTVIEW
        income_lay2_home = findViewById(R.id.income_lay2_home);//#1
        income_lay2_home_1 = findViewById(R.id.income_lay2_home_1);//#1
        expense_lay2_home = findViewById(R.id.expense_lay2_home);//#2
        expense_lay2_home_1 = findViewById(R.id.expense_lay2_home_1);//#2
        total_balance_lay2_home = findViewById(R.id.total_balance_lay2_home);//#3
        total_balance_lay2_home_1 = findViewById(R.id.total_balance_lay2_home_1);//#3
        prev_balance_lay2_home = findViewById(R.id.prev_balance_lay2_home);//#4
        prev_balance_lay2_home_1 = findViewById(R.id.prev_balance_lay2_home_1);//#4
        balance_lay2_home = findViewById(R.id.balance_lay2_home);//#5
        balance_lay2_home_1 = findViewById(R.id.balance_lay2_home_1);//#5

        //                                           #3
        cardview_lay3_home = findViewById(R.id.cardview_lay3_home);// #cardview
        recent_transaction_txt_lay3_home = findViewById(R.id.recent_transaction_txt_lay3_home); // #text
        recent_transaction_close_btn_lay3_home = findViewById(R.id.recent_transaction_close_btn_lay3_home);// #close btn
        recent_transaction_more_btn_lay3_home = findViewById(R.id.recent_transaction_more_btn_lay3_home);// #more btn
        recent_transactions_list_lay3_home = findViewById(R.id.recent_transactions_list_lay3_home);// #Recent Transaction Listview

        //                                           #4
        cardview_lay4_home = findViewById(R.id.cardview_lay4_home);
        pay_meth_txt_lay4_home = findViewById(R.id.pay_meth_txt_lay4_home); // #text
        pay_meth_close_btn_lay4_home = findViewById(R.id.pay_meth_close_btn_lay4_home);// #close btn
        pay_meth_more_btn_lay4_home = findViewById(R.id.pay_meth_more_btn_lay4_home);// #more btn
        pay_meth_list_lay4_home = findViewById(R.id.pay_meth_list_lay4_home);// #Payment  Method Listview


        //                                           #5
        cardview_lay5_home = findViewById(R.id.cardview_lay5_home);
        monthly_expense_txt_lay5_home = findViewById(R.id.monthly_expense_txt_lay5_home); // #text
        monthly_expense_close_btn_lay5_home = findViewById(R.id.monthly_expense_close_btn_lay5_home);// #close btn
        monthly_expense_more_btn_lay5_home = findViewById(R.id.monthly_expense_more_btn_lay5_home);// #more btn
        monthly_expense_Barchart_lay5_home = findViewById(R.id.monthly_expense_barchart_lay5_home);// #Monthly expense Barchart


        pra_txt_home = findViewById(R.id.pra_txt_home);
        list = new ArrayList<>();

    }

    // Dark/ Light set Textview
    private void set_DarkLight_txtview() {
        // Dark / Light Mode
        if (isDarkMode) {
            income_lay2_home.setTextColor(getResources().getColor(R.color.income2));
            income_lay2_home_1.setTextColor(getResources().getColor(R.color.income2));
            expense_lay2_home.setTextColor(getResources().getColor(R.color.expense2));//Black
            expense_lay2_home_1.setTextColor(getResources().getColor(R.color.expense2));//Black
        } else {
            income_lay2_home.setTextColor(getResources().getColor(R.color.income1));
            income_lay2_home_1.setTextColor(getResources().getColor(R.color.income1));//Black
            expense_lay2_home.setTextColor(getResources().getColor(R.color.expense1));//Black
            expense_lay2_home_1.setTextColor(getResources().getColor(R.color.expense1));//Black
        }
    }

    // image and text set navigationview
    @SuppressLint("Range")
    private void setImageView_and_Textview_nav() {

        // Set the image and text
        try {
            String query = "SELECT name, img FROM loginandregister_table WHERE email = ?";
            Cursor cursor = db.rawQuery(query, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex("img"));
                // Convert byte array to Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageViewNav.setImageBitmap(imageBitmap);
                txtViewNameNav.setText(name);

            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Navigation View click
    private void NavigationView_click_event() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                // nav_recentTransaction
                if (id == R.id.nav_recenttransaction) {
                    cardview_lay3_home.setVisibility(View.VISIBLE);
                    recentTransaction();
                }

                if (id == R.id.nav_paymentmethod) {
                    cardview_lay4_home.setVisibility(View.VISIBLE);
                    paymentMethod();
                }

                if (id == R.id.nav_category) {
                    cardview_lay5_home.setVisibility(View.VISIBLE);
                    monthly_expense();
                }

                if (id == R.id.nav_paymentmethod_chart) {
                    Intent i = new Intent(Home_Activity.this, Payment_Method_Chart_Activity.class);
                    i.putExtra("USER_EMAIL", userEmail);
                    startActivity(i);
                }

                if (id == R.id.nav_category_chart) {
                    Intent i = new Intent(Home_Activity.this, Category_Chart_Activity.class);
                    i.putExtra("USER_EMAIL", userEmail);
                    startActivity(i);
                }

                if (id == R.id.nav_profile) {
                    Intent i = new Intent(Home_Activity.this, Profile_Activity.class);
                    i.putExtra("USER_EMAIL", userEmail);
                    startActivity(i);
                }

                if (id == R.id.nav_darkmode) {
                    toggleDarkMode(isDarkMode);
                }

                if (id == R.id.nav_language) {
                    showChangeLanguageDialog();
                }

                if (id == R.id.nav_rateus) {
                    rateUs();
                }

                if (id == R.id.nav_shareapp) {
                    shareApp();
                }

                if (id == R.id.nav_logout) {
                    logout();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }


    // All button click events
    private void all_button_click_event() {

        // Add Income
        addincome_cardview_lay1_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Income_Activity.class);
                i.putExtra("USER_EMAIL", userEmail);
                startActivity(i);
            }
        });

        // Add Expense
        addexpense_cardview_lay1_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Expense_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

        //Transfer
        transfer_cardview_lay1_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Transfer_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

        //Transaction
        transactions_cardview_lay1_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Transactions_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

        // fetchAndSet Layout - 1
        fetchAndSet_Layout_Data();


    }

    // layout - 1 Fetach and set Data
    private void fetchAndSet_Layout_Data() {
        try {

            String query1 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') AND user_email = ? ";
            Cursor cursor1 = db.rawQuery(query1, new String[]{userEmail});

            int income = 0;
            int expense = 0;

            while (cursor1.moveToNext()) {
                income += Integer.parseInt(cursor1.getString(1));
                expense += Integer.parseInt(cursor1.getString(2));
            }

            income_lay2_home_1.setText(String.valueOf(income));
            expense_lay2_home_1.setText(String.valueOf(expense));

            int balance = income - expense;

            String query2 = "SELECT * FROM incexp_tbl WHERE strftime('%Y-%m', entry_date) < strftime('%Y-%m', 'now') AND user_email = ? ";
            Cursor cursor2 = db.rawQuery(query2, new String[]{userEmail});

            int inc = 0;
            int exp = 0;

            while (cursor2.moveToNext()) {
                inc += Integer.parseInt(cursor2.getString(1));
                exp += Integer.parseInt(cursor2.getString(2));
            }


            int pre_bal = inc - exp;
            int total_bal = balance + pre_bal;

            if (isDarkMode) {
                total_balance_lay2_home_1.setTextColor(Color.parseColor("#B2B2B2"));//Black
                prev_balance_lay2_home_1.setTextColor(Color.parseColor("#B2B2B2"));//Black
                balance_lay2_home_1.setTextColor(Color.parseColor("#B2B2B2"));//Black


            } else {

                if (balance == 0) {
                    total_balance_lay2_home_1.setTextColor(Color.parseColor("#000000"));//Black
                } else if (balance >= 1) {
                    total_balance_lay2_home_1.setTextColor(Color.parseColor("#1D5A20"));//Green
                } else {
                    total_balance_lay2_home_1.setTextColor(Color.parseColor("#E63131"));//Red
                }


                if (pre_bal == 0) {
                    prev_balance_lay2_home_1.setTextColor(Color.parseColor("#000000"));//Black
                } else if (pre_bal >= 1) {
                    prev_balance_lay2_home_1.setTextColor(Color.parseColor("#1D5A20"));//Green
                } else {
                    prev_balance_lay2_home_1.setTextColor(Color.parseColor("#E63131"));//Red
                }

                if (total_bal == 0) {
                    balance_lay2_home_1.setTextColor(Color.parseColor("#000000"));//Black
                } else if (total_bal >= 1) {
                    balance_lay2_home_1.setTextColor(Color.parseColor("#1D5A20"));//Green
                } else {
                    balance_lay2_home_1.setTextColor(Color.parseColor("#E63131"));//Red
                }


            }

            total_balance_lay2_home_1.setText(String.valueOf(balance));
            prev_balance_lay2_home_1.setText(String.valueOf(pre_bal));
            balance_lay2_home_1.setText(String.valueOf(total_bal));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // ALL Cardview Event
    private void allCardClickEvent() {

        //recent_transaction_close_btn
        recent_transaction_close_btn_lay3_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_lay3_home.setVisibility(View.GONE);
            }
        });

        //payment_method_close_btn
        pay_meth_close_btn_lay4_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_lay4_home.setVisibility(View.GONE);
            }
        });

        //monthly_expense_close_btn
        monthly_expense_close_btn_lay5_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_lay5_home.setVisibility(View.GONE);
            }
        });

        //recent_transaction_more_btn
        recent_transaction_more_btn_lay3_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Transactions_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

        //payment_method_more_btn
        pay_meth_more_btn_lay4_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Payment_Method_Chart_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

        //monthly_expense_more_btn
        monthly_expense_more_btn_lay5_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this, Category_Chart_Activity.class);
                i.putExtra("USER_EMAIL", userEmail); // Use the current userEmail
                startActivity(i);
            }
        });

    }


    //Update Date 1st Layout
    private void updateDate() {

        //Set DATE
        calendar.setTime(currentDate);
        calendar1.setTime(currentDate);

        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Set the calendar to the first day of the month
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar1.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

        // Get the first date of the month
        Date firstDateOfMonth = calendar.getTime();
        Date firstDateOfMonth1 = calendar1.getTime();

        // Format the first date of the month using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(firstDateOfMonth);
        String formattedDate1 = dateFormat.format(firstDateOfMonth1);

        date_txt1_lay2_home.setText(formattedDate);
        date_txt3_lay2_home.setText(formattedDate1);

    }


    //  Recent Transaction
    public void recentTransaction() {
        try {
            list.clear();

            // Fetch income and expense for the ALL
            String query = "SELECT * FROM incexp_tbl WHERE user_email = ? ORDER BY entry_date DESC";
            Cursor cursor = db.rawQuery(query, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                do {
                    int Reg_id = cursor.getInt(0);
                    String Income = cursor.getString(1);
                    String Expense = cursor.getString(2);
                    String Category = cursor.getString(4);
                    String Paymentmethod = cursor.getString(5);
                    String Date = cursor.getString(7);

                    Income_Expense_Model model = new Income_Expense_Model(Reg_id, Income, Expense, Category, Paymentmethod, Date);
                    list.add(model);

                }
                while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use


            inc_exp_adapter = new Incomeexpense_1_Adapter(this, list, db, userEmail);
            recent_transactions_list_lay3_home.setAdapter(inc_exp_adapter);

            inc_exp_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(Home_Activity.this, "Home : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //  Payment Method
    @SuppressLint("Range")
    private void paymentMethod() {
        try {
            list.clear();

            String query = "SELECT paymentmethod, SUM(income - expense) AS total_incomeexpense FROM incexp_tbl WHERE user_email = ? GROUP BY paymentmethod ORDER BY total_incomeexpense DESC";
            Cursor cursor1 = db.rawQuery(query, new String[]{userEmail});

            if (cursor1.moveToFirst()) {
                do {

                    String paymentMethod = cursor1.getString(cursor1.getColumnIndex("paymentmethod"));
                    int totalIncome = cursor1.getInt(cursor1.getColumnIndex("total_incomeexpense"));
                    Income_Expense_Model model = new Income_Expense_Model(totalIncome, paymentMethod);
                    list.add(model);
                } while (cursor1.moveToNext());
                cursor1.close();
            }
            pay_adapter = new Payment_method_Adapter(Home_Activity.this, list, db, userEmail);
            pay_meth_list_lay4_home.setAdapter(pay_adapter);
            setListViewHeightBasedOnChildren(pay_meth_list_lay4_home);  // Adjust ListView height based on its content

            pay_adapter.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //  Category (Monthly expense)
    @SuppressLint("Range")
    private void monthly_expense() {

        String query = "SELECT category, SUM(expense) AS total_expense FROM incexp_tbl WHERE user_email = ? AND strftime('%Y-%m', entry_date) = strftime('%Y-%m', 'now') GROUP BY category ORDER BY total_expense DESC";
        Cursor cursor1 = db.rawQuery(query, new String[]{userEmail});

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        int index = 0;

        while (cursor1.moveToNext()) {
            String category = cursor1.getString(0);
            int expense;
            expense = cursor1.getInt(1);
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
        monthly_expense_Barchart_lay5_home.setFitBars(true);
        monthly_expense_Barchart_lay5_home.setData(barData);
        monthly_expense_Barchart_lay5_home.animateY(1000);

        // Customize the X-axis labels
        monthly_expense_Barchart_lay5_home.getXAxis().setValueFormatter(new IndexAxisValueFormatter(categories));
        monthly_expense_Barchart_lay5_home.getXAxis().setGranularity(1f);
        monthly_expense_Barchart_lay5_home.getXAxis().setGranularityEnabled(true);
        if (isDarkMode) {
            monthly_expense_Barchart_lay5_home.getXAxis().setTextColor(Color.GRAY);
        } else {
            monthly_expense_Barchart_lay5_home.getXAxis().setTextColor(Color.BLACK);

        }


        // Customize YAxis
        YAxis leftYAxis = monthly_expense_Barchart_lay5_home.getAxisLeft();
        leftYAxis.setAxisLineWidth(0.5f);
        leftYAxis.setDrawGridLines(false);
        if (isDarkMode) {
            leftYAxis.setAxisLineColor(Color.GRAY);
            leftYAxis.setTextColor(Color.GRAY);

        } else {
            leftYAxis.setAxisLineColor(Color.BLACK);
            leftYAxis.setTextColor(Color.BLACK);

        }

        // Customize YAxis
        YAxis rightYAxis = monthly_expense_Barchart_lay5_home.getAxisRight();
        rightYAxis.setAxisLineWidth(0.5f);
        rightYAxis.setDrawAxisLine(false);
        rightYAxis.setDrawLabels(false);
        rightYAxis.setDrawGridLines(false);

        if (isDarkMode) {
            rightYAxis.setAxisLineColor(Color.GRAY);
            rightYAxis.setTextColor(Color.GRAY);

        } else {
            rightYAxis.setAxisLineColor(Color.BLACK);
            rightYAxis.setTextColor(Color.BLACK);
        }

        // Customize XAxis
        XAxis xAxis = monthly_expense_Barchart_lay5_home.getXAxis();
        xAxis.setAxisLineWidth(0.5f);
        xAxis.setDrawGridLines(false);
        if (isDarkMode) {
            xAxis.setAxisLineColor(Color.GRAY);
        } else {
            xAxis.setAxisLineColor(Color.BLACK);
        }
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        monthly_expense_Barchart_lay5_home.getDescription().setEnabled(false);
        monthly_expense_Barchart_lay5_home.getLegend().setEnabled(false);
        monthly_expense_Barchart_lay5_home.invalidate(); // Refresh the chart


        //click to BAR CHART
        monthly_expense_Barchart_lay5_home.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int value1 = Math.round(e.getY());

                int index = (int) e.getX();
                String category = categories.get(index);
                if (e instanceof BarEntry) {
                    Toast.makeText(Home_Activity.this, category + " : " + value1, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    //  Dark / Light Mode
    private void toggleDarkMode(boolean darkMode) {

        boolean isDarkMode = sharedPreferences.getBoolean(PREF_DARK_MODE, false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            editor.putBoolean(PREF_DARK_MODE, false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean(PREF_DARK_MODE, true);
        }
        editor.apply();
        // Directly set the theme without recreating the activity to avoid blinking
        UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        if (isDarkMode) {
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        } else {
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        }

        // Update menu item title
        updateMenuItemTitle(darkMode);

    }


    //  Language

    private void showChangeLanguageDialog() {
        final String[] languages = {"English", "Española", "हिंदी", "اردو", "ગુજરાતી"};

//        boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
//        int dialogTheme = isDarkMode ? R.style.CustomDialogTheme_Dark : R.style.CustomDialogTheme_Light;

        AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
        builder.setTitle("Select Language");

        // Create a variable to keep track of the selected item
        final int[] selectedLanguage = {-1}; // -1 means no selection

        builder.setSingleChoiceItems(languages, selectedLanguage[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedLanguage[0] = which; // Update the selected item
            }
        });


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedLanguage[0] != -1) { // Check if an item was selected
                    String langCode;
                    switch (selectedLanguage[0]) {
                        case 0:
                            langCode = "en";
                            break;
                        case 1:
                            langCode = "es";
                            break;
                        case 2:
                            langCode = "hi";
                            break;
                        case 3:
                            langCode = "ur";
                            break;
                        case 4:
                            langCode = "gu";
                            break;
                        default:
                            langCode = "en"; // Default to English if something goes wrong
                            break;
                    }
                    setLocal(langCode);
                    updateText(); // Update the text of the views
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Simply dismiss the dialog without applying changes
            }
        });

        AlertDialog dialog = builder.create();
        dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        if (isDarkMode) {
            dialog.getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        } else {
            dialog.getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        }

        dialog.show();
        // Ensure the button colors are set correctly
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (positiveButton != null) {
            positiveButton.setTextColor(Color.rgb(52, 172, 239));
        }
        if (negativeButton != null) {
            negativeButton.setTextColor(Color.rgb(52, 172, 239));
        }

    }


//        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Choose Language")
//                .setItems(languages, (dialog, which) -> {
//                    switch (which) {
//                        case 0:
//                            setLocal("en");
//                            break;
//                        case 1:
//                            setLocal("es");
//                            break;
//                        case 2:
//                            setLocal("hi");
//                            break;
//                        case 3:
//                            setLocal("ur");
//                            break;
//                        case 4:
//                            setLocal("gu");
//                            break;
//                    }
//                    updateText();
//                     Restart activity to apply language change
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
//                });
//        builder.create().show();

//    }

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


    //  Rate Us
    private void rateUs() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        }
    }


    //Share App
    private void shareApp() {
        String shareMessage = "Income_Expense app: https://play.google.com/store/apps/details?id=" + getPackageName();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share with"));
    }


    // Dark / Light Mode Title Update
    private void updateMenuItemTitle(boolean darkMode) {
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_darkmode);
        if (darkMode) {
            item.setTitle("Light Mode");
            item.setIcon(R.drawable.light_mode);
        } else {
            item.setTitle("Dark Mode");
            item.setIcon(R.drawable.dark_mode);
        }
    }


    //  Update text (language)
    private void updateText() {
        // Layout ---- 1
        add_income_txt_lay1_home.setText(R.string.add_income);
        add_expense_txt_lay1_home.setText(R.string.add_expense);
        transfer_txt_lay1_home.setText(R.string.transfer);
        transaction_txt_lay1_home.setText(R.string.transactions);

        // Layout ---- 2
        updateDate();
        income_lay2_home.setText(R.string.income_lay2);
        expense_lay2_home.setText(R.string.expense_lay2);
        total_balance_lay2_home.setText(R.string.balance_one_lay2);
        prev_balance_lay2_home.setText(R.string.prev_balance_lay2);
        balance_lay2_home.setText(R.string.balance_two_lay2);

        // Layout ---- 3
        recent_transaction_txt_lay3_home.setText(R.string.recent_transaction_lay3);
        recent_transaction_more_btn_lay3_home.setText(R.string.more_btn_lay3);

        // Layout ---- 4
        pay_meth_txt_lay4_home.setText(R.string.payment_method_lay4);
        pay_meth_more_btn_lay4_home.setText(R.string.more_btn_lay4);

        // Layout ---- 5
        monthly_expense_txt_lay5_home.setText(R.string.monhtly_expense_lay5);
        monthly_expense_more_btn_lay5_home.setText(R.string.more_btn_lay5);

    }


    //SaveSelectedAccount Method
    private void saveSelectedAccount(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("AccountPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SELECTED_EMAIL", email);
        editor.apply();
    }

    //LoadSelectedAccount Method
    private String loadSelectedAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("AccountPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("SELECTED_EMAIL", null);
    }

    // Logout
    private void logout() {
        clearLoginState();
        clearSelectedAccount();
        Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
        startActivity(intent);
        finish();
    }

    // ClearLoginState
    private void clearLoginState() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USER_EMAIL");
        editor.apply();
    }

    // ClearSelectedAccount
    private void clearSelectedAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("AccountPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("SELECTED_EMAIL");
        editor.apply();
    }

    // ShowAccount_From_Dialog
    @SuppressLint("Range")
    public void showAccount_From_Dialog(String email) {
        try {
            String query = "SELECT name FROM loginandregister_table WHERE email = ?";
            Cursor cursor = db.rawQuery(query, new String[]{userEmail});

            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                pra_txt_home.setText(email);
                toolbar.setTitle(name);
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Show_Toolbar_Dialog
    @SuppressLint("Range")
    public void show_Toolbar_Dialog() {
        BottomSheetDialog bd = new BottomSheetDialog(this);
        bd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bd.setContentView(R.layout.account_dialog);

        ListView transfer_email_list = bd.findViewById(R.id.transfer_email_list);

        try {
            list.clear();
            String query = "SELECT DISTINCT name, email FROM loginandregister_table";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    Income_Expense_Model model = new Income_Expense_Model(name, email);
                    list.add(model);
                } while (cursor.moveToNext());
            }
            cursor.close();

            emailAndName_adapter = new EmailAndName_Adapter(Home_Activity.this, list, db, userEmail);
            transfer_email_list.setAdapter(emailAndName_adapter);
            emailAndName_adapter.notifyDataSetChanged();

            transfer_email_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Income_Expense_Model selectedModel = (Income_Expense_Model) parent.getItemAtPosition(position);
                    String selectedEmail = selectedModel.getUser_email();
                    toolbar.setTitle(selectedEmail); // Set the selected email to the TextView
                    saveSelectedAccount(selectedEmail); // Save selected account
                    bd.dismiss(); // Close the dialog if needed

                    userEmail = selectedEmail;
                    fetchAndSetEmail(userEmail);

                }
            });

        } catch (Exception e) {
            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bd.show();
    }


    // FetchAndSetEmail
    @SuppressLint("Range")
    private void fetchAndSetEmail(String email) {
        try {
            String query = "SELECT name FROM loginandregister_table WHERE email = ?";
            Cursor cursor = db.rawQuery(query, new String[]{email});


            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                pra_txt_home.setText(email);
                toolbar.setTitle(name);
            } else {
                pra_txt_home.setText("Email not found");
            }
            cursor.close();


            setImageView_and_Textview_nav();

            //fetchAndSet_Layout_Data
            fetchAndSet_Layout_Data();

            // Fetch Payment method
            paymentMethod();

            // Fetch Monthly expense
            monthly_expense();

            // Fetch recent transactions for the new account
            recentTransaction();


        } catch (Exception e) {
            Toast.makeText(Home_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


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

    // CLOSE ACTIVITY
    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            // Close the navigation drawer if it's open
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            // Otherwise, handle the back press as usual
//            super.onBackPressed();
        // Close the app
        finishAffinity();
//        }
    }
    //x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

}
