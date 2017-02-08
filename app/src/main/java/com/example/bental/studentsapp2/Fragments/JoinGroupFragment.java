package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.MyAppContext;
import com.example.bental.studentsapp2.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinGroupFragment extends BaseFragment {


    public JoinGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        tvTitle.setText("Join Group");
        View view = inflater.inflate(R.layout.fragment_join_group, container, false);

        final EditText etGroupKey = (EditText) view.findViewById(R.id.etGroupKey);
        Button btnJoin = (Button) view.findViewById(R.id.btnOk);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Model.instance().getUserByEmail(etGroupKey.getText().toString(), new Model.GetUserByIdListener() {
                    @Override
                    public void onComplete(User user) {

                    }
                });




/*               Model.instance().addUserToGroup(Helper.getCurrentUser().getUid(),
                       etGroupKey.getText().toString(), new Model.AddUserToGroupListener() {
                           @Override
                           public void onComplete(Boolean success) {
                               if (success) {
                                   getActivity().onBackPressed();
                               } else{
                                   Toast.makeText(view.getContext(),
                                           "Group with such key does not exist",Toast.LENGTH_LONG).show();
                               }
                           }
                       });*/

            }
        });
        return view;
    }

}
