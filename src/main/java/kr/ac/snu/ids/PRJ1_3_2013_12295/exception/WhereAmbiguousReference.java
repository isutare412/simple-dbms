package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class WhereAmbiguousReference extends DBException {
    public WhereAmbiguousReference() {
        super("Where clause contains ambiguous reference");
    }
}
