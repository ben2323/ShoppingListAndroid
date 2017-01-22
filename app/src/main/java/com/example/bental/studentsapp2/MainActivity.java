package com.example.bental.studentsapp2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;


import com.example.bental.studentsapp2.Fragments.AddEditFragment;
import com.example.bental.studentsapp2.Fragments.CreateNewGroupFragment;
import com.example.bental.studentsapp2.Fragments.GroupListFragment;
import com.example.bental.studentsapp2.Fragments.JoinGroupFragment;
import com.example.bental.studentsapp2.Fragments.LoginFragment;
import com.example.bental.studentsapp2.Fragments.ProductDetailsFragment;
import com.example.bental.studentsapp2.Fragments.RegisterNewUserFragment;
import com.example.bental.studentsapp2.Fragments.UserGroupsFragment;

public class MainActivity extends Activity {
private BroadcastReceiver mBroadcastReceiver;
    private static String ADD_EDIT_STUDENT = "addEditStudentFragment";
    //private static String EDIT_STUDENT = "editStudentFragment";
    private static String STUDENT_LIST = "GroupListFragment";
    private static String CREATE_NEW_GROUP = "createNewGrop";
    private static String REGISTER_NEW_USER = "registerNewUser";
    private static String LOGIN = "login";
    private static String USER_GROUPS = "userGroups";
    private static String JOIN_GROUP = "joinGroup";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        onShowUserGroups();


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(getString(R.string.default_web_client_id))) {

                    onShowStudentDetails(intent);

                } else if (intent.getAction().equals(getString(R.string.show_login))) {

                    onShowLogin();
                } else if (intent.getAction().equals(getString(R.string.show_register))) {
                    onShowRegisterNewUser();
                } else if (intent.getAction().equals(getString(R.string.show_user_groups))) {
                    onShowUserGroups();
                } else if (intent.getAction().equals(getString(R.string.show_create_group))) {
                    onShowCreateNewGroup();
                } else if (intent.getAction().equals(getString(R.string.show_join_group))) {
                    onShowAddUserToGroup();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
       // intentFilter.addAction(getString(R.string.show_add_student));
        intentFilter.addAction(getString(R.string.show_user_groups));
        intentFilter.addAction(getString(R.string.show_login));
        intentFilter.addAction(getString(R.string.show_register));
        intentFilter.addAction(getString(R.string.show_create_group));
        this.registerReceiver(mBroadcastReceiver, intentFilter);


    }
    private void onShowAddUserToGroup() {
        FragmentManager fm = getFragmentManager();

        JoinGroupFragment fragment = new JoinGroupFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, LOGIN);
        ftr.commit();
    }

    private void onShowLogin() {
        FragmentManager fm = getFragmentManager();

        LoginFragment fragment = new LoginFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, LOGIN);
        ftr.commit();

    }

    private void onShowUserGroups() {
        FragmentManager fm = getFragmentManager();

        UserGroupsFragment fragment = new UserGroupsFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, USER_GROUPS);
        ftr.commit();

    }



    private void onShowCreateNewGroup() {
        FragmentManager fm = getFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            //clear all fragments from stack so the last fragment on stack will be the student list
            //we do that so when pressing the back button application will end
            fm.popBackStack();
        }
        CreateNewGroupFragment fragment = new CreateNewGroupFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, CREATE_NEW_GROUP);
        ftr.commit();
    }

    private void onShowRegisterNewUser() {
        FragmentManager fm = getFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            //clear all fragments from stack so the last fragment on stack will be the student list
            //we do that so when pressing the back button application will end
            fm.popBackStack();
        }
        RegisterNewUserFragment fragment = new RegisterNewUserFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, REGISTER_NEW_USER);
        ftr.commit();

    }

    private void onShowStudentList() {
        FragmentManager fm = getFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            //clear all fragments from stack so the last fragment on stack will be the student list
            //we do that so when pressing the back button application will end
            fm.popBackStack();
        }
        GroupListFragment fragment = new GroupListFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, STUDENT_LIST);
        ftr.commit();

    }

    private void onShowStudentDetails(Intent intent) {
/*        ProductDetailsFragment studentDetailFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        studentDetailFragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, studentDetailFragment, STUDENT_DETAILS);
        if (getFragmentManager().findFragmentByTag(ADD_EDIT_STUDENT) == null) {
            ftr.addToBackStack(null);
        }

        ftr.commit();*/

    }

    private void onShowAddStudent() {
    }

    private void onShowEditStudent(Intent intent) {
/*        AddEditFragment fragment = new AddEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        fragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, ADD_EDIT_STUDENT);
        ftr.addToBackStack(null);
        ftr.commit();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}