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
import com.example.bental.studentsapp2.Fragments.StudentDetailsFragment;
import com.example.bental.studentsapp2.Fragments.StudentListFragment;

public class MainActivity extends Activity {
private BroadcastReceiver mBroadcastReceiver;
    private static String ADD_EDIT_STUDENT = "addEditStudentFragment";
    //private static String EDIT_STUDENT = "editStudentFragment";
    private static String STUDENT_LIST = "StudentListFragment";
    private static String STUDENT_DETAILS = "StudentDetailsFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
onShowStudentList();


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(getString(R.string.show_student_details))) {

                    onShowStudentDetails(intent);

                } else if (intent.getAction().equals(getString(R.string.show_student_list))) {

                    onShowStudentList();

                } else if (intent.getAction().equals(getString(R.string.show_add_student))) {

                    onShowEditStudent(intent);

                } else if (intent.getAction().equals(getString(R.string.show_edit_student))) {

                    onShowEditStudent(intent);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.show_add_student));
        intentFilter.addAction(getString(R.string.show_edit_student));
        intentFilter.addAction(getString(R.string.show_student_details));
        intentFilter.addAction(getString(R.string.show_student_list));
        this.registerReceiver(mBroadcastReceiver, intentFilter);


    }

    private void onShowStudentList() {
        FragmentManager fm = getFragmentManager();

        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            //clear all fragments from stack so the last fragment on stack will be the student list
            //we do that so when pressing the back button application will end
            fm.popBackStack();
        }
        StudentListFragment fragment = new StudentListFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, STUDENT_LIST);
        ftr.commit();

    }

    private void onShowStudentDetails(Intent intent) {
        StudentDetailsFragment studentDetailFragment = new StudentDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        studentDetailFragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, studentDetailFragment, STUDENT_DETAILS);
        if (getFragmentManager().findFragmentByTag(ADD_EDIT_STUDENT) == null) {
            ftr.addToBackStack(null);
        }

        ftr.commit();

    }

    private void onShowAddStudent() {
    }

    private void onShowEditStudent(Intent intent) {
        AddEditFragment fragment = new AddEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        fragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, ADD_EDIT_STUDENT);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}