package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewGroupFragment extends Fragment {


    public CreateNewGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_group, container, false);
        final EditText etGroupName = (EditText) view.findViewById(R.id.etPasswordConfirm);
        ImageView btnCreate = (ImageView) view.findViewById(R.id.btnCreate);
        ImageView btnCancel = (ImageView) view.findViewById(R.id.btnCancel);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group group = new Group();
                group.setGroupName(etGroupName.getText().toString());
                Model.instance().createNewGroup(group);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}