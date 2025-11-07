package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import jwp.model.User;
import jwp.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionDao questionDao;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (UserSessionUtils.isLogined(session)) {
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Question question) throws SQLException {
        Question savedQuestion = questionDao.insert(question);
        System.out.println("saved question id = " + savedQuestion.getQuestionId());
        return "redirect:/";
    }

    @GetMapping("/show")
    public String show(@RequestParam int questionId, Model model) throws SQLException {
        Question question = questionDao.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam int questionId, HttpSession session, Model model) throws SQLException {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }
        Question question = questionDao.findByQuestionId(questionId);
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "qna/updateForm";
    }

    @PostMapping("/update")
    public String update(@RequestParam int questionId,
                         @RequestParam String title,
                         @RequestParam String contents,
                         HttpSession session) throws SQLException {
        if (!UserSessionUtils.isLogined(session)) {
            return "redirect:/user/loginForm";
        }
        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionDao.findByQuestionId(questionId);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다릅니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionDao.update(question);
        return "redirect:/";
    }
}
