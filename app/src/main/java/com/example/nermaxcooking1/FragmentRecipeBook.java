package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

import java.util.ArrayList;

public class FragmentRecipeBook extends Fragment{
    ArrayList<Recipe> recipes;
    Button addButton;

    FragmentRecipeBook(ArrayList<Recipe> recipes){
        this.recipes = ControllerForRecipes.filterUserRecipes(recipes, ControllerUser.getController().getMe().getEmail());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sender1 = (SendPositionOfRecipeForEditing) context;
        sender2 = (SendForRecipeCreation) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender2.goToRecipeCreation();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.scrollRecipesList);
        RecipeAdapter.OnRecipeClickListener onRecipeClickListener = new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(int position) {
                sender1.goToEditRecipe(position, recipes);
            }
        };
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), onRecipeClickListener, recipes);
        recyclerView.setAdapter(recipeAdapter);

        return view;
    }

    interface SendPositionOfRecipeForEditing{
        void goToEditRecipe(int position, ArrayList<Recipe> recipes);
    }
    SendPositionOfRecipeForEditing sender1;

    interface SendForRecipeCreation{
        void goToRecipeCreation();
    }
    SendForRecipeCreation sender2;
}