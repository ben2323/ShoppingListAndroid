package com.example.bental.studentsapp2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.R.attr.version;

/**
 * Created by ben on 1/14/2017.
 */

public class ModelSql {
    SQLiteDatabase dbWritable, dbReadable;

    public ModelSql() {
        DatabaseHelper helper = new DatabaseHelper(MyAppContext.getAppContext());
        dbWritable = helper.getWritableDatabase();
        dbReadable = helper.getReadableDatabase();
    }

    public ArrayList<Group> getGroupsByUserId(final String userId) {
        String query = "SELECT g.groupId, groupName FROM userGroups ug" +
                " INNER JOIN groups g ON ug.groupId = g.groupId WHERE ug.userId=?";

        Cursor cursor = dbReadable.rawQuery(query, new String[]{userId});
        ArrayList<Group> groups = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Group group = new Group();
                group.setGroupName(cursor.getString(1));
                group.setGroupId(cursor.getString(0));
                groups.add(group);
            } while (cursor.moveToNext());
        }
        return groups;
    }

    public ArrayList<ShoppingItem> getShoppingItemsByGroupId(String groupId) {
        String query = "SELECT * FROM shoppingItems si" +
                " INNER JOIN users u ON u.userId = si.addedByUserId WHERE groupId=?";

        Cursor cursor = dbReadable.rawQuery(query, new String[]{groupId});
        ArrayList<ShoppingItem> shoppingItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                ShoppingItem shoppingItem = new ShoppingItem();
                shoppingItem.setItemId(cursor.getString(0));
                shoppingItem.setName(cursor.getString(1));
                shoppingItem.setQuantity(cursor.getInt(2));
                shoppingItem.setAddedDate(cursor.getString(3));
                shoppingItem.setAddedByUserId(cursor.getString(4));
                shoppingItem.setImageUrl(cursor.getString(5));
                user.setUserId(cursor.getString(6));
                user.setFirstName(cursor.getString(7));
                user.setLastName(cursor.getString(8));
                user.setEmail(cursor.getString(9));
                shoppingItem.setAddedByUser(user);
                shoppingItems.add(shoppingItem);
            } while (cursor.moveToNext());
        }
        return shoppingItems;
    }

    public void addUserToGroup(final String userId, final String groupId) {
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("groupId", groupId);

        long rowId = dbWritable.insert("userGroups", "", values);

        if (rowId <= 0) throw new SQLException("Failed to insert user to group");

    }

    public void addNewGroup(Group group, String userId) {
        ContentValues values = new ContentValues();

        values.put("groupId", group.getGroupId());
        values.put("groupName", group.getGroupName());

        long rowId = dbWritable.insert("groups", "", values);

        addUserToGroup(userId, group.getGroupId());

        if (rowId <= 0) throw new SQLException("Failed to insert new group");
    }

    public void addNewUser(User user) {
        ContentValues values = new ContentValues();

        values.put("userId", user.getUserId());
        values.put("firstName", user.getLastName());
        values.put("lastName", user.getLastName());
        values.put("email", user.getEmail());

        long rowId = dbWritable.insert("users", "", values);
        if (rowId <= 0) throw new SQLException("Failed to insert new user");
    }

    public void addShoppingItem(ShoppingItem shoppingItem, String groupId) {
        ContentValues values = new ContentValues();

        values.put("itemId", shoppingItem.getItemId());
        values.put("addedByUserId", shoppingItem.getAddedByUserId());
        values.put("addedDate", shoppingItem.getAddedDate());
        values.put("imageUrl", shoppingItem.getImageUrl());
        values.put("name", shoppingItem.getName());
        values.put("quantity", shoppingItem.getQuantity());
        values.put("groupId", groupId);

        long rowId = dbWritable.insert("shoppingItems", "", values);
        if (rowId <= 0) throw new SQLException("Failed to insert new shopping item");
    }

    public void editShoppingItem(ShoppingItem shoppingItem, String groupId) {
        ContentValues values = new ContentValues();
        values.put("addedByUserId", shoppingItem.getAddedByUserId());
        values.put("addedDate", shoppingItem.getAddedDate());
        values.put("imageUrl", shoppingItem.getImageUrl());
        values.put("name", shoppingItem.getName());
        values.put("quantity", shoppingItem.getQuantity());

        long rowId = dbWritable.
                update("shoppingItems", values, "itemId=?", new String[]{shoppingItem.getItemId()});
        if (rowId <= 0) throw new SQLException("Failed to edit shopping item");
    }

    public void removeShoppingItemByGroupId(String groupId, String itemId) {
        long rowId = dbWritable.
                delete("shoppingItems", "itemId=?", new String[]{itemId});
        if (rowId <= 0) throw new SQLException("Failed to edit shopping item");
    }

    public void removeGroupByUserId(String userId, String groupId) {
        long rowId = dbWritable.
                delete("userGroups", "userId=? AND groupId=?", new String[]{userId, groupId});
        if (rowId <= 0) throw new SQLException("Failed to remove user from group");
    }
}
