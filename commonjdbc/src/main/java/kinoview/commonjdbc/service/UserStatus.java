package kinoview.commonjdbc.service;

/**
 * Created by alexfomin on 05.07.17.
 */
public enum UserStatus {
    ACTIVE(1), INACTIVE(2), DELETED(3);

    private int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
