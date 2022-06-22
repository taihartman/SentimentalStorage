package com.example.sentimentalstorage.viewModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sentimentalstorage.TinyDB;
import com.example.sentimentalstorage.models.CardModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppViewModel extends ViewModel {
    final String SAVED_CARDS = "saved_cards";
    MutableLiveData<ArrayList<CardModel>> cardModelLiveData;
    ArrayList<CardModel> cardModelList;
    CardViewModel cardModel;
    Gson gson = new Gson();

    public AppViewModel() {

    }
    //returns array list of cards
    public MutableLiveData<ArrayList<CardModel>> getCardModels() {
        if(cardModelLiveData==null){
            cardModelList = new ArrayList<>();
            cardModelLiveData = new MutableLiveData<>(cardModelList);
        }
        return cardModelLiveData;
    }

    public void undo(ArrayList<CardModel> cardModelArrayList) {
        cardModelList.clear();
        cardModelList.addAll(cardModelArrayList);
        cardModelLiveData.setValue(cardModelList);
    }

    //adds the passed in card model
    public void addCard(CardModel cardModel){
        cardModelList.add(cardModel);
        cardModelLiveData.setValue(cardModelList);

    }
    public void addCard(int index, CardModel cardModel){
        cardModelList.add(index, cardModel);
        cardModelLiveData.setValue(cardModelList);

    }
    public void updateCard(CardModel cardModel){
        cardModelList.set(cardModelList.indexOf(cardModel), cardModel);
        cardModelLiveData.setValue(cardModelList);
    }


    //removes the a specified card
    public void removeCard(CardModel cardModel){
        cardModelList.remove(cardModel);
        cardModelLiveData.setValue(cardModelList);
    }

    //removes the a specified index
    public void removeCard(int index){
        cardModelList.remove(index);
        cardModelLiveData.setValue(cardModelList);
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
    public void saveAllCards(SharedPreferences preferences){
        String jsonString = gson.toJson(cardModelList);
        preferences.edit().putString(SAVED_CARDS, jsonString).apply();
    }

    //load the card models from shared prefs
    public void loadAllCards(SharedPreferences preferences) {
        String jsonString = preferences.getString(SAVED_CARDS, "");
        if(jsonString!=""){
            Type type = new TypeToken<ArrayList<CardModel>>() {}.getType();
            List<CardModel> list = gson.fromJson(jsonString, type);
            cardModelLiveData.setValue(new ArrayList<CardModel>(list));
            Log.d("model", list.toString());

        }else{
            cardModelLiveData =  new MutableLiveData<>();

        }


    }
}
