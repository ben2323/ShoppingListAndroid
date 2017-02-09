package com.example.bental.studentsapp2;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.bental.studentsapp2.Fragments.AddNewShoppingItemFragment;
import com.example.bental.studentsapp2.Fragments.CreateNewGroupFragment;
import com.example.bental.studentsapp2.Fragments.EditShoppingItemFragment;
import com.example.bental.studentsapp2.Fragments.InviteFragment;
import com.example.bental.studentsapp2.Fragments.LoginFragment;
import com.example.bental.studentsapp2.Fragments.ProductListFragment;
import com.example.bental.studentsapp2.Fragments.RegisterNewUserFragment;
import com.example.bental.studentsapp2.Fragments.UserGroupsFragment;
import com.example.bental.studentsapp2.model.Group;
import com.example.bental.studentsapp2.model.Model;
import com.example.bental.studentsapp2.model.ShoppingItem;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {
    private BroadcastReceiver mBroadcastReceiver;
    private static String ADD_PRODUCT = "addProductFragment";
    private static String EDIT_PRODUCT = "editProductFragment";
    private static String PRODUCT_LIST = "productListFragment";
    private static String CREATE_NEW_GROUP = "createNewGrop";
    private static String REGISTER_NEW_USER = "registerNewUser";
    private static String LOGIN = "login";
    private static String USER_GROUPS = "userGroups";
    private static String JOIN_GROUP = "joinGroup";
    LinearLayout linearTopBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        linearTopBar = (LinearLayout)findViewById(R.id.linearTopBar);
        linearTopBar.setVisibility(View.VISIBLE);
        final TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        final ImageView ivLogout = (ImageView)findViewById(R.id.ivLogout);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                onShowLogin();
            }
        });
        onShowUserGroups();


        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(getString(R.string.default_web_client_id))) {
                    onShowStudentDetails(intent);
                } else if (intent.getAction().equals(getString(R.string.show_login))) {
                    onShowLogin();
                } else if (intent.getAction().equals(getString(R.string.show_register))) {
                    onShowRegisterNewUser();
                } else if (intent.getAction().equals(getString(R.string.show_user_groups))) {
                    onShowUserGroups();
                } else if (intent.getAction().equals(getString(R.string.show_create_group))) {
                    onShowCreateNewGroup();
                } else if (intent.getAction().equals(getString(R.string.show_join_group))) {
                    onShowAddUserToGroup(intent);
                } else if (intent.getAction().equals(getString(R.string.show_group))) {
                    onShowGroupProductList(intent);
                } else if (intent.getAction().equals(getString(R.string.show_add_shopping_item))) {
                    onShowAddShoppingItem(intent);
                } else if (intent.getAction().equals(getString(R.string.remove_item))) {
                    onRemoveItem(intent);
                } else if (intent.getAction().equals(getString(R.string.remove_user_from_group))) {
                    onRemoveUserFromGroup(intent);
                } else if (intent.getAction().equals(getString(R.string.show_edit_shopping_item))) {
                    onShowEditShoppingItem(intent);
                } else if (intent.getAction().equals(getString(R.string.show_capture_image_activity))) {
                    onShowCapturePictureActivity(intent);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.show_group));
        intentFilter.addAction(getString(R.string.show_add_shopping_item));
        intentFilter.addAction(getString(R.string.show_user_groups));
        intentFilter.addAction(getString(R.string.show_login));
        intentFilter.addAction(getString(R.string.show_register));
        intentFilter.addAction(getString(R.string.show_create_group));
        intentFilter.addAction(getString(R.string.show_join_group));
        intentFilter.addAction(getString(R.string.remove_user_from_group));
        intentFilter.addAction(getString(R.string.remove_item));
        intentFilter.addAction(getString(R.string.show_edit_shopping_item));
        intentFilter.addAction(getString(R.string.show_capture_image_activity));
        this.registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private void onRemoveItem(Intent intent) {
        String itemId = intent.getStringExtra(getString(R.string.item_id));
        String groupId = intent.getStringExtra(getString(R.string.group_id));
        Model.instance().removeShoppingItemByGroupId(groupId, itemId);
    }

    private void onRemoveUserFromGroup(Intent intent) {
        String userId = intent.getStringExtra(getString(R.string.user_id));
        String groupId = intent.getStringExtra(getString(R.string.group_id));
        Model.instance().removeGroupByUserId(userId, groupId);
    }

    private void onShowAddUserToGroup(Intent intent) {
        FragmentManager fm = getFragmentManager();
        String groupId = intent.getStringExtra(getString(R.string.group_id));
        InviteFragment fragment = new InviteFragment();
        fragment.setGroupId(groupId);

        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, LOGIN);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    private void onShowLogin() {
        FragmentManager fm = getFragmentManager();
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, LOGIN);
        ftr.commit();

    }

    private void onShowUserGroups() {
        linearTopBar.setVisibility(View.VISIBLE);
        FragmentManager fm = getFragmentManager();
        UserGroupsFragment fragment = new UserGroupsFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, USER_GROUPS);
        ftr.commit();

    }

    private void onShowCreateNewGroup() {
        FragmentManager fm = getFragmentManager();
        CreateNewGroupFragment fragment = new CreateNewGroupFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, CREATE_NEW_GROUP);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    private void onShowRegisterNewUser() {
        FragmentManager fm = getFragmentManager();

        RegisterNewUserFragment fragment = new RegisterNewUserFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, REGISTER_NEW_USER);
        ftr.addToBackStack(null);
        ftr.commit();

    }

    private void onShowGroupProductList(Intent intent) {

        //todo get the whole group object and show the name of the group in the title
        ProductListFragment fragment = new ProductListFragment();
        Group group = (Group) intent
                .getSerializableExtra(getString(R.string.shopping_items));
        fragment.setCurrentGroup(group);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, PRODUCT_LIST);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    private void onShowStudentDetails(Intent intent) {
/*        ProductDetailsFragment studentDetailFragment = new ProductDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        studentDetailFragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, studentDetailFragment, STUDENT_DETAILS);
        if (getFragmentManager().findFragmentByTag(ADD_EDIT_STUDENT) == null) {
            ftr.addToBackStack(null);
        }

        ftr.commit();*/

    }

    private void onShowAddShoppingItem(Intent intent) {
        AddNewShoppingItemFragment fragment = new AddNewShoppingItemFragment();
        String groupId = intent.getStringExtra(getString(R.string.group_id));
        fragment.setGroupId(groupId);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, ADD_PRODUCT);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    private void onShowEditShoppingItem(Intent intent) {
        EditShoppingItemFragment fragment = new EditShoppingItemFragment();
        String groupId = intent.getStringExtra(getString(R.string.group_id));
        ShoppingItem shoppingItem = (ShoppingItem) intent
                .getSerializableExtra(getString(R.string.current_item));
        fragment.setCurrentShoppingItem(shoppingItem);
        fragment.setGroupId(groupId);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, EDIT_PRODUCT);
        ftr.addToBackStack(null);
        ftr.commit();
    }

    private void onShowEditStudent(Intent intent) {
/*        AddEditFragment fragment = new AddEditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.current_student_index), intent.getIntExtra(getString(R.string.current_student_index), -1));
        fragment.setArguments(bundle);
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.mainContainer, fragment, ADD_EDIT_STUDENT);
        ftr.addToBackStack(null);
        ftr.commit();*/
    }

    private void onShowCapturePictureActivity(Intent intent) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Helper.setImageFromStorage(picturePath,this);
            Log.d("CameraDemo", "Pic saved");
            onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
