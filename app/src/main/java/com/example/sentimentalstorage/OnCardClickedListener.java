package com.example.sentimentalstorage;

import com.example.sentimentalstorage.models.CardModel;

public interface OnCardClickedListener {
    void onItemClickedEdit(CardModel cardModel);
    void onItemClickedView(CardModel cardModel);

}
