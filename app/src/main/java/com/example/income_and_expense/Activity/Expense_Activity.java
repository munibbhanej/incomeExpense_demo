package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.income_and_expense.Adapter.RecyclerviewAdapter_category;
import com.example.income_and_expense.Model.Category_model;
import com.example.income_and_expense.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Expense_Activity extends AppCompatActivity {

    // ------------------------------------------------------------------------------------------------------------------------

    List<Category_model> list;
    RecyclerviewAdapter_category adapter;

    TextView text_toolbar;
    ImageView back_icon_toolbar;

    private String userEmail;

    Cursor cursor;
    private String Id = "";

    private static final int SPEECH_REQUEST_CODE = 100;
    Calendar calendar = Calendar.getInstance();

    int DateCount = 0;

    // Dark mode

    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";
    boolean isDarkMode;


    // ------------------------------------------------------------------------------------------------------------------------
    // Layout 1
    RelativeLayout expense_relativelayout_1;
    TextView expense__addExpense;
    EditText expense_ent_txt_1;
    ImageView expense_cal_icon_1;


    // Layout 2
    RelativeLayout expense_relativelayout_2;
    TextView category_addExpense, expense_cat_txt_2;
    ImageView expense_cat_icon_2;


    // Layout 3
    RelativeLayout expense_relativelayout_3;
    TextView paymentMethod_addExpense, expense_payment_method_txt_3;


    // Layout 4
    RelativeLayout expense_relativelayout_4;
    TextView notes_addExpense;
    ImageView expense_voice_icon_4, expense_notes_icon_4;
    EditText expense_notes_txt_4;


    // Layout 5
    RelativeLayout expense_relativelayout_5;
    ImageView expense_less_icon_5, expense_greater_icon_5, expense_date_icon_5;
    TextView expense_txt_5;


    // Layout 6
    RelativeLayout expense_relativelayout_6;
    TextView expense_txt_6;
    ImageView expense_clock_icon_6;

    // Layout 7
    Button expense_save_btn;


    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    SQLiteDatabase db;
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";

    private SQLiteDatabase createDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    private void createTable() {
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



    //-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x


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

        setContentView(R.layout.activity_expense);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");
        Id = intent.getStringExtra("id_exp");

        db = createDatabase();
        createTable();

        // Toolbar
        toolbar();

        // Initialize views
        initializeViews();

        // Update ( Date ) and ( Time )
        UpdatetDate();
        UpdatetTime();

        // Darl / Light Mode text
        darkLightMode();

        // Edittext income cursor
        edittext_Expense_cursor();

        // Update ( AddIncome data )
        update_AddIncome_Data();

        // AllButtonClickevent
        all_button_click_events();

        //updateText
        updateText();

    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    // Toolbar
    @SuppressLint("SetTextI18n")
    private void toolbar() {
        back_icon_toolbar = findViewById(R.id.lefticon_toolbar);
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText(R.string.add_expense_title);

        back_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom behavior on back button press
                Intent intent = new Intent(Expense_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);
            }
        });
    }

    // Initialize views
    private void initializeViews() {
        // #1
        expense_relativelayout_1 = findViewById(R.id.expense_relativelayout_1);
        expense_ent_txt_1 = findViewById(R.id.expense_ent_txt_1);
        expense_cal_icon_1 = findViewById(R.id.expense_cal_icon_1);
        expense__addExpense = findViewById(R.id.expense__addExpense);

        // #2
        expense_relativelayout_2 = findViewById(R.id.expense_relativelayout_2);
        expense_cat_icon_2 = findViewById(R.id.expense_cat_icon_2);
        expense_cat_txt_2 = findViewById(R.id.expense_cat_txt_2);
        category_addExpense = findViewById(R.id.category_addExpense);

        // #3
        expense_relativelayout_3 = findViewById(R.id.expense_relativelayout_3);
        expense_payment_method_txt_3 = findViewById(R.id.expense_payment_method_txt_3);
        paymentMethod_addExpense = findViewById(R.id.paymentMethod_addExpense);


        // #4
        expense_relativelayout_4 = findViewById(R.id.expense_relativelayout_4);
        expense_voice_icon_4 = findViewById(R.id.expense_voice_icon_4);
        expense_notes_txt_4 = findViewById(R.id.expense_notes_txt_4);
        expense_notes_icon_4 = findViewById(R.id.expense_notes_icon_4);
        notes_addExpense = findViewById(R.id.notes_addExpense);

        // #5
        expense_relativelayout_5 = findViewById(R.id.expense_relativelayout_5);
        expense_less_icon_5 = findViewById(R.id.expense_less_icon_5);
        expense_txt_5 = findViewById(R.id.expense_txt_5);
        expense_greater_icon_5 = findViewById(R.id.expense_greater_icon_5);
        expense_date_icon_5 = findViewById(R.id.expense_date_icon_5);


        // #6
        expense_relativelayout_6 = findViewById(R.id.expense_relativelayout_6);
        expense_txt_6 = findViewById(R.id.expense_txt_6);
        expense_clock_icon_6 = findViewById(R.id.expense_clock_icon_6);

        // #7
        expense_save_btn = findViewById(R.id.expense_save_btn);

    }

    // Edittext income cursor
    private void edittext_Expense_cursor() {
        expense_ent_txt_1.requestFocus();
        expense_ent_txt_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                expense_ent_txt_1.setSelection(expense_ent_txt_1.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(expense_ent_txt_1, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 100);
    }

    // Update ( AddIncome data )
    @SuppressLint("SetTextI18n")
    private void update_AddIncome_Data() {
        if (Id != null && !Id.isEmpty()) {

            String query = "SELECT * FROM incexp_tbl WHERE regid = ? AND user_email = ?";
            Cursor cursor = db.rawQuery(query, new String[]{Id,userEmail});

            if (cursor.moveToNext()) {
                expense_ent_txt_1.setText(cursor.getString(2));
                byte[] imageBytes = cursor.getBlob(3);
                // Convert byte array to Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                expense_cat_icon_2.setImageBitmap(imageBitmap);
                expense_cat_txt_2.setText(cursor.getString(4));
                expense_payment_method_txt_3.setText(cursor.getString(5));
                expense_notes_txt_4.setText(cursor.getString(6));
                expense_txt_5.setText(cursor.getString(7));
                expense_txt_6.setText(cursor.getString(8));
                expense_save_btn.setText("Update");


                // Toolbar adjustments for editing
                text_toolbar.setText("Edit Transaction");

                ImageView delete_icon = findViewById(R.id.delete_toolbar);
                delete_icon.setVisibility(View.VISIBLE);
                delete_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String delete = "DELETE FROM incexp_tbl WHERE regid=? AND user_email=?";
                        db.execSQL(delete, new String[]{Id, userEmail});
                        Toast.makeText(Expense_Activity.this, "Transaction Deleted", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Expense_Activity.this, Transactions_Activity.class);
                        i.putExtra("USER_EMAIL", userEmail);
                        startActivity(i);
                    }
                });
            }
        }

    }

    // All button click events
    private void all_button_click_events() {

        //                           [ 1 ]

        // ----[ expense_cal_icon_1 ] click listener
        expense_cal_icon_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showCalculaterDialog();
            }
        });

        //                           [ 2 ]

        // ----[ expense_relativelayout_2 ] click listener
        expense_relativelayout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });

        //                           [ 3 ]

        // ----[ expense_relativelayout_3 ] click listener
        expense_relativelayout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentMethodDialog();
            }
        });

        //                          [ 4 ]

        // ----[ expense_voice_icon_4 ] click listener
        expense_voice_icon_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVoiceDialog();
            }
        });

        // ----[ expense_notes_icon_4 ] click listener
        expense_notes_icon_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showVoiceDialog();
            }
        });

        //                          [ 5 ]

        // ----[ expense_less_icon_5] click listener
        expense_less_icon_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.expense_less_icon_5) {
                    DateCount--;
                    lessthan(DateCount);
                }

            }
        });

        // ----[ expense_greater_icon_5] click listener
        expense_greater_icon_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.expense_greater_icon_5) {
                    DateCount++;
                    greaterthan(DateCount);
                }
            }
        });


        // ----[ expense_date_icon_5] click listener
        expense_date_icon_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalender();
            }
        });

        //                          [ 6 ]

        // ----[ expense_clock_icon_6 ] click listener
        expense_clock_icon_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();

            }
        });

        //                          [ 7 ]
        // ----[ expense_save_btn ] click listener
        expense_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((Id != null && !Id.isEmpty())) {
                    updateTransaction();
                } else {
                    saveExpense();
                }

            }
        });


    }


    // Function to update transaction
    private void updateTransaction() {
        String expenseAmount = expense_ent_txt_1.getText().toString();
        String category = expense_cat_txt_2.getText().toString();
        String paymentMethod = expense_payment_method_txt_3.getText().toString();
        String notes = expense_notes_txt_4.getText().toString();
        String entryDate = expense_txt_5.getText().toString();
        String entryTime = expense_txt_6.getText().toString();

        // Convert category image to byte array
        Drawable drawable = expense_cat_icon_2.getDrawable();
        Bitmap imageBitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] category_image = outputStream.toByteArray();

        // Update statement
        String update = "UPDATE incexp_tbl SET income = '0', expense = ?, cat_img = ?, category = ?, paymentmethod = ?, notes = ?, entry_date = ?, time = ? WHERE regid = ? AND user_email = ?";
        SQLiteStatement statement = db.compileStatement(update);
        statement.bindString(1, expenseAmount);
        statement.bindBlob(2, category_image);
        statement.bindString(3, category);
        statement.bindString(4, paymentMethod);
        statement.bindString(5, notes);
        statement.bindString(6, entryDate);
        statement.bindString(7, entryTime);
        statement.bindString(8, Id);
        statement.bindString(9, userEmail);

        // Execute update
        statement.executeUpdateDelete();

        Toast.makeText(this, "Transaction Updated", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(Expense_Activity.this, Transactions_Activity.class);
        i.putExtra("USER_EMAIL", userEmail);
        startActivity(i);
    }

    // Function to save income
    private void saveExpense() {
        String expenseAmount = expense_ent_txt_1.getText().toString();
        String category = expense_cat_txt_2.getText().toString();
        String paymentMethod = expense_payment_method_txt_3.getText().toString();
        String notes = expense_notes_txt_4.getText().toString();
        String entryDate = expense_txt_5.getText().toString();
        String entryTime = expense_txt_6.getText().toString();

        // Convert category image to byte array
        Drawable drawable = expense_cat_icon_2.getDrawable();
        Bitmap imageBitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] category_image = outputStream.toByteArray();

        if (expenseAmount.isEmpty() || category.isEmpty() || paymentMethod.isEmpty() || entryDate.isEmpty() || entryTime.isEmpty()) {
            Toast.makeText(this, "Please Enter Expense", Toast.LENGTH_SHORT).show();
        } else {
            String insertSQL = "INSERT INTO incexp_tbl (income, expense, cat_img, category, paymentmethod, notes, entry_date, time, user_email) VALUES ( '0', ?, ?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(insertSQL);
            statement.bindString(1, expenseAmount);
            statement.bindBlob(2, category_image);
            statement.bindString(3, category);
            statement.bindString(4, paymentMethod);
            statement.bindString(5, notes);
            statement.bindString(6, entryDate);
            statement.bindString(7, entryTime);
            statement.bindString(8, userEmail);

            statement.executeInsert();

            Toast.makeText(this, "Expense Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Expense_Activity.this, Home_Activity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
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

    //Update Date
    private void UpdatetDate() {
        SimpleDateFormat simpledateformate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateTime = simpledateformate.format(calendar.getTime());
        expense_txt_5.setText(dateTime);
    }

    //Update Time
    private void UpdatetTime() {
        SimpleDateFormat simpledateformate = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        String dateTime = simpledateformate.format(calendar.getTime());
        expense_txt_6.setText(dateTime);
    }

    // Darl / Light Mode text
    private void darkLightMode() {

        if (isDarkMode) {
            expense_voice_icon_4.setImageDrawable(getDrawable(R.drawable.voice_icon_light));
            expense_less_icon_5.setImageDrawable(getDrawable(R.drawable.less_than_light));
            expense_greater_icon_5.setImageDrawable(getDrawable(R.drawable.greater_then_light));
            expense__addExpense.setTextColor(getResources().getColor(R.color.expense1));

        } else {
            expense_voice_icon_4.setImageDrawable(getDrawable(R.drawable.voice_icon_dark));
            expense_less_icon_5.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            expense_greater_icon_5.setImageDrawable(getDrawable(R.drawable.greater_then_dark));
            expense__addExpense.setTextColor(getResources().getColor(R.color.expense1));
        }

    }

    // #2( Show_Category ) Dialog
    private void showCategoryDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(R.layout.category_layout);

        // fullscreen Dialog
        View view = (View) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

        if (view != null) {
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(view);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setDraggable(false);
        }


        list = new ArrayList<>();
        list.add(new Category_model(R.drawable.air_tickets_1, "Air Tickets"));
        list.add(new Category_model(R.drawable.auto_rickshaw_2, "Auto Rickshaw"));
        list.add(new Category_model(R.drawable.bike_3, "Bike"));
        list.add(new Category_model(R.drawable.bills_4, "Bills"));
        list.add(new Category_model(R.drawable.cable_tv_5, "Cable Tv"));
        list.add(new Category_model(R.drawable.car_6, "Car"));
        list.add(new Category_model(R.drawable.car_insurance_7, "Car Insurance"));
        list.add(new Category_model(R.drawable.card_fee_8, "Card Fee"));
        list.add(new Category_model(R.drawable.cigarette_9, "Cigarette"));
        list.add(new Category_model(R.drawable.clothes_10, "Clothes"));
        list.add(new Category_model(R.drawable.drinks_11, "Drinks"));
        list.add(new Category_model(R.drawable.driver_12, "Driver"));
        list.add(new Category_model(R.drawable.durables_13, "Durables"));
        list.add(new Category_model(R.drawable.education_14, "Education"));
        list.add(new Category_model(R.drawable.electricity_15, "Electricity"));
        list.add(new Category_model(R.drawable.emi_16, "EMI"));
        list.add(new Category_model(R.drawable.entertainment_17, "Entertainment"));
        list.add(new Category_model(R.drawable.fast_food_18, "Fast Food"));
        list.add(new Category_model(R.drawable.festivals_19, "Festivals"));
        list.add(new Category_model(R.drawable.fitness_20, "Fitness"));
        list.add(new Category_model(R.drawable.food_21, "Food"));
        list.add(new Category_model(R.drawable.fruits_and_vegetables_22, "Fruits and\nVegetables"));
        list.add(new Category_model(R.drawable.fuel_23, "Fuel"));
        list.add(new Category_model(R.drawable.furniture_24, "Furniture"));
        list.add(new Category_model(R.drawable.gas_25, "Gas"));
        list.add(new Category_model(R.drawable.gifts_26, "Gifts"));
        list.add(new Category_model(R.drawable.groceries_27, "Groceries"));
        list.add(new Category_model(R.drawable.health_28, "Health"));
        list.add(new Category_model(R.drawable.health_insurance_29, "Health and\nInsurance"));
        list.add(new Category_model(R.drawable.hobby_30, "Hobby"));
        list.add(new Category_model(R.drawable.home_insurance_31, "Home Insurance"));
        list.add(new Category_model(R.drawable.household_expenses_32, "Household\nExpenses"));
        list.add(new Category_model(R.drawable.insurance_33, "Insurance"));
        list.add(new Category_model(R.drawable.internet_34, "Internet"));
        list.add(new Category_model(R.drawable.investment_expense_35, "Investment\nExpense"));
        list.add(new Category_model(R.drawable.kids_36, "Kids"));
        list.add(new Category_model(R.drawable.laundry_37, "Laundry"));
        list.add(new Category_model(R.drawable.maid_servant_38, "Maid/Servant"));
        list.add(new Category_model(R.drawable.medicine_39, "Medicine"));
        list.add(new Category_model(R.drawable.milk_40, "Milk"));
        list.add(new Category_model(R.drawable.mobile_41, "Mobile"));
        list.add(new Category_model(R.drawable.other_expense_42, "Other Expense"));
        list.add(new Category_model(R.drawable.parking_43, "Parking"));
        list.add(new Category_model(R.drawable.party_44, "Party"));
        list.add(new Category_model(R.drawable.personal_grooming_45, "Personal Grooming"));
        list.add(new Category_model(R.drawable.pet_46, "Pet"));
        list.add(new Category_model(R.drawable.rent_47, "Rent"));
        list.add(new Category_model(R.drawable.repair_and_maintenance_48, "Repair and\nMaintenance"));
        list.add(new Category_model(R.drawable.restaurant_and_hotel_49, "Restaurant and\nHotel"));
        list.add(new Category_model(R.drawable.savings_50, "Savings"));
        list.add(new Category_model(R.drawable.shopping_51, "Shopping"));
        list.add(new Category_model(R.drawable.social_52, "Social"));
        list.add(new Category_model(R.drawable.stationery_53, "Stationery"));
        list.add(new Category_model(R.drawable.taxes_54, "Taxes"));
        list.add(new Category_model(R.drawable.taxi_55, "Taxi"));
        list.add(new Category_model(R.drawable.toiletries_56, "Toiletries"));
        list.add(new Category_model(R.drawable.toll_57, "Toll"));
        list.add(new Category_model(R.drawable.toys_58, "Toys"));
        list.add(new Category_model(R.drawable.transportation_59, "Transportation"));
        list.add(new Category_model(R.drawable.vacation_60, "Vacation"));
        list.add(new Category_model(R.drawable.water_61, "Water"));
        list.add(new Category_model(R.drawable.account_transfer_62, "Account Transfer"));


        TextView category_cancel_btn = bottomSheetDialog.findViewById(R.id.category_cancel_btn);
        category_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.recyclerview_category);
        // Set up RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new RecyclerviewAdapter_category(this, list, new RecyclerviewAdapter_category.OnItemClickListener() {
            @Override
            public void onItemClick(Category_model category) {
                // Handle item click
                updateMainViews(category);
                bottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(adapter);

        // search Category
        SearchView search_category = bottomSheetDialog.findViewById(R.id.search_category);
        search_category.clearFocus();
        search_category.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        bottomSheetDialog.show();
    }

    private void updateMainViews(Category_model item) {
        // Update ImageView and TextView with selected item data
        expense_cat_icon_2.setImageResource(item.getImg());
        expense_cat_txt_2.setText(item.getName());
    }

    private void filterList(String query) {
        List<Category_model> categoryList = new ArrayList<>();
        for (Category_model category : list) {
            if (category.getName().toLowerCase().contains(query.toLowerCase())) {
                categoryList.add(category);
            }
        }
        if (categoryList.isEmpty()) {
            Toast.makeText(this, "Item Not Found !!!", Toast.LENGTH_SHORT).show();
        } else {
            adapter.set_category(categoryList);
        }

    }

    // #3 ( Show_Payment_Method ) Dialog
    private void showPaymentMethodDialog() {
        BottomSheetDialog bd = new BottomSheetDialog(this);
        bd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bd.setContentView(R.layout.paymentmethod_dialog);


        TextView bank_txt = bd.findViewById(R.id.bank_txt_paymentdialog);
        TextView card_txt = bd.findViewById(R.id.card_txt_paymentdialog);
        TextView cash_txt = bd.findViewById(R.id.cash_txt_paymentdialog);
        TextView other_txt = bd.findViewById(R.id.other_txt_paymentdialog);

        //Bank
        bank_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_payment_method_txt_3.setText(bank_txt.getText().toString());
                bd.dismiss();
            }
        });
        //Card
        card_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_payment_method_txt_3.setText(card_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Cash
        cash_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_payment_method_txt_3.setText(cash_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Other
        other_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expense_payment_method_txt_3.setText(other_txt.getText().toString());
                bd.dismiss();
            }
        });

        bd.show();
    }

    // #4  ( Show_Voice )
    private void showVoiceDialog() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");
        startActivityForResult(i, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList<String> list = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = list.get(0);
            expense_notes_txt_4.setText(voice);
        } else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    // #5  ( Less_than )
    private void lessthan(int dateCount) {
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat simpledateformate = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String date = simpledateformate.format(calendar.getTime());
        expense_txt_5.setText(date);


    }

    // #5  ( Greater_than )
    private void greaterthan(int dateCount) {
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());
        expense_txt_5.setText(date);
    }

    // #5  (  Open Calender )
    @SuppressLint("ResourceAsColor")
    private void openCalender() {
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                UpdatetDate();
            }
        }, Year, Month, Day);
        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY);
    }

    // #6  (  Open Timepicker )
    private void openTimePickerDialog() {
        int Hours = calendar.get(Calendar.HOUR);
        int Minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                UpdatetTime();
            }
        }, Hours, Minute, true);

        timePickerDialog.show();
        timePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY);
        timePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY);
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
        expense__addExpense.setText(R.string.expense_txt_lay1_addExpense);

        // Layout ---- 2
        category_addExpense.setText(R.string.category_txt_lay2_addExpense);


        // Layout ---- 3
        paymentMethod_addExpense.setText(R.string.paymentMethod_txt_lay3_addExpense);

        // Layout ---- 4
        notes_addExpense.setText(R.string.notes_txt_lay4_addExpense);


        // Layout ---- 5
        expense_save_btn.setText(R.string.save_btn_txt_lay5_addExpense);
    }

    //x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x--x-x-x-x-x-x-x-x





}