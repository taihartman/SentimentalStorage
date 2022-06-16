package com.example.sentimentalstorage.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.models.CardModel;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CardPhotoAdapter extends RecyclerView.Adapter<CardPhotoAdapter.ViewHolder> {
    private ArrayList<Uri> workingCardUriList;
    public CardPhotoAdapter(ArrayList<Uri> cardModelList) {
        this.workingCardUriList = cardModelList;
    }
    @NonNull
    @Override
    public CardPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardPhotoAdapter.ViewHolder holder, int position) {
        holder.photoImageView.setImageURI(workingCardUriList.get(position));
        holder.photoNumberTextView.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return workingCardUriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize the used views in the item layout we created
        ImageView photoImageView;
        TextView photoNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photo);
            photoNumberTextView = itemView.findViewById(R.id.photoNumber);

        }
    }
}
