import com.fasterxml.jackson.annotation.JsonProperty;

abstract class User {
    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("password")
    private String password;

    public User(String username, String name, String surname, String password) {
    }

    protected String getUsername() {
        return username;
    }

    protected String getName() {
        return name;
    }

    protected String getSurname() {
        return surname;
    }

    protected String getPassword() {
        return password;
    }

}
