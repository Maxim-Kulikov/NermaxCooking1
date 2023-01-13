package com.example.nermaxcooking1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nermaxcooking.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        FragmentRecipeSorting.Signal,
        FragmentCreateRecipe.OnRecipeImageClickListener,
        FragmentCreateRecipe.OnRecipeCreationListener,
        CaloriesFilterFragment.SignalForMainActivity,
        ProteinsFilterFragment.SignalForMainActivity,
        FatsFilterFragment.SignalForMainActivity,
        CarbohydratesFilterFragment.SignalForMainActivity,
        FragmentChooseFilter.ApplyFilter,
        FragmentChooseFilter.ClearFilter,
        FragmentChooseFilter.BackFromFilter,
        FragmentRecipeBook.SendForRecipeCreation,
        FragmentRecipeBook.SendPositionOfRecipeForEditing,
        FragmentCreateRecipe.BackFromCreation,
        FragmentEditRecipe.BackFromEditRecipe,
        FragmentEditRecipe.OnRecipeCreationListener1,
        FragmentEditRecipe.OnRecipeImageClickListener1,
        FragmentUser.ExitFromFragmentUser,
        FragmentUser.SaveChangesInFragmentUser
{

    private User user = null;
    private final int PICK_IMAGE = 100;
    public HashMap<String,  androidx.fragment.app.Fragment> fragmentsMap;
    private ControllerUser controllerUser = ControllerUser.getController();
    private ControllerForRecipes controllerForRecipes = ControllerForRecipes.getController();
    private ControllerFilter controllerFilter = ControllerFilter.getController();

    @Override
    public void backToRecipeBook() {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentRecipeBook))).commit();
    }

    @Override
    public void backToRecipeBookFromEditRecipe() {
        FragmentRecipeBook fragmentRecipeBook = new FragmentRecipeBook(controllerForRecipes.getRecipes());
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentRecipeBook), fragmentRecipeBook);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentRecipeBook))).commit();
    }

    @Override
    public void exitFromFragmentUser() {
        controllerUser.setMe(null);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentUserEnter))).commit();
    }

    @Override
    public void saveChangesInFragmentUser(User user) {
        controllerUser.updateUserInDB(this, user);
    }

    enum FragmentsEnum{
        FragmentRecipeBook,
        FragmentMainPanel,
        FragmentCreateRecipe,
        FragmentUserEnter,
        FragmentUser,
        FragmentAppBarHome,
        FragmentChooseFilter,
        FragmentSearch,
        FragmentEditRecipe
    }


    @Override
    public void sendSortingSignal(ArrayList<Recipe> recipes) {
        System.out.println("Вернулся в активити после кнопки сортировка");
        FragmentAppBarHome fragmentAppBarHome = new FragmentAppBarHome(recipes);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentAppBarHome)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void backFromCaloriesFilter() {
       goToChooseFilter();
    }

    @Override
    public void backFromFatsFilter() {
        goToChooseFilter();
    }

    @Override
    public void backFromProteinsFilter() {
        goToChooseFilter();
    }

    @Override
    public void backFromCarbohydratesFilter() {
        goToChooseFilter();
    }

    @Override
    public void applyFilter() {
        ArrayList<Recipe> recipes = ControllerForRecipes.filterRecipes(
                controllerForRecipes.getRecipes(),
                controllerFilter.getFromCall(), controllerFilter.getToCall(),
                controllerFilter.getFromProteins(), controllerFilter.getToProteins(),
                controllerFilter.getFromFats(), controllerFilter.getToFats(),
                controllerFilter.getFromCarbohydrates(), controllerFilter.getToCarbohydrates());
        FragmentAppBarHome fragmentAppBarHome = new FragmentAppBarHome(recipes);
        System.out.println(controllerFilter.getFromCall() + " " + controllerFilter.getToCall() + "\n" +
                controllerFilter.getFromProteins() + " " + controllerFilter.getToProteins() + "\n" +
                controllerFilter.getFromFats() + " " + controllerFilter.getToFats() + "\n" +
                controllerFilter.getFromCarbohydrates() + " " + controllerFilter.getToCarbohydrates() + "\n");
        System.out.println("Размер " + recipes.size());
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentAppBarHome), fragmentAppBarHome);
        goToFragmentAppBarHome();
    }

    @Override
    public void clearFilter() {
        FragmentAppBarHome fragmentAppBarHome = new FragmentAppBarHome(controllerForRecipes.getRecipes());
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentAppBarHome), fragmentAppBarHome);
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentChooseFilter), new FragmentChooseFilter());
        controllerFilter.clear();
        goToFragmentAppBarHome();
    }

    @Override
    public void backFromFilter() {
        goToFragmentAppBarHome();
    }

    @Override
    public void goToEditRecipe(int position, ArrayList<Recipe> recipes) {
        FragmentEditRecipe fragmentEditRecipe = new FragmentEditRecipe(recipes.get(position));
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentEditRecipe), fragmentEditRecipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentEditRecipe)))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToRecipeCreation() {
        FragmentCreateRecipe fragmentCreateRecipe = new FragmentCreateRecipe();
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentCreateRecipe), fragmentCreateRecipe);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentCreateRecipe)))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void removeAndCreateFragmentCreateRecipe() {
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentCreateRecipe), new FragmentCreateRecipe());
        FragmentAppBarHome fragmentAppBarHome = new FragmentAppBarHome(controllerForRecipes.getRecipes());
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentAppBarHome), fragmentAppBarHome);
        FragmentRecipeBook fragmentRecipeBook = new FragmentRecipeBook(controllerForRecipes.getRecipes());
        fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentRecipeBook), fragmentRecipeBook);
        goToFragmentRecipeBook();
    }

    @Override
    protected void onStart() {
        super.onStart();
        controllerForRecipes.setActivity(this);
     }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("пиздец11111111111111111111111111111");
        if(controllerForRecipes.getRecipes().size()!=0) {
            fragmentsMap = new HashMap<>();
            System.out.println(Data.IP);
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentSearch), new FragmentSearch(controllerForRecipes.getRecipes()));
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentMainPanel), new FragmentMainPanel());
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentUserEnter), new FragmentUserEnter());
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentUser), new FragmentUser());
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentAppBarHome), new FragmentAppBarHome(controllerForRecipes.getRecipes()));
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentChooseFilter), new FragmentChooseFilter());
            fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentCreateRecipe), new FragmentCreateRecipe());
            goToFragmentAppBarHome();
        }

        ImageButton
                homeButton = findViewById(R.id.ButtonHome),
                searchButton = findViewById(R.id.ButtonSearch),
                recipeBookButton = findViewById(R.id.ButtonRecipeBook),
                userButton = findViewById(R.id.ButtonUser);

        homeButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        recipeBookButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ButtonHome:
                    goToFragmentAppBarHome();
                break;
            case R.id.ButtonSearch:
                    goToFragmentSearch();
                break;
            case R.id.ButtonRecipeBook:
                    goToFragmentRecipeBook();
                break;
