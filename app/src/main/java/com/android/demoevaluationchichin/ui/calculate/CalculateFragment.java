package com.android.demoevaluationchichin.ui.calculate;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.android.demoevaluationchichin.LoginActivity;
import com.android.demoevaluationchichin.R;
import com.android.demoevaluationchichin.controller.AppController;
import com.android.demoevaluationchichin.model.CurrencyParameter;
import com.android.demoevaluationchichin.network.AsyncProcess;

public class CalculateFragment extends Fragment {

    private CalculateViewModel calculateViewModel;
    public AppController appController;
    public CurrencyParameter currencyParameter;
    public static String CLASS_TAG= CalculateFragment.class.getSimpleName();
    public TextView btc_text;
    public TextView eth_text;
    public TextView ptr_text;
    public TextView usd_text;
    public TextView eur_text;
    public TextView bs_text;
    public Double money_input;
    public Button calculate;
    public EditText input_money;
    public Spinner spinner;
    public ImageButton refreshData;
    public AsyncProcess callbackFetchCurrency;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        calculateViewModel =
//                ViewModelProviders.of(this).get(CalculateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculate, container, false);
        btc_text=root.findViewById(R.id.btc_text);
        eth_text=root.findViewById(R.id.eth_text);
        ptr_text=root.findViewById(R.id.ptr_text);
        usd_text=root.findViewById(R.id.usd_text);
        eur_text=root.findViewById(R.id.eur_text);
        bs_text=root.findViewById(R.id.bs_text);
        input_money=root.findViewById(R.id.input_money);
        calculate=root.findViewById(R.id.button_calculate);
        refreshData=root.findViewById(R.id.button_refresh);
        String [] values =
                {"ETH","BTC","PTR","BS","EUR","USD",};
        spinner= (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appController=AppController.getInstance(getActivity());
        callbackFetchCurrency= new AsyncProcess() {
            @SuppressLint("SetTextI18n")
            @Override
            public void Success(Object o) {
                currencyParameter=(CurrencyParameter) o;
                Log.d(CLASS_TAG,"currency "+currencyParameter.getContent().getBTC());
                btc_text.setText(""+currencyParameter.getContent().getBTC());
                eth_text.setText(""+currencyParameter.getContent().getETH());
                ptr_text.setText(""+currencyParameter.getContent().getPTR());
                usd_text.setText(""+currencyParameter.getContent().getUSD());
                eur_text.setText(""+currencyParameter.getContent().getEUR());
                bs_text.setText(""+currencyParameter.getContent().getBS());

            }

            @Override
            public void Error(Object o) {
                Toast.makeText(getActivity(),"Error currency",Toast.LENGTH_LONG).show();
            }
        };
        appController.fetchCurrencyParameter(false, getActivity(),callbackFetchCurrency);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSpinner=spinner.getSelectedItem().toString();
                money_input= Double.valueOf(input_money.getText().toString());
                if(selectedSpinner.equals("ETH")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getETH());
                }else if(selectedSpinner.equals("BTC")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getBTC());
                }else if(selectedSpinner.equals("PTR")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getPTR());
                }else if(selectedSpinner.equals("USD")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getUSD());
                }else if(selectedSpinner.equals("EUR")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getEUR());
                }else if(selectedSpinner.equals("BS")){
                    money_input *=Double.parseDouble(currencyParameter.getContent().getBS());
                }
                appController.setMoneyInput(money_input);
                currencyParameter.calculate(money_input,getActivity());
                Navigation.findNavController(v).navigate(R.id.nav_result);

            }
        });

        refreshData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appController.fetchCurrencyParameter(true,
                        getActivity(), callbackFetchCurrency);
                Toast.makeText(getActivity(),"Currency update......",Toast.LENGTH_LONG).show();
            }
        });
    }
}