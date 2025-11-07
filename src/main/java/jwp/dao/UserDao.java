package jwp.dao;

import jwp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final EntityManager em;

    @Transactional
    public void insert(User user) {
        em.persist(user);
    }

    public void update(User user) {
        em.merge(user);
    }

    public void delete(String userId) {
        User user = em.find(User.class, userId);
        if (user != null) {
            em.remove(user);
        }
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public User findByUserId(String userId) {
        return em.find(User.class, userId);
    }
}
