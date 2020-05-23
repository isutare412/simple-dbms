package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class TableExistenceError extends DBException {
    public TableExistenceError() {
        super("Create table has failed: table with the same name already exists");
    }
}