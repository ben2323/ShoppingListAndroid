package com.example.bental.studentsapp2.Fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.MainActivity;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.SpinnerDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {
    EditText etEmail;
    EditText etPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Activity activity;
    Context context;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        context = view.getContext();
        linearTopBar.setVisibility(View.GONE);
        activity = getActivity();
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
        TextView tvRegister = (TextView) view.findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity.getString(R.string.show_register));
                activity.sendBroadcast(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in - send him to the user groups screen
                    Intent intent = new Intent(getActivity().getString(R.string.show_user_groups));
                    getActivity().sendBroadcast(intent);
                } else {
                    // User is signed out
                    Log.d("aa", "onAuthStateChanged:signed_out");
                }

            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final SpinnerDialog spinner = Helper.showLoader(getFragmentManager());
                String errorMessage = validate(etEmail.getText().toString(), etPassword.getText().toString());
                if (!errorMessage.isEmpty()) {
                    Toast.makeText(context,errorMessage,Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                spinner.dismiss();
                                if (task.isSuccessful()) {
                                    //redirect to main screen
                                    Intent intent = new Intent(activity.getString(R.string.show_user_groups));
                                    activity.sendBroadcast(intent);
                                } else{
                                    Toast.makeText(context,
                                            "User does not exist or the password you entered is wrong.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        return view;
    }
    private String validate(String email, String password){
        if (email.isEmpty()) {
            return "Please enter the email you registered with.";
        }
        if (password.isEmpty()) {
            return "Please enter your password.";
        }
        return "";
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
