package com.example.bental.studentsapp2.Fragments;

import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;


public class AddNewShoppingItemFragment extends AddEditBaseFragment {
    public AddNewShoppingItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSave(ShoppingItem item) {
        Model.instance().addShoppingItem(item,groupId);
    }
}
