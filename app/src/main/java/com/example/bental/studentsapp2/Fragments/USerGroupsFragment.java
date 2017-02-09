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
import android.widget.Button;
import android.widget.ListView;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.SpinnerDialog;
import com.example.bental.studentsapp2.adapters.CustomBaseAdapter;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.adapters.GroupAdapter;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserGroupsFragment extends BaseFragment {
    View _rootView;

    public UserGroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        tvTitle.setText("My Groups");
        if (Helper.getCurrentUser() == null) {
            Intent intent = new Intent(getString(R.string.show_login));
            getActivity().sendBroadcast(intent);
            return null;
        }
        if (_rootView == null) {
            ArrayList<String> userGroups = new ArrayList<>();
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                //redirect to login page
            }
            _rootView = inflater.inflate(R.layout.fragment_group_list, container, false);

            final ListView listview = (ListView) _rootView.findViewById(R.id.listViewGroups);
            //Button btnJoinGroup = (Button) _rootView.findViewById(R.id.btnJoinGroup);
            Button btnCreateGroup = (Button) _rootView.findViewById(R.id.btnCreateGroup);

/*            btnJoinGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getString(R.string.show_join_group));
                    getActivity().sendBroadcast(intent);
                }
            });*/

            btnCreateGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getString(R.string.show_create_group));
                    getActivity().sendBroadcast(intent);
                }
            });

            Model.instance().getGroupsForUser(getFragmentManager(),
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    new Model.GetGroupsByUserIdListener() {
                        @Override
                        public void onComplete(HashMap<String, Group> groups) {
                            if (getActivity() != null) {
                                listview.setAdapter(new GroupAdapter(getActivity().getApplicationContext(), groups));
                            }
                        }
                    });
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // (it will be added back).
            // ((ViewGroup)_rootView.getParent()).removeView(_rootView);
        }
        return _rootView;
    }
}
