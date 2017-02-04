package com.example.bental.studentsapp2.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;


public class EditShoppingItemFragment extends AddEditBaseFragment {
    ShoppingItem currentShoppingItem;

    public EditShoppingItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        pictureName = currentShoppingItem.getImageUrl();
        etName.setText(currentShoppingItem.getName());
        etQuantity.setText(String.valueOf(currentShoppingItem.getQuantity()));
        if (currentShoppingItem.getImageUrl() != "") {
            Helper.setImageFromStorage(currentShoppingItem.getImageUrl(), view.getContext(), ivProductImage);
        }
        return view;
    }

    @Override
    public void onSave(ShoppingItem item) {
        currentShoppingItem.setQuantity(item.getQuantity());
        currentShoppingItem.setName(item.getName());
        currentShoppingItem.setImageUrl(item.getImageUrl());
        Model.instance().editShoppingItem(currentShoppingItem, groupId);
    }

    public void setCurrentShoppingItem(ShoppingItem currentShoppingItem) {
        this.currentShoppingItem = currentShoppingItem;
    }

}
