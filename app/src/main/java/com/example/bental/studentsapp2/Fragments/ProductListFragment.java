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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment {

    List<ShoppingItem> shoppingList;
    String groupId;


    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.shopping_items_list);

        Model.instance().getShoppingItemsByGroupId(groupId, new Model.GetShoppingItemsByGroupIdListener() {
            @Override
            public void onComplete(List<ShoppingItem> shoppingItems) {
                listview.setAdapter(new ShoppingItemAdapter(getActivity().getApplicationContext(), (ArrayList) shoppingItems));
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(getActivity().getString(R.string.show_add_shopping_item));
                intent.putExtra(getString(R.string.group_id),groupId);
                getActivity().sendBroadcast(intent);
                break;
        }
        return true;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<ShoppingItem> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingItem> shoppingList) {
        this.shoppingList = shoppingList;
    }

}
