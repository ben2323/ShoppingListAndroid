package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;

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
        View view = inflater.inflate(R.layout.fragment_join_group, container, false);

        final EditText etGroupKey = (EditText) view.findViewById(R.id.etGroupKey);
        ImageView btnJoin = (ImageView) view.findViewById(R.id.btnOk);
        ImageView btnCancel = (ImageView) view.findViewById(R.id.btnCancel);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Model.instance().addUserToGroup(getCurrentUser().getUid(),etGroupKey.getText().toString());
            }
        });
        return view;
    }

}