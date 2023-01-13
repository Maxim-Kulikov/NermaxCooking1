package com.example.nermaxcooking1;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nermaxcooking.R;

public class FragmentUser extends Fragment {
    TextView email;
    EditText lastname;
    EditText name;
    EditText patronymic;
    Button update;
    ImageButton exit;
    User user;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        exitFromFragmentUser = (ExitFromFragmentUser) context;
        saveChangesInFragmentUser = (SaveChangesInFragmentUser) context;
    }

    interface SaveChangesInFragmentUser{
        void saveChangesInFragmentUser(User user);
    }

    interface ExitFromFragmentUser{
        void exitFromFragmentUser();
    }

    SaveChangesInFragmentUser saveChangesInFragmentUser;
    ExitFromFragmentUser exitFromFragmentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        user = ControllerUser.getController().getMe();
        email = view.findViewById(R.id.emailTV1);
        lastname = view.findViewById(R.id.lastnameTV1);
        name = view.findViewById(R.id.nameTV1);
        patronymic = view.findViewById(R.id.patronymicTV1);
        update = view.findViewById(R.id.updateButton);
        exit = view.findViewById(R.id.exitButton);

        email.setText(user.getEmail());
        lastname.setText(user.getLastname());
        name.setText(user.getName());
        patronymic.setText(user.getPatronymic());


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(email.getText().toString().equals(user.getEmail()) &&
                        lastname.getText().toString().equals(user.getLastname()) &&
                        name.getText().toString().equals(user.getName()) &&
                        patronymic.getText().toString().equals(user.getPatronymic()))){
                    User user1 = new User();
                    user1.setEmail(email.getText().toString());
                    user1.setLastname(lastname.getText().toString());
                    user1.setName(name.getText().toString());
                    user1.setPatronymic(patronymic.getText().toString());
                    saveChangesInFragmentUser.saveChangesInFragmentUser(user1);
                } else Toast.makeText(getContext(), "Вы ничего не меняли!", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitFromFragmentUser.exitFromFragmentUser();
            }
        });

        return view;
    }
}