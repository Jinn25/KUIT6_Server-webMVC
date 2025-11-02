package jwp.controller;

import core.mvc.Controller;
import jwp.dao.QuestionDao;
import jwp.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowController implements Controller {

    private final QuestionDao questionDao = new QuestionDao();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String questionIdParam = request.getParameter("questionId");

            if (questionIdParam == null || questionIdParam.isEmpty()) {
                List<Question> questions = questionDao.findAll();
                request.setAttribute("questions", questions);
                return "/qna/list"; // list.jsp로 forward
            }

            long questionId = Long.parseLong(questionIdParam);
            Question question = questionDao.findByQuestionId(questionId);

            if (question == null) {
                List<Question> questions = questionDao.findAll();
                request.setAttribute("questions", questions);
                return "/qna/list";
            }

            request.setAttribute("question", question);
            return "/qna/show";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }
}
