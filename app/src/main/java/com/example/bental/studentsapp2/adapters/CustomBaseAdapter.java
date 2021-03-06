package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


import java.util.HashMap;


/**
 * Created by Ben on 07/12/2016.
 */

public abstract class CustomBaseAdapter<T> extends BaseAdapter {
    private float x1, x2;
    protected static LayoutInflater inflater = null;
    protected HashMap<String,T> hashMap;
    protected String[] keys = new String[]{};
    protected Context context;
    public CustomBaseAdapter(Context context, HashMap<String,T> hashMap){
        this.hashMap = hashMap;
        this.context = context;
        this.keys = hashMap.keySet().toArray(new String[]{});
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hashMap.size();
    }

    @Override
    public Object getItem(int i) {
        return hashMap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    abstract public View getView(int i, View view, ViewGroup viewGroup);

}
