package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

public class CaloriesFilterFragment extends Fragment implements View.OnClickListener {

    EditText fromET,toET;
    RadioButton low,medium,high;
    ImageButton accept;
    TextView param;
    ControllerFilter controllerFilter = ControllerFilter.getController();
    double from = 0 ,to = Double.POSITIVE_INFINITY;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signal = (SignalForMainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    interface SignalForMainActivity{
        void backFromCaloriesFilter();
    }
    SignalForMainActivity signal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_filter, container, false);
        fromET= view.findViewById(R.id.fromET);
        toET = view.findViewById(R.id.toET);
        param = view.findViewById(R.id.filter_tv);
        param.setText("Калории");
        low = view.findViewById(R.id.lowCalories_rbtn);
        medium = view.findViewById(R.id.mediumCalories_rbtn);
        high = view.findViewById(R.id.highCalories_rbtn);
        accept = view.findViewById(R.id.back_btn);
        low.setOnClickListener(this);
        medium.setOnClickListener(this);
        high.setOnClickListener(this);
        accept.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                  if(!toET.getText().toString().isEmpty())
                      to= Double.parseDouble(toET.getText().toString());
                  if(!fromET.getText().toString().isEmpty())
                      from = Double.parseDouble(fromET.getText().toString());
                controllerFilter.setFromCall(from);
                controllerFilter.setToCall(to);
                signal.backFromCaloriesFilter();
                break;
            case R.id.lowCalories_rbtn: from=0;to=250;
                controllerFilter.setFromCall(from);
                controllerFilter.setToCall(to);
                break;
            case R.id.mediumCalories_rbtn: from=251;to=400;
                controllerFilter.setFromCall(from);
                controllerFilter.setToCall(to);
                break;
            case R.id.highCalories_rbtn: from = 401; to = Double.POSITIVE_INFINITY;
                controllerFilter.setFromCall(from);
                controllerFilter.setToCall(to);
                break;
        }
    }
}