package com.example.nermaxcooking1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nermaxcooking.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogOrRegActivity extends AppCompatActivity implements FragmentRegistration2.NewUserManager, FragmentAuthorization.AuthorizationManager {
    private User user = null;
    private RequestParams params;
    private AsyncHttpClient client;
    private final String MYURLFORREGISTER = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/RegisterServlet";
    private final String MYURLFORAUTHORIZATION = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/AuthorizationServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_or_reg);

        if (findViewById(R.id.LogOrRegActivity) != null) {
            if (savedInstanceState != null) {
                return;
            }
            goToFragmentAuthorization();

        }

    }

    public void goToFragmentAuthorization(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.LogOrRegActivity, FragmentAuthorization.class, null)
                .commit();
    }

    @Override
    public void createNewUser(User user) {
        ControllerUser.getController().setMe(user);
        this.user = user;
        System.out.println("В активити: " + user.toString());

        params = new RequestParams();
                    params.put("email", user.getEmail());
                    params.put("password", user.getPassword());
                    params.put("lastname", user.getLastname());
                    params.put("name", user.getName());
                    params.put("patronymic", user.getPatronymic());

        client = new AsyncHttpClient();
        client.post(MYURLFORREGISTER, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("message") == Errors.SUCCESSFUL_REGISTRATION.getCode()) {
                        Toast.makeText(LogOrRegActivity.this, Errors.SUCCESSFUL_REGISTRATION.getMessage(), Toast.LENGTH_SHORT).show();
                        ControllerUser.getController().setMe(user);
                        Intent intent = new Intent(LogOrRegActivity.this, MainActivity.class);
                        startActivity(intent);
                        postSession(response.getString("email"));
                        finish();
                    } else  Toast.makeText(LogOrRegActivity.this, Errors.EXISTENT_USER.getMessage(), Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
             public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(LogOrRegActivity.this, Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void authorization(String email, String password) {
        System.out.println("в authorization: " + email + " " + password);

        params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        client = new AsyncHttpClient();

        client.get(MYURLFORAUTHORIZATION, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
                try{
                    if(response.getInt("message") == Errors.SUCCESSFUL_AUTHORIZATION.getCode()) {

                        user = new User();
                        user.setEmail(response.getString("email"));
                        user.setPassword(response.getString("password"));
                        user.setLastname(response.getString("lastname"));
                        user.setName(response.getString("name"));
                        user.setPatronymic(response.getString("patronymic"));

                        ControllerUser.getController().setMe(user);
                        System.out.println("В LogOrRegActivity activity authorization: " + user.toString());

                        ControllerUser.getController().setMe(user);
                        Toast.makeText(LogOrRegActivity.this, Errors.SUCCESSFUL_AUTHORIZATION.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LogOrRegActivity.this, MainActivity.class);
                        startActivity(intent);
                        postSession(response.getString("email"));
                        finish();
                    } else Toast.makeText(LogOrRegActivity.this, Errors.NON_EXISTENT_USER.getMessage(), Toast.LENGTH_LONG).show();

                }catch(Exception e){
                    e.printStackTrace();
                }
                }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(LogOrRegActivity.this, Errors.SOMETHING_IS_WRONG.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postSession(String email){
        final String URL = "http://" + Data.IP + "/Gradle___com_example___NermaxCookingEE_1_0_SNAPSHOT_war/CheckAuthorizationServlet";

        RequestParams params = new RequestParams();
        params.put("email", email);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                super.onSuccess(statusCode, headers, response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}