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

@SuppressWarnings("serial")
class TableExistenceError extends DBException {
    public TableExistenceError() {
        super("Create table has failed: table with the same name already exists");
    }
}

