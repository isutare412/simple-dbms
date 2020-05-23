package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class ShowTablesNoTable extends DBException {
    public ShowTablesNoTable() {
        super("There is no table");
    }
}