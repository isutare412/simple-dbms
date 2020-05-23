package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class DuplicateColumnDefError extends DBException {
    public DuplicateColumnDefError() {
        super("Create table has failed: column definition is duplicated");
    }
}