package kr.ac.snu.ids.PRJ1_2_2013_12295.Database;

public class Column {
    String name;
    String tableName;
    DataType type;
    Boolean primaryKey = false;
    Reference reference;
    Boolean nullable = true;
}

class Reference {
    String tableName;
    String columnName;
}
