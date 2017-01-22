package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.content.Intent;
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

public abstract class CustomBaseAdapter<T> extends BaseAdapter {
    protected static LayoutInflater inflater = null;
    protected ArrayList<T> list;
    protected Context context;
    public CustomBaseAdapter(Context context, ArrayList<T> list){
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override

    abstract public View getView(int i, View view, ViewGroup viewGroup);
}
