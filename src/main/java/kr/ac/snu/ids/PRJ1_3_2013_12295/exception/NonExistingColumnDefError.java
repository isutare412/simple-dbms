package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class NonExistingColumnDefError extends DBException {
    public NonExistingColumnDefError(String columnName) {
        super("Create table has failed: \'" + columnName + "\' does not exists in column definition");
    }
}