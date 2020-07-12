package com.android.demoevaluationchichin.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.android.demoevaluationchichin.R;
import com.android.demoevaluationchichin.controller.AppController;
import com.android.demoevaluationchichin.ui.result.ResultFragment;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanFragment extends Fragment {

    private ScanViewModel scanViewModel;
    public static String CLASS_TAG= ScanFragment.class.getSimpleName();
    public AppController appController;
    public View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        scanViewModel =
//                ViewModelProviders.of(this).get(ScanViewModel.class);
//
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        scanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        root = inflater.inflate(R.layout.fragment_scan, container, false);
        appController=AppController.getInstance(getActivity());
        IntentIntegrator scanIntegrator = IntentIntegrator.forSupportFragment(ScanFragment.this);
        scanIntegrator.setPrompt("Scan");
        scanIntegrator.setBeepEnabled(true);

        //enable the following line if you want QR code
        //scanIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);

        scanIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
        scanIntegrator.setOrientationLocked(true);
        scanIntegrator.setBarcodeImageEnabled(true);
        scanIntegrator.initiateScan();
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = "";
            String scanFormat = "";
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }

            Toast.makeText(getActivity(), scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
            Log.d(CLASS_TAG,scanContent + "    type:" + scanFormat);
            //textView.setText(scanContent + "    type:" + scanFormat);
            try {
                JSONObject dataReceive= new JSONObject(scanContent);
                appController.initialize(dataReceive);
                Navigation.findNavController(root).navigate(R.id.nav_result);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }
}