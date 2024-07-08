package com.example.income_and_expense.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.income_and_expense.Activity.Expense_Activity;
import com.example.income_and_expense.Activity.Income_Activity;
import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;

import java.text.DecimalFormat;
import java.util.List;

public class Incomeexpense_Adapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private List<Income_Expense_Model> inc_exp_model;
    private SQLiteDatabase db;
    private String userEmail;


    public Incomeexpense_Adapter(@NonNull Context context, List<Income_Expense_Model> inc_exp_model, SQLiteDatabase db, String userEmail) {
        super(context, 0, inc_exp_model);
        this.context = context;
        this.resource = resource;
        this.inc_exp_model = inc_exp_model;
        this.db = db;
        this.userEmail = userEmail;
    }


    //-------------- Declare getTItem() method ------------------
    @Nullable
    @Override
    public Income_Expense_Model getItem(int position) {
        return inc_exp_model.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_list_layout, parent, false);
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View view = layoutInflater.inflate(resource, null);

        TextView income = convertView.findViewById(R.id.income_transaction_all_list1);
        TextView expense = convertView.findViewById(R.id.expense_transaction_all_list1);
        TextView category = convertView.findViewById(R.id.category_transaction_all_list1);
        TextView paymentmethod = convertView.findViewById(R.id.paymentmethod_transaction_all_list1);
        TextView date = convertView.findViewById(R.id.date_transaction_all_list1);
        TextView time = convertView.findViewById(R.id.time_transaction_all_list1);
        TextView note = convertView.findViewById(R.id.note_transaction_all_list1);
        LinearLayout linearLayout = convertView.findViewById(R.id.tra_tablayout);


        // model
        Income_Expense_Model model = getItem(position);
        updateTextViewWithFormattedNumber(income, model.getIncome());
        updateTextViewWithFormattedNumber(expense, model.getExpense());


//        expense.setText(model.getExpense());
        category.setText(model.getCategory());
        paymentmethod.setText(model.getPaymentmethod());
        note.setText(model.getNotes());
        date.setText(model.getDate());
        time.setText(model.getTime());

        // Determine if the app is in dark mode
        boolean isDarkMode = (context.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                == android.content.res.Configuration.UI_MODE_NIGHT_YES;

        int textColor1,textColor2;

        if (isDarkMode) {
            textColor1 = context.getResources().getColor(R.color.income2, null);
            textColor2 = context.getResources().getColor(R.color.expense2, null);
        } else {
            textColor1= context.getResources().getColor(R.color.income1, null);
            textColor2= context.getResources().getColor(R.color.expense1, null);
        }

        income.setTextColor(textColor1);
        expense.setTextColor(textColor2);

        // Set noteTransaction visibility based on its content
        String notes = model.getNotes();
        if (notes == null || notes.trim().isEmpty()) {
            note.setVisibility(View.GONE);
        } else {
            note.setVisibility(View.VISIBLE);
            note.setText(model.getNotes());
        }


        if (income.getText().toString().equals("0")) {
            income.setText("");
        } else if (expense.getText().toString().equals("0")) {
            expense.setText("");
        }


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                try {
                    if (!income.getText().toString().isEmpty()) {
                        i = new Intent(context, Income_Activity.class);
                        //passing data to activity (putExtra)
                        i.putExtra("id_inc", String.valueOf(model.getRegId()));

                    } else {
                        i = new Intent(context, Expense_Activity.class);
                        //passing data to activity (putExtra)
                        i.putExtra("id_exp", String.valueOf(model.getRegId()));

                    }
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("USER_EMAIL", userEmail);
                    context.startActivity(i);


                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    // Assuming you have a method to update your TextView with formatted number
    private void updateTextViewWithFormattedNumber(TextView textView, String number) {
        try {
            double num = Double.parseDouble(number);
            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String formattedNumber = decimalFormat.format(num);
            textView.setText(formattedNumber);
        } catch (NumberFormatException | NullPointerException e) {
            textView.setText(number);
        }
    }
}
