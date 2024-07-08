package com.example.income_and_expense.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.income_and_expense.Model.Income_Expense_Model;
import com.example.income_and_expense.R;

import java.util.List;

public class Payment_method_Adapter extends ArrayAdapter {

    private Context con;
    private int resource;
    private List<Income_Expense_Model> model;
    private SQLiteDatabase db;
    private String userEmail;


    public Payment_method_Adapter(@NonNull Context context, List<Income_Expense_Model> inc_exp_model, SQLiteDatabase db, String userEmail) {
        super(context, 0, inc_exp_model);
        this.con = context;
        this.resource = resource;
        this.model = inc_exp_model;
        this.db = db;
        this.userEmail = userEmail;
    }

    //-------------- Declare getTItem() method ------------------
    @Nullable
    @Override
    public Income_Expense_Model getItem(int position) {
        return model.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        if (convertView == null) {
//        convertView = LayoutInflater.from(con).inflate(res, null);
//        }
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_method_chart_layout, parent, false);

//        LayoutInflater layoutInflater = LayoutInflater.from(con);
//        View view = layoutInflater.inflate(res, null);


        TextView paymentmethod = convertView.findViewById(R.id.pay_paymentmethod_list);
        TextView  inc_exp_paymentmethod_list= convertView.findViewById(R.id.inc_exp_pay_txt);
        TableLayout tableLayout = convertView.findViewById(R.id.tra_tablayout_home);


        // model
        Income_Expense_Model model = getItem(position);
        paymentmethod.setText(model.getPaymentmethod());
//        String val = model.getIncome() + model.getExpense();
        inc_exp_paymentmethod_list.setText(String.format( model.getIncome(), model.getExpense()));





        return convertView;
    }

}
