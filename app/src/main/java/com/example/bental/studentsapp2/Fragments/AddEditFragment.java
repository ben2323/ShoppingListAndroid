package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.bental.studentsapp2.DateDialog.DateDialogFragment;
import com.example.bental.studentsapp2.DialogCreator;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.CustomSimpleDate;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.Student;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends Fragment {
    EditText etName;
    EditText etId;
    EditText etPhone;
    EditText etAddress;
    CheckBox chkbxCheck;
    EditText etBirthDate;
    EditText etBirthTime;
    CustomSimpleDate tempBirthDate;
    CustomSimpleDate tempBirthTime;
    Student currentStudent;


    public AddEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);


        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnDelete = (Button) view.findViewById(R.id.btnDelete);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);


        etId = (EditText) view.findViewById(R.id.etId);
        etName = (EditText) view.findViewById(R.id.etName);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        chkbxCheck = (CheckBox) view.findViewById(R.id.chkbxEditChecked);
        etBirthDate = (EditText) view.findViewById(R.id.etBirthDate);
        etBirthTime = (EditText) view.findViewById(R.id.etBirthTime);
        final int currentStudentIndex = this.getArguments().getInt(getString(R.string.current_student_index));
        if (currentStudentIndex != -1) {//edit mode
            getActivity().setTitle("Edit Student");
            prepareViewsForEditMode(currentStudentIndex, btnDelete);
        } else {//add mode
            getActivity().setTitle("Add Student");
            btnCancel.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }


        //onClick listeners


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave(currentStudentIndex);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void onSave(final int currentStudentIndex) {
        String dialogMessage = "";
        String dialogTitle = "";
        Student student = new Student(
                etAddress.getText().toString(),
                etName.getText().toString(),
                etId.getText().toString(),
                etPhone.getText().toString(),
                chkbxCheck.isChecked(),
                1,
                tempBirthDate == null ? currentStudent.getBirthDate() : tempBirthDate,
                tempBirthTime == null ? currentStudent.getBirthTime() : tempBirthTime
        );
        if (currentStudentIndex != -1) {
            dialogMessage = "Student details were successfully updated.";
            dialogTitle = "Edit Student";

            Model.instance().editStudent(currentStudentIndex, student);
        } else {
            dialogMessage = "Student was successfully added.";
            dialogTitle = "Add Student";
            Model.instance().addStudent(student);
        }

        Runnable callback = new Runnable() {
            public void run() {
                getActivity().sendBroadcast(new Intent(getString(R.string.show_student_list)));
            }
        };
        DialogCreator.createDialog(getActivity(), dialogMessage, dialogTitle, callback);
    }

    private void prepareViewsForEditMode(int currentStudentIndex, Button btnDelete) {

        currentStudent = Model.instance().getAllStudents().get(currentStudentIndex);
        etName.setText(currentStudent.getName());
        etId.setText(String.valueOf(currentStudent.getId()));
        etPhone.setText(String.valueOf(currentStudent.getPhone()));
        etAddress.setText(currentStudent.getAddress());
        chkbxCheck.setChecked(currentStudent.isChecked());
        etBirthDate.setText(currentStudent.getBirthDate().toDateString());
        etBirthTime.setText(currentStudent.getBirthTime().toTimeString());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance().deleteStudent(currentStudent);
                Runnable callback = new Runnable() {
                    public void run() {
                        getActivity().sendBroadcast(new Intent(getString(R.string.show_student_list)));
                    }
                };
                DialogCreator.createDialog(getActivity(), "Student was successfully deleted.", "Edit Student", callback);
            }
        });

        etBirthTime.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (tempBirthTime == null) {
                        tempBirthTime = new CustomSimpleDate().createTime(currentStudent.getBirthTime().getHour(), currentStudent.getBirthTime().getMinute(), 0);
                    }

                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            tempBirthTime = new CustomSimpleDate().createTime(selectedHour, selectedMinute, 0);
                            etBirthTime.setText(tempBirthTime.toTimeString());
                        }
                    }, tempBirthTime.getHour(), tempBirthTime.getMinute(), true);
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
                return true;
            }
        });

        etBirthDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    DateDialogFragment dialog = new DateDialogFragment();
                    if (tempBirthDate == null) {
                        tempBirthDate = new CustomSimpleDate().createDate(currentStudent.getBirthDate().getYear(), currentStudent.getBirthDate().getMonth(), currentStudent.getBirthDate().getDay());
                    }
                    dialog.setOnDateSetListener(new DateDialogFragment.OnDateSetLisetener() {
                        @Override
                        public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
                            tempBirthDate = new CustomSimpleDate().createDate(year, monthOfYear + 1, dayOfMonth);
                            etBirthDate.setText(tempBirthDate.toDateString());
                        }
                    }, tempBirthDate);
                    dialog.show(getFragmentManager(), "TAG");
                }
                return true;
            }
        });
    }
}
