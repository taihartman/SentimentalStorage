package com.example.sentimentalstorage.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentimentalstorage.OnCardClickedListener;
import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.adapters.CardAdapter;
import com.example.sentimentalstorage.models.CardModel;
import com.example.sentimentalstorage.viewModels.AppViewModel;
import com.example.sentimentalstorage.viewModels.CardViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewCardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewCardsFragment extends Fragment implements OnCardClickedListener {
    private AppViewModel appViewModel;
    RecyclerView cardRecyclerView;
    CardAdapter cardAdapter;
    LinearLayoutManager layoutManager;

    public ViewCardsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewCardsFragment newInstance() {
        ViewCardsFragment fragment = new ViewCardsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_cards, container, false);
        requireActivity().setTitle(getString(R.string.view_cards_title));

        cardRecyclerView = root.findViewById(R.id.cardRecyclerView);
        layoutManager = new LinearLayoutManager(requireActivity());
        cardRecyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(this);
        cardRecyclerView.setAdapter(cardAdapter);

        //setting up the floating action button
        root.findViewById(R.id.createCardButton).setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new TakeAndCropPhoto(), TakeAndCropPhoto.class.getName()).addToBackStack(TakeAndCropPhoto.class.getName()).commit());


        //don't allow the user to go back when on this page
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // do nothing
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        appViewModel.getCardModels().observe(getViewLifecycleOwner(),userListUpdateObserver);

        //checking if the user is currently working on a card, if not, creating new card to work on
        appViewModel.setWorkingCardViewModel(new CardViewModel());
    }

    //updating the list when a change occurs
    Observer<ArrayList<CardModel>> userListUpdateObserver = new Observer<ArrayList<CardModel>>() {
        @Override
        public void onChanged(ArrayList<CardModel> cardModels) {
            cardAdapter.updateCardList(cardModels);
        }
    };


    @Override
    public void onItemClickedEdit(CardModel cardModel) {
        appViewModel.setWorkingCardViewModel(new CardViewModel(cardModel));
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, CreateCardFragment.newInstance(true)).addToBackStack(CreateCardFragment.class.getName()).commit();
    }

    @Override
    public void onItemClickedView(CardModel cardModel) {


    }

}