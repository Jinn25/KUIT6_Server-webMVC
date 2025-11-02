package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class CreateQuestionController implements Controller {

    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            return "redirect:/user/loginForm";
        }

        User loginUser = (User) session.getAttribute("user");

        String writer = loginUser.getUserId();
        String title = request.getParameter("title");
        String contents = request.getParameter("contents");

        Question question = new Question(
                null,
                writer,
                title,
                contents,
                LocalDateTime.now(),
                0
        );

        questionDao.insert(question);

        return "redirect:/";
    }
}
