package core.jdbc;

import java.util.List;

public class SelectJdbcTemplate<T> {
    private final JdbcTemplate<T> jdbcTemplate = new JdbcTemplate<>();

    public List<T> query(String sql, RowMapper<T> rowMapper) {
        try {
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            throw new DataAccessException("SELECT query 실패", e);
        }
    }

    public T queryForObject(String sql, PreparedStatementSetter pstmtSetter, RowMapper<T> rowMapper) {
        try {
            return jdbcTemplate.queryForObject(sql, pstmtSetter, rowMapper);
        } catch (Exception e) {
            throw new DataAccessException("SELECT queryForObject 실패", e);
        }
    }
}
