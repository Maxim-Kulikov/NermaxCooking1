package com.example.nermaxcooking1;

public enum Errors {

    NON_EXISTENT_USER(1, "Такого пользователя не существует!"),
    SUCCESSFUL_AUTHORIZATION(2, "Вы успешно авторизовались!"),
    NON_SUCCESSFUL_AUTHORIZATION(3, "Авторизация не успешна!"),
    SUCCESSFUL_REGISTRATION(4, "Вы успешно зарегистрировались!"),
    NON_SUCCESSFUL_REGISTRATION(5, "Регистрация не успешна!"),
    EXISTENT_USER(6, "Такой пользователь уже существует!"),
    SOMETHING_IS_WRONG(7, "Что-то пошло не так!"),
    SUCCESSFUL_RECIPE_CREATION(8, "Рецепт добавлен!"),
    NON_SUCCESSFUL_RECIPE_CREATION(9, "Рецепт не добавлен!"),
    SUCCESSFUL_RECIPE_DELETE(10, "Рецепт удалён!"),
    SUCCESSFUL_RECIPE_UPDATE(11, "Рецепт был изменён!"),
    SUCCESSFUL_USER_UPDATE(12, "Информация изменена!");


    private int code;
    private String message;

    Errors(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

}
