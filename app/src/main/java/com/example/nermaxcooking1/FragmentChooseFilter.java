package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

import java.util.HashMap;

public class FragmentChooseFilter extends Fragment{

    Button clear, calories, proteins, fats, carbohydrates, apply;
    TextView caloriesTV, proteinsTV, fatsTV, carbohydratesTV;
    ImageButton back;
    HashMap<String, Fragment> fragments = new HashMap<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        applyFilter = (ApplyFilter) context;
        clearFilter = (ClearFilter) context;
        backFromFilter = (BackFromFilter) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments.put("CaloriesFilter", new CaloriesFilterFragment());
        fragments.put("ProteinsFilter", new ProteinsFilterFragment());
        fragments.put("FatsFilter", new FatsFilterFragment());
        fragments.put("CarbohydratesFilter", new CarbohydratesFilterFragment());
    }

    interface ApplyFilter{
        void applyFilter();
    }
    ApplyFilter applyFilter;

    interface ClearFilter{
        void clearFilter();
    }
    ClearFilter clearFilter;

    interface BackFromFilter{
        void backFromFilter();
    }
    BackFromFilter backFromFilter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_filter, container, false);
        clear = view.findViewById(R.id.clear);
        calories = view.findViewById(R.id.caloriesBtn);
        proteins = view.findViewById(R.id.proteinsBtn);
        fats = view.findViewById(R.id.fatsBtn);
        carbohydrates = view.findViewById(R.id.carbohydratesBtn);
        caloriesTV = view.findViewById(R.id.caloriesTV);
        proteinsTV = view.findViewById(R.id.proteinsTV);
        fatsTV = view.findViewById(R.id.fatsTV);
        carbohydratesTV = view.findViewById(R.id.carbohydratesTV);
        apply = view.findViewById(R.id.applyFilters);
        back = view.findViewById(R.id.back_btn);
        showFilters();

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilter.clearFilter();
            }
        });
        calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             goToFragment(fragments.get("CaloriesFilter"));
            }
        });
        proteins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(fragments.get("ProteinsFilter"));
            }
        });
        fats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(fragments.get("FatsFilter"));
            }
        });
        carbohydrates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(fragments.get("CarbohydratesFilter"));
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter.applyFilter();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFromFilter.backFromFilter();
            }
        });


        return view;
    }

    private void goToFragment(Fragment fragment){
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void showFilters(){
        ControllerFilter controller = ControllerFilter.getController();
        caloriesTV.setText("от " + controller.getFromCall() + " до " + controller.getToCall());
        proteinsTV.setText("от " + controller.getFromProteins() + " до " + controller.getToProteins());
        fatsTV.setText("от " + controller.getFromFats() + " до " + controller.getToFats());
        carbohydratesTV.setText("от " + controller.getFromCarbohydrates() + " до " + controller.getToCarbohydrates());
    }

}