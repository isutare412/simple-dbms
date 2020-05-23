package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class CharLengthError extends DBException {
    public CharLengthError() {
        super("Char length should be over 0");
    }
}