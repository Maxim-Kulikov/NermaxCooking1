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

public class FragmentRegistration2 extends Fragment {

    public interface NewUserManager {
        public void createNewUser(User user);
    }

    NewUserManager newUserManager;
    private User newUser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            newUserManager = (NewUserManager) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NewUserManager");
        }
    }


    @Override
    public void onDetach() {
        newUserManager = null;
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        newUser = new User();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            newUser.setEmail(bundle.getString("email", ""));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_registration2, container, false);

        EditText createPassword = view.findViewById(R.id.CreatePassword);
        EditText repeatPassword = view.findViewById(R.id.RepeatPassword);
        EditText lastname = view.findViewById(R.id.Lastname);
        EditText name = view.findViewById(R.id.Name);
        EditText patronymic = view.findViewById(R.id.Patronymic);
        Button create = view.findViewById(R.id.CreateButton);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(createPassword.getText().toString().equals(repeatPassword.getText().toString())
                        && !lastname.getText().toString().equals("")
                        && !name.getText().toString().equals("")
                        && !patronymic.getText().toString().equals("")) {

                    String salt = SHA512.getSalt();
                    newUser.setPassword(createPassword.getText().toString());
                    newUser.setLastname(lastname.getText().toString());
                    newUser.setName(name.getText().toString());
                    newUser.setPatronymic(patronymic.getText().toString());

                    newUserManager.createNewUser(newUser);

                    System.out.println(newUser.toString());

                } else {
                    Toast.makeText(getActivity(), "Проверьте правильность ввода!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}