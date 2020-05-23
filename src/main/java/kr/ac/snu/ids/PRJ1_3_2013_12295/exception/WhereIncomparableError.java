package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class WhereIncomparableError extends DBException {
    public WhereIncomparableError() {
        super("Where clause try to compare incomparable values");
    }
}