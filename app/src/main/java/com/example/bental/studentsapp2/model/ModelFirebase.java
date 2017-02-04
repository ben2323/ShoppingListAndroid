package com.example.bental.studentsapp2.model;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ben on 1/14/2017.
 */

public class ModelFirebase {
    public void getGroupsByUserId(final String userId, final Model.GetGroupsByUserIdListener listener) {
        final HashMap<String, Group> groups = new HashMap<String, Group>();
        // List the names of all Mary's groups
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();

        ref.child("users/" + userId + "/registeredGroups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // for each group, fetch the name and print it
                final String groupKey = snapshot.getKey();
                ref.child("groups/" + groupKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Group group = snapshot.getValue(Group.class);
                        group.setGroupId(groupKey);
                        boolean isNewItem = true;
                        groups.put(groupKey, group);

                        listener.onComplete(groups);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getShoppingItemsByGroupId(String groupId, final Model.GetShoppingItemsByGroupIdListener listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        final HashMap<String, ShoppingItem> shoppingItemHashMap = new HashMap<>();
        ref.child("shoppingItems/" + groupId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // for each group, fetch the name and print it
                final String key = snapshot.getKey();
                final ShoppingItem shoppingItem = snapshot.getValue(ShoppingItem.class);
                shoppingItem.setItemId(key);

                getUserById(shoppingItem.addedByUserId, new Model.GetUserByIdListener() {
                    @Override
                    public void onComplete(User user) {
                        shoppingItem.setAddedByUser(user);
                        shoppingItemHashMap.put(key, shoppingItem);
                        listener.onComplete(shoppingItemHashMap);
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                final ShoppingItem shoppingItem = dataSnapshot.getValue(ShoppingItem.class);
                shoppingItemHashMap.put(shoppingItem.getItemId(),shoppingItem);
                listener.onComplete(shoppingItemHashMap);
                Log.d("ss", "ggg");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                shoppingItemHashMap.remove(dataSnapshot.getKey());
                Log.d("ss", "ggg");
                listener.onComplete(shoppingItemHashMap);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d("ss", "ggg");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addUserToGroup(final String userId, final String groupId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //adds groupId node in the users node
        DatabaseReference registeredGroupsRef = database.getReference("users").child(userId)
                .child("registeredGroups").child(groupId);
        registeredGroupsRef.setValue(true);
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

    public void editShoppingItem(ShoppingItem shoppingItem, String groupId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingItems").child(groupId)
                .child(shoppingItem.getItemId());
        myRef.setValue(shoppingItem);
    }

    public void removeShoppingItemByGroupId(String groupId, String itemId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupItemsRef = database.getReference("shoppingItems").child(groupId)
                .child(itemId);
        groupItemsRef.removeValue();
    }

    public void removeGroupByUserId(String userId, String groupId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userGroupRef = database.getReference("users").child(userId)
                .child("registeredGroups").child(groupId);
        userGroupRef.removeValue();
    }

    public void uploadPicture(String fileName, Uri file, final Model.UploadFileListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://android-shopping-list-d852d.appspot.com")
                .child("images").child(fileName);
        storageRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                listener.onComplete(taskSnapshot.getDownloadUrl());
            }
        });
    }

    public void downloadPicture(final File destFile, final Model.DownloadFileListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://android-shopping-list-d852d.appspot.com")
                .child("images").child(destFile.getName());

        storageRef.getFile(destFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                listener.onComplete(destFile);
            }
        });

    }

    private void getUserById(String userUid, final Model.GetUserByIdListener listener) {
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


