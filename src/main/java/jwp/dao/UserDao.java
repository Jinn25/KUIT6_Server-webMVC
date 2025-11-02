package jwp.dao;

import core.jdbc.*;
import jwp.model.User;
import java.util.List;

public class UserDao {
    private final InsertJdbcTemplate insertTemplate = new InsertJdbcTemplate();
    private final UpdateJdbcTemplate updateTemplate = new UpdateJdbcTemplate();
    private final SelectJdbcTemplate<User> selectTemplate = new SelectJdbcTemplate<>();


    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        insertTemplate.insert(sql, pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        });
    }

    public void update(User user) {
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        updateTemplate.update(sql, pstmt -> {
            pstmt.setString(1, user.getPassword());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUserId());
        });
    }

    public void delete(User user) {
        String sql = "DELETE FROM USERS WHERE userId=?";
        updateTemplate.update(sql, pstmt -> pstmt.setString(1, user.getUserId()));
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );
        return selectTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) {
        String sql = "SELECT * FROM USERS WHERE userId=?";
        RowMapper<User> rowMapper = rs -> new User(
                rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email")
        );
        return selectTemplate.queryForObject(sql, pstmt -> pstmt.setString(1, userId), rowMapper);
    }
}
