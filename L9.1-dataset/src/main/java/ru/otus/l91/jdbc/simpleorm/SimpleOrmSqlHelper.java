package ru.otus.l91.jdbc.simpleorm;

import java.util.Arrays;
import java.util.Collection;

/**
 *
 */
public class SimpleOrmSqlHelper {
    private final static String FIND_BY_ID = "SELECT %2$s FROM %1$s WHERE %3$s = ?";
    private final static String INSERT = "INSERT INTO %1$s (%2$s) VALUES (%3$s)";
    private final static String UPDATE = "UPDATE %1$s SET %2$s WHERE %3$s=?";

    private SimpleOrmSqlHelper() {
    }

    public static String formatSelectByIdSql(String tableName, String idColumn){
        return String.format(FIND_BY_ID,tableName,idColumn,idColumn);
    }

    public static String formatSelectByIdSql(String tableName, String idColumn,Collection<String> columns){
        String columnsStr = String.join(",",columns);

        return String.format(FIND_BY_ID,tableName,columnsStr,idColumn);
    }

    public static String formatInsertSql(String tableName, Collection<String> columns){
        String columnsStr = String.join(",",columns);

        String[] parameters = new String[columns.size()];
        Arrays.fill(parameters,"?");

        String parametersStr = String.join(",",parameters);

        return String.format(INSERT,tableName,columnsStr,parametersStr);
    }

    public static String formatUpdateSql(String tableName, String idColumn, Collection<String> columns){

        String columnsStr = String.join(",",
                (String[])(columns.stream().map( it -> it + "=?").toArray(String[]::new))
        );

        return String.format(UPDATE,tableName,columnsStr,idColumn);
    }
}
