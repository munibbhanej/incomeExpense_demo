package com.example.income_and_expense.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;

import java.util.List;

public class Email_Adapter extends ArrayAdapter {

    private Context con;
    private int resource;
    private List<Income_Expense_Model> inc_exp_model;
    private SQLiteDatabase db;

    public Email_Adapter(@NonNull Context con, List<Income_Expense_Model> inc_exp_model, SQLiteDatabase db) {
        super(con, 0, inc_exp_model);
        this.con = con;
        this.resource = resource;
        this.inc_exp_model = inc_exp_model;
        this.db = db;
    }

    @Nullable
    @Override
    public Income_Expense_Model getItem(int position) {
        return inc_exp_model.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        if (convertView == null) {
//        convertView = LayoutInflater.from(con).inflate(res, null);
//        }
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.email_layout, parent, false);

//        LayoutInflater layoutInflater = LayoutInflater.from(con);
//        View view = layoutInflater.inflate(res, null);

        TextView email = convertView.findViewById(R.id.account_transfer_datashow_list);
        TableLayout tableLayout = convertView.findViewById(R.id.transfer_tablayout);




        // model
        Income_Expense_Model model = getItem(position);
        email.setText(model.getUser_email());


        // Determine if the app is in dark mode
        boolean isDarkMode = (con.getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK)
                == android.content.res.Configuration.UI_MODE_NIGHT_YES;

        int textColor1;

        if (isDarkMode) {
            textColor1 = con.getResources().getColor(R.color.darkText2, null);
        } else {
            textColor1= con.getResources().getColor(R.color.lightText2, null);
        }

        email.setTextColor(textColor1);

        return convertView;
    }


}
