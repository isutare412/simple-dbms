package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

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
    public void setTableName(String name) { this.tableName = name; }
    public void setDataType(DataType type) { this.type = type; }
    public void setCharLength(int length) { this.charLength = length; }
    public void setNullable(boolean able) { this.nullable = able; }
}

class Reference {
    String tableName;
    String columnName;
}
