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

public class Category_Adapter  extends ArrayAdapter {

    private Context con;
    private int res;
    private List<Income_Expense_Model> model;
    private SQLiteDatabase db;
    String useremail;

    public Category_Adapter(@NonNull Context con, List<Income_Expense_Model> model, SQLiteDatabase db, String useremail) {
        super(con,0, model);
        this.con = con;
        this.model = model;
        this.db = db;
        this.useremail = useremail;
    }

    @Nullable
    @Override
    public Income_Expense_Model getItem(int position) {
        return model.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Income_Expense_Model model = getItem(position);

        convertView = LayoutInflater.from(getContext()).inflate(    R.layout.category_chart_layout, parent, false);


        TextView category = convertView.findViewById(R.id.cat_category_list);
        TextView  inc_exp_category_list= convertView.findViewById(R.id.inc_exp_category_list);
        TextView  percentage_category_list= convertView.findViewById(R.id.percentage_category_list);
        TableLayout tableLayout = convertView.findViewById(R.id.tra_tablayout_home);

        // model
        category.setText(model.getCategory());
        inc_exp_category_list.setText(String.format( model.getIncome(), model.getExpense()));
        percentage_category_list.setText(model.getExpense() + " %");



        return convertView;
    }


}
