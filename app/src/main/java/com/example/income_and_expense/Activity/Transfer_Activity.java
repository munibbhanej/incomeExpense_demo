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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.example.income_and_expense.Adapter.Email_Adapter;
import com.example.income_and_expense.Adapter.Incomeexpense_Adapter;
import com.example.income_and_expense.Adapter.RecyclerviewAdapter_category;
import com.example.income_and_expense.Model.Category_model;
import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Transfer_Activity extends AppCompatActivity {


    TextView practice_txt;
    List<Category_model> list;
    List<Income_Expense_Model> list1;
    Email_Adapter adapter1;
    TextView text_toolbar;
    ImageView back_icon_toolbar;

    private String userEmail;

    Calendar calendar = Calendar.getInstance();
    Cursor cursor;

    private static final int SPEECH_REQUEST_CODE = 100;

    int DateCount = 0;

    String name = "";
    String email_txt = "";

    int all_balance;
    int amount_txt;

    // Dark mode

    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    boolean isDarkMode;



    // Layout 1
    RelativeLayout transfer_relativelayout_1;
    TextView amount__transfer;
    EditText transfer_ent_txt_1;

    // Layout 2
    RelativeLayout transfer_relativelayout_2;
    TextView category_transfer;
    ImageView transfer_cat_icon_2;
    TextView transfer_cat_txt_2;

    // Layout 3
    RelativeLayout transfer_relativelayout_3;
    TextView paymentMethod_transfer;
    TextView transfer_payment_method_txt_3;


    // Layout 4
    RelativeLayout transfer_relativelayout_4;
    TextView notes_transfer;
    ImageView transfer_voice_icon_4;
    TextView transfer_notes_txt_4;


    // Layout 5
    RelativeLayout transfer_relativelayout_5;
    TextView from_transfer, transfer_from_txt_5;
    TextView account_from_transfer, transfer_account_from_txt_5;

    ImageView from_dropdown_icon_transfer;



    // Layout 6
    RelativeLayout transfer_relativelayout_6;
    TextView to_transfer, transfer_to_txt_6;
    TextView account_to_transfer, transfer_account_to_txt_6;
    ImageView to_dropdown_icon_transfer, account_to_dropdown_icon_transfer ;

    // Layout 7
    RelativeLayout transfer_relativelayout_7;
    ImageView transfer_less_icon_7;
    TextView transfer_txt_7;
    ImageView transfer_greater_icon_7;
    ImageView transfer_date_icon_7;


    // Layout 8
    RelativeLayout transfer_relativelayout_8;
    TextView transfer_txt_8;
    ImageView transfer_clock_icon_8;


    // Layout 9
    Button transfer_btn;

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

//    private void createTable1() {
//        String login_reg = "CREATE TABLE IF NOT EXISTS loginandregister_table(" +
//                "regid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
//                "name VARCHAR(50) NOT NULL," +
//                "email VARCHAR(50) NOT NULL," +
//                "password VARCHAR(50) NOT NULL" +
//                ")";
//        db.execSQL(login_reg);
//    }


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

        setContentView(R.layout.activity_transfer);


        Intent intent = getIntent();
        userEmail = intent.getStringExtra("USER_EMAIL");

        db = createDatabase();
        createTable();
//        createTable1();


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
        edittext_income_cursor();


        // AllButtonClickevent
        all_button_click_events();

        // Update Text
        updateText();

    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    // Toolbar
    @SuppressLint("SetTextI18n")
    private void toolbar() {
        back_icon_toolbar = findViewById(R.id.lefticon_toolbar);
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Transfer");
        text_toolbar.setText(R.string.transfer_title);

        back_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom behavior on back button press
                Intent intent = new Intent(Transfer_Activity.this, Home_Activity.class);
                intent.putExtra("USER_EMAIL", userEmail);
                startActivity(intent);

            }
        });
    }

    // Initialize views
    private void initializeViews() {
        // #1
        transfer_relativelayout_1 = findViewById(R.id.transfer_relativelayout_1);
        amount__transfer = findViewById(R.id.amount__transfer);
        transfer_ent_txt_1 = findViewById(R.id.transfer_ent_txt_1);


        // #2
        transfer_relativelayout_2 = findViewById(R.id.transfer_relativelayout_2);
        category_transfer = findViewById(R.id.category_transfer);
        transfer_cat_icon_2 = findViewById(R.id.transfer_cat_icon_2);
        transfer_cat_txt_2 = findViewById(R.id.transfer_cat_txt_2);


        // #3
        transfer_relativelayout_3 = findViewById(R.id.transfer_relativelayout_3);
        paymentMethod_transfer = findViewById(R.id.paymentMethod_transfer);
        transfer_payment_method_txt_3 = findViewById(R.id.transfer_payment_method_txt_3);


        // #4
        transfer_relativelayout_4 = findViewById(R.id.transfer_relativelayout_4);
        notes_transfer = findViewById(R.id.notes_transfer);
        transfer_voice_icon_4 = findViewById(R.id.transfer_voice_icon_4);
        transfer_notes_txt_4 = findViewById(R.id.transfer_notes_txt_4);

        // #5
        transfer_relativelayout_5 = findViewById(R.id.transfer_relativelayout_5);
        from_transfer = findViewById(R.id.from_transfer);
        transfer_from_txt_5 = findViewById(R.id.transfer_from_txt_5);
        account_from_transfer = findViewById(R.id.account_from_transfer);
        transfer_account_from_txt_5 = findViewById(R.id.transfer_account_from_txt_5);
        from_dropdown_icon_transfer = findViewById(R.id.from_dropdown_icon_transfer);


        // #6
        transfer_relativelayout_6 = findViewById(R.id.transfer_relativelayout_6);
        to_transfer = findViewById(R.id.to_transfer);
        transfer_to_txt_6 = findViewById(R.id.transfer_to_txt_6);
        account_to_transfer = findViewById(R.id.account_to_transfer);
        transfer_account_to_txt_6 = findViewById(R.id.transfer_account_to_txt_6);

         to_dropdown_icon_transfer = findViewById(R.id.to_dropdown_icon_transfer);
        account_to_dropdown_icon_transfer = findViewById(R.id.account_to_dropdown_icon_transfer);



        // #7
        transfer_relativelayout_7 = findViewById(R.id.transfer_relativelayout_7);
        transfer_less_icon_7 = findViewById(R.id.transfer_less_icon_7);
        transfer_txt_7 = findViewById(R.id.transfer_txt_7);
        transfer_greater_icon_7 = findViewById(R.id.transfer_greater_icon_7);
        transfer_date_icon_7 = findViewById(R.id.transfer_date_icon_7);

        // #8
        transfer_relativelayout_8 = findViewById(R.id.transfer_relativelayout_8);
        transfer_txt_8 = findViewById(R.id.transfer_txt_8);
        transfer_clock_icon_8 = findViewById(R.id.transfer_clock_icon_8);


        // #9
        transfer_btn = findViewById(R.id.transfer_btn);

        list1 = new ArrayList<>();
        practice_txt = findViewById(R.id.practice_txt);

    }

    // Edittext income cursor
    private void edittext_income_cursor() {
        transfer_ent_txt_1.requestFocus();
        transfer_ent_txt_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                transfer_ent_txt_1.setSelection(transfer_ent_txt_1.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(transfer_ent_txt_1, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        }, 100);
    }


    // All button click events
    private void all_button_click_events() {

        //                           [ 5 ]

        // ----[ transfer_from_txt_5 ] click listener
        transfer_from_txt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDialog();
            }
        });
        // ----[ Account From ]
        showAccount_From_Dialog();

        //                           [ 6 ]

        // ----[ transfer_to_txt_6 ] click listener
        transfer_to_txt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDialog();
            }
        });

        // ----[ transfer_account_to_txt_6 ] click listener
        transfer_account_to_txt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccount_To_Dialog();
            }
        });


        //                          [ 7 ]

        // ----[ transfer_less_icon_7] click listener
        transfer_less_icon_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.transfer_less_icon_7) {
                    DateCount--;
                    lessthan(DateCount);
                }

            }
        });

        // ----[ transfer_greater_icon_7] click listener
        transfer_greater_icon_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.transfer_greater_icon_7) {
                    DateCount++;
                    greaterthan(DateCount);
                }
            }
        });


        // ----[ income_date_icon_5] click listener
        transfer_date_icon_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalender();
            }
        });

        //                          [ 8 ]

        // ----[ transfer_clock_icon_8 ] click listener
        transfer_clock_icon_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog();
            }
        });


        //                          [ 7 ]
        // ----[ transfer_btn ] click listener
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                set_All_Data(all_balance);

                if (transfer_ent_txt_1.getText().toString().isEmpty()) {
                    Toast.makeText(Transfer_Activity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveIncome();
                }



            }
        });

    }


    // Function to save income
    private void saveIncome() {
        String amount = transfer_ent_txt_1.getText().toString();
        amount_txt = Integer.parseInt(amount);
        String category = transfer_cat_txt_2.getText().toString();
        String from_paymentMethod = transfer_from_txt_5.getText().toString();
        String to_paymentMethod = transfer_to_txt_6.getText().toString();
        String notes_account_from = "From : " + transfer_account_from_txt_5.getText().toString();
        String notes_account_to_1 = transfer_account_to_txt_6.getText().toString();
        String notes_account_to_2 = "To : " + transfer_account_to_txt_6.getText().toString();
        String entryDate = transfer_txt_7.getText().toString();
        String entryTime = transfer_txt_8.getText().toString();

        // Convert category image to byte array
        Drawable drawable = transfer_cat_icon_2.getDrawable();
        Bitmap imageBitmap = drawableToBitmap(drawable);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] category_image = outputStream.toByteArray();

        if (amount.isEmpty()) {
            Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
        } else {
            if (notes_account_to_1.equals("Select Account")) {
                Toast.makeText(this, "Please select Account", Toast.LENGTH_SHORT).show();
            } else {
                if (amount_txt > all_balance) {
                    Toast.makeText(Transfer_Activity.this, "Total Balance : " + all_balance + "\n! insufficient balance", Toast.LENGTH_LONG).show();
                } else {


                    try {

                        // First Insert Statement
                        String insertSQL = "INSERT INTO incexp_tbl (income, expense, cat_img, category, paymentmethod, notes, entry_date, time, user_email) VALUES ( '0', ?, ?, ?, ?, ?, ?, ?, ?)";
                        SQLiteStatement statement = db.compileStatement(insertSQL);
                        statement.bindString(1, amount);
                        statement.bindBlob(2, category_image);
                        statement.bindString(3, category);
                        statement.bindString(4, from_paymentMethod);
                        statement.bindString(5, notes_account_to_2);
                        statement.bindString(6, entryDate);
                        statement.bindString(7, entryTime);
                        statement.bindString(8, userEmail);

                        statement.executeInsert();


                        // Second Insert Statement
                        String insertSQL2 = "INSERT INTO incexp_tbl (income, expense, cat_img, category, paymentmethod, notes, entry_date, time, user_email) VALUES ( ?, '0',  ?, ?, ?, ?, ?, ?, ?)";
                        SQLiteStatement statement2 = db.compileStatement(insertSQL2);
                        statement2.bindString(1, amount);
                        statement2.bindBlob(2, category_image);
                        statement2.bindString(3, category);
                        statement2.bindString(4, to_paymentMethod);
                        statement2.bindString(5, notes_account_from);
                        statement2.bindString(6, entryDate);
                        statement2.bindString(7, entryTime);
                        statement2.bindString(8, email_txt);

                        statement2.executeInsert();


                        Toast.makeText(Transfer_Activity.this, email_txt, Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Transfer_Activity.this, Home_Activity.class);
                    intent.putExtra("USER_EMAIL", userEmail);
                    startActivity(intent);
                }
            }


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
        transfer_txt_7.setText(dateTime);
    }

    //Update Time
    private void UpdatetTime() {
        SimpleDateFormat simpledateformate = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
        String dateTime = simpledateformate.format(calendar.getTime());
        transfer_txt_8.setText(dateTime);
    }

    // Darl / Light Mode text
    private void darkLightMode() {
        if (isDarkMode) {
            from_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow_light));
            to_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow_light));
            account_to_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow_light));
            transfer_less_icon_7.setImageDrawable(getDrawable(R.drawable.less_than_light));
            transfer_greater_icon_7.setImageDrawable(getDrawable(R.drawable.greater_then_light));

        } else {
            from_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow));
            to_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow));
            account_to_dropdown_icon_transfer.setImageDrawable(getDrawable(R.drawable.dropdown_arrow));
            transfer_less_icon_7.setImageDrawable(getDrawable(R.drawable.less_than_dark));
            transfer_greater_icon_7.setImageDrawable(getDrawable(R.drawable.greater_then_dark));
        }

    }


    // #5 ( Show_from ) Dialog
    private void fromDialog() {
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
                transfer_from_txt_5.setText(bank_txt.getText().toString());
                bd.dismiss();
            }
        });
        //Card
        card_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_from_txt_5.setText(card_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Cash
        cash_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_from_txt_5.setText(cash_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Other
        other_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_from_txt_5.setText(other_txt.getText().toString());
                bd.dismiss();
            }
        });

        bd.show();
    }

    // #5  ( Show_Account To Dialog )
    @SuppressLint("Range")
    public void showAccount_From_Dialog() {

        try {

            // Fetch income and expense for the ALL
            String query = "SELECT name FROM loginandregister_table WHERE email = ?"; // Assuming your table name is 'users' and it has 'name' and 'email' columns
            Cursor cursor = db.rawQuery(query, new String[]{userEmail});

            String name = "";
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex("name"));
            }
            cursor.close();

            transfer_account_from_txt_5.setText(name);
        } catch (Exception e) {
            Toast.makeText(Transfer_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    // #6 ( Show_to ) Dialog
    private void toDialog() {
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
                transfer_to_txt_6.setText(bank_txt.getText().toString());
                bd.dismiss();
            }
        });
        //Card
        card_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_to_txt_6.setText(card_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Cash
        cash_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_to_txt_6.setText(cash_txt.getText().toString());
                bd.dismiss();
            }
        });

        //Other
        other_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transfer_to_txt_6.setText(other_txt.getText().toString());
                bd.dismiss();
            }
        });

        bd.show();
    }

    // #6  ( Show_Account To Dialog )
    @SuppressLint("Range")
    public void showAccount_To_Dialog() {
        BottomSheetDialog bd = new BottomSheetDialog(this);
        bd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bd.setContentView(R.layout.account_dialog);

        ListView transfer_email_list = bd.findViewById(R.id.transfer_email_list);

        try {

            list1.clear();
            // Fetch income and expense for the ALL
            String query = "SELECT DISTINCT name FROM loginandregister_table";
            Cursor cursor = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                do {
                    String email = cursor.getString(cursor.getColumnIndex("name")); // Use column name directly
                    Income_Expense_Model model = new Income_Expense_Model(email);
                    list1.add(model);

                }
                while (cursor.moveToNext());
            }
            cursor.close(); // Close the cursor after use


            adapter1 = new Email_Adapter(Transfer_Activity.this, list1, db);
            transfer_email_list.setAdapter(adapter1);

            adapter1.notifyDataSetChanged();

            // Set item click listener
            transfer_email_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Income_Expense_Model selectedModel = (Income_Expense_Model) parent.getItemAtPosition(position);
                    String selectedEmail = selectedModel.getUser_email();
                    transfer_account_to_txt_6.setText(selectedEmail); // Set the selected email to the TextView
                    bd.dismiss(); // Close the dialog if needed


                    name = transfer_account_to_txt_6.getText().toString();

                    fetchAndSetEmail(name);


                }
            });

        } catch (Exception e) {
            Toast.makeText(Transfer_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bd.show();
    }


    // #7  ( Less_than )
    private void lessthan(int dateCount) {
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat simpledateformate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = simpledateformate.format(calendar.getTime());
        transfer_txt_7.setText(date);
    }

    // #7  ( Greater_than )
    private void greaterthan(int dateCount) {
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = simpleDateFormat.format(calendar.getTime());
        transfer_txt_7.setText(date);
    }

    // #7  (  Open Calender )
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

    // #8  (  Open Timepicker )
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


    //fetchAndSetEmail
    @SuppressLint("Range")
    private void fetchAndSetEmail(String name) {
        try {
            String query = "SELECT email FROM loginandregister_table WHERE name = ?";
            Cursor cursor1 = db.rawQuery(query, new String[]{name});

            if (cursor1 != null && cursor1.moveToFirst()) {
                String email = cursor1.getString(cursor1.getColumnIndex("email"));
                practice_txt.setText(email);

                email_txt = practice_txt.getText().toString();
            } else {
                practice_txt.setText("Email not found");
            }
        } catch (Exception e) {
            Toast.makeText(Transfer_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  > > > > set (ALL) data income,expense and total.
    private void set_All_Data(int balance) {
        cursor = db.rawQuery("SELECT * FROM incexp_tbl WHERE user_email = ?", new String[]{userEmail});
        int Income = 0, Expense = 0;

        while (cursor.moveToNext()) {
            Income += Integer.parseInt(cursor.getString(1));
            Expense += Integer.parseInt(cursor.getString(2));
        }


        balance = Income - Expense;

        all_balance = balance;

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
        amount__transfer.setText(R.string.amount__transfer);

        // Layout ---- 2
        from_transfer.setText(R.string.from_transfer);
        account_from_transfer.setText(R.string.account_from_transfer);


        // Layout ---- 3
        to_transfer.setText(R.string.to_transfer);
        account_to_transfer.setText(R.string.account_to_transfer);

        // Layout ---- 4
        transfer_btn.setText(R.string.transfer_btn);

    }

    // -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

}