package jwp.controller;

import core.mvc.Controller;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserFormController implements Controller {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/";
        }

        User sessionUser = (User) session.getAttribute("user");
        String userId = request.getParameter("userId");

        if (!sessionUser.getUserId().equals(userId)) {
            return "redirect:/";
        }

        request.setAttribute("user", sessionUser);
        return "/user/updateForm";
    }
}
