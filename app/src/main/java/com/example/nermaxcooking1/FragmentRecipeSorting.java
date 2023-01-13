package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class FragmentRecipeSorting extends Fragment implements View.OnClickListener {
    ArrayList<Recipe> recipes;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        signal = (Signal) context;
    }

    interface Signal{
        void sendSortingSignal(ArrayList<Recipe> recipes);
    }

    Signal signal;

    public void setRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_sorting, container, false);
        ImageButton imageButton = view.findViewById(R.id.back_btn);
     /*   imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("нажал на назад в сортировке");
                signal.sendSortingSignal(recipes);
            }
        });*/
        RadioButton radioButton = view.findViewById(R.id.SortUpCalories),
        radioButton1 = view.findViewById(R.id.SortDownCalories),
        radioButton2 = view.findViewById(R.id.SortUpProteins),
        radioButton3 = view.findViewById(R.id.SortDownProteins),
        radioButton4 = view.findViewById(R.id.SortUpFats),
        radioButton5 = view.findViewById(R.id.SortDownFats),
        radioButton6 = view.findViewById(R.id.SortUpCarbohydrates),
        radioButton7 = view.findViewById(R.id.SortDownCarbohydrates),
        radioButton8 = view.findViewById(R.id.SortUpCookingTime),
        radioButton9 = view.findViewById(R.id.SortDownCookingTime);

        imageButton.setOnClickListener(this);
        radioButton.setOnClickListener(this);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        radioButton5.setOnClickListener(this);
        radioButton6.setOnClickListener(this);
        radioButton7.setOnClickListener(this);
        radioButton8.setOnClickListener(this);
        radioButton9.setOnClickListener(this);

        return view;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.SortUpCalories:
                System.out.println("сортировка...");
                recipes = ControllerForRecipes.sortUpCalories(recipes);
                break;
            case R.id.SortDownCalories:
                recipes = ControllerForRecipes.sortDownCalories(recipes);
                break;
            case R.id.SortUpProteins:
                recipes = ControllerForRecipes.sortUpProteins(recipes);
                break;
            case R.id.SortDownProteins:
                recipes = ControllerForRecipes.sortDownProteins(recipes);
                break;
            case R.id.SortUpFats:
                recipes = ControllerForRecipes.sortUpFats(recipes);
                break;
            case R.id.SortDownFats:
                recipes = ControllerForRecipes.sortDownFats(recipes);
                break;
            case R.id.SortUpCarbohydrates:
                recipes = ControllerForRecipes.sortUpCarbohydrates(recipes);
                break;
            case R.id.SortDownCarbohydrates:
                recipes = ControllerForRecipes.sortDownCarbohydrates(recipes);
                break;
            case R.id. SortUpCookingTime:
                recipes = ControllerForRecipes.sortUpCookingTime(recipes);
                break;
            case R.id.SortDownCookingTime:
                recipes = ControllerForRecipes.sortDownCookingTime(recipes);
                break;
            case R.id.back_btn:
                signal.sendSortingSignal(recipes);
            }
    }
}