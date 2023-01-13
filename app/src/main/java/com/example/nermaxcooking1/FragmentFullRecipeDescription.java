package com.example.nermaxcooking1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

public class FragmentFullRecipeDescription extends Fragment {
    ImageView recipeImage;
    TextView recipeName;
    TextView recipeComposition;
    TextView recipeDescription;
    TextView recipeWCPFC;
    TextView cookingTime;
    Recipe recipe;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_recipe_description, container, false);
        recipeImage = view.findViewById(R.id.recipeImageIV);
        recipeName = view.findViewById(R.id.recipeNameTV);
        recipeComposition = view.findViewById(R.id.compositionTV);
        recipeDescription = view.findViewById(R.id.recipeDescriptionTV);
        recipeWCPFC = view.findViewById(R.id.WCPFC);
        cookingTime = view.findViewById(R.id.cookingTimeTV);

        recipeImage.setImageResource(R.drawable.ic_add);
        recipeName.setText(recipe.getName());
        recipeComposition.setText(recipe.getCompositionForUser());
        recipeWCPFC.setText(recipe.getWeight() + "\n" + (int) recipe.getCalories() + "/" + (int) recipe.getProteins() + "/" + (int) recipe.getProteins() + "/" + (int) recipe.getCarbohydrates());
        cookingTime.setText(String.valueOf(recipe.getCookingTime()));
        recipeDescription.setText(recipe.getDescription());

        return view;
    }

    public void setRecipe(Recipe recipe){
        this.recipe = recipe;
    }
}