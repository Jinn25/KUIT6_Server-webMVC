package jwp.dao;

import jwp.model.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {

    private final EntityManager em;

    @Transactional
    public Question insert(Question question) {
        em.persist(question);
        return question;
    }


    public void update(Question question) {
        em.merge(question);
    }

    public void delete(int questionId)  {
        Question question = em.find(Question.class, questionId);
        if (question != null) {
            em.remove(question);
        }
    }

    public List<Question> findAll()  {
        return em.createQuery("SELECT q FROM Question q ORDER BY q.questionId", Question.class).getResultList();
    }

    public Question findByQuestionId(int questionId)  {
        return em.find(Question.class, questionId);
    }
}