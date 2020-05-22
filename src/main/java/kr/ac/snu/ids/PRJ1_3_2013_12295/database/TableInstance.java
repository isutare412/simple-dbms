package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;

public class TableInstance {
    private TableSchema tableSchema;
    private ArrayList<TableRow> rows;

    public TableInstance(TableSchema schema) {
        this.tableSchema = schema;
        rows = new ArrayList<>();
    }

    public void readRow(String rawData) {
        TableRow row = new TableRow(tableSchema);
        row.deserialize(rawData);
        rows.add(row);
    }
}