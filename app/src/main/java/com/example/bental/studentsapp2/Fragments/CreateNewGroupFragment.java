package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewGroupFragment extends BaseFragment {


    public CreateNewGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater,container,savedInstanceState);
        tvTitle.setText("Create New Group");
        View view = inflater.inflate(R.layout.fragment_create_new_group, container, false);
        final EditText etGroupName = (EditText) view.findViewById(R.id.etGroupName);
        Button btnCreate = (Button) view.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errorMessage = validate(etGroupName.getText().toString());
                if (errorMessage=="") {
                    Group group = new Group();
                    group.setGroupName(etGroupName.getText().toString());
                    Model.instance().createNewGroup(group, Helper.getCurrentUser().getUid());
                    getActivity().onBackPressed();
                } else{
                    Toast.makeText(view.getContext(),errorMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private String validate(String name){
        if (name.isEmpty()) {
            return "Please enter a group name.";
        }
        return "";
    }
}
