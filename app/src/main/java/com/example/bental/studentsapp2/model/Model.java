package com.example.bental.studentsapp2.model;

import com.example.bental.studentsapp2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 12/12/2016.
 */

public class Model {
    private ArrayList<Student> studentList = new ArrayList<Student>();
    final private static Model instance = new Model();
    private Model(){
        for (int i =0; i<10; i++) {
            studentList.add(new Student("aaa_" + i, "bbbbb_" + i, String.valueOf(i), "0344444",false, R.mipmap.ic_launcher,
                    new CustomSimpleDate().createDate(2016,12,30), new CustomSimpleDate().createTime(22,30,10)));
        }
    }
    public static Model instance(){
        return instance;
    }
    public ArrayList<Student> getAllStudents(){
        return studentList;
    }

    public void addStudent(Student student){
        student.setImage(R.mipmap.ic_launcher);
        studentList.add(student);
    }

    public void editStudent(int index, Student student){
        studentList.get(index).setChecked(student.isChecked());
        studentList.get(index).setName(student.getName());
        studentList.get(index).setAddress(student.getAddress());
        studentList.get(index).setId(student.getId());
        studentList.get(index).setPhone(student.getPhone());
        studentList.get(index).setBirthDate(student.getBirthDate());
        studentList.get(index).setBirthTime(student.getBirthTime());
    }

    public void deleteStudent(Student student){
        studentList.remove(student);
    }
}
