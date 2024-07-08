package com.example.income_and_expense.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


import com.example.income_and_expense.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity {

    ImageView profile_image_register;
    ImageView add_image_register;
    EditText edittxt_name_register, edittxt_email_register, edittxt_password_register, edittxt_cof_password_register;

    TextView login_txt_register;
    Button register_btn;

    ProgressBar progressBar;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;


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
        setContentView(R.layout.activity_register);

        db = createDatabase();
        createTable();


        profile_image_register = findViewById(R.id.profile_image_register);
        add_image_register = findViewById(R.id.add_image_register);


        edittxt_name_register = findViewById(R.id.edittxt_name_register);
        edittxt_email_register = findViewById(R.id.edittxt_email_register);
        edittxt_password_register = findViewById(R.id.edittxt_password_register);
        edittxt_cof_password_register = findViewById(R.id.edittxt_cof_password_register);
        register_btn = findViewById(R.id.register_btn);
        login_txt_register = findViewById(R.id.login_txt_register);

        // add Button click
        add_image_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showImageSelectionDialog();

                }
            });


        progressBar = findViewById(R.id.progressBar_reg);


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edittxt_name_register.getText().toString();
                String email = edittxt_email_register.getText().toString();
                String password = edittxt_password_register.getText().toString();


                if (validateInputs()) {

                    Boolean checkUserEmail = checkEmail(email);

                    if (checkUserEmail) {
                        Toast.makeText(Register_Activity.this, "User already exists, please login", Toast.LENGTH_SHORT).show();
                    } else {

                        Drawable drawable = profile_image_register.getDrawable();
                        Bitmap imageBitmap = drawableToBitmap(drawable);
                        byte[] category_image = getBitmapAsByteArray(imageBitmap);

                        Boolean insert = insertData(name, email, password,category_image);


                        if (insert) {

                            register_btn.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);

                            // Schedule the loader to dismiss after 1 minute
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                loader.dismiss();
                                    progressBar.setVisibility(View.GONE);
                                    register_btn.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Register_Activity.this, "Register Success...", Toast.LENGTH_SHORT).show();
                                }
                            }, 1500);


                        } else {
                            Toast.makeText(Register_Activity.this, "Register Failed...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        login_txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Activity.this, Login_Activity.class));
            }
        });

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

    // Convert bitmap to byte array
    private byte[] getBitmapAsByteArray(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Compress the image to reduce its size
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();

    }


    private void showImageSelectionDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Camera option selected
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
                        } else if (which == 1) {
                            // Gallery option selected
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
                        }
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profile_image_register.setImageBitmap(photo);

            } else if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    profile_image_register.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Insert data
    public Boolean insertData(String name, String email, String password , byte[] image) {

        String insertSQL = "INSERT INTO loginandregister_table (name, email, password,img) VALUES (?, ?, ?,?)";
        SQLiteStatement statement = db.compileStatement(insertSQL);
        statement.bindString(1, name);
        statement.bindString(2, email);
        statement.bindString(3, password);
        statement.bindBlob(4, image);
        statement.executeInsert();

        return true;

    }

    // Check email
    public Boolean checkEmail(String email) {
        cursor = db.rawQuery("SELECT * FROM loginandregister_table where email = ? ", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // Validate Input
    private boolean validateInputs() {
        String name = edittxt_name_register.getText().toString();
        String email = edittxt_email_register.getText().toString();
        String password = edittxt_password_register.getText().toString();
        String con_password = edittxt_cof_password_register.getText().toString();

        if (!isValidName(name)) {
//            edittxt_name_register.setError("Name is required");
            edittxt_name_register.setError("At least 4 Character");
            return false;
        }

        if (!isValidEmail(email)) {
            edittxt_email_register.setError("Invalid email format");
            return false;
        }

        if (!isValidPassword(password)) {
            edittxt_password_register.setError("Password must be at least 6 characters long");
            return false;
        }

        if (!isValidConfirmPassword(password, con_password)) {
            edittxt_cof_password_register.setError("Passwords do not match");
            return false;
        }

        return true;
    }

    // Name Validation
    private boolean isValidName(String name) {
        if (name != null && name.length() > 3) {
            return true;
        }
        return false;
    }

    // Email Validation
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = p.matcher(email);
        return matcher.matches();
    }


    // Password Validation
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

}