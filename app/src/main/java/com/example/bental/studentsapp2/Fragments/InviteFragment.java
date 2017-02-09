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
public class InviteFragment extends BaseFragment {

    String groupId;

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public InviteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        tvTitle.setText("Invite a Friend");
        View view = inflater.inflate(R.layout.fragment_join_group, container, false);

        final EditText etEmail = (EditText) view.findViewById(R.id.etUserEmail);
        Button btnJoin = (Button) view.findViewById(R.id.btnOk);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String errorMessage = validate(etEmail.getText().toString());
                if (!errorMessage.isEmpty()) {
                    Toast.makeText(view.getContext(),
                            errorMessage,Toast.LENGTH_LONG).show();
                    return;
                }
                Model.instance().getUserByEmail(etEmail.getText().toString(), new Model.GetUserByIdListener() {
                    @Override
                    public void onComplete(User user) {
                        if (user!=null) {
                            Model.instance().addUserToGroup(user.getUserId(), groupId, new Model.AddUserToGroupListener() {
                                @Override
                                public void onComplete(Boolean success) {
                                    getActivity().onBackPressed();
                                }
                            });
                        } else {

                        }
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

    private String validate(String userEmail){
        if (userEmail.equals(Helper.getCurrentUser().getEmail())) {
            return "You cannot add yourself to a group you are already a member at.";
        }
        if (userEmail.isEmpty()) {
            return "Please enter an email of a friend you woud like to invite.";
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            return "Please enter a valid email address.";
        }
        return "";
    }
}
