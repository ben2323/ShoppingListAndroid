package com.example.bental.studentsapp2.Fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.DialogCreator;
import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class AddEditBaseFragment extends BaseFragment {
    String groupId;
    EditText etName;
    EditText etQuantity;
    ImageView btnSave;
    ImageView btnCancel;
    ImageView ivProductImage;
    View view;
    Fragment fragment = this;
    String pictureName = "";
    // LayoutInflater inflater;

    public AddEditBaseFragment() {
        // Required empty public constructor
    }

    abstract public void onSave(ShoppingItem item);

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_edit, container, false);
        super.onCreateView(inflater,container,savedInstanceState);
        btnSave = (ImageView) view.findViewById(R.id.btnAdd);
        btnCancel = (ImageView) view.findViewById(R.id.btnCancel);
        etQuantity = (EditText) view.findViewById(R.id.etQuantity);
        etName = (EditText) view.findViewById(R.id.etPasswordConfirm);
        ivProductImage = (ImageView) view.findViewById(R.id.btnAddPicture);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingItem item = new ShoppingItem();
                item.setName(etName.getText().toString());
                item.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
                item.setAddedByUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                item.setAddedDate(Helper.getCurrentDate());
                item.setImageUrl(pictureName);
                //todo set image url
                onSave(item);
                getActivity().onBackPressed();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setTitle("Select a Picture");
                View dialogView = inflater.inflate(R.layout.image_picker_dialog, null);
                alertDialog.setView(dialogView);

                TextView tvFromCamera = (TextView) dialogView.findViewById(R.id.tvFromCamera);
                TextView tvFromGallery = (TextView) dialogView.findViewById(R.id.tvFromGallery);

                tvFromCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pictureName = Helper.capturePicture(etName.getText().toString(), view.getContext(), fragment, 1);
                        alertDialog.dismiss();
                    }
                });
                tvFromGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Helper.capturePicture(etName.getText().toString(), view.getContext(), fragment, 2);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            if (requestCode == 1) {
                //image taken from camera
            } else if (requestCode == 2) {
                //image taken from gallery
                String imagePath = data.getData().toString() + ".png";
                //copying image from gallery to local app images path
                Helper.copyFile(imagePath, view.getContext());
            }

            //uploading image to server
            File file = Helper.getFile(pictureName, view.getContext());
            Model.instance().uploadPicture(pictureName,
                    Helper.getUriFromFile(file, view.getContext()),
                    new Model.UploadFileListener() {
                        @Override
                        public void onComplete(Uri fileUrl) {
                            Helper.setImageFromStorage(pictureName, view.getContext(), ivProductImage);
                        }
                    }
            );
        } else {
            pictureName = "";
        }
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
