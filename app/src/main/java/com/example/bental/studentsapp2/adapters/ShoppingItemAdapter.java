package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.Helper;
import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.ShoppingItem;

import java.util.HashMap;

/**
 * Created by ben on 1/13/2017.
 */

public class ShoppingItemAdapter extends CustomBaseAdapter<ShoppingItem> {
    String groupId;

    public ShoppingItemAdapter(Context context, HashMap<String, ShoppingItem> list, String groupId) {
        super(context, list);
        this.groupId = groupId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //super.updateKeysArray();
        final int index = i;
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.product_list_item, null);
        }
        super.setSwipeEvent(vi);
        TextView productName = (TextView) vi.findViewById(R.id.productName);
        TextView addOnDate = (TextView) vi.findViewById(R.id.addedOnDate);
        ImageView productImage = (ImageView) vi.findViewById(R.id.productImage);
        ImageView deleteItem = (ImageView) vi.findViewById(R.id.deleteItem);
        final TextView addedBy = (TextView) vi.findViewById(R.id.addedBy);

        productName.setText(hashMap.get(keys[index]).getName());
        addedBy.setText(hashMap.get(keys[index]).getAddedByUser().getFirstName() + " " + hashMap.get(keys[index]).getAddedByUser().getLastName());
        addOnDate.setText(hashMap.get(keys[index]).getAddedDate().toString());
        Helper.setImageFromStorage(hashMap.get(keys[index]).getImageUrl(), context, productImage);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getString(R.string.remove_item));
                intent.putExtra(context.getString(R.string.group_id), groupId);
                intent.putExtra(context.getString(R.string.item_id), hashMap.get(keys[index]).getItemId());
                context.sendBroadcast(intent);
            }
        });

        //todo set image url
        //iv.setImageResource(list.get(i).getImageUrl());

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow back button
                Intent intent = new Intent(context.getString(R.string.show_edit_shopping_item));
                intent.putExtra(context.getString(R.string.group_id), groupId);
                intent.putExtra(context.getString(R.string.current_item), hashMap.get(keys[index]));
                context.sendBroadcast(intent);
            }
        });
        return vi;

    }
}
