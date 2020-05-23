package kr.ac.snu.ids.PRJ1_3_2013_12295.expression;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class CompOperand {
    private DataValue dataValue;
    private String tableName;
    private String columnName;

    public CompOperand(DataValue value) {
        dataValue = value;
    }

    public CompOperand(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public DataValue evaluate(
            InstanceBuffer buffer, ArrayList<TableRow> row) throws DBException {
        if (dataValue != null) {
            return dataValue;
        }

        ColumnSchema columnSchema = buffer.getColumnSchema(tableName, columnName);
        for (TableRow eachRow : row) {
            DataValue dataValue = eachRow.getDataValue(columnSchema);
            if (dataValue != null) {
                return dataValue;
            }
        }
        throw new WhereColumnNotExist();
    }
}