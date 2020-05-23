package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class InsertColumnNonNullableError extends DBException {
    public InsertColumnNonNullableError(String columnName) {
        super("Insertion has failed: \'" + columnName + "\' is not nullable");
    }
}
