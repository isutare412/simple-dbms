package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.lang.StringBuilder;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Column {
    private int order;
    private String name;
    private String tableName;
    private DataType type;
    private boolean nullable = true;
    private boolean primaryKey = false;
    private Reference reference = null;

    static Pattern valuePattern = Pattern.compile("\\([^\\(\\)]*\\)|[^,]+");

    public Column(String name) {
        this.name = name;
        type = new DataType();
    }

    public int getOrder() { return order; }
    public String getName() { return name; }
    public String getTableName() { return tableName; }
    public DataType getDataType() { return type; }
    public boolean getNullable() { return nullable; }
    public boolean getPrimaryKey() { return primaryKey; }
    public Reference getReference() { return reference; }
    public void setOrder(int order) { this.order = order; }
    public void setTableName(String name) { this.tableName = name; }
    public void setDataType(DataType type) { this.type = type; }
    public void setNullable(boolean able) { this.nullable = able; }
    public void setPrimaryKey(boolean able) { this.primaryKey = able; }
    public void setReference(String tableName, String columnName) { reference = new Reference(tableName, columnName); }

    public boolean isSameType(Column rhs) { return type.equals(rhs.type); }

    public String getKey() { return getKey(tableName); }
    public static String getKey(String tableName) { return "c-" + tableName; }

    public String toValue() {
        // format: name , order, type , charLength , nullable , primaryKey , reference
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        builder.append(',');
        builder.append(String.valueOf(order));
        builder.append(',');
        builder.append(type.baseType.name());
        builder.append(',');
        builder.append(String.valueOf(type.charLength));
        builder.append(',');
        builder.append(String.valueOf(nullable));
        builder.append(',');
        builder.append(String.valueOf(primaryKey));
        builder.append(',');
        builder.append(reference == null ? "null" : reference.toValue());
        return builder.toString();
    }

    public static Column fromValue(String value) {
        // format: name, order , type , charLength , nullable , primaryKey , reference
        Matcher matcher = Column.valuePattern.matcher(value);
        matcher.find(); Column column = new Column(matcher.group());
        matcher.find(); column.order = Integer.parseInt(matcher.group());
        matcher.find(); column.type.baseType = BaseType.getEnum(matcher.group());
        matcher.find(); column.type.charLength = Integer.parseInt(matcher.group());
        matcher.find(); column.nullable = Boolean.parseBoolean(matcher.group());
        matcher.find(); column.primaryKey = Boolean.parseBoolean(matcher.group());
        matcher.find(); column.reference = Reference.fromValue(matcher.group());
        return column;
    }
}

class Reference {
    String tableName;
    String columnName;

    static Pattern valuePattern = Pattern.compile("\\(([^\\(\\)]+)-([^\\(\\)]+)\\)");

    public Reference(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String getTableName() { return tableName; }

    public String toValue() {
        // format: ( tableName - columnName )
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        builder.append(tableName);
        builder.append('-');
        builder.append(columnName);
        builder.append(')');
        return builder.toString();
    }

    static Reference fromValue(String value) {
        // format: ( tableName - columnName )
        Matcher matcher = Reference.valuePattern.matcher(value);
        if (!matcher.find()) {
            return null;
        }
        return new Reference(matcher.group(1), matcher.group(2));
    }
}
