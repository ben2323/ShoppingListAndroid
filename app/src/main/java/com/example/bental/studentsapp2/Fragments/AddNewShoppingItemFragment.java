package com.example.bental.studentsapp2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;


public class AddNewShoppingItemFragment extends AddEditBaseFragment {
    public AddNewShoppingItemFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater,container,savedInstanceState);
        tvTitle.setText("Add Product");
        return view;

    }
    @Override
    public void onSave(ShoppingItem item) {
        Model.instance().addShoppingItem(item,groupId);
    }
}
