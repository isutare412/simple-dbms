package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.util.HashMap;

public class Table {
    private String name;
    private HashMap<String, Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new HashMap<String, Column>();
    }

    public void addColumn(Column column) throws DBException {
        // check column duplicates
        if (columns.containsKey(column.getName())) {
            throw new DuplicateColumnDefError();
        }

        column.setTableName(this.name);
        columns.put(column.getName(), column);
    }
}