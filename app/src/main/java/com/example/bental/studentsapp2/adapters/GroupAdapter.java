package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Group;

import java.util.HashMap;

/**
 * Created by ben on 1/13/2017.
 */

public class GroupAdapter extends CustomBaseAdapter<Group> {
    public GroupAdapter(Context context, HashMap<String, Group> list) {
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
        //ImageView groupImage = (ImageView) vi.findViewById(R.id.groupImage);
        ImageView deleteItem = (ImageView) vi.findViewById(R.id.deleteItem);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove user from group
                Intent intent = new Intent(context.getString(R.string.remove_user_from_group));
                intent.putExtra(context.getString(R.string.group_id), hashMap.get(keys[index]).getGroupId());
                intent.putExtra(context.getString(R.string.user_id), Helper.getCurrentUser().getUid());
                context.sendBroadcast(intent);
            }
        });
        groupName.setText(hashMap.get(keys[index]).getGroupName());
        //Helper.setImageFromStorage(hashMap.get(keys[index]).getGroupImageUrl(), context, groupImage);

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getString(R.string.show_group));
                intent.putExtra(context.getString(R.string.group_id), hashMap.get(keys[index]).getGroupId());

                intent.putExtra(context.getString(R.string.shopping_items), hashMap.get(keys[index]));
                context.sendBroadcast(intent);
            }
        });
        return vi;
    }
}
