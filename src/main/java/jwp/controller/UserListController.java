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
import java.util.Collection;

@WebServlet("/user/list")
public class UserListController extends HttpServlet {

    private final MemoryUserRepository userRepository = MemoryUserRepository.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/user/login.jsp"); // 로그인 x -> 로그인 페이지로 이동
            return;
        }

        User loginUser = (User) session.getAttribute("user");

        Collection<User> users = userRepository.findAll();

        request.setAttribute("users", users);
        request.setAttribute("loginUser", loginUser);

        request.getRequestDispatcher("/user/list.jsp").forward(request, response);
    }
}
