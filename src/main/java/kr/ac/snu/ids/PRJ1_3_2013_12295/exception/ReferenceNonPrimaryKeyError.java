package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class ReferenceNonPrimaryKeyError extends DBException {
    public ReferenceNonPrimaryKeyError() {
        super("Create table has failed: foreign key references non primary key column");
    }
}