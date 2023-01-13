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

import java.util.ArrayList;


public class FragmentCreateRecipe extends Fragment{
    ArrayList<Ingredient> chosenIngredients = new ArrayList<>();
    RecyclerView recyclerView;
    EditText writeName;
    EditText writeTime;
    EditText writeDescription;
    Button add;
    Button ready;
    ImageButton back;
    ImageView image;
    String recipeName;
    String description;
    ImageButton imageButton;
    Fragment fragment;
    Bitmap imageBitmap;
    int cookingTime;

    interface OnRecipeImageClickListener {
        public void setOnClickListener(Fragment fragment);
    }

    interface OnRecipeCreationListener{
        public void removeAndCreateFragmentCreateRecipe();
    }

    interface BackFromCreation{
        public void backToRecipeBook();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (OnRecipeImageClickListener) context;
        removeListener = (OnRecipeCreationListener) context;
        backFromCreation = (BackFromCreation) context;
    }

    OnRecipeCreationListener removeListener;
    OnRecipeImageClickListener listener;
    BackFromCreation backFromCreation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        fragment = this;
        add = view.findViewById(R.id.addIngredientB);
        ready = view.findViewById(R.id.readyB);
        back = view.findViewById(R.id.backB);
        writeName = view.findViewById(R.id.writeName);
        writeTime = view.findViewById(R.id.inputTimeET);
        writeDescription = view.findViewById(R.id.descriptionET);
        imageButton = view.findViewById(R.id.loadImgB);
        //image = view.findViewById(R.id.img);

        if(chosenIngredients.isEmpty()) chosenIngredients.add(new Ingredient());
        if(imageBitmap != null) imageButton.setImageBitmap(imageBitmap);

        recyclerView = view.findViewById(R.id.listOfIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientAdapter ingredientAdapter = new IngredientAdapter(chosenIngredients, getContext());
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
                    int position = ingredientAdapter.getItemCount()-1;
                if(!holder.inputGrams.getText().toString().isEmpty() &&
                        !holder.autoComplete.getText().toString().isEmpty() &&
                        !writeName.getText().toString().isEmpty() &&
                        !writeTime.getText().toString().isEmpty() &&
                        !writeDescription.getText().toString().isEmpty() ||
                        holder.getAdapterPosition() == -1)
                {
                    if(holder.getAdapterPosition() != -1) {
                        String name = holder.autoComplete.getText().toString();
                        double weight = Double.parseDouble(holder.inputGrams.getText().toString());
                        ingredientAdapter.getChosenIngredients().get(position).setName(name);
                        ingredientAdapter.getChosenIngredients().get(position).setWeight(weight);
                    }
                    description = writeDescription.getText().toString();
                    recipeName = writeName.getText().toString();
                    cookingTime = Integer.parseInt(writeTime.getText().toString());
                    ControllerForRecipes.getController().addMyRecipe(getContext(), ControllerUser.getController().getMe().getEmail(), imageBitmap, recipeName, cookingTime, ingredientAdapter.getChosenIngredients(), description);
                    //finishFragment();
                    removeListener.removeAndCreateFragmentCreateRecipe();
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFromCreation.backToRecipeBook();
            }
        });

        return view;
    }

    public void setSelectedImage(Bitmap imageBitmap){
        imageButton.setImageBitmap(imageBitmap);
        this.imageBitmap=imageBitmap;
    }

    public void deleteIngredient(int position){
        chosenIngredients.remove(position);
    }

}