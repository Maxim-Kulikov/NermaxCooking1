package com.example.nermaxcooking1;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nermaxcooking.R;

public class FragmentEditRecipe extends Fragment {
    //ArrayList<Ingredient> chosenIngredients = new ArrayList<>();
    RecyclerView recyclerView;
    EditText writeName;
    EditText writeTime;
    EditText writeDescription;
    Button add;
    Button ready;
    Button delete;
    ImageButton back;
    ImageView image;
    ImageButton imageButton;
    Fragment fragment;
    Recipe recipe;

    FragmentEditRecipe(Recipe recipe){
        this.recipe = recipe;
    }

    interface OnRecipeImageClickListener1 {
        public void setOnClickListener(Fragment fragment);
    }

    interface OnRecipeCreationListener1{
        public void removeAndCreateFragmentCreateRecipe();
    }

    interface BackFromEditRecipe{
        void backToRecipeBookFromEditRecipe();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnRecipeImageClickListener1) context;
        removeListener = (OnRecipeCreationListener1) context;
        backFromEditRecipe = (BackFromEditRecipe) context;
    }

    OnRecipeCreationListener1 removeListener;
    OnRecipeImageClickListener1 listener;
    BackFromEditRecipe backFromEditRecipe;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);
        fragment = this;
        recyclerView = view.findViewById(R.id.listOfIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        delete = view.findViewById(R.id.deleteB);
        back = view.findViewById(R.id.backB);
        add = view.findViewById(R.id.addIngredientB);
        ready = view.findViewById(R.id.readyB);
        writeName = view.findViewById(R.id.writeName);
        writeTime = view.findViewById(R.id.inputTimeET);
        writeDescription = view.findViewById(R.id.descriptionET);
        imageButton = view.findViewById(R.id.loadImgB);

        if(this.recipe!=null) {
            //image = view.findViewById(R.id.img);
            writeName.setText(recipe.getName());
            writeTime.setText(String.valueOf(recipe.getCookingTime()));
            writeDescription.setText(recipe.getDescription());

            if (recipe.getBitmap() != null) imageButton.setImageBitmap(recipe.getBitmap());
        }
        IngredientAdapter ingredientAdapter = new IngredientAdapter(recipe.getChosenIngredients(), getContext());
        recyclerView.setAdapter(ingredientAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientAdapter.ViewHolder holder = ingredientAdapter.getHolder();
                System.out.println("adapterposition in add " + holder.getAdapterPosition());

                if(
                        (!holder.inputGrams.getText().toString().isEmpty() && !holder.autoComplete.getText().toString().isEmpty()) ||
                               holder.getAdapterPosition() == -1
                ) {
                    if(holder.getAdapterPosition() != -1) {
                        String name = holder.autoComplete.getText().toString();
                        double weight = Double.parseDouble(holder.inputGrams.getText().toString());
                        int position = ingredientAdapter.getItemCount() - 1;
                        Ingredient ingredient = ingredientAdapter.getChosenIngredients().get(position);
                        ingredient.setName(name);
                        ingredient.setWeight(weight);
                        ingredientAdapter.updateChosenIngredientAt(position, ingredient);
                    }
                    ingredientAdapter.addIngredient(new Ingredient());
                    ingredientAdapter.notifyItemInserted(ingredientAdapter.getItemCount()-1);
                }
            }
        });
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientAdapter.ViewHolder holder = ingredientAdapter.getHolder();
                System.out.println("adapterposition in ready " + holder.getAdapterPosition());
                int position = ingredientAdapter.getItemCount() - 1;
                if(
                        !ingredientAdapter.areIngredientsEmpty() &&
                        !holder.inputGrams.getText().toString().isEmpty() &&
                        !holder.autoComplete.getText().toString().isEmpty() &&
                        !writeName.getText().toString().isEmpty() &&
                        !writeTime.getText().toString().isEmpty() &&
                        !writeDescription.getText().toString().isEmpty() ||
                                holder.getAdapterPosition() == -1
                )
                {
                    if(holder.getAdapterPosition() != -1) {
                        String name = holder.autoComplete.getText().toString();
                        double weight = Double.parseDouble(holder.inputGrams.getText().toString());
                        ingredientAdapter.getChosenIngredients().get(position).setName(name);
                        ingredientAdapter.getChosenIngredients().get(position).setWeight(weight);
                    }
                    //recipe.getChosenIngredients().get(position).setName(name);
                    //recipe.getChosenIngredients().get(position).setWeight(weight);
                    recipe.setDescription(writeDescription.getText().toString());
                    recipe.setName(writeName.getText().toString());
                    recipe.setCookingTime(Integer.parseInt(writeTime.getText().toString()));
                    recipe.setChosenIngredients(ingredientAdapter.getChosenIngredients());

                    Recipe updatedRecipe = new Recipe("photo", recipe.getCookingTime(), recipe.getEmail(), recipe.getName(), recipe.getChosenIngredients(), recipe.getDescription());
                    updatedRecipe.setId(recipe.getId());

                    ControllerForRecipes.getController().deleteRecipeFromArray(recipe.getId());
                    ControllerForRecipes.getController().addRecipeInArray(updatedRecipe);
                    ControllerForRecipes.getController().updateRecipeInDB(updatedRecipe);
                    backFromEditRecipe.backToRecipeBookFromEditRecipe();
                } else
                    Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickListener(fragment);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ControllerForRecipes.getController().deleteRecipe(recipe.getId());
                ControllerForRecipes.getController().deleteRecipeFromArray(recipe.getId());
                backFromEditRecipe.backToRecipeBookFromEditRecipe();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFromEditRecipe.backToRecipeBookFromEditRecipe();
            }
        });

        return view;
    }

    public void setSelectedImage(Bitmap imageBitmap){
        imageButton.setImageBitmap(imageBitmap);
        this.recipe.setBitmap(imageBitmap);
    }



    private void finishFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

}