package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TableRow {
    TableSchema tableSchema;
    ArrayList<DataValue> dataValues;

    static private final Pattern rowPattern = Pattern.compile("'[^']*'|[^,]+");

    public TableRow(TableSchema schema) {
        tableSchema = schema;
        dataValues = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        final TableRow other = (TableRow)obj;
        if (!tableSchema.equals(other.tableSchema)) {
            return false;
        }

        for (ColumnSchema columnSchema : tableSchema.columns.values()) {
            if (!columnSchema.isPrimary()) {
                continue;
            }
            int primaryKeyIndex = columnSchema.getOrder();
            DataValue lhsValue = dataValues.get(primaryKeyIndex);
            DataValue rhsValue = other.dataValues.get(primaryKeyIndex);
            if (lhsValue.isEqual(rhsValue) != LogicValue.TRUE) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(ArrayList<String> columnNames, ArrayList<DataValue> columnValues) {
        if (columnNames == null || columnValues == null ||
                columnNames.size() != columnValues.size()) {
            return false;
        }

        for (int i = 0; i < columnNames.size(); i++) {
            String columnName = columnNames.get(i);
            DataValue columnValue = columnValues.get(i);

            DataValue foundValue = getDataValue(columnName);
            if (foundValue == null || foundValue.isNull() ||
                    foundValue.isEqual(columnValue) != LogicValue.TRUE) {
                return false;
            }
        }
        return true;
    }

    public String getKey() { return getKey(tableSchema.getName()); }
    public static String getKey(String tableName) { return "r-" + tableName; }

    public TableSchema getTableSchema() { return tableSchema; }
    public String getTableName() { return tableSchema.getName(); }
    public ArrayList<DataValue> getDataValues() { return dataValues; }

    public DataValue getDataValue(String columnName) {
        ColumnSchema columnSchema = tableSchema.columns.get(columnName);
        if (columnSchema == null) {
            return null;
        }
        return getDataValue(columnSchema.getOrder());
    }

    public DataValue getDataValue(ColumnSchema targetSchema) {
        ColumnSchema columnSchema = tableSchema.columns.get(targetSchema.getName());
        if (columnSchema == null || !columnSchema.equals(targetSchema)) {
            return null;
        }
        return getDataValue(columnSchema.getOrder());
    }

    public DataValue getDataValue(int columnIndex) {
        return dataValues.get(columnIndex);
    }

    public HashMap<ColumnSchema, DataValue> getPrimaryKey() {
        HashMap<ColumnSchema, DataValue> primaryKeys = new HashMap<>();
        for (ColumnSchema columnSchema : tableSchema.columns.values()) {
            if (columnSchema.isPrimary()) {
                primaryKeys.put(columnSchema, dataValues.get(columnSchema.getOrder()));
            }
        }
        return primaryKeys;
    }

    public HashMap<ColumnSchema, DataValue> getForeignKey(String targetTableName) {
        HashMap<ColumnSchema, DataValue> foreignKeys = new HashMap<>();
        for (ColumnSchema columnSchema : tableSchema.columns.values()) {
            Reference reference = columnSchema.getReference();
            if (reference != null && reference.getTableName().equals(targetTableName)) {
                foreignKeys.put(columnSchema, dataValues.get(columnSchema.getOrder()));
            }
        }
        return foreignKeys;
    }

    // CAUTION: call order of addValue is very important
    public void addValue(DataValue value) { dataValues.add(value); }

    public String serialize() {
        // format: DataValue1, DataValue2, ...
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < dataValues.size(); i++) {
            builder.append(dataValues.get(i).serialize());
            if (i != dataValues.size() - 1) {
                builder.append(',');
            }
        }
        return builder.toString();
    }

    public String serializeWithoutQuote() {
        // format: DataValue1, DataValue2, ...
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < dataValues.size(); i++) {
            builder.append(dataValues.get(i).serializeWithoutQuote());
            if (i != dataValues.size() - 1) {
                builder.append(',');
            }
        }
        return builder.toString();
    }

    public void deserialize(String rawValue) {
        // format: DataValue1, DataValue2, ...
        Matcher matcher = TableRow.rowPattern.matcher(rawValue);

        // read every columns from row raw data
        for (ColumnSchema columnSchema : tableSchema.getOrderedColumns()) {
            matcher.find();
            DataValue dataValue = new DataValue(columnSchema.getDataType());
            dataValue.deserialize(matcher.group());
            dataValues.add(dataValue);
        }
    }
}