package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class DuplicatePrimaryKeyDefError extends DBException {
    public DuplicatePrimaryKeyDefError() {
        super("Create table has failed: primary key definition is duplicated");
    }
}
