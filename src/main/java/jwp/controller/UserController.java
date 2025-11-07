package jwp.controller;

import jwp.dao.UserDao;
import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    @GetMapping("/form")
    public String showSignupForm() {
        return "user/form";
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute User user) throws Exception {
        userDao.insert(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userId,
                        @RequestParam String password,
                        HttpSession session) throws Exception {

        User user = userDao.findByUserId(userId);

        if (user == null || !user.matchPassword(password)) {
            return "user/loginFailed";
        }

        session.setAttribute("user", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/list")
    public ModelAndView list() throws Exception {
        ModelAndView mav = new ModelAndView("user/list");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam String userId, HttpSession session) throws Exception {
        User user = userDao.findByUserId(userId);
        Object value = session.getAttribute("user");

        if (user != null && value != null && user.equals(value)) {
            session.setAttribute("targetUser", user); // 선택한 유저 세션 저장
            return "user/updateForm";
        }

        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("user");

        if (loginUser != null && loginUser.isSameUser(user)) {
            userDao.update(user);
            return "redirect:/user/list";
        }

        return "redirect:/";
    }
}
