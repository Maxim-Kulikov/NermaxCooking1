package com.example.nermaxcooking1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

public class FragmentRegistration extends Fragment{
    private View view;
    private EditText editEmail;
    private String email = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        Button checkButton = view.findViewById(R.id.CheckEmailButton);
        editEmail = view.findViewById(R.id.Email);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editEmail.getText().toString();
                if (!email.equals("")) {

                    /*Bundle result = new Bundle();
                    result.putString("email", email);
                    getParentFragmentManager().setFragmentResult("email", result);*/

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);

                    getParentFragmentManager().
                            beginTransaction().
                            replace(R.id.LogOrRegActivity, FragmentRegistration2.class, bundle).
                            commit();
                }
            }
        });

        return view;
    }

    public String getEmail(){
        return email;
    }
}