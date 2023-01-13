package com.example.nermaxcooking1;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public class ControllerMainActivity {
    private static ControllerMainActivity controllerMainActivity;
    private Activity activity;
    private Fragment currentFragment;
    private int containerViewId;

    private ControllerMainActivity(Activity activity, Fragment currentFragment, int containerViewId){
        this.activity = activity;
        this.currentFragment = currentFragment;
        this.containerViewId = containerViewId;
    }

    public synchronized ControllerMainActivity getController(Activity activity, Fragment currentFragment, int containerViewId){
        if(controllerMainActivity == null)
            return new ControllerMainActivity(activity, currentFragment, containerViewId);
        else return controllerMainActivity;
    }

    public void goToRecipeFragment(){}




}
