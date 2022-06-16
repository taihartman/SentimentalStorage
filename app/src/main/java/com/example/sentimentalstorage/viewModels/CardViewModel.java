package com.example.sentimentalstorage.viewModels;


import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.sentimentalstorage.BR;
import com.example.sentimentalstorage.models.CardModel;

public class CardViewModel extends BaseObservable {
    private CardModel model;
    public CardViewModel(CardModel cardModel) {

        // instantiating object of
        // model class
        model = cardModel;
    }
    // constructor of ViewModel class
    public CardViewModel() {

        // instantiating object of
        // model class
        model = new CardModel("","","");
    }

    // getter and setter methods
    // Gift Giver
    @Bindable
    public String getGiftGiver() {
        return model.getGiftGiver();
    }

    public void setGiftGiver(String giftGiver) {
        model.setGiftGiver(giftGiver);
        notifyPropertyChanged(BR.giftGiver);
    }

    // getter and setter methods
    // Occasion
    @Bindable
    public String getOccasion(){
        return model.getOccasion();
    }

    public void setOccasion(String occasion) {
        model.setOccasion(occasion);
        notifyPropertyChanged(BR.occasion);
    }

    // getter and setter methods
    // DateReceived
    @Bindable
    public String getDateReceived(){
        return model.getDateReceived();
    }

    public void setDateReceived(String dateReceived) {
        model.setDateReceived(dateReceived);
        notifyPropertyChanged(BR.dateReceived);
    }

    public CardModel getModel() {
        return model;
    }

    public void createCard(){

        Log.d("testing",getGiftGiver() + getOccasion() + getDateReceived());
    }

}
