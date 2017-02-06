package com.example.bental.studentsapp2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ben on 1/13/2017.
 */

public class Group implements Serializable{
    String groupId;
    String key;
    String groupName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String groupImageUrl;
    HashMap<String,ShoppingItem> shoppingItems;
    public Group(){
        this.groupImageUrl = "";
        this.shoppingItems = new LinkedHashMap<String,ShoppingItem>();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public HashMap<String,ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(HashMap<String,ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

/*
    public String getGroupKey() {
        return groupKey;
    }
*/

/*    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
*/
    public String getGroupImageUrl() {
        return groupImageUrl;
    }

    public void setGroupImageUrl(String groupImageUrl) {
        this.groupImageUrl = groupImageUrl;
    }

}
