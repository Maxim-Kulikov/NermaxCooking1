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

public class CarbohydratesFilterFragment extends Fragment implements View.OnClickListener{
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
        void backFromCarbohydratesFilter();
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
        low.setText("от 0 до 49 уровень ГИ");
        medium.setText("от 50 до 69 уровень ГИ");
        high.setText("от 70 и больше уровень ГИ");
        param.setText("Углеводы");
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
                controllerFilter.setFromCarbohydrates(from);
                controllerFilter.setToCarbohydrates(to);
                signal.backFromCarbohydratesFilter();
                break;
            case R.id.lowCalories_rbtn: from=0;to=49;
                controllerFilter.setFromCarbohydrates(from);
                controllerFilter.setToCarbohydrates(to);
                break;
            case R.id.mediumCalories_rbtn: from=50;to=69;
                controllerFilter.setFromCarbohydrates(from);
                controllerFilter.setToCarbohydrates(to);
                break;
            case R.id.highCalories_rbtn: from = 70; to = Double.POSITIVE_INFINITY;
                controllerFilter.setFromCarbohydrates(from);
                controllerFilter.setToCarbohydrates(to);
                break;

        }
    }
}


