package com.example.bental.studentsapp2.model;

import android.graphics.PorterDuff;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben on 1/14/2017.
 */

public class ModelFirebase {
    public void addShoppingItem(ShoppingItem item, int groupId){
        //student.setImage(R.mipmap.ic_launcher);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups").child(String.valueOf(groupId))
                .child("shoppingItems");
        myRef.setValue(item);
    }

    public void getGroupsForUser(String userId, final Model.GetGroupsForUserListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(String.valueOf(userId))
                .child("registeredGroups");
        final DatabaseReference groupsRef = database.getReference("groups");
        myRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> userGroupKeys = new ArrayList<String>();
//getting keys only
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                    userGroupKeys.add("-" + stSnapshot.getKey());
                }
                //getting the whole group object
                groupsRef.orderByKey().startAt(userGroupKeys.get(0))
                        .endAt(userGroupKeys.get(userGroupKeys.size()-1)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Group> groups = new ArrayList<Group>();
                        for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                            groups.add(stSnapshot.getValue(Group.class));
                        }
                        listener.onComplete(groups);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public void getShoppingItemsByGroupId(int groupId, final Model.GetShoppingItemsByGroupIdListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups").child(String.valueOf(groupId))
                .child("shoppingItems");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                    ShoppingItem shoppingItem = stSnapshot.getValue(ShoppingItem.class);
                    shoppingItems.add(shoppingItem);
                }
                listener.onComplete(shoppingItems);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public void addUserToGroup(String userId, String groupId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(userId)
                .child("registeredGroups").child(groupId);
        myRef.setValue(true).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.getMessage();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                int a = 6;
            }
        });
    }

    public void addNewGroup(Group group){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups");
        myRef.push().setValue(group);
    }

    public void addNewUser(User user){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getUserId());
        myRef.setValue(user);
    }

}


