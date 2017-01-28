package com.example.bental.studentsapp2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bental.studentsapp2.R;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;
import com.example.bental.studentsapp2.model.User;

import java.util.ArrayList;

/**
 * Created by ben on 1/13/2017.
 */

public class ShoppingItemAdapter extends CustomBaseAdapter<ShoppingItem> {
    public ShoppingItemAdapter(Context context, ArrayList<ShoppingItem> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final int index = i;
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.product_list_item, null);
        }
        TextView productName = (TextView) vi.findViewById(R.id.productName);
        TextView addOnDate = (TextView) vi.findViewById(R.id.addedOnDate);
        final TextView addedBy = (TextView) vi.findViewById(R.id.addedBy);
        ImageView productImage = (ImageView) vi.findViewById(R.id.productImage);
        productName.setText(list.get(i).getName());
        addedBy.setText(list.get(i).getAddedByUser().getFirstName() + " " + list.get(i).getAddedByUser().getLastName());
        addOnDate.setText(list.get(i).getAddedDate().toString());

        //todo set image url
        //iv.setImageResource(list.get(i).getImageUrl());

        vi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow back button
                Intent intent = new Intent(context.getString(R.string.show_edit_product));
                intent.putExtra(context.getString(R.string.show_edit_product), index);
                context.sendBroadcast(intent);

            }
        });
        return vi;

    }
}
