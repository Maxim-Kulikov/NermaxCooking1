package com.example.nermaxcooking1;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class ControllerUser {
    private static ControllerUser controllerUser;
    private User me;

    private ControllerUser(){
        checkAuthorization();
    }

    public static synchronized ControllerUser getController(){
        if(controllerUser == null) {
            controllerUser = new ControllerUser();
        }
        return controllerUser;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public void updateUserInDB(Activity activity, User user){
        updateUserInClient(user);
        String URL = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/UpdateUserServlet";
        RequestParams params;
        AsyncHttpClient client;

        params = new RequestParams();

        for(Map.Entry<String, String> param: user.getMap().entrySet()){
            params.put(param.getKey(), param.getValue());
        }

        client = new AsyncHttpClient();
        client.post(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_USER_UPDATE.getCode())
                        Toast.makeText(activity, Errors.SUCCESSFUL_USER_UPDATE.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void updateUserInClient(User user){
        user.setPassword(me.getPassword());
        me = user;
    }

    private void checkAuthorization(){
        System.out.println("в checkAuthorization");
        final String URL = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/CheckAuthorizationServlet";

        RequestParams params = new RequestParams();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try{
                    if(response.getString("status").equals("true")){
                        User user = new User();
                        user.setEmail(response.getString("email"));
                        user.setPassword(response.getString("password"));
                        user.setLastname(response.getString("lastname"));
                        user.setName(response.getString("name"));
                        user.setPatronymic(response.getString("patronymic"));
                        me = user;
                    } else me = null;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
