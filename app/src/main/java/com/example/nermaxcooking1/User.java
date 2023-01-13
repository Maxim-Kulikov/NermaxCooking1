package com.example.nermaxcooking1;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
  //  private int id;
    private String email = "";
    private String password = "";
    private String lastname = "";
    private String name = "";
    private String patronymic = "";
    private HashMap<String, String> map;
    public enum Enum{
        //id,
        email,
        lastname,
        name,
        patronymic,
        password
    }

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public HashMap<String, String> getMap(){
        if(map == null){
            map = new HashMap<>();
            //map.put(String.valueOf(Enum.id), String.valueOf(id));
            map.put(String.valueOf(Enum.email), email);
            map.put(String.valueOf(Enum.patronymic), patronymic);
            map.put(String.valueOf(Enum.name), name);
            map.put(String.valueOf(Enum.lastname), lastname);
            map.put(String.valueOf(Enum.password), password);
            return map;
        }
        return map;
    }

    public void setData(String ... args){
        setEmail(args[0]);
        setPassword(args[1]);
        setLastname(args[2]);
        setName(args[3]);
        setPatronymic(args[4]);
    }

}
