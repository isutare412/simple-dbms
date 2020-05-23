package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class NoSuchTable extends DBException {
    public NoSuchTable() {
        super("No such table");
    }
}
