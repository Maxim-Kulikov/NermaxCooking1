package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

public class FragmentAuthorization extends Fragment implements View.OnClickListener{
    private View view;
    private EditText inputEmail;
    private EditText inputPassword;
    private String email = "", password = "";

    public interface AuthorizationManager{
        public void authorization(String email, String password);
    }

    AuthorizationManager authorizationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            authorizationManager = (AuthorizationManager) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AuthorizationManager");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_authorization, container, false);

        Button buttonAuthorization = view.findViewById(R.id.EnterButton),
                buttonRegister = view.findViewById(R.id.ToRegisterButton);
        buttonAuthorization.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.EnterButton:
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();

                if(!email.equals("") && !password.equals("")) {
                    System.out.println("в FragmentAuthorization: " + email + " " + password);
                    authorizationManager.authorization(email, password);
                }else {
                    Toast.makeText(getActivity(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ToRegisterButton:
                getParentFragmentManager().
                        beginTransaction().
                        replace(R.id.LogOrRegActivity, FragmentRegistration.class, null).
                        commit();

        }
    }


}