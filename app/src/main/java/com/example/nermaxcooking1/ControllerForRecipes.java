package com.example.nermaxcooking1;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ControllerForRecipes {
    private static ControllerForRecipes controllerForRecipes;
    private HashMap<String, Ingredient> ingredients;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Activity activity;

    private ControllerForRecipes(){
        getIngredientsFromDB();
        getRecipesFromDB();
    }


    private void getRecipesFromDB() {
        final String MYURLFORGETTINGINRGEDIENTS = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/RecipesServlet";


        RequestParams params = new RequestParams();;
        AsyncHttpClient client = new AsyncHttpClient();;
        recipes = new ArrayList<>();
        client.get(MYURLFORGETTINGINRGEDIENTS, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                //    System.out.println(response.getString("name" + 1) + response.getString("photo1") + response.getString("composition1"));
                    System.out.println("размер: " + response.getInt("size"));
                    for (int i = 0; i<response.getInt("size"); i++) {
                        HashMap<String, String> recipeMap = new HashMap<>();;
                        for(Recipe.Enum param: Recipe.Enum.values()){
                            recipeMap.put(String.valueOf(param), response.getString(String.valueOf(param) + i));
                            System.out.println(response.getString(String.valueOf(param)+i));
                        }
                        recipeMap.put("id", response.getString("id" + i));
                        System.out.println(response.getString("id"+i));
                        recipes.add(new Recipe(recipeMap));

                    }
                    System.out.println("Рецепты получены успешно!");
                }catch(Exception e){
                    System.out.println("Ошибка при получении рецептов!");
                    e.printStackTrace();
                }
                if(activity!=null) activity.recreate();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static synchronized ControllerForRecipes getController(){
        if(controllerForRecipes == null) {
            controllerForRecipes = new ControllerForRecipes();
        }
        return controllerForRecipes;
    }

    public void setActivity(Activity activity) {
        if(this.activity==null) {
            this.activity = activity;
        }
    }

    private void getIngredientsFromDB(){
        final String MYURLFORGETTINGINRGEDIENTS = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/IngredientsServlet";


            RequestParams params = new RequestParams();;
            AsyncHttpClient client = new AsyncHttpClient();;
            ingredients = new HashMap<>();

            client.post(MYURLFORGETTINGINRGEDIENTS, params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                    super.onSuccess(statusCode, headers, response);
                    try {
                        System.out.println(response.getString("name" + 1) + response.getDouble("calories1") + response.getDouble("proteins1"));

                        for (int i = 0; i<response.getInt("size"); i++) {
                            Ingredient ingredient = new Ingredient();
                            ingredient.setName(response.getString("name" + Integer.toString(i)));
                            ingredient.setCalories(response.getDouble("calories" + Integer.toString(i)));
                            ingredient.setProteins(response.getDouble("proteins" + Integer.toString(i)));
                            ingredient.setFats(response.getDouble("fats" + Integer.toString(i)));
                            ingredient.setCarbohydrates(response.getDouble("carbohydrates" + Integer.toString(i)));
                            ingredients.put(response.getString("name" + Integer.toString(i)), ingredient);
                        }
                        System.out.println("Овощи получены успешно!");
                    }catch(Exception e){
                        System.out.println("Ошибка при получении овощей!");
                    }
                    if(activity!=null) activity.recreate();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });

    }

    public HashMap<String, Ingredient> getIngredients(){
        if(ingredients == null)
            getIngredientsFromDB();
        return ingredients;
    }

    public ArrayList<Recipe> getRecipes(){
        if(recipes == null)
            getRecipesFromDB();
        return recipes;
    }

    public void addMyRecipe(Context context, String email, Bitmap photo, String name, int cookingTime, ArrayList<Ingredient> chosenIngredients, String description){
        Recipe recipe = new Recipe(/*new String(getByteArrayFromBitmap(photo), StandardCharsets.UTF_8)*/"photo", cookingTime, email, name, chosenIngredients, description);
        addRecipeInArray(recipe);
        System.out.println("Тест: " + recipes.get(recipes.size()-1).getCookingTime());
        final String MYURLFORPOSTRECIPE = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/RecipesServlet";

        RequestParams params = new RequestParams();
        for(Map.Entry<String, String> entry: recipe.getMapForJSON().entrySet()){
            params.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(MYURLFORPOSTRECIPE, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_RECIPE_CREATION.getCode()) {
                        recipe.setId(response.getInt("id"));
                        addRecipeInArray(recipe);
                        Toast.makeText(context, Errors.SUCCESSFUL_RECIPE_CREATION.getMessage(), Toast.LENGTH_SHORT).show();
                        } else  Toast.makeText(context, Errors.NON_SUCCESSFUL_RECIPE_CREATION.getMessage(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(context, Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void addRecipe(String email, int photo, String name, int cookingTime, ArrayList<Ingredient> ingredients){
        //запрос в бд
    }

    public void addRecipeInArray(Recipe recipe){
        this.recipes.add(recipe);
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    public static Bitmap getBitmapFromByteArray(byte[] bitmap){
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }

    public static ArrayList<Recipe> sortUpCalories(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CaloriesComparator);
        return recipes;
    }

    public static ArrayList<Recipe> sortDownCalories(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CaloriesComparator);
        reverse(recipes);
        return recipes;
    }

    public static ArrayList<Recipe> sortUpProteins(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.ProteinsComparator);
        return recipes;
    }

    public static ArrayList<Recipe> sortDownProteins(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.ProteinsComparator);
        reverse(recipes);
        return recipes;
    }

    public static ArrayList<Recipe> sortUpFats(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.FatsComparator);
        return recipes;
    }

    public static ArrayList<Recipe> sortDownFats(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.FatsComparator);
        reverse(recipes);
        return recipes;
    }

    public static ArrayList<Recipe> sortUpCarbohydrates(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CarbohydratesComparator);
        return recipes;
    }

    public static ArrayList<Recipe> sortDownCarbohydrates(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CarbohydratesComparator);
        reverse(recipes);
        return recipes;
    }

    public static ArrayList<Recipe> sortUpCookingTime(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CookingTimeComparator);
        return recipes;
    }

    public static ArrayList<Recipe> sortDownCookingTime(ArrayList<Recipe> recipes){
        sort(recipes, Recipe.CookingTimeComparator);
        reverse(recipes);
        return recipes;
    }


    public static ArrayList<Recipe> filterRecipes(ArrayList<Recipe> recipes,
                                                  double fromCall, double toCall,
                                                  double fromProteins, double toProteins,
                                                  double fromFats, double toFats,
                                                  double fromCarbohydrates, double toCarbohydrates)
    {
        ArrayList<Recipe> newArray = new ArrayList<>();
        for(Recipe recipe: recipes){
                if((recipe.getCalories()>=fromCall && recipe.getCalories()<=toCall) &&
                        (recipe.getProteins()>=fromProteins && recipe.getProteins()<=toProteins) &&
                        (recipe.getFats()>=fromFats && recipe.getFats()<=toFats) &&
                        (recipe.getCarbohydrates()>=fromCarbohydrates && recipe.getFats()<=toCarbohydrates))
                    newArray.add(recipe);
        }
        return newArray;
    }

    public static ArrayList<Recipe> filterRecipes(ArrayList<Recipe> recipes, String recipeName){
        if(recipeName.isEmpty())
            return ControllerForRecipes.getController().getRecipes();
        ArrayList<Recipe> newArray = new ArrayList<>();
        for(Recipe recipe: recipes){
            if(recipe.getName().contains(recipeName))
                newArray.add(recipe);
        }
        return newArray;
    }



    public ArrayList<Recipe> filterRecipes1(String recipeName){
        if(recipeName.isEmpty())
            recipeName = "*";
        final String MYURLFORGETTINGINRGEDIENTS = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/RecipeByNameServlet";


        RequestParams params = new RequestParams();;
        AsyncHttpClient client = new AsyncHttpClient();
        ArrayList<Recipe> recipes1 = new ArrayList<>();

        client.get(MYURLFORGETTINGINRGEDIENTS, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    for (int i = 0; i<response.getInt("size"); i++) {
                        for(Recipe.Enum params: Recipe.Enum.values()){

                        }
                    }
                    System.out.println("Овощи получены успешно!");
                }catch(Exception e){
                    System.out.println("Ошибка при получении овощей!");
                }
                if(activity!=null) activity.recreate();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


        ArrayList<Recipe> newArray = new ArrayList<>();
        return newArray;
    }

    public static ArrayList<Recipe> filterUserRecipes(ArrayList<Recipe> recipes, String userName){
        ArrayList<Recipe> arr = new ArrayList<>();
        System.out.println(userName);
        System.out.println(recipes.size());
        int i = 0;
        for(Recipe recipe: recipes){
            if(recipe.getEmail().equals(userName)){
                arr.add(recipe);
            }

            System.out.println(i);
            i++;
        }
        return arr;
    }

    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }

    public void deleteRecipe(int id){
        String URL = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/DeleteRecipeServlet";
        RequestParams params;
        AsyncHttpClient client;

        params = new RequestParams();
        params.put("id", id);

        client = new AsyncHttpClient();
        client.post(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_RECIPE_DELETE.getCode())
                        Toast.makeText(activity, Errors.SUCCESSFUL_RECIPE_DELETE.getMessage(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(activity, Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteRecipeFromArray(int id){
        for(int i = 0; i<recipes.size(); i++){
            if(recipes.get(i).getId() == id){
                this.recipes.remove(i);
            }
        }
    }

    public void updateRecipeInDB(Recipe recipe){
        String URL = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/UpdateRecipeServlet";
        RequestParams params;
        AsyncHttpClient client;

        params = new RequestParams();
        params.put("id", recipe.getId());

        for(Map.Entry<String, String> param: recipe.getMapForJSON().entrySet()){
            params.put(param.getKey(), param.getValue());
        }

        client = new AsyncHttpClient();
        client.post(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_RECIPE_UPDATE.getCode())
                        Toast.makeText(activity, Errors.SUCCESSFUL_RECIPE_UPDATE.getMessage(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(activity, Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
