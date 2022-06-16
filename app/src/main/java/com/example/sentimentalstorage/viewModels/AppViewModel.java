package com.example.sentimentalstorage.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sentimentalstorage.models.CardModel;

import java.util.ArrayList;

public class AppViewModel extends ViewModel {
    MutableLiveData<ArrayList<CardModel>> cardModelLiveData;
    ArrayList<CardModel> cardModelList = new ArrayList<>();
    CardViewModel cardModel;
    public AppViewModel() {
        cardModelLiveData =  new MutableLiveData<>();
        init();

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

    //later used shared prefs
    public void init(){
        cardModelLiveData.setValue(cardModelList);
    }

    public void setWorkingCardViewModel(CardViewModel cardModel){
        this.cardModel = cardModel;
    }

    public CardViewModel getWorkingCardViewModel() {
        return cardModel;
    }
}
