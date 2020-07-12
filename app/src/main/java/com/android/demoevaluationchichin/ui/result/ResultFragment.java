package com.android.demoevaluationchichin.ui.result;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.demoevaluationchichin.R;
import com.android.demoevaluationchichin.controller.AppController;
import com.android.demoevaluationchichin.ui.calculate.CalculateFragment;
import com.android.demoevaluationchichin.ui.calculate.CalculateViewModel;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class ResultFragment extends Fragment {

    public TextView btc_text;
    public TextView eth_text;
    public TextView ptr_text;
    public TextView usd_text;
    public TextView eur_text;
    public TextView bs_text;
    public TextView title;
    public AppController appController;
    public JSONObject dataQr;
    public static String CLASS_TAG= ResultFragment.class.getSimpleName();
    public ImageView qrviewer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        calculateViewModel =
//                ViewModelProviders.of(this).get(CalculateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_result, container, false);
        btc_text=root.findViewById(R.id.btc_text);
        eth_text=root.findViewById(R.id.eth_text);
        ptr_text=root.findViewById(R.id.ptr_text);
        usd_text=root.findViewById(R.id.usd_text);
        eur_text=root.findViewById(R.id.eur_text);
        bs_text=root.findViewById(R.id.bs_text);
        title=root.findViewById(R.id.text_title_result);
        qrviewer=root.findViewById(R.id.qr_image);

        return root;
    }

    @SuppressLint("SetTextI18n")
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appController=AppController.getInstance(getActivity());
        title.setText("If you make the investment of $"+
                appController.getMoneyInput()
                +", you will get this capital in shares of one of the following currencies\n");
        if(appController.getCalculateResult()!=null) {
            btc_text.setText(appController.getCalculateResult().getBTC());
            eth_text.setText(appController.getCalculateResult().getETH());
            ptr_text.setText(appController.getCalculateResult().getPTR());
            usd_text.setText(appController.getCalculateResult().getUSD());
            eur_text.setText(appController.getCalculateResult().getEUR());
            bs_text.setText(appController.getCalculateResult().getBS());

            dataQr=new JSONObject();
            try {
                dataQr.put("amount",appController.getMoneyInput());
                dataQr.put("btc",appController.getCalculateResult().getBTC());
                dataQr.put("eth",appController.getCalculateResult().getETH());
                dataQr.put("ptr",appController.getCalculateResult().getPTR());
                dataQr.put("usd",appController.getCalculateResult().getUSD());
                dataQr.put("eur",appController.getCalculateResult().getEUR());
                dataQr.put("bs",appController.getCalculateResult().getBS());
                Log.d(CLASS_TAG,"Json Data Qr "+dataQr.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            QRGEncoder qrgEncoder = new QRGEncoder(dataQr.toString(),
                    null, QRGContents.Type.TEXT, 10);
            try {
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                qrviewer.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    }


}
