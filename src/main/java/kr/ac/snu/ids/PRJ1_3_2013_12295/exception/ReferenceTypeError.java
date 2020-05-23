package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class ReferenceTypeError extends DBException {
    public ReferenceTypeError() {
        super("Create table has failed: foreign key references wrong type");
    }
}