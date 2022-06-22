package com.example.sentimentalstorage.fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentimentalstorage.OnCardClickedListener;
import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.adapters.CardAdapter;
import com.example.sentimentalstorage.models.CardModel;
import com.example.sentimentalstorage.viewModels.AppViewModel;
import com.example.sentimentalstorage.viewModels.CardViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

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
    Gson gson;
    private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                boolean allPermsGranted = false;
                for (Boolean perm: isGranted.values()) {
                    allPermsGranted = true;
                }

                if (!allPermsGranted) {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
                    builder.setTitle("Permission Not Granted");
                    builder.setMessage("Please grant permissions to take and save photos on your device.");
                    builder.show();
                }


            });
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
        requestPermissionLauncher.launch(new String [] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});

        cardRecyclerView = root.findViewById(R.id.cardRecyclerView);
        layoutManager = new LinearLayoutManager(requireActivity());
        cardRecyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(this);

        //setting up the swipe to remove functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ArrayList<CardModel> tempData = new ArrayList<>(appViewModel.getCardModels().getValue());
                appViewModel.removeCard(viewHolder.getAdapterPosition());
                Snackbar.make(requireActivity(),root.findViewById(R.id.createCardButton),getText(R.string.removed_card_toast),Snackbar.LENGTH_LONG).setAction("Undo", view ->
                        appViewModel.undo(tempData)).show();
            }
        }).attachToRecyclerView(cardRecyclerView);

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

        //updating the list when a change occurs
        appViewModel.getCardModels().observe(getViewLifecycleOwner(), cardModels -> {
            cardAdapter.updateCardList(cardModels);
        });

        //checking if the user is currently working on a card, if not, creating new card to work on
        appViewModel.setWorkingCardViewModel(new CardViewModel());
    }


    @Override
    public void onItemClickedEdit(CardModel cardModel) {
        appViewModel.setWorkingCardViewModel(new CardViewModel(cardModel));
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, CreateCardFragment.newInstance(true)).addToBackStack(CreateCardFragment.class.getName()).commit();
    }

    @Override
    public void onItemClickedView(CardModel cardModel) {
        appViewModel.setWorkingCardViewModel(new CardViewModel(cardModel));
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ViewCardDetailsFragment()).addToBackStack(ViewCardDetailsFragment.class.getName()).commit();

    }

}