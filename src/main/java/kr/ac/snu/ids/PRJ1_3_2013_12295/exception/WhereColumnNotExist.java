package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class WhereColumnNotExist extends DBException {
    public WhereColumnNotExist() {
        super("Where clause try to reference non existing column");
    }
}
