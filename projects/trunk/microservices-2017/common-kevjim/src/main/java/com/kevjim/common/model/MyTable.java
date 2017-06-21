package com.kevjim.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The MyTable class maps to the my_table table
 */
@Entity
@Table(name = "my_table")
public class MyTable implements Serializable {
    private long myTableId;
    private String myTableColumnA;
    private Boolean myTableColumnB;

    public MyTable() {
        // Default constructor
    }

    public MyTable(long myTableId, String myTableColumnA) {
        this.myTableId = myTableId;
        this.myTableColumnA = myTableColumnA;
    }

    @Id
    @Column(name = "my_table_id", unique = true, nullable = false)
    public long getMyTableId() {
        return myTableId;
    }

    public void setMyTableId(long myTableId) {
        this.myTableId = myTableId;
    }

    @Column(name = "my_table_column_a", nullable = false)
    public String getMyTableColumnA() {
        return myTableColumnA;
    }

    public void setMyTableColumnA(String myTableColumnA) {
        this.myTableColumnA = myTableColumnA;
    }

    @Column(name = "my_table_column_b", nullable = false)
    public Boolean getMyTableColumnB() {
        return myTableColumnB;
    }

    public void setMyTableColumnB(Boolean myTableColumnB) {
        this.myTableColumnB = myTableColumnB;
    }
}
