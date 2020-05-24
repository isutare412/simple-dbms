package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class SelectColumnResolveError extends DBException {
    public SelectColumnResolveError(String columnName) {
        super("Selection has failed: failed to resolve \'" + columnName + "\'");
    }
}