package com.example.sentimentalstorage.models;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardModel {


    @Nullable
    String giftGiver, occasion, dateReceived;

    ArrayList<Uri> associatedPhotos;
    public CardModel(String giftGiver, String occasion, String dateReceived) {
        this.giftGiver = giftGiver;
        this.occasion = occasion;
        this.dateReceived = dateReceived;
        associatedPhotos = new ArrayList<>();
    }
    public CardModel() {
        this.giftGiver = "";
        this.occasion = "";
        this.dateReceived = "";
        associatedPhotos = new ArrayList<>();

    }


    @Nullable
    public String getGiftGiver() {
        return giftGiver;
    }

    public void setGiftGiver(@Nullable String giftGiver) {
        this.giftGiver = giftGiver;
    }

    @Nullable
    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(@Nullable String occasion) {
        this.occasion = occasion;
    }


    @Nullable
    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(@Nullable String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public ArrayList<Uri> getAssociatedPhotos() {
        return associatedPhotos;
    }

    public void addAssociatedPhoto(Uri uri) {
        this.associatedPhotos.add(uri);
    }

    public void removeAssociatedPhoto(Uri uri){
        this.associatedPhotos.remove(uri);
    }
}
