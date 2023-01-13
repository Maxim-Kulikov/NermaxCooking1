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

public class FatsFilterFragment extends Fragment implements View.OnClickListener{
    EditText fromET,toET;
    RadioButton low,medium,high;
    ImageButton accept;
    TextView param;
    ControllerFilter controllerFilter = ControllerFilter.getController();
    double from = 0,to = Double.POSITIVE_INFINITY;
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
        void backFromFatsFilter();
    }
    SignalForMainActivity signal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_filter, container, false);
        fromET= view.findViewById(R.id.fromET);
        toET = view.findViewById(R.id.toET);

        low = view.findViewById(R.id.lowCalories_rbtn);
        medium = view.findViewById(R.id.mediumCalories_rbtn);
        high = view.findViewById(R.id.highCalories_rbtn);
        accept = view.findViewById(R.id.back_btn);
        param = view.findViewById(R.id.filter_tv);
        param.setText("Жиры");
        low.setText("от 0 до 12 г.");
        medium.setText("от 13 до 40 г.");
        high.setText("от 41 г. и больше");
        low.setOnClickListener(this);
        medium.setOnClickListener(this);
        high.setOnClickListener(this);

        accept = view.findViewById(R.id.back_btn);
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

                controllerFilter.setFromFats(from);
                controllerFilter.setToFats(to);
                signal.backFromFatsFilter();
                break;
            case R.id.lowCalories_rbtn: from=0;to=12;
                controllerFilter.setFromFats(from);
                controllerFilter.setToFats(to);
                break;
            case R.id.mediumCalories_rbtn: from=13;to=40;
                controllerFilter.setFromFats(from);
                controllerFilter.setToFats(to);
                break;
            case R.id.highCalories_rbtn: from = 41; to = Double.POSITIVE_INFINITY;
                controllerFilter.setFromFats(from);
                controllerFilter.setToFats(to);
                break;

        }
    }
}

