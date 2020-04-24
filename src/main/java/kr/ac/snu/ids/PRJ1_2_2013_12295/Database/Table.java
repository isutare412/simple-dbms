package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.util.HashMap;

public class Table {
    private String name;
    HashMap<String, Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new HashMap<String, Column>();
    }

    public String getName() { return name; }
    public int getPrimaryKeyCount() {
        int count = 0;
        for (Column column : columns.values()) {
            if (column.getPrimaryKey() == true) ++count;
        }
        return count;
    }

    public String getKey() { return Table.getKey(name); }
    static public String getKey(String name) { return "t-" + name; }

    public String toValue() { return "exists"; }

    public void addColumn(Column column) throws DBException {
        // check column duplicates
        if (columns.containsKey(column.getName())) {
            throw new DuplicateColumnDefError();
        }

        column.setTableName(this.name);
        columns.put(column.getName(), column);
    }

    public boolean hasColumn(String columnName) {
        return columns.containsKey(columnName);
    }
}