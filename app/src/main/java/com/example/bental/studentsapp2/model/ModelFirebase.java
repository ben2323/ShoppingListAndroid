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
    public void getGroupsForUser(String userId, final Model.GetGroupsForUserListener listener) {
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
                    userGroupKeys.add(/*"-" + */stSnapshot.getKey());
                }
                //getting the whole group object
                groupsRef.orderByKey().startAt(userGroupKeys.get(0))
                        .endAt(userGroupKeys.get(userGroupKeys.size() - 1)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Group> groups = new ArrayList<Group>();
                        for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {
                        Group group = stSnapshot.getValue(Group.class);
                            group.setGroupId(stSnapshot.getKey());
                            groups.add(group);
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

    public void getShoppingItemsByGroupId(String groupId, final Model.GetShoppingItemsByGroupIdListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems").child(groupId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
                for (DataSnapshot stSnapshot : dataSnapshot.getChildren()) {

                    final ShoppingItem shoppingItem = stSnapshot.getValue(ShoppingItem.class);
                    shoppingItem.setItemId(stSnapshot.getKey());
                    getUserById(shoppingItem.getAddedByUserId(), new Model.GetUserByIdListener() {
                        @Override
                        public void onComplete(User user) {
                            shoppingItem.setAddedByUser(user);
                            shoppingItems.add(shoppingItem);
                            if (shoppingItems.size()==dataSnapshot.getChildrenCount()) {
                                listener.onComplete(shoppingItems);
                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onComplete(null);
            }
        });
    }

    public void addUserToGroup(String userId, String groupId) {
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

    public void addNewGroup(Group group) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups");
        myRef.push().setValue(group);
    }

    public void addNewUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(user.getUserId());
        myRef.setValue(user);
    }

    public void addShoppingItem(ShoppingItem shoppingItem, String groupId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems").child(groupId);
        myRef.push().setValue(shoppingItem);
    }

    private void getUserById(String userUid, final Model.GetUserByIdListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(String.valueOf(userUid));
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listener.onComplete(dataSnapshot.getValue(User.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}


