package jwp.controller;

import core.db.MemoryUserRepository;
import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/update")
public class UpdateUserController extends HttpServlet {

    private final MemoryUserRepository userRepository = MemoryUserRepository.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/");
            return;
        }

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            response.sendRedirect("/");
            return;
        }

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (!sessionUser.getUserId().equals(userId)) {
            response.sendRedirect("/");
            return;
        }

        User updatedUser = new User(userId, password, name, email);

        userRepository.changeUserInfo(updatedUser);

        session.setAttribute("user", updatedUser);

        response.sendRedirect("/user/list");
    }
}
