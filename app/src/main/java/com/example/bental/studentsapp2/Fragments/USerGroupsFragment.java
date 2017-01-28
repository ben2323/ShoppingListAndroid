package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bental.studentsapp2.adapters.CustomBaseAdapter;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.adapters.GroupAdapter;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserGroupsFragment extends Fragment {

    public UserGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> userGroups = new ArrayList<>();
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //redirect to login page

        }
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        final ListView listview = (ListView) view.findViewById(R.id.listViewGroups);

        Model.instance().getGroupsForUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                new Model.GetGroupsForUserListener() {
                    @Override
                    public void onComplete(List<Group> groups) {
                        if (getActivity()!=null) {
                            listview.setAdapter(new GroupAdapter(getActivity().getApplicationContext(), (ArrayList)groups));
                        }
                    }
                });


        return view;
    }

}
