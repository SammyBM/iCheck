package labs;

import models.User;

public class UserLab {
    private static UserLab sUserLab;
    private User currentUser;

    public static UserLab get(){
        if (sUserLab == null)
            sUserLab = new UserLab();
        return sUserLab;
    }

    private UserLab(){
        currentUser = new User();
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
