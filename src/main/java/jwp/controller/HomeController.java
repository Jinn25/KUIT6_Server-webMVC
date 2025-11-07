package jwp.controller;

import jwp.dao.QuestionDao;
import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuestionDao questionDao;

    @GetMapping("/")
    public String home(Model model) throws SQLException {
        List<Question> questions = questionDao.findAll();
        model.addAttribute("questions", questions);
        return "/home";
    }
}
