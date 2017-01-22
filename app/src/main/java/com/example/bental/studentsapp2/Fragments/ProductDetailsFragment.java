package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.Student;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {
Student currentStudent;
    int currentStudentIndex;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }
    private void fillTextViews(View view) {
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        ImageView ivStudentPicture = (ImageView) view.findViewById(R.id.ivStudentPicture);
        CheckBox chkbxChecked = (CheckBox) view.findViewById(R.id.chkbxChecked);

        tvName.setText(currentStudent.getName());
        tvId.setText(currentStudent.getId());
        tvPhone.setText(currentStudent.getPhone());
        tvAddress.setText(currentStudent.getAddress());
        ivStudentPicture.setImageResource(currentStudent.getImage());
        chkbxChecked.setChecked(currentStudent.isChecked());
        tvDate.setText(currentStudent.getBirthDate().toDateString());
        tvTime.setText(currentStudent.getBirthTime().toTimeString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        return view;
/*        getActivity().setTitle("Student Details");
        currentStudentIndex = getArguments().getInt(getString(R.string.current_student_index));
        currentStudent = Model.instance().getAllStudents().get(currentStudentIndex);

        fillTextViews(view);
        setHasOptionsMenu(true);
        */
    }
    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/*        inflater.inflate(R.menu.student_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*        switch (item.getItemId()) {
            case R.id.btnEdit:
                Intent intent = new Intent(getActivity().getString(R.string.show_edit_student));
                intent.putExtra(getString(R.string.current_student_index),currentStudentIndex);
                getActivity().sendBroadcast(intent);
                break;
        }*/
        return true;
    }

}
