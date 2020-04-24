package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.lang.StringBuilder;

public class Column {
    private String name;
    private String tableName;
    private DataType type;
    private int charLength;
    private boolean nullable = true;
    private boolean primaryKey = false;
    private Reference reference = null;

    public Column(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public String getTableName() { return tableName; }
    public boolean getPrimaryKey() { return primaryKey; }
    public void setTableName(String name) { this.tableName = name; }
    public void setDataType(DataType type) { this.type = type; }
    public void setCharLength(int length) { this.charLength = length; }
    public void setNullable(boolean able) { this.nullable = able; }
    public void setPrimaryKey(boolean able) { this.primaryKey = able; }
    public void putReference(String tableName, String columnName) { reference = new Reference(tableName, columnName); }

    public boolean isSameType(Column rhs) {
        if (type != rhs.type) {
            return false;
        }
        if (type == DataType.CHAR && charLength != rhs.charLength) {
            return false;
        }
        return true;
    }

    public String getKey() { return getKey(tableName); }
    public static String getKey(String tableName) { return "c-" + tableName; }

    public String toValue() {
        // format: name , type , charLength , nullable , primaryKey , reference
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(',');
        builder.append(type.name());
        builder.append(',');
        builder.append(String.valueOf(charLength));
        builder.append(',');
        builder.append(String.valueOf(nullable));
        builder.append(',');
        builder.append(String.valueOf(primaryKey));
        builder.append(',');
        builder.append(reference == null ? "null" : reference.toValue());
        return builder.toString();
    }

    public static Column fromValue(String value) {
        // format: name , type , charLength , nullable , primaryKey , reference
        String[] tokens = value.split(",");
        if (tokens.length != 6) {
            return null;
        }

        Column column = new Column(tokens[0]);
        column.type = DataType.getEnum(tokens[1]);
        column.charLength = Integer.parseInt(tokens[2]);
        column.nullable = Boolean.parseBoolean(tokens[3]);
        column.primaryKey = Boolean.parseBoolean(tokens[4]);
        column.reference = Reference.fromValue(tokens[5]);
        return column;
    }
}

class Reference {
    String tableName;
    String columnName;

    public Reference(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String toValue() {
        // format: tableName - columnName
        StringBuilder builder = new StringBuilder();
        builder.append(tableName);
        builder.append('-');
        builder.append(columnName);
        return builder.toString();
    }

    static Reference fromValue(String value) {
        // format: tableName - columnName
        String[] tokens = value.split("-");
        if (tokens.length != 2) {
            return null;
        }
        return new Reference(tokens[0], tokens[1]);
    }
}
