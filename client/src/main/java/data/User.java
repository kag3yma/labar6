package data;
import java.io.Serializable;
public class User implements Serializable{
     private String login;
     private String password;
    static final long serialVersionUID = 1L;

    public User(String login, String pwd){
        this.login = login;
        this.password = pwd;
    }
    public String getLogin(){
        return login;
    }

    public String getPassword(){
        return password;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
