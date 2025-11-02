package core.jdbc;

public class InsertJdbcTemplate {
    private final JdbcTemplate<Void> jdbcTemplate = new JdbcTemplate<>();

    public void insert(String sql, PreparedStatementSetter pstmtSetter) {
        jdbcTemplate.update(sql, pstmtSetter);
    }
    public void update(String sql, PreparedStatementSetter pstmtSetter, KeyHolder holder) {
        jdbcTemplate.update(sql, pstmtSetter, holder);
    }
}
