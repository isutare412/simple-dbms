package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class ReferenceColumnExistenceError extends DBException {
    public ReferenceColumnExistenceError() {
        super("Create table has failed: foreign key references non existing column");
    }
}