package com.example.bental.studentsapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.bental.studentsapp2.model.Student;

import java.util.ArrayList;


/**
 * Created by Ben on 07/12/2016.
 */

public class CustomBaseAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    ArrayList<Student> studentList;
    Context context;
    public CustomBaseAdapter(Context context, ArrayList<Student> studentList){
        this.studentList = studentList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int i) {
        return studentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int index= i;
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.list_item, null);
        }
        TextView studentName = (TextView) vi.findViewById(R.id.studentName);
        TextView studentId = (TextView) vi.findViewById(R.id.studentId);
        ImageView iv = (ImageView) vi.findViewById(R.id.studentImage);
        studentName.setText(studentList.get(i).getName());
        studentId.setText(String.valueOf( studentList.get(i).getId()));
        iv.setImageResource(studentList.get(i).getImage());
        CheckBox chkbxCheck = (CheckBox)vi.findViewById(R.id.chkbxChecked);
        chkbxCheck.setChecked(studentList.get(i).isChecked());

        chkbxCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.get(index).setChecked(((CheckBox) view).isChecked());
            }
        });
        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow back button
                Intent intent = new Intent(context.getString(R.string.show_student_details));
                intent.putExtra(context.getString(R.string.current_student_index), index);
                context.sendBroadcast(intent);

            }
        });
        return vi;
    }
}
