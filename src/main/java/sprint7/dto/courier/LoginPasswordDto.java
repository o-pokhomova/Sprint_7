package sprint7.dto.courier;

public class LoginPasswordDto {
    private String login;
    private String password;

    public LoginPasswordDto() {
    }

    public LoginPasswordDto(String login, String password) {
        this.password = password;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
