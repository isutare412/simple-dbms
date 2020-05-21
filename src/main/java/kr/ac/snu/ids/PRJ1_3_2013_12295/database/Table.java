package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.StringBuilder;

public class Table {
    private String name;
    private HashSet<String> referencedByTableNames;
    HashMap<String, Column> columns;

    static Pattern valuePattern = Pattern.compile("\\([^\\(\\)]*\\)|[^,]+");
    static Pattern listPattern = Pattern.compile("[^,\\(\\)]+");

    public Table(String name) {
        this.name = name;
        this.columns = new HashMap<String, Column>();
        this.referencedByTableNames = new HashSet<String>();
    }

    public String getName() { return name; }
    public HashSet<String> getReferencedBy() { return referencedByTableNames; }
    public HashSet<String> getReferencing() {
        HashSet<String> tables = new HashSet<String>();

        for (Column column : columns.values()) {
            Reference reference = column.getReference();
            if (reference == null) {
                continue;
            }
            tables.add(reference.getTableName());
        }

        return tables;
    }
    public int getPrimaryKeyCount() {
        int count = 0;
        for (Column column : columns.values()) {
            if (column.getPrimaryKey() == true) ++count;
        }
        return count;
    }

    public String getKey() { return Table.getKey(name); }
    static public String getKey(String name) { return getRangeKey() + name; }
    static public String getRangeKey() { return "t-"; }

    public String toValue() {
        // format: ( referencedBy, ... )
        if (referencedByTableNames.size() <= 0) {
            return "null";
        }

        StringBuilder builder = new StringBuilder();
        builder.append('(');
        for (String tableName : referencedByTableNames) {
            builder.append(tableName);
            builder.append(',');
        }
        builder.deleteCharAt(builder.length()-1); // cancel last ','
        builder.append(')');

        return builder.toString();
    }

    public void fromValue(String value) {
        // format: ( referencedBy, ... )
        Matcher valueMatcher = Table.valuePattern.matcher(value);

        // parse reference data
        valueMatcher.find();
        String referenceList = valueMatcher.group();
        if (referenceList.startsWith("(")) {
            Matcher listMatcher = Table.listPattern.matcher(referenceList);
            while (listMatcher.find()) {
                referencedByTableNames.add(listMatcher.group());
            }
        }
    }

    public void addReferencedBy(String tableName) {
        referencedByTableNames.add(tableName);
    }
    public boolean removeReferencedBy(String tableName) {
        return referencedByTableNames.remove(tableName);
    }

    public void addColumn(Column column) throws DBException {
        // check column duplicates
        if (columns.containsKey(column.getName())) {
            throw new DuplicateColumnDefError();
        }

        // check char length
        if (column.getDataType() == DataType.CHAR && column.getCharLength() <= 0) {
            throw new CharLengthError();
        }

        column.setTableName(this.name);
        columns.put(column.getName(), column);
    }

    public void registerPrimaryKey(String columnName) throws DBException {
        Column column = columns.get(columnName);

        // check if the column exists
        if (column == null) {
            throw new NonExistingColumnDefError(columnName);
        }

        // check if primary key definition is duplicated
        if (column.getPrimaryKey() == true) {
            throw new DuplicatePrimaryKeyDefError();
        }

        // set primary key
        column.setPrimaryKey(true);
        column.setNullable(false);
    }

    public void registerForeignKey(
        Table targetTable,
        ArrayList<String> refers,
        ArrayList<String> referreds
    ) throws DBException {
        // count unique columns that this table referenced
        HashSet<String> referencedColumns = new HashSet<String>();

        for (int i = 0; i < refers.size(); i++) {
            String sourceColumnName = refers.get(i);
            Column sourceColumn = this.columns.get(sourceColumnName);
            String targetColumnName = referreds.get(i);
            Column targetColumn = targetTable.columns.get(targetColumnName);

            // check the referencing column exists
            if (sourceColumn == null) {
                throw new NonExistingColumnDefError(sourceColumnName);
            }

            // check if the referenced column exists
            if (targetColumn == null) {
                throw new ReferenceColumnExistenceError();
            }

            // check both columns have same type
            if (!targetColumn.isSameType(sourceColumn)) {
                throw new ReferenceTypeError();
            }

            // check referenced column is primary key
            if (targetColumn.getPrimaryKey() == false) {
                throw new ReferenceNonPrimaryKeyError();
            }

            // remember referenced unique columns for check
            referencedColumns.add(targetColumn.getName());

            // set reference data to the referencing column
            sourceColumn.setReference(targetTable.getName(), targetColumnName);
        }

        // check if this table has referenced all primary keys of target table
        if (referencedColumns.size() != targetTable.getPrimaryKeyCount()) {
            throw new ReferenceNonPrimaryKeyError();
        }

        targetTable.addReferencedBy(getName());
    }

    public boolean hasColumn(String columnName) {
        return columns.containsKey(columnName);
    }
}