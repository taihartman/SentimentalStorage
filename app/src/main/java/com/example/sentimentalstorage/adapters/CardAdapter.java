package com.example.sentimentalstorage.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sentimentalstorage.OnCardClickedListener;
import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.models.CardModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ArrayList<CardModel> cardModelList;
    private OnCardClickedListener onCardClickedListener;
    public CardAdapter(ArrayList<CardModel> cardModelList) {
        this.cardModelList = cardModelList;

    }
    public CardAdapter(OnCardClickedListener onCardClickedListener) {
        this.cardModelList = new ArrayList<CardModel>();
        this.onCardClickedListener = onCardClickedListener;
    }
    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {

        holder.cardImageView.setImageURI(cardModelList.get(position).getAssociatedPhotos().get(0));

        if(!cardModelList.get(position).getGiftGiver().equals("")){
            holder.giverLayout.setVisibility(View.VISIBLE);
            holder.giftGiverText.setText(cardModelList.get(position).getGiftGiver());
        }

        if(!cardModelList.get(position).getOccasion().equals("")){
            holder.occasionLayout.setVisibility(View.VISIBLE);
            holder.occasionText.setText(cardModelList.get(position).getOccasion());
        }

        if(!cardModelList.get(position).getDateReceived().equals("")){
            holder.dateReceivedLayout.setVisibility(View.VISIBLE);
            holder.dateReceivedText.setText(cardModelList.get(position).getDateReceived());
        }

        holder.editButton.setOnClickListener(view -> {
            onCardClickedListener.onItemClickedEdit(cardModelList.get(holder.getAdapterPosition()));
        });

        holder.materialCardView.setOnClickListener(v -> {
            Log.d("test","clicked");
            onCardClickedListener.onItemClickedView(cardModelList.get(holder.getAdapterPosition()));
        });
    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }
    public void updateCardList(ArrayList<CardModel> cardModelList){
        this.cardModelList.clear();
        this.cardModelList = cardModelList;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize the used views in the item layout we created
        ImageView cardImageView;
        MaterialTextView giftGiverText;
        MaterialTextView occasionText;
        MaterialTextView dateReceivedText;
        LinearLayout giverLayout;
        LinearLayout occasionLayout;
        LinearLayout dateReceivedLayout;
        MaterialCardView materialCardView;
        MaterialButton editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardImageView);
            giftGiverText = itemView.findViewById(R.id.giftGiverText);
            occasionText = itemView.findViewById(R.id.occasionText);
            dateReceivedText = itemView.findViewById(R.id.dateReceivedText);
            giverLayout = itemView.findViewById(R.id.giverLayout);
            occasionLayout = itemView.findViewById(R.id.occasionLayout);
            dateReceivedLayout = itemView.findViewById(R.id.dateReceivedLayout);
            materialCardView = itemView.findViewById(R.id.materialCardView);
            editButton = itemView.findViewById(R.id.cardEditbutton);

        }
    }

}
