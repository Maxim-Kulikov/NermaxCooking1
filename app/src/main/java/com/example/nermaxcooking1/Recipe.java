package com.example.nermaxcooking1;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Recipe implements Serializable{
    private int id;
    private String email;
    private String photo;
    private String name;
    //private double rating;
    //private int voices;
    private int cookingTime;
    private double weight;
    private double calories;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private ArrayList<Ingredient> chosenIngredients = new ArrayList<>();
    private String composition;
    private String description;
    private HashMap<String, String> mapForJSON;
    private Bitmap imageBitmap;


    public enum Enum{
        email,
        photo,
        name,
        cookingTime,
        weight,
        calories,
        proteins,
        fats,
        carbohydrates,
        composition,
        description
    }

    public Recipe(){}

    public Recipe(HashMap<String, String> map){
        this.mapForJSON = map;
        unpackHashMap(map);
    }

    public Recipe(JSONObject jsonObject){
        try {
            mapForJSON = new HashMap<>();
            for (Enum params : Enum.values()) {
                mapForJSON.put(String.valueOf(params), jsonObject.getString(String.valueOf(params)));
            }
            mapForJSON.put("id", jsonObject.getString("id"));
            unpackHashMap(mapForJSON);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void unpackHashMap(HashMap<String, String> map){
        this.email = map.get(String.valueOf(Enum.email));
        this.photo = map.get(String.valueOf(Enum.photo));
        this.name = map.get(String.valueOf(Enum.name));
        this.cookingTime = Integer.parseInt(map.get(String.valueOf(Enum.cookingTime)));
        this.weight = Double.parseDouble(map.get(String.valueOf(Enum.weight)));
        this.calories = Double.parseDouble(map.get(String.valueOf(Enum.calories)));
        this.proteins = Double.parseDouble(map.get(String.valueOf(Enum.proteins)));
        this.fats = Double.parseDouble(map.get(String.valueOf(Enum.fats)));
        this.carbohydrates = Double.parseDouble(map.get(String.valueOf(Enum.carbohydrates)));
        this.composition = map.get(String.valueOf(Enum.composition));
        this.description = map.get(String.valueOf(Enum.description));
        if(map.get("id") != null) this.id = Integer.parseInt(map.get("id"));
        unpackCompositionToIngredients();
    }

    Recipe(String photo, int cookingTime, String email, String name, ArrayList<Ingredient> chosenIngredients, String description){
       mapForJSON = new HashMap<>();
       this.photo = photo;
       this.chosenIngredients = chosenIngredients;
       this.description = description;
       this.cookingTime = cookingTime;
       this.name = name;
       this.email = email;
        setWeight();
        setCalories();
        setProteins();
        setFats();
        setCarbohydrates();
        setComposition();

       mapForJSON.put("photo", "photo");
       mapForJSON.put("cookingTime", Integer.toString(cookingTime));
       mapForJSON.put("email", email);
       mapForJSON.put("name", name);
       mapForJSON.put("description", description);
       mapForJSON.put("composition", composition);
       mapForJSON.put("weight", Double.toString(weight));
       mapForJSON.put("calories", Double.toString(calories));
       mapForJSON.put("proteins", Double.toString(proteins));
       mapForJSON.put("fats", Double.toString(fats));
       mapForJSON.put("carbohydrates", Double.toString(carbohydrates));
    }

    public Bitmap getBitmap() {
        return imageBitmap;
    }

    public void setBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public HashMap<String, String> getMapForJSON(){
        return mapForJSON;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime){
        this.cookingTime = cookingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getWeight() {
        return weight;
    }

    private void setWeight() {
        this.weight = 0;

        for(Ingredient i: chosenIngredients){
            this.weight+=i.getWeight();
        }
    }

    public double getCalories() {
        return calories;
    }

    private void setCalories() {
        this.calories = 0;

        for(Ingredient i: chosenIngredients){
            this.calories+=ControllerForRecipes.getController().getIngredients().get(i.getName()).getCalories()*(i.getWeight()/100);
        }

        calories=calories*100/weight;
    }

    public ArrayList<Ingredient> getIngredients() {
        return chosenIngredients;
    }

    public double getProteins() {
        return proteins;
    }

    private void setProteins() {
        this.proteins = 0;

        for(Ingredient i: chosenIngredients){
            this.proteins+=ControllerForRecipes.getController().getIngredients()
                    .get(i.getName())
                    .getProteins()
                    *(i.getWeight()/100);
        }

        proteins=proteins/weight*100;
    }

    public double getFats() {
        return fats;
    }

    private void setFats() {
        this.fats = 0;

        for(Ingredient i: chosenIngredients){
            this.proteins+=ControllerForRecipes.getController().getIngredients()
                    .get(i.getName())
                    .getFats()
                    *(i.getWeight()/100);
        }
        fats=fats/weight*100;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    private void setCarbohydrates() {
        this.carbohydrates = 0;

        for(Ingredient i: chosenIngredients){
            this.carbohydrates += ControllerForRecipes.getController().getIngredients()
                    .get(i.getName())
                    .getCarbohydrates()
                    *(i.getWeight()/100);
        }

        carbohydrates=carbohydrates/weight*100;
    }

   public void setComposition(){
        composition = "";
        for(Ingredient ingredient: chosenIngredients){
            composition+=(ingredient.getName() + "-" + ingredient.getWeight() + "-");
        }
    }

    public String getComposition(){
        return composition;
    }

    public String getCompositionForUser(){
        String string = "";
        for(Ingredient ingredient: chosenIngredients){
            string+=ingredient.getName() + ": " + ingredient.getWeight() + " гр.\n";
        }
        return string;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Comparator<Recipe> CaloriesComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return (int) (o1.getCalories() - o2.getCalories());
        }
    };

    public static Comparator<Recipe> ProteinsComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return (int) (o1.getProteins() - o2.getProteins());
        }
    };

    public static Comparator<Recipe> FatsComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return (int) (o1.getFats() - o2.getFats());
        }
    };

    public static Comparator<Recipe> CarbohydratesComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return (int) (o1.getCarbohydrates() - o2.getCarbohydrates());
        }
    };

    public static Comparator<Recipe> CookingTimeComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return (int) (o1.getCookingTime() - o2.getCookingTime());
        }
    };

    private void unpackCompositionToIngredients(){
        String[] strings = composition.split("-");
        Ingredient ingredient = new Ingredient();

        for(int i = 0; i<strings.length; i++){
            if(i%2==0){
                System.out.println(strings[i] + " ");
                ingredient = ControllerForRecipes.getController().getIngredients().get(strings[i]);
            }else {
                System.out.println(strings[i] + '\n');
                ingredient.setWeight(Double.parseDouble(strings[i]));
                this.chosenIngredients.add(ingredient);
                ingredient = new Ingredient();
            }
        }
    }

    public ArrayList<Ingredient> getChosenIngredients(){
        return chosenIngredients;
    }

    public void setChosenIngredient(Ingredient ingredient){
        this.chosenIngredients.add(ingredient);
    }

    public void setChosenIngredients(ArrayList<Ingredient> ingredients){
        this.chosenIngredients = ingredients;
    }

    public void deleteChosenIngredient(int position){
        this.chosenIngredients.remove(position);
    }

    public void addChosenIngredient(Ingredient ingredient){
        this.chosenIngredients.add(ingredient);
    }

    public void updateChosenIngredient(int position, Ingredient ingredient){
        this.chosenIngredients.set(position, ingredient);
    }
}
