package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
import java.util.HashMap;

public class TableInstance {
    private TableSchema tableSchema;
    private ArrayList<TableRow> rows;

    public TableInstance(TableSchema schema) {
        this.tableSchema = schema;
        rows = new ArrayList<>();
    }

    public ArrayList<TableRow> getRows() { return rows; }

    public boolean contains(TableRow target) {
        for (TableRow row : rows) {
            if (row.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public boolean foreignKeyExists(ArrayList<String> columnNames,
            ArrayList<DataValue> columnValues) {
        if (columnNames == null || columnValues == null ||
                columnNames.size() != columnValues.size()) {
            return false;
        }

        for (TableRow eachRow : rows) {
            if (eachRow.contains(columnNames, columnValues)) {
                return true;
            }
        }
        return false;
    }

    public void readRow(String rawData) {
        TableRow row = new TableRow(tableSchema);
        row.deserialize(rawData);
        rows.add(row);
    }
}