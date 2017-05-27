package edu.hm.shareit.oauth.models;

/**
 * The class for user objects.
 *
 * @author Florian Tobusch, tobusch@hm.edu
 * @author Carolin Direnberger
 * @author Juliane Seidl
 * @author Maximilian Lipp, lipp@hm.edu
 * @version 2017-05-19
 */
public class User {
    /**
     * The user's username.
     */
    private final String username;
    /**
     * The user's password.
     */
    private final String password;
    /**
     * The user's token for authorization.
     */
    private String token = null;
    /**
     * The token's time to live.
     */
    private long ttl;
    /**
     * The user's admin status.
     */
    private boolean admin = false;
    /**
     * Used for converting time units.
     */
    private static final int THOUSAND = 1000;
    /**
     * Used for converting time units.
     */
    private static final int SIXTY = 60;


    /**
     * Only for Tests.
     *
     * @param ttlSeconds The time to live in seconds
     */
    @Deprecated
    public void setTTLSeconds(long ttlSeconds) {
        this.ttl = System.currentTimeMillis() + THOUSAND * ttlSeconds;
    }

    /**
     * Sets the user's token and its time to live.
     *
     * @param token The user's unique token
     */
    public void setToken(String token) {
        this.token = token;
        ttl = System.currentTimeMillis() + THOUSAND * SIXTY * 2;
    }

    /**
     * Returns the token. Resets it to null when ttl is over.
     *
     * @return The token
     */
    public String getToken() {
        if (ttl < System.currentTimeMillis()) {
            token = null;
        }
        return token;
    }

    /**
     * private default constructor is needed for reflection (Jackson)
     */
    @SuppressWarnings("unused")
    private User() {
        this.username = "";
        this.password = "";

    }

    /**
     * Constructor for a User.
     *
     * @param username The user's username
     * @param password The user's password
     * @param admin    The user's admin status
     */
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    /**
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The user's password
     */
    @SuppressWarnings("WeakerAccess")
    public String getPassword() {
        return password;
    }

    /**
     * Users are equal when username and password are the same.
     *
     * @param o The object to test for equality
     * @return True when equal, false when not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

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
        return "User{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + '}';
    }

    /**
     * @return Whether the user is an admin or not
     */
    public boolean isAdmin() {
        return admin;
    }
}
