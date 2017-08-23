package ru.otus.l91.jdbc.simpleorm;

import java.sql.*;
import java.util.*;

/**
 *
 */
public class Executor {
    private Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public long insertWithGeneratedId(String sql, Object... args) throws SQLException, SimpleOrmException {
        try(PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            setParameters(stmt,args);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                return rs.getLong(1);
            }
        }

        throw new SimpleOrmException("Could not insert new entry!");
    }

    public void insert(String sql, Object... args) throws SQLException, SimpleOrmException {
        try(PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)){
            setParameters(stmt,args);

            stmt.executeUpdate();
        }

        throw new SimpleOrmException("Could not insert new entry!");
    }

    public void update(String sql, long id, Object... args) throws SQLException, SimpleOrmException {
        try(PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS)){
            Object[] parameters =  Arrays.copyOf(args,args.length + 1);
            parameters[args.length] = id;

            setParameters(stmt, parameters);

            stmt.executeUpdate();
        }catch (SQLException ex){
            throw new SimpleOrmException("Could not insert new entry!", ex);
        }
    }

    public Map<String,Object> selectSingleById(String sql, long id, Collection<String> columns) throws SQLException {
        try(PreparedStatement stmt = this.connection.prepareStatement(sql)){
            setParameters(stmt,id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Map<String,Object> values = new HashMap<>();

                for (String column: columns) {
                    values.put(column,rs.getObject(column));
                }

                return values;
            }
        }

        return Collections.emptyMap();
    }

    protected void setParameters(PreparedStatement stmt, Object... args) throws SQLException {
        for (int i = 0; i < args.length;i++) {
            stmt.setObject(i + 1, args[i]);
        }
    }
}
