package com.example.bental.studentsapp2.model;

import com.example.bental.studentsapp2.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 12/12/2016.
 */

public class Model {
    private static final Model instance = new Model();

    public static Model instance(){
        return instance;
    }

    private ModelFirebase modelFirebase = new ModelFirebase();
    private Model(){
    }
    public void addShoppingItem(ShoppingItem shoppingItem, String groupId){
        modelFirebase.addShoppingItem(shoppingItem, groupId);
    }

    public void getShoppingItemsByGroupId(String groupId, GetShoppingItemsByGroupIdListener listener){
        modelFirebase.getShoppingItemsByGroupId(groupId,listener);
    }

    public void addUserToGroup(String userId, String groupId){
        modelFirebase.addUserToGroup(userId, groupId);
        //modelFirebase.addShoppingItem(shoppingItem, groupId);
    }

    public void addNewUser(User user){
        //modelSql.addStudent(student);
        modelFirebase.addNewUser(user);
    }

    public void createNewGroup(Group group){
        //modelSql.addStudent(student);
        modelFirebase.addNewGroup(group);
    }

    public void getGroupsForUser(String userId, GetGroupsForUserListener listener){
        modelFirebase.getGroupsForUser(userId,listener);
    }

    public void getUserById(String userUid, final Model.GetUserByIdListener listener){
        //modelFirebase.getUserById(userUid,listener);
    }

    public interface GetShoppingItemListener {
        void onComplete(ShoppingItem shoppingItem);
    }

    public interface GetShoppingItemsByGroupIdListener{
        void onComplete(List<ShoppingItem> shoppingItems);
    }

    public interface GetGroupsForUserListener{
        void onComplete(List<Group> groups);
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
    }


}
