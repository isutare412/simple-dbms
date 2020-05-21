package kr.ac.snu.ids.PRJ1_3_2013_12295.query;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;

public class InsertQuery {
    private String tableName;
    private ArrayList<String> columnNames;
    private ArrayList<DataValue> columnValues;

    public InsertQuery(String name) {
        tableName = name;
        columnNames = new ArrayList<>();
        columnValues = new ArrayList<>();
    }

    public void setColumnNames(ArrayList<String> names) { columnNames = names; }
    public void setColumnValues(ArrayList<DataValue> values) { columnValues = values; }

    public String getTableName() { return tableName; }
    public ArrayList<String> getColumnNames() { return columnNames; }
    public ArrayList<DataValue> getColumnValues() { return columnValues; }
}