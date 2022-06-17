package com.example.sentimentalstorage.fragments;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sentimentalstorage.R;
import com.example.sentimentalstorage.databinding.FragmentTakeAndCropPhotoBinding;
import com.example.sentimentalstorage.viewModels.AppViewModel;
import com.example.sentimentalstorage.viewModels.CardViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TakeAndCropPhoto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TakeAndCropPhoto extends Fragment {
    private AppViewModel appViewModel;
    private FragmentTakeAndCropPhotoBinding fragmentMainBinding;
    private Uri savedFileUri;
    private String currentFileName;
    private boolean tookPhoto = false;

    private static final String ARG_PARAM1 = "isEdit";

    private boolean isEdit;


    private ActivityResultLauncher<Uri> mTakePhoto = registerForActivityResult(new ActivityResultContracts.TakePicture(), result ->{
        if(result){
            tookPhoto = true;
            //running on new thread to avoid blocking the UI
            CompletableFuture.runAsync(()->{
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver() , savedFileUri);
                    fragmentMainBinding.documentScanner.setImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    });

    public TakeAndCropPhoto() {
        // Required empty public constructor
    }

    public static TakeAndCropPhoto newInstance(boolean isEdit) {
        TakeAndCropPhoto fragment = new TakeAndCropPhoto();

        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().setTitle(getString(R.string.scan_photo_title));
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Starting the camera to take picture
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (savedInstanceState==null){
            takePicture();
        }


        fragmentMainBinding = FragmentTakeAndCropPhotoBinding.inflate(inflater, container,false);

        //requesting permissions

        //will let the user take a picture if they back out of the camera by accident
        if (fragmentMainBinding.progressBar.getVisibility() == View.VISIBLE){
            fragmentMainBinding.progressBar.setVisibility(View.VISIBLE);
            fragmentMainBinding.cropPictureButton.setText(getString(R.string.add_photo));
            fragmentMainBinding.cropPictureButton.setOnClickListener(view -> {
                takePicture();
            });
        }


        //setting loading animation
        fragmentMainBinding.documentScanner.setOnLoadListener(loading ->{

            fragmentMainBinding.progressBar.setVisibility(View.GONE);
            fragmentMainBinding.cropPictureButton.setText(getString(R.string.crop_photo_button));
            fragmentMainBinding.cropPictureButton.setOnClickListener(view1 -> {
                CompletableFuture.runAsync(() -> {
                    Bitmap croppedBitmap = fragmentMainBinding.documentScanner.getCroppedImage();
                    saveCroppedImage(requireContext(),croppedBitmap);
                    fragmentMainBinding.documentScanner.setImage(croppedBitmap);
                });
            });

            return null;
        });


        //setting up done button
        fragmentMainBinding.doneButton.setOnClickListener(view1 ->{
            //adding the uri to the model we are currently working with
            appViewModel.getWorkingCardViewModel().getModel().addAssociatedPhoto(savedFileUri);
            if(isEdit){
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,CreateCardFragment.newInstance(isEdit)).addToBackStack(CreateCardFragment.class.getName()).commit();

            }else{
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new CreateCardFragment()).addToBackStack(CreateCardFragment.class.getName()).commit();

            }
        });

        return fragmentMainBinding.getRoot();
    }

    //will take picture and store the file using the date and time
    private void takePicture(){
        currentFileName = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".jpg";
        savedFileUri = writeFileOnInternalStorage(requireContext(),currentFileName);
        Log.d("testing",savedFileUri.getPath());
        mTakePhoto.launch(savedFileUri);

    }

    //saving the image to the internal storage
    public Uri writeFileOnInternalStorage(Context inContext, String sFileName){
        File dir = new File(inContext.getExternalFilesDir("external_files"),"photos");

        if(!dir.exists()){
            dir.mkdir();
        }

        return FileProvider.getUriForFile(inContext,requireActivity().getPackageName() +".provider",new File(dir, sFileName));
    }

    //save the cropped image over the old file path
    public void saveCroppedImage(Context inContext, Bitmap inImage)  {
        try{

            File dir = new File(inContext.getExternalFilesDir("external_files"),"photos");
            File file = new File(dir, currentFileName);
            OutputStream fOut = null;
            fOut = new FileOutputStream(file);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
        }catch (IOException ioException){
            ioException.printStackTrace();
        }


    }
}