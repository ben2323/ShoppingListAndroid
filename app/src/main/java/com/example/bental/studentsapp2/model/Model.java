package com.example.bental.studentsapp2.model;

import android.app.FragmentManager;
import android.net.Uri;

import com.example.bental.studentsapp2.*;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ben on 12/12/2016.
 */

public class Model {
    private static final Model instance = new Model();

    public static Model instance() {
        return instance;
    }

    private ModelFirebase modelFirebase = new ModelFirebase();
    private ModelSql modelSql = new ModelSql();

    private Model() {
    }

    public void addShoppingItem(ShoppingItem shoppingItem, String groupId) {
        modelFirebase.addShoppingItem(shoppingItem, groupId);
        modelSql.addShoppingItem(shoppingItem, groupId);
    }

    public void editShoppingItem(ShoppingItem shoppingItem, String groupId) {
        modelFirebase.editShoppingItem(shoppingItem, groupId);
        modelSql.editShoppingItem(shoppingItem,groupId);
    }

    public void getShoppingItemsByGroupId(FragmentManager fm, String groupId, GetShoppingItemsByGroupIdListener listener) {
        modelFirebase.getShoppingItemsByGroupId(fm, groupId, listener);
        ArrayList<ShoppingItem> items = modelSql.getShoppingItemsByGroupId(groupId);
    }

    public void addUserToGroup(String userId, String groupId, AddUserToGroupListener listener) {
        modelFirebase.addUserToGroup(userId, groupId, listener);
        modelSql.addUserToGroup(userId, groupId);
    }

    public void addNewUser(User user) {
        modelFirebase.addNewUser(user);
        modelSql.addNewUser(user);
    }

    public void createNewGroup(Group group, String userId) {
        modelFirebase.addNewGroup(group, userId);
        modelSql.addNewGroup(group, userId);
    }

    public void removeShoppingItemByGroupId(String groupId, String itemId) {
        modelFirebase.removeShoppingItemByGroupId(groupId, itemId);
    }

    public void removeGroupByUserId(String userId, String groupId) {
        modelFirebase.removeGroupByUserId(userId, groupId);
        modelSql.addUserToGroup(userId,groupId);
    }

    public void getGroupsForUser(FragmentManager fm, String userId, GetGroupsByUserIdListener listener) {
        modelFirebase.getGroupsByUserId(fm, userId, listener);
        modelSql.getGroupsByUserId(userId);
    }
    public void getUserByEmail(String email, final Model.GetUserByIdListener listener) {
        modelFirebase.getUserByEmail(email, listener);

    }
    public void getUserById(String userUid, final Model.GetUserByIdListener listener) {
        //modelFirebase.getUserById(userUid,listener);
    }
    public void uploadPicture(String fileName, Uri file, final Model.UploadFileListener listener){
        modelFirebase.uploadPicture(fileName,file,listener);
    }
    public void downloadPicture(File file, final Model.DownloadFileListener listener){
        modelFirebase.downloadPicture(file,listener);
    }
    public interface GetShoppingItemListener {
        void onComplete(ShoppingItem shoppingItem);
    }

    public interface GetShoppingItemsByGroupIdListener {
        void onComplete(HashMap<String, ShoppingItem> shoppingItems);
    }

    public interface GetGroupsByUserIdListener {
        void onComplete(HashMap<String, Group> groups);
    }
    public interface UploadFileListener {
        void onComplete(Uri fileUrl);
    }
    public interface DownloadFileListener {
        void onComplete(File file);
    }

    public interface GetUserByIdListener {
        void onComplete(User user);
    }

    public interface AddUserToGroupListener {
        void onComplete(Boolean success);
    }


}
