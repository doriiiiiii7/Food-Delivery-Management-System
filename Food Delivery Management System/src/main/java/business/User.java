package business;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String username;
    private String password;
    private Role role;

    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role = Role.setRole(role);
    }

    @Override
    public boolean equals(Object o) {
        return username.equals(((User)o).username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
