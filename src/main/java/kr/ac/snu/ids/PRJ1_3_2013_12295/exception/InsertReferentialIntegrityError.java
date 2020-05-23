package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class InsertReferentialIntegrityError extends DBException {
    public InsertReferentialIntegrityError() {
        super("Insertion has failed: Referential integrity violation");
    }
}