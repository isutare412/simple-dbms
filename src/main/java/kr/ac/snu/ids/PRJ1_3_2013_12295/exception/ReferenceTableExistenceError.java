package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class ReferenceTableExistenceError extends DBException {
    public ReferenceTableExistenceError() {
        super("Create table has failed: foreign key references non existing table");
    }
}
