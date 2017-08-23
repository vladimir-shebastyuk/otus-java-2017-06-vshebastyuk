package ru.otus.l91.jdbc.simpleorm.testentity;

import ru.otus.l91.datasets.DataSet;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 */
@Entity
public class EntityExample extends DataSet{
    public static final String SOME_CONST = "";

    private String name;

    private String jobTitle;

    private transient String transientField;

    @Transient
    private String transientFieldByAnnotation;

    public EntityExample() {
    }

    public EntityExample(int id, String name, String jobTitle, String transientField) {
        super(id);
        this.name = name;
        this.jobTitle = jobTitle;
        this.transientField = transientField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getTransientField() {
        return transientField;
    }

    public void setTransientField(String transientField) {
        this.transientField = transientField;
    }
}
