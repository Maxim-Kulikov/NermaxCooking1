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

public class ProteinsFilterFragment extends Fragment implements View.OnClickListener{
    EditText fromET,toET;
    RadioButton low,medium,high;
    ImageButton accept;
    TextView param;
    ControllerFilter controllerFilter = ControllerFilter.getController();
    double from = 0 ,to = Double.POSITIVE_INFINITY;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signal = (SignalForMainActivity) context;
    }

    interface SignalForMainActivity{
        void backFromProteinsFilter();
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
        low = view.findViewById(R.id.lowCalories_rbtn);
        medium = view.findViewById(R.id.mediumCalories_rbtn);
        high = view.findViewById(R.id.highCalories_rbtn);
        accept = view.findViewById(R.id.back_btn);
        param.setText("Белки");
        low.setText("от 0 до 5 г.");
        medium.setText("от 6 до 15 г.");
        high.setText("от 16 г. и больше");
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
                    to = Double.parseDouble(toET.getText().toString());
                if(!fromET.getText().toString().isEmpty())
                    from = Double.parseDouble(fromET.getText().toString());
                controllerFilter.setFromProteins(from);
                controllerFilter.setToProteins(to);
                signal.backFromProteinsFilter();
                break;
            case R.id.lowCalories_rbtn: from=0;to=5;
                controllerFilter.setFromProteins(from);
                controllerFilter.setToProteins(to);
                break;
            case R.id.mediumCalories_rbtn: from=6;to=15;
                controllerFilter.setFromProteins(from);
                controllerFilter.setToProteins(to);
                break;
            case R.id.highCalories_rbtn: from = 16; to = Double.POSITIVE_INFINITY;
                controllerFilter.setFromProteins(from);
                controllerFilter.setToProteins(to);
                break;

        }
    }
}
