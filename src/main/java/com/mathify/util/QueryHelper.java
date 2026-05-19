package com.mathify.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Thin JDBC helper — eliminates boilerplate without becoming an ORM.
 *
 * Every method opens a connection, runs the query, and closes everything
 * in a try-with-resources block. Callers never touch Connection directly.
 *
 * Logging (SLF4J):
 *   DEBUG — SQL + bound params logged before every execution
 *   DEBUG — execution time + row count logged after every execution
 *   ERROR — SQLException logged with SQL + params before re-throwing
 *
 * To suppress query logs in production, set com.mathify.util.QueryHelper
 * to WARN in logback.xml.
 *
 * Usage:
 *   // Query many rows
 *   List<User> users = QueryHelper.queryList(
 *       "SELECT * FROM users WHERE active = ?",
 *       rs -> new User(rs.getInt("id"), rs.getString("name")),
 *       true
 *   );
 *
 *   // Query single row (returns null if not found)
 *   User user = QueryHelper.queryOne(
 *       "SELECT * FROM users WHERE id = ?",
 *       rs -> new User(rs.getInt("id"), rs.getString("name")),
 *       42
 *   );
 *
 *   // Insert / update / delete — returns rows affected
 *   int rows = QueryHelper.executeUpdate(
 *       "INSERT INTO users (name, email) VALUES (?, ?)",
 *       "Alice", "alice@example.com"
 *   );
 *
 *   // Insert and get generated primary key
 *   int newId = QueryHelper.executeInsert(
 *       "INSERT INTO users (name, email) VALUES (?, ?)",
 *       "Alice", "alice@example.com"
 *   );
 */
public class QueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryHelper.class);

    private QueryHelper() {}

    // -------------------------------------------------------------------------
    // Functional interface — maps one ResultSet row to an object
    // -------------------------------------------------------------------------

    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    // -------------------------------------------------------------------------
    // Query helpers
    // -------------------------------------------------------------------------

    /**
     * Execute a SELECT and map every row to T.
     * Returns an empty list (never null) if no rows match.
     */
    public static <T> List<T> queryList(String sql, RowMapper<T> mapper, Object... params)
            throws SQLException {
        logQuery(sql, params);
        long start = System.currentTimeMillis();
        List<T> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = prepare(conn, sql, params);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            log.debug("queryList completed in {}ms — {} row(s) returned", elapsed(start), results.size());
            return results;
        } catch (SQLException e) {
            logError("queryList", sql, params, e);
            throw e;
        }
    }

    /**
     * Execute a SELECT and map the first row to T.
     * Returns null if no row matches — callers should null-check.
     */
    public static <T> T queryOne(String sql, RowMapper<T> mapper, Object... params)
            throws SQLException {
        logQuery(sql, params);
        long start = System.currentTimeMillis();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = prepare(conn, sql, params);
             ResultSet rs = ps.executeQuery()) {
            T result = rs.next() ? mapper.map(rs) : null;
            log.debug("queryOne completed in {}ms — {}", elapsed(start),
                    result != null ? "row found" : "no row found");
            return result;
        } catch (SQLException e) {
            logError("queryOne", sql, params, e);
            throw e;
        }
    }

    // -------------------------------------------------------------------------
    // Update helpers
    // -------------------------------------------------------------------------

    /**
     * Execute an INSERT / UPDATE / DELETE.
     * Returns the number of rows affected.
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        logQuery(sql, params);
        long start = System.currentTimeMillis();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = prepare(conn, sql, params)) {
            int rows = ps.executeUpdate();
            log.debug("executeUpdate completed in {}ms — {} row(s) affected", elapsed(start), rows);
            return rows;
        } catch (SQLException e) {
            logError("executeUpdate", sql, params, e);
            throw e;
        }
    }

    /**
     * Execute an INSERT and return the generated primary key (int).
     * Throws IllegalStateException if the DB doesn't return a generated key.
     */
    public static int executeInsert(String sql, Object... params) throws SQLException {
        logQuery(sql, params);
        long start = System.currentTimeMillis();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParams(ps, params);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    log.debug("executeInsert completed in {}ms — generated id={}", elapsed(start), generatedId);
                    return generatedId;
                }
                throw new IllegalStateException("INSERT returned no generated key: " + sql);
            }
        } catch (SQLException e) {
            logError("executeInsert", sql, params, e);
            throw e;
        }
    }

    // -------------------------------------------------------------------------
    // Internal
    // -------------------------------------------------------------------------

    private static PreparedStatement prepare(Connection conn, String sql, Object[] params)
            throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql);
        setParams(ps, params);
        return ps;
    }

    private static void setParams(PreparedStatement ps, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);  // setObject handles all common Java types
        }
    }

    private static void logQuery(String sql, Object[] params) {
        if (log.isDebugEnabled()) {
            log.debug("Executing SQL: {}", sql.replaceAll("\\s+", " ").strip());
            log.debug("       Params: {}", Arrays.toString(params));
        }
    }

    private static void logError(String method, String sql, Object[] params, SQLException e) {
        log.error("{} failed — SQL: {} | Params: {} | Error: {}",
                method,
                sql.replaceAll("\\s+", " ").strip(),
                Arrays.toString(params),
                e.getMessage());
    }

    private static long elapsed(long startMs) {
        return System.currentTimeMillis() - startMs;
    }
}