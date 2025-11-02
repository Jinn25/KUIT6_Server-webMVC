package jwp.dao;

import core.jdbc.*;
import jwp.model.Question;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionDao {
    private final InsertJdbcTemplate insertTemplate = new InsertJdbcTemplate();
    private final SelectJdbcTemplate<Question> selectTemplate = new SelectJdbcTemplate<>();

    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";
        RowMapper<Question> rowMapper = rs -> mapRowToQuestion(rs);
        return selectTemplate.query(sql, rowMapper);
    }

    public Question findByQuestionId(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId = ?";
        PreparedStatementSetter pss = pstmt -> pstmt.setLong(1, questionId);
        RowMapper<Question> rowMapper = rs -> mapRowToQuestion(rs);
        return selectTemplate.queryForObject(sql, pss, rowMapper);
    }

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS(writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new KeyHolder();

        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, java.sql.Timestamp.valueOf(question.getCreatedDate()));
            pstmt.setInt(5, question.getCountOfAnswer());
        };

        insertTemplate.update(sql, pss, keyHolder);
        return findByQuestionId(keyHolder.getId());
    }

    private Question mapRowToQuestion(ResultSet rs) throws SQLException {
        return new Question(
                rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate").toLocalDateTime(),
                rs.getInt("countOfAnswer")
        );
    }
}
