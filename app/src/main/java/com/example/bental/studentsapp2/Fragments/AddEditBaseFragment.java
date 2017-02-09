package com.example.bental.studentsapp2.Fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

                //todo set image url
                String errorMessage = validate(etName.getText().toString(),
                        etQuantity.getText().toString());
                if (errorMessage=="") {
                    ShoppingItem item = new ShoppingItem();
                    item.setName(etName.getText().toString());
                    item.setQuantity(Integer.parseInt(etQuantity.getText().toString()));
                    item.setAddedByUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    item.setAddedDate(Helper.getCurrentDate());
                    item.setImageUrl(pictureName);
                    onSave(item);
                    getActivity().onBackPressed();
                } else{
                    Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_LONG).show();
                }

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
                Uri selectedImageUri = data.getData();
                String imagePath = getPath(selectedImageUri);
                pictureName = new File(imagePath).getName();
                //copying image from gallery to local app images path
                Helper.copyFile(imagePath, view.getContext());
            }

            //uploading image to server
            final SpinnerDialog spinner = Helper.showLoader(getFragmentManager());
            File file = Helper.getFile(pictureName, view.getContext());
            Model.instance().uploadPicture(pictureName,
                    Helper.getUriFromFile(file, view.getContext()),
                    new Model.UploadFileListener() {
                        @Override
                        public void onComplete(Uri fileUrl) {
                            Helper.setImageFromStorage(pictureName, view.getContext(), ivProductImage);
                            spinner.dismiss();
                        }
                    }
            );
        } else {
            pictureName = "";
        }
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String validate(String name, String quantity){
        if (name.isEmpty()) {
            return "Please enter a product name.";
        }
        if (quantity.isEmpty()) {
            return "Please enter quantity";
        }
        return "";
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}
