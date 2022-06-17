package com.example.sentimentalstorage.viewModels;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sentimentalstorage.TinyDB;
import com.example.sentimentalstorage.models.CardModel;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AppViewModel extends ViewModel {
    final String SAVED_CARDS = "saved_cards";
    MutableLiveData<ArrayList<CardModel>> cardModelLiveData;
    ArrayList<CardModel> cardModelList = new ArrayList<>();
    CardViewModel cardModel;

    public AppViewModel() {
        cardModelLiveData = new MutableLiveData<>();
    }
    //returns array list of cards
    public MutableLiveData<ArrayList<CardModel>> getCardModels() {
        return cardModelLiveData;
    }

    //adds the passed in card model
    public void addCard(CardModel cardModel){
        cardModelList.add(cardModel);
        cardModelLiveData.setValue(cardModelList);

    }

    public void updateCard(CardModel cardModel){
        cardModelList.set(cardModelList.indexOf(cardModel), cardModel);
        cardModelLiveData.setValue(cardModelList);
    }


    //removes the a specified card
    public void removeCard(CardModel cardModel){
        cardModelLiveData.getValue().remove(cardModel);
    }



    //set the card model that is currently being worked on by user
    public void setWorkingCardViewModel(CardViewModel cardModel){
        this.cardModel = cardModel;
    }

    //get the model that is being worked on by user
    public CardViewModel getWorkingCardViewModel() {
        return cardModel;
    }

    //save the current card models to shared prefs
    public void saveAllCards(Context context){


    }

    //load the card models from shared prefs
    public void loadAllCards(Context context) {

    }
}
