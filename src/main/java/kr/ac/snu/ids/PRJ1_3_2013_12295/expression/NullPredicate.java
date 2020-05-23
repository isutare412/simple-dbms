package kr.ac.snu.ids.PRJ1_3_2013_12295.expression;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class NullPredicate implements Evaluator {
    private String tableName;
    private String columnName;
    private boolean isNull;

    public NullPredicate(String tableName, String columnName, boolean isNull) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.isNull = isNull;
    }

    public LogicValue evaluate(
            InstanceBuffer buffer,
            ArrayList<TableRow> row) throws DBException {
        ColumnSchema columnSchema = buffer.getColumnSchema(tableName, columnName);
        for (TableRow eachRow : row) {
            DataValue dataValue = eachRow.getDataValue(columnSchema);
            if (dataValue != null) {
                return dataValue.isNull() == isNull ? LogicValue.TRUE : LogicValue.FALSE;
            }
        }
        throw new WhereColumnNotExist();
    }
}