package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class WhereTableNotSpecified extends DBException {
    public WhereTableNotSpecified() {
        super("Where clause try to reference tables which are not specified");
    }
}
