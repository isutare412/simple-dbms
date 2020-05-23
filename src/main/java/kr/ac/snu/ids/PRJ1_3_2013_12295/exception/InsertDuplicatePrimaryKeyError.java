package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class InsertDuplicatePrimaryKeyError extends DBException {
    public InsertDuplicatePrimaryKeyError() {
        super("Insertion has failed: Primary key duplication");
    }
}
