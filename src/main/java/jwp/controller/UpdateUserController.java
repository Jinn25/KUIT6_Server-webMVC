package jwp.controller;

import core.mvc.Controller;
import jwp.dao.UserDao;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserController implements Controller {

    private final UserDao userDao = new UserDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/";
        }

        User sessionUser = (User) session.getAttribute("user");

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (!sessionUser.getUserId().equals(userId)) {
            return "redirect:/";
        }

        User updatedUser = new User(userId, password, name, email);

        userDao.update(updatedUser);

        session.setAttribute("user", updatedUser);

        return "redirect:/user/list";
    }
}
