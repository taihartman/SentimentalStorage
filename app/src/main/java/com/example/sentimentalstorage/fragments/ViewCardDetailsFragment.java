package com.example.sentimentalstorage.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.adapters.CardPhotoAdapter;
import com.example.sentimentalstorage.adapters.ViewCardImagesAdapter;
import com.example.sentimentalstorage.databinding.FragmentTakeAndCropPhotoBinding;
import com.example.sentimentalstorage.databinding.FragmentViewCardDetailsBinding;
import com.example.sentimentalstorage.viewModels.AppViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCardDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCardDetailsFragment extends Fragment {
    private AppViewModel appViewModel;
    private FragmentViewCardDetailsBinding fragmentMainBinding;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ViewCardDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewCardDetailsFragment newInstance(String param1, String param2) {
        ViewCardDetailsFragment fragment = new ViewCardDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        fragmentMainBinding = FragmentViewCardDetailsBinding.inflate(inflater, container,false);
        //setting up the recyclerview for the images
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView photoList = fragmentMainBinding.fullScreenPhotoRecyclerView;
        photoList.setLayoutManager(layoutManager);

        photoList.setAdapter(new CardPhotoAdapter(appViewModel.getWorkingCardViewModel().getModel().getAssociatedPhotos()));
        // Inflate the layout for this fragment
        return fragmentMainBinding.getRoot();
    }
}