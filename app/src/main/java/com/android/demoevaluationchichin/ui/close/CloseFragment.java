package com.android.demoevaluationchichin.ui.close;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.demoevaluationchichin.LoginActivity;
import com.android.demoevaluationchichin.R;
import com.android.demoevaluationchichin.controller.StorageController;

public class CloseFragment extends Fragment {

    private CloseViewModel closeViewModel;
    public StorageController storageController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        storageController=StorageController.getInstance(getActivity());
        storageController.deleteToken();
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        return root;
    }
}