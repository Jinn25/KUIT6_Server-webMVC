package jwp.controller;

import core.db.MemoryUserRepository;
import core.mvc.Controller;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController implements Controller {

    private final MemoryUserRepository userRepository = MemoryUserRepository.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        User user = new User(userId, password, name, email);
        userRepository.addUser(user);

        System.out.println("회원가입 완료: " + userId);
        return "redirect:/user/list";
    }
}
