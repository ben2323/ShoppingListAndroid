package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNewUserFragment extends BaseFragment {
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public RegisterNewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_new_user, container, false);
        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) view.findViewById(R.id.etPasswordConfirm);

        ImageView btnSave = (ImageView) view.findViewById(R.id.btnCreate);
        ImageView btnCancel = (ImageView) view.findViewById(R.id.btnCancel);
        mAuth = FirebaseAuth.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "auth faild",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //user was added in firebase auth service, add it now to users table
                                    final User newUser = new User();
                                    newUser.setFirstName(etFirstName.getText().toString());
                                    newUser.setLastName(etLastName.getText().toString());
                                    newUser.setEmail(etEmail.getText().toString());
                                    newUser.setUserId(task.getResult().getUser().getUid());
                                    Model.instance().addNewUser(newUser);
                                    //log the new user in
                                    mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        //send him to the user groups screen
                                                        Intent intent = new Intent(getActivity().getString(R.string.show_user_groups));
                                                        getActivity().sendBroadcast(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });

            }
        });

        return view;
    }

}
