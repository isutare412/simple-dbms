package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

@SuppressWarnings("serial")
public class DBException extends Exception {
    public DBException(String msg) {
        super(msg);
    }
}

////////////////////////////////////////////////////////////////
// Create table exceptions
////////////////////////////////////////////////////////////////

@SuppressWarnings("serial")
class DuplicateColumnDefError extends DBException {
    public DuplicateColumnDefError() {
        super("Create table has failed: column definition is duplicated");
    }
}
