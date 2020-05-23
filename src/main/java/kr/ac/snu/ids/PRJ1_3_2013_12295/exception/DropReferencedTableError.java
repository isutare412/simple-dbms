package kr.ac.snu.ids.PRJ1_3_2013_12295.exception;

@SuppressWarnings("serial")
public class DropReferencedTableError extends DBException {
    public DropReferencedTableError(String tableName) {
        super("Drop table has failed: \'" + tableName + "\' is referenced by other table");
    }
}
