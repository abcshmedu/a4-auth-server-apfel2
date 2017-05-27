/**
 * Organisation: Hochschule Muenchen, Fakultaet 07 Informatik und Mathematik
 * Purpose: lab software-architecture, IF4B, SS2017
 * Purpose: solution of assignment 2
 */

package edu.hm.lipptobusch.shareit.oauth.models;

/**
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger
 * @author Juliane Seidl
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public class User {
    private final String username;
    private final String password;
    private String token = null;
    private long ttl;
    private boolean admin = false;


    /**
     * Ony for Tests
     */
    @Deprecated
    public void setTTLSeconds(long ttlSeconds) {
        this.ttl = System.currentTimeMillis() + 1000 * ttlSeconds;
    }

    public void setToken(String token) {
        this.token = token;
        ttl = System.currentTimeMillis() + 1000 * 60 * 2;
    }

    public String getToken() {
        // Reset token if the current time is after ttl
        if (ttl < System.currentTimeMillis()) {
            token = null;
        }
        return token;
    }

    /**
     * private default constructor is needed for reflection (Jackson)
     */
    private User() {
        this.username = "";
        this.password = "";

    }


    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public boolean isAdmin() {
        return admin;
    }
}
