package com.example.bental.studentsapp2;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.bental.studentsapp2.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ben on 1/14/2017.
 */

public class FirebaseHelper {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    public void createUser(String email, String password){
/*        mAuth.createUserWithEmailAndPassword(email, etPassword.getText().toString())
                .addOnCompleteListener(getClass(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("ccc", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        Model.instance().addNewUser(user);
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "auth faild",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }
}
