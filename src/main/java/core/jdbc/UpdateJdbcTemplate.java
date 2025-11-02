package core.jdbc;

public class UpdateJdbcTemplate {
    private final JdbcTemplate<Void> jdbcTemplate = new JdbcTemplate<>();

    public void update(String sql, PreparedStatementSetter pstmtSetter) {
        jdbcTemplate.update(sql, pstmtSetter);
    }
}