package com.example.bental.studentsapp2.model;

import java.util.Date;

/**
 * Created by ben on 1/13/2017.
 */

public class ShoppingItem {
    int itemId;
    String name;
    int quantity;
    String imageUrl;
    User addedBy;
    User removedBy;
    RemoveReasons removeReason;
    Date addedDate;
    Date removedDate;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public User getRemovedBy() {
        return removedBy;
    }

    public void setRemovedBy(User removedBy) {
        this.removedBy = removedBy;
    }

    public RemoveReasons getRemoveReason() {
        return removeReason;
    }

    public void setRemoveReason(RemoveReasons removeReason) {
        this.removeReason = removeReason;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(Date removedDate) {
        this.removedDate = removedDate;
    }
}
