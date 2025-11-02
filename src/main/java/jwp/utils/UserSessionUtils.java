package jwp.utils;

import javax.servlet.http.HttpSession;
import jwp.model.User;

public class UserSessionUtils {

    public static boolean isLogined(HttpSession session) {
        if (session == null) {
            return false;
        }
        return session.getAttribute("user") != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }

    public static String getUserIdFromSession(HttpSession session) {
        User user = getUserFromSession(session);
        return user != null ? user.getUserId() : null;
    }

    public static boolean isSameUser(HttpSession session, String userId) {
        String loginUserId = getUserIdFromSession(session);
        return loginUserId != null && loginUserId.equals(userId);
    }
}
