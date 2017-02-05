package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.adapters.GroupAdapter;
import com.example.bental.studentsapp2.adapters.ShoppingItemAdapter;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends BaseFragment {
    View _rootView;
    Group currentGroup;
    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        tvTitle.setText("My Product List - " + currentGroup.getGroupName());
        if (_rootView == null) {
            _rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
            final ListView listview = (ListView) _rootView.findViewById(R.id.shopping_items_list);
            Model.instance().getShoppingItemsByGroupId(currentGroup.getGroupId(), new Model.GetShoppingItemsByGroupIdListener() {
                @Override
                public void onComplete(HashMap<String, ShoppingItem> shoppingItems) {
                    listview.setAdapter(new ShoppingItemAdapter(getActivity().getApplicationContext(),
                            shoppingItems, currentGroup.getGroupId()));
                }
            });
            setHasOptionsMenu(true);
        }
        return _rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(getActivity().getString(R.string.show_add_shopping_item));
                intent.putExtra(getString(R.string.group_id), currentGroup.getGroupId());
                getActivity().sendBroadcast(intent);
                break;
        }
        return true;
    }

    public void setCurrentGroup(Group group) {
        this.currentGroup = group;
    }


}