//            case R.id.ButtonMessages:
  //              break;
            case R.id.ButtonUser:
                    goToFragmentUser();
            default:
        }
    }

    @Override
    public void setOnClickListener(androidx.fragment.app.Fragment fragment) {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        Uri imageUri = null;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && reqCode == 100){
            imageUri = data.getData();

            try{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.getContentResolver(), imageUri));
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 2000, true);
            FragmentCreateRecipe fragmentCreateRecipe = (FragmentCreateRecipe) fragmentsMap.get(FragmentsEnum.FragmentCreateRecipe.toString());
            fragmentCreateRecipe.setSelectedImage(bitmap);
        }
    }

    private void goToFragmentRecipeBook(){
        if(controllerUser.getMe() == null){
            goToFragmentUserEnter();
        } else {
            if(fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentRecipeBook)) == null)
                fragmentsMap.put(String.valueOf(FragmentsEnum.FragmentRecipeBook), new FragmentRecipeBook(controllerForRecipes.getRecipes()));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentRecipeBook)))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void goToFragmentUserEnter(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentUserEnter)))
                .addToBackStack(null)
                .commit();
    }

    private void goToFragmentUser(){
        if(controllerUser.getMe() == null){
            goToFragmentUserEnter();
        }
        else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentUser)))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void goToFragmentAppBarHome(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentAppBarHome)))
                .addToBackStack(null)
                .commit();
    }

    private void goToChooseFilter(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentChooseFilter)))
                .addToBackStack(null)
                .commit();
    }

    private void goToFragmentSearch(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragmentsMap.get(String.valueOf(FragmentsEnum.FragmentSearch)))
                .addToBackStack(null)
                .commit();
    }
}