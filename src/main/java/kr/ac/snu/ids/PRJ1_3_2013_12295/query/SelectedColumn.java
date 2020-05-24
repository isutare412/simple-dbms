package kr.ac.snu.ids.PRJ1_3_2013_12295.query;

public class SelectedColumn {
    public String tableName;
    public String columnName;
    public String alias;

    public SelectedColumn(String tableName, String columnName, String alias) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.alias = alias;
    }
}