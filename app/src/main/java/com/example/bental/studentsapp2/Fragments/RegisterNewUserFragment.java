package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.example.bental.studentsapp2.SpinnerDialog;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterNewUserFragment extends BaseFragment {
    EditText etFirstName;
    EditText etLastName;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirm;
    TextView tvErrorMessage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public RegisterNewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        linearTopBar.setVisibility(View.GONE);
        // getActivity().findViewById()
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_new_user, container, false);
        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etPasswordConfirm = (EditText) view.findViewById(R.id.etPasswordConfirm);
        tvErrorMessage = (TextView) view.findViewById(R.id.tvErrorMessage);

        Button btnSave = (Button) view.findViewById(R.id.btnCreate);
        mAuth = FirebaseAuth.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String errorMessage = validate(
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        etPasswordConfirm.getText().toString());
                if (errorMessage!="") {
                    tvErrorMessage.setText(errorMessage);
                    return;
                } else {
                    tvErrorMessage.setText("");
                }
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final SpinnerDialog spinner = Helper.showLoader(getFragmentManager());
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    spinner.dismiss();
                                    Toast.makeText(getActivity(), task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
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
                                                        spinner.dismiss();
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

    private String validate(String firstName, String lastName, String email, String password, String confirm){
        if (firstName.isEmpty()) {
            return "Please enter your first name.";
        }
        if (lastName.isEmpty()) {
            return "Please enter your last name.";
        }
        if (email.isEmpty()) {
            return "Please enter your email address.";
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Please enter a valid email address.";
        }
        if (password.isEmpty()) {
            return "Please enter a password.";
        }
        if (password.length()<6) {
            return "Password must be at least 6 characters long.";
        }
        if (!password.equals(confirm)) {
            return "Password does not match";
        }
        return "";
    }
}
