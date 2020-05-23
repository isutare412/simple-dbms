package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class NonExistingPrimaryKeyError extends DBException {
    public NonExistingPrimaryKeyError() {
        super("Create table has failed: primary key does not exists in table definition");
    }
}
