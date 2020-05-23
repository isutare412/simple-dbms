package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class InsertTypeMismatchError extends DBException {
    public InsertTypeMismatchError() {
        super("Insertion has failed: Types are not matched");
    }
}
