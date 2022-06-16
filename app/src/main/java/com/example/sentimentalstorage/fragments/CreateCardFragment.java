package com.example.sentimentalstorage.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.adapters.CardPhotoAdapter;
import com.example.sentimentalstorage.databinding.CreateCardFragmentBinding;
import com.example.sentimentalstorage.viewModels.AppViewModel;
import com.example.sentimentalstorage.viewModels.CardViewModel;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateCardFragment extends Fragment {

    private AppViewModel appViewModel;
    private CreateCardFragmentBinding fragmentMainBinding;
    private static final String ARG_PARAM1 = "isEdit";

    private boolean isEdit;
    public CreateCardFragment() {
        // Required empty public constructor
    }


    public static CreateCardFragment newInstance(Boolean isEdit) {
        CreateCardFragment fragment = new CreateCardFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle(getString(R.string.create_sentiment_title));

        //checking params to reuse this fragment if editing
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(ARG_PARAM1);
        }

        if(isEdit){
            requireActivity().setTitle(getString(R.string.edit_sentiment_title));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.create_card_fragment,container,false);
        //setting up the app viewmodel
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        //setting the card model from the previous fragment as the one we are working with
        fragmentMainBinding.setViewModel(new CardViewModel(appViewModel.getWorkingCardViewModel().getModel()));
        fragmentMainBinding.executePendingBindings();

        //setting up the recyclerview for the images
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView photoList = fragmentMainBinding.photoRecyclerView;
        photoList.setLayoutManager(layoutManager);

        photoList.setAdapter(new CardPhotoAdapter(fragmentMainBinding.getViewModel().getModel().getAssociatedPhotos()));

        //setting up the new picture button
        fragmentMainBinding.addPictureButton.setOnClickListener(view1 -> {
            if (isEdit){
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, TakeAndCropPhoto.newInstance(isEdit), TakeAndCropPhoto.class.getName()).addToBackStack(TakeAndCropPhoto.class.getName()).commit();
            }else {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TakeAndCropPhoto(), TakeAndCropPhoto.class.getName()).addToBackStack(TakeAndCropPhoto.class.getName()).commit();
            }
        });

        //setting up the save button
        fragmentMainBinding.saveCardButton.setOnClickListener(view1 -> {

            if(isEdit){
                appViewModel.updateCard(fragmentMainBinding.getViewModel().getModel());
            }else{
                //adding the card to the app view model
                appViewModel.addCard(fragmentMainBinding.getViewModel().getModel());
            }

            Snackbar.make(requireActivity(),fragmentMainBinding.saveCardButton,getText(R.string.save_sentiment_toast),Snackbar.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack(ViewCardsFragment.class.getName(),0);
        });

        return fragmentMainBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



}