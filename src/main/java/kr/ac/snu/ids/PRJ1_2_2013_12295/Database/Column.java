package database;

public class Column {
    private String name;
    private String tableName;
    private DataType type;
    private int charLength;
    private boolean primaryKey = false;
    private Reference reference = null;
    private boolean nullable = true;
}

class Reference {
    String tableName;
    String columnName;
}
