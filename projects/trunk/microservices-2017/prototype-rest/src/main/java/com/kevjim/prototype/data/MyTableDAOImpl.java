package com.kevjim.prototype.data;

import com.kevjim.common.model.MyTable;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * MyTableDAO Implemention
 */
public class MyTableDAOImpl extends AbstractDAO implements MyTableDAO {

    /**
     * @inheritDoc
     */
    public void addRow(MyTable myTable) {
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    hibernateTemplate.execute((Session session) -> {
                        hibernateTemplate.saveOrUpdate(myTable);
                        return null;
                    });
                }
            });
        } catch (Exception e) {
            logger.error("Error saving institution", e);
            throw e;
        }
    }

    /**
     * @inheritDoc
     */
    public String getMyTableColumnA(long myTableId) {
        try {
            MyTable myTable = (MyTable) hibernateTemplate.execute(new HibernateCallback<Object>() {
                public Object doInHibernate(Session session) {
                    return (MyTable) session
                            .createQuery("from " + MyTable.class.getName() + " where myTableId = :myTableId")
                            .setParameter("myTableId", myTableId)
                            .setReadOnly(true)
                            .list().get(0);
                }
            });
            return myTable.getMyTableColumnA();

        } catch (Exception e) {
            logger.error("Error getting MyTableColumnA name, error = ", e);
            throw e;
        }
    }
}
