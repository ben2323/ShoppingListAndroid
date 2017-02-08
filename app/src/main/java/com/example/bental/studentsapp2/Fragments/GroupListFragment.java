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

import com.example.bental.studentsapp2.adapters.CustomBaseAdapter;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends Fragment {


    public GroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        ListView listview = (ListView) view.findViewById(R.id.listViewGroups);
        //listview.setAdapter(new CustomBaseAdapter(getActivity(), Model.instance().getAllStudents()));
        return view;
    }
    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*        switch (item.getItemId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(getActivity().getString(R.string.show_add_student));
                getActivity().sendBroadcast(intent);
                break;
        }*/
        return true;
    }
}
