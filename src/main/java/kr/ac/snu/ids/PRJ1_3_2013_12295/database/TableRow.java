package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
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

    public String getKey() { return getKey(tableSchema.getName()); }
    public static String getKey(String tableName) { return "r-" + tableName; }

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