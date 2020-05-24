package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class SelectTableExistenceError extends DBException {
    public SelectTableExistenceError(String tableName) {
        super("Selection has failed: \'" + tableName + "\' does not exist");
    }
}