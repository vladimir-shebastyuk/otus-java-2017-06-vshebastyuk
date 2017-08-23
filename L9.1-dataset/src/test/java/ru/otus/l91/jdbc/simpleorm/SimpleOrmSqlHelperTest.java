package ru.otus.l91.jdbc.simpleorm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jstingo on 21.08.2017.
 */
public class SimpleOrmSqlHelperTest {
    @Test
    public void formatUpdateSql() throws Exception {
        List<String> columns = Arrays.asList(
                "id",
                "name",
                "age",
                "some_other_column"
        );

        Assert.assertEquals(
                "UPDATE test_table SET id=?,name=?,age=?,some_other_column=? WHERE id=?",
                SimpleOrmSqlHelper.formatUpdateSql("test_table","id",columns)
        );

    }

    @Test
    public void formatSelectByIdSql1() throws Exception {
    }

    @Test
    public void formatSelectByIdSql() throws Exception {
        //select single id
        Assert.assertEquals(
                "SELECT id FROM test_table WHERE id = ?",
                SimpleOrmSqlHelper.formatSelectByIdSql("test_table","id")
        );

        List<String> columns = Arrays.asList(
                "id",
                "name",
                "age",
                "some_other_column"
        );

        Assert.assertEquals(
                "SELECT id,name,age,some_other_column FROM test_table WHERE id = ?",
                SimpleOrmSqlHelper.formatSelectByIdSql("test_table","id",columns)
        );
    }

    @Test
    public void formatInsertSql() throws Exception {
        List<String> columns = Arrays.asList(
                "id",
                "name",
                "age",
                "some_other_column"
        );

        Assert.assertEquals(
                "INSERT INTO test_table (id,name,age,some_other_column) VALUES (?,?,?,?)",
                SimpleOrmSqlHelper.formatInsertSql("test_table",columns)
        );

    }

}