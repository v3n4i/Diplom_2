import com.github.javafaker.Faker;

public class Constants {
    static Faker faker = new Faker();
    public static final String URL = "https://stellarburgers.nomoreparties.site/";

    public static final String email_static = "test123127-ya@ya.ru";
    public static final String name_static = "ven4i7";
    public static final String password_static = "Pass127";

    public static final String email = faker.name().firstName()+"@ya.ru";
    public static final String password = faker.name().lastName()+"pass";
    public static final String name = faker.name().username();

}
