package com.example.income_and_expense.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.income_and_expense.Activity.Category_Chart_Activity;
import com.example.income_and_expense.Fragment.ExpenseFragment;
import com.example.income_and_expense.Fragment.IncomeFragment;
import com.example.income_and_expense.Fragment.SummaryFragment;

public class Viewpager_Adapter  extends FragmentStateAdapter {
    private final String userEmail;


    public Viewpager_Adapter(@NonNull FragmentActivity fragmentActivity,String userEmail) {
        super(fragmentActivity);
        this.userEmail = userEmail;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle args = new Bundle();
        args.putString("USER_EMAIL", userEmail);

        if (position == 0) {
            fragment = new SummaryFragment();
        } else if (position == 1) {
            fragment = new ExpenseFragment();
        } else {
            fragment = new IncomeFragment();
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
