package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.income_and_expense.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Activity extends AppCompatActivity {


    EditText edittxt_email_login, edittxt_password_login;
    Button login_btn;

    String email_txt;
    TextView register_txt_login, forget_password_txt;

    TextView email_txt_error, password_txt_error;

    ProgressBar progressBar;
    ProgressBar progressBar_fog_dialog;
    ProgressBar progressBar_create_pass_dialog;

    // create password
    String email ;
    String password ;
    String con_password ;


    EditText edittxt_password_cre_for_pass;
    EditText edittxt_con_password_cre_for_pass;
    Button updatepassword_btn_cre_forgetpassword;
    TextView login_txt_cre_forgetpassword;
    TextView email_set_cre_for_pass;



    // Dark mode
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    boolean isDarkMode;


    //    private AlertDialog loader;
    //-x-x-x-x-x-x-x-x-x-x-x-x- DataBase -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x
    Cursor cursor;
    SQLiteDatabase db;
    private static final String DATABASE_NAME = "IncomeAndExpenseDatabase";

    private SQLiteDatabase createDatabase() {
        return openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
    }

    private void createTable() {
        String login_reg = "CREATE TABLE IF NOT EXISTS loginandregister_table(" +
                "regid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(50) NOT NULL," +
                "email VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL," +
                "img BLOB" +
                ")";
        db.execSQL(login_reg);
    }

    //-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x

    @SuppressLint("MissingInflatedId")
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
//        setLocal(getSavedLanguage());

        setContentView(R.layout.activity_login);


        db = createDatabase();
        createTable(); // Ensure the table is created

        email_txt_error = findViewById(R.id.email_txt_error);
        password_txt_error = findViewById(R.id.password_txt_error);


        edittxt_email_login = findViewById(R.id.edittxt_email_login);
        edittxt_password_login = findViewById(R.id.edittxt_password_login);
        login_btn = findViewById(R.id.login_btn);
        register_txt_login = findViewById(R.id.register_txt_login);
        forget_password_txt = findViewById(R.id.forget_password_txt);

        progressBar = findViewById(R.id.progressBar);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edittxt_email_login.getText().toString();
                String password = edittxt_password_login.getText().toString();


                if (validateInputs()) {

                    Boolean checkCredentialEmail = checkEmail(email);
                    Boolean checkCredentialPassword = checkPassword(password);

                    if (checkCredentialEmail) {

                        if (checkCredentialPassword) {
                            login_btn.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);

                            // Schedule the loader to dismiss after 1 minute
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                loader.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    login_btn.setVisibility(View.VISIBLE);

                                    saveLoginState(email);

                                    Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                                    intent.putExtra("USER_EMAIL", email);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1500);
                        } else {
                            Toast.makeText(Login_Activity.this, "Password Not Correct...", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });


        register_txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Register_Activity.class));
            }
        });

        forget_password_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPasswordDialog();
            }
        });

    }

    private void saveLoginState(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_EMAIL", email);
        editor.apply();
    }

    //Forget password Dialog
    private void showForgetPasswordDialog() {
        BottomSheetDialog bd = new BottomSheetDialog(this);
        bd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bd.setContentView(R.layout.forget_password_dialog);


        EditText edittxt_email_forget_password = bd.findViewById(R.id.edittxt_email_forget_password);
        Button next_btn_forgetpassword = bd.findViewById(R.id.next_btn_forgetpassword);
        TextView login_txt_forgetpassword = bd.findViewById(R.id.login_txt_forgetpassword);

        progressBar_fog_dialog = bd.findViewById(R.id.progressBar_fog_dialog);


        //Next Button Click
        next_btn_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_txt = edittxt_email_forget_password.getText().toString();
                if (email_txt.equals("")) {
                    Toast.makeText(Login_Activity.this, "Email field is mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkEmail = checkEmail(email_txt);
                    if (checkEmail) {
                        next_btn_forgetpassword.setVisibility(View.GONE);
                        progressBar_fog_dialog.setVisibility(View.VISIBLE);

                        // Schedule the loader to dismiss after 1 minute
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                loader.dismiss();
                                progressBar_fog_dialog.setVisibility(View.GONE);
                                next_btn_forgetpassword.setVisibility(View.VISIBLE);

                                bd.dismiss();
                                showCreatePasswordDialog();
                            }
                        }, 1500);



                    } else {
                        Toast.makeText(Login_Activity.this, "Email is not correct....", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //Card
        login_txt_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd.dismiss();
            }
        });

        bd.show();
    }

    //Create password Dialog
    private void showCreatePasswordDialog() {
        BottomSheetDialog bd = new BottomSheetDialog(this);
        bd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bd.setContentView(R.layout.create_new_password_dialog);


         edittxt_password_cre_for_pass = bd.findViewById(R.id.edittxt_password_cre_for_pass);
         edittxt_con_password_cre_for_pass = bd.findViewById(R.id.edittxt_con_password_cre_for_pass);
         updatepassword_btn_cre_forgetpassword = bd.findViewById(R.id.updatepassword_btn_cre_forgetpassword);
         login_txt_cre_forgetpassword = bd.findViewById(R.id.login_txt_cre_forgetpassword);
         email_set_cre_for_pass = bd.findViewById(R.id.email_set_cre_for_pass);

        progressBar_create_pass_dialog = bd.findViewById(R.id.progressBar_create_pass_dialog);

        email_set_cre_for_pass.setText(email_txt);


        //Next Button Click
        updatepassword_btn_cre_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_set_cre_for_pass.getText().toString();
                password = edittxt_password_cre_for_pass.getText().toString();
                con_password = edittxt_con_password_cre_for_pass.getText().toString();

                if (validateInputsPassword()) {
                    Boolean checkEmail = checkEmail(email);

                    if (checkEmail) {
                        // Query the database to get the existing password
                        String existingPassword = getExistingPasswordForEmail(email);

                        if (existingPassword != null && existingPassword.equals(password)) {
                            Toast.makeText(Login_Activity.this, "Enter different password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        updatepassword_btn_cre_forgetpassword.setVisibility(View.GONE);
                        progressBar_create_pass_dialog.setVisibility(View.VISIBLE);

                        // Schedule the loader to dismiss after 1.5 seconds
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar_create_pass_dialog.setVisibility(View.GONE);
                                updatepassword_btn_cre_forgetpassword.setVisibility(View.VISIBLE);

                                // Update the password in the database
                                String update = "UPDATE loginandregister_table SET password = ? WHERE email = ?";
                                db.execSQL(update, new String[]{password, email});

                                Toast.makeText(Login_Activity.this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
                                bd.dismiss();
                            }
                        }, 1500);

                    } else {
                        Toast.makeText(Login_Activity.this, "Email is not correct.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Login txt click to close dialog
        login_txt_cre_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd.dismiss();
            }
        });

        bd.show();
    }
    //Check Email
    public Boolean checkEmail(String email) {
        cursor = db.rawQuery("SELECT * FROM loginandregister_table where email = ? ", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            Toast.makeText(Login_Activity.this, "User account not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //Check Password
    public Boolean checkPassword(String password) {
        cursor = db.rawQuery("SELECT * FROM loginandregister_table where password = ? ", new String[]{password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    //Check validInput
    private boolean validateInputs() {
        String email = edittxt_email_login.getText().toString().trim();
        String password = edittxt_password_login.getText().toString().trim();


        if (!isValidEmail(email)) {
            edittxt_email_login.setError("Invalid email address");
            return false;
        }

        if (!isValidPassword(password)) {
            edittxt_password_login.setError("Password must be at least 6 characters long");
            return false;
        }


        return true;
    }

    //Check validInput Password
    private boolean validateInputsPassword() {
        String password = edittxt_password_cre_for_pass.getText().toString().trim();
        String con_password = edittxt_con_password_cre_for_pass.getText().toString();

        if (!isValidPassword(password)) {
            edittxt_password_cre_for_pass.setError("Password must be at least 6 characters long");
            return false;
        }

        if (!isValidConfirmPassword(password, con_password)) {
            edittxt_con_password_cre_for_pass.setError("Passwords do not match");
            return false;
        }

        return true;

    }

    // Method to get the existing password for the provided email from the database
    @SuppressLint("Range")
    private String getExistingPasswordForEmail(String email) {
        String password = null;
        Cursor cursor = db.rawQuery("SELECT password FROM loginandregister_table WHERE email = ?", new String[]{email});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                password = cursor.getString(cursor.getColumnIndex("password"));
            }
            cursor.close();
        }
        return password;
    }

    //Check isValidInput
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }


    //Check isValidPassword
    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 5) {
            return true;
        }
        return false;
    }

    // Con_Password Validation
    private boolean isValidConfirmPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        // Close the login activity
        super.onBackPressed();
        finishAffinity();

    }


}


// <-------------------------------------------------------------------- Languages  ------------------------------------------------------------------------------>


//    private void setLocal(String lang) {
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        Resources resources = getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//        config.setLocale(locale);
//        resources.updateConfiguration(config, dm);
//        // Save the selected language in SharedPreferences
//        saveLanguage(lang);
//    }
//
//    private void saveLanguage(String lang) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("selected_language", lang);
//        editor.apply();
//    }
//
//    private String getSavedLanguage() {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        return prefs.getString("selected_language", "en"); // Default to English
//    }


// <-------------------------------------------------------------------- Update Text ------------------------------------------------------------------------------>
//    private void updateText() {
//        // Layout ---- 1
//        income__addIncome.setText(R.string.income_txt_lay1_addIncome);
//
//        // Layout ---- 2
//        category_addIncome.setText(R.string.category_txt_lay2_addIncome);
//
//
//        // Layout ---- 3
//        paymentMethod_addIncome.setText(R.string.paymentMethod_txt_lay3_addIncome);
//
//        // Layout ---- 4
//        notes_addIncome.setText(R.string.notes_txt_lay4_addIncome);
//
//
//        // Layout ---- 5
//        income_save_btn.setText(R.string.save_btn_txt_lay5_addIncome);
//    }