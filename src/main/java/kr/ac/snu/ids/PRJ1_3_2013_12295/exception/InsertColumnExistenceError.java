package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class InsertColumnExistenceError extends DBException {
    public InsertColumnExistenceError(String columnName) {
        super("Insertion has failed: \'" + columnName + "\' does not exist");
    }
}
