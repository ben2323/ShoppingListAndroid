package com.example.bental.studentsapp2.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.bental.studentsapp2.DialogCreator;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;
import com.example.bental.studentsapp2.model.Student;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditFragment extends BaseFragment {
    String groupId;

    EditText etName;
    EditText etQuantity;

    Student currentStudent;


    public AddEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);


        ImageView btnSave = (ImageView) view.findViewById(R.id.btnAdd);
        ImageView btnCancel = (ImageView) view.findViewById(R.id.btnCancel);


        etQuantity = (EditText) view.findViewById(R.id.etQuantity);
        etName = (EditText) view.findViewById(R.id.etPasswordConfirm);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingItem item = new ShoppingItem();
                item.setName(etName.getText().toString());
                item.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
                item.setAddedByUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                item.setAddedDate(getCurrentDate());
                //item.setImageUrl();
                Model.instance().addShoppingItem(item,groupId);
                getActivity().onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
/*        final int currentProductIndex = this.getArguments().getInt(getString(R.string.current_product_index));
        if (currentProductIndex != -1) {//edit mode
            getActivity().setTitle("Edit Student");
            //prepareViewsForEditMode(currentProductIndex, btnDelete);
        } else {//add mode
            getActivity().setTitle("Add Student");
            btnCancel.setVisibility(View.INVISIBLE);
            //btnDelete.setVisibility(View.INVISIBLE);
        }*/


        //onClick listeners




        return view;
    }

    private void onSave(final int currentStudentIndex) {
        String dialogMessage = "";
        String dialogTitle = "";
/*        ShoppingItem shoppingItem = new ShoppingItem(
                etName.getText().toString(),
                etQuantity.getText().toString(),
                1,
                tempBirthDate == null ? currentStudent.getBirthDate() : tempBirthDate,
                tempBirthTime == null ? currentStudent.getBirthTime() : tempBirthTime
        );*/
        if (currentStudentIndex != -1) {
            dialogMessage = "Student details were successfully updated.";
            dialogTitle = "Edit Student";

            //Model.instance().editStudent(currentStudentIndex, student);
        } else {
            dialogMessage = "Student was successfully added.";
            dialogTitle = "Add Student";
            //Model.instance().addStudent(student);
        }

        Runnable callback = new Runnable() {
            public void run() {
                //getActivity().sendBroadcast(new Intent(getString(R.string.show_student_list)));
            }
        };
        DialogCreator.createDialog(getActivity(), dialogMessage, dialogTitle, callback);
    }

    private void prepareViewsForEditMode(int currentStudentIndex, Button btnDelete) {

/*        currentStudent = Model.instance().getAllStudents().get(currentStudentIndex);
        etName.setText(currentStudent.getName());*/
        //etId.setText(String.valueOf(currentStudent.getId()));


/*        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance().deleteStudent(currentStudent);
                Runnable callback = new Runnable() {
                    public void run() {
                      //  getActivity().sendBroadcast(new Intent(getString(R.string.show_student_list)));
                    }
                };
                DialogCreator.createDialog(getActivity(), "Student was successfully deleted.", "Edit Student", callback);
            }
        });*/

        //etBirthTime.setOnTouchListener(new View.OnTouchListener() {

/*            @Override
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
        });*/

/*        etBirthDate.setOnTouchListener(new View.OnTouchListener() {
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
        });*/
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
