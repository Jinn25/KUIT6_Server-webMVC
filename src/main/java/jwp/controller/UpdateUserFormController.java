package jwp.controller;

import jwp.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/updateForm")
public class UpdateUserFormController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/user/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("user");
        String userId = request.getParameter("userId");

        if (loginUser.getUserId().equals(userId)) {
            request.setAttribute("user", loginUser);
            request.getRequestDispatcher("/user/updateForm.jsp").forward(request, response);
        } else {
            response.sendRedirect("/");
        }
    }
}
