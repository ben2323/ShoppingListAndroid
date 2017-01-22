package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Group;

import java.util.ArrayList;

/**
 * Created by ben on 1/13/2017.
 */

public class GroupAdapter extends CustomBaseAdapter<Group> {
    public GroupAdapter(Context context, ArrayList<Group> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int index = i;
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.group_list_item, null);
        }
        TextView groupName = (TextView) vi.findViewById(R.id.groupName);
        ImageView iv = (ImageView) vi.findViewById(R.id.groupImage);
        groupName.setText(list.get(i).getGroupName());
        //todo set image url
        //iv.setImageResource(list.get(i).getImage());

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow back button
                Intent intent = new Intent(context.getString(R.string.show_group));
                intent.putExtra(context.getString(R.string.show_group), index);
                context.sendBroadcast(intent);

            }
        });
        return vi;

    }


}
