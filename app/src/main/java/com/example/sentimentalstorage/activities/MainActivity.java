package com.example.sentimentalstorage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.fragments.CreateCardFragment;
import com.example.sentimentalstorage.fragments.TakeAndCropPhoto;
import com.example.sentimentalstorage.fragments.ViewCardsFragment;
import com.example.sentimentalstorage.models.CardModel;
import com.example.sentimentalstorage.viewModels.AppViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ViewCardsFragment.newInstance(), ViewCardsFragment.class.getName()).addToBackStack(ViewCardsFragment.class.getName()).commit();

        }

    }

}