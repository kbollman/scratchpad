package com.kevjim.prototype.data;

import com.kevjim.common.model.MyTable;

/**
 * DAO for MyTable model
 */
public interface MyTableDAO {

    /**
     * Adds a row to the MyTable
     *
     * @param myTable - MyTable object to add
     */
    void addRow(MyTable myTable);

    /**
     * Retrives the myTableColumnA value for the the myTableId passed in
     *
     * @param myTableId - Table Id to get the MyTableColumnA for
     * @return myTableColumnA for the myTableId passed in
     */
    String getMyTableColumnA(long myTableId);
}
