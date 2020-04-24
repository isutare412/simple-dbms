package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.lang.StringBuilder;

public class Column {
    private String name;
    private String tableName;
    private DataType type;
    private int charLength;
    private boolean primaryKey = false;
    private Reference reference = null;
    private boolean nullable = true;

    public Column(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getTableName() { return tableName; }
    public void setTableName(String name) { this.tableName = name; }
    public void setDataType(DataType type) { this.type = type; }
    public void setCharLength(int length) { this.charLength = length; }
    public void setNullable(boolean able) { this.nullable = able; }

    public String getKey() { return "c-" + tableName + "-" + name; }
    public String getValue() {
        // format: name , type , charLength , nullable , primaryKey , reference
        StringBuilder builder = new StringBuilder();
        builder.append(type.name());
        builder.append(',');
        builder.append(String.valueOf(charLength));
        builder.append(',');
        builder.append(String.valueOf(nullable));
        builder.append(',');
        builder.append(String.valueOf(primaryKey));
        builder.append(',');
        builder.append(String.valueOf(reference));
        return builder.toString();
    }
}

class Reference {
    String tableName;
    String columnName;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append(tableName);
        builder.append(',');
        builder.append(columnName);
        builder.append(')');
        return builder.toString();
    }
}
