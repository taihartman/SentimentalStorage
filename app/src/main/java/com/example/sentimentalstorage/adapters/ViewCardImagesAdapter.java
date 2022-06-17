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
import com.github.chrisbanes.photoview.PhotoView;


import java.util.ArrayList;

public class ViewCardImagesAdapter extends RecyclerView.Adapter<ViewCardImagesAdapter.ViewHolder> {
    private ArrayList<Uri> workingCardUriList;
    public ViewCardImagesAdapter(ArrayList<Uri> cardModelList) {
        this.workingCardUriList = cardModelList;
    }
    @NonNull
    @Override
    public ViewCardImagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fullscreen_photo_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCardImagesAdapter.ViewHolder holder, int position) {
        holder.fullscreenPhotoView.setImageURI(workingCardUriList.get(position));

    }

    @Override
    public int getItemCount() {
        return workingCardUriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //initialize the used views in the item layout we created
        PhotoView fullscreenPhotoView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullscreenPhotoView = itemView.findViewById(R.id.photo_view);

        }
    }
}

