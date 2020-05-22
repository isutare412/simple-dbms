package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.StringBuilder;

public class TableSchema {
    private String name;
    private HashSet<String> referencedByTableNames;
    HashMap<String, ColumnSchema> columns;

    static Pattern valuePattern = Pattern.compile("\\([^\\(\\)]*\\)|[^,]+");
    static Pattern listPattern = Pattern.compile("[^,\\(\\)]+");

    public TableSchema(String name) {
        this.name = name;
        this.columns = new HashMap<String, ColumnSchema>();
        this.referencedByTableNames = new HashSet<String>();
    }

    public String getName() { return name; }
    public HashSet<String> getReferencedBy() { return referencedByTableNames; }
    public HashSet<String> getReferencing() {
        HashSet<String> tables = new HashSet<String>();

        for (ColumnSchema column : columns.values()) {
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
        for (ColumnSchema column : columns.values()) {
            if (column.getPrimaryKey() == true) ++count;
        }
        return count;
    }

    public String getKey() { return TableSchema.getKey(name); }
    static public String getKey(String name) { return getRangeKey() + name; }
    static public String getRangeKey() { return "t-"; }

    public String serialize() {
        // format: ( referencedBy, ... )
        if (referencedByTableNames.size() <= 0) {
            return "NULL";
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

    public void deserialize(String value) {
        // format: ( referencedBy, ... )
        Matcher valueMatcher = TableSchema.valuePattern.matcher(value);

        // parse reference data
        valueMatcher.find();
        String referenceList = valueMatcher.group();
        if (referenceList.startsWith("(")) {
            Matcher listMatcher = TableSchema.listPattern.matcher(referenceList);
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

    public void addColumn(ColumnSchema column) throws DBException {
        // check column duplicates
        if (columns.containsKey(column.getName())) {
            throw new DuplicateColumnDefError();
        }

        // check char length
        if (column.getDataType().baseType == BaseType.CHAR && column.getDataType().charLength <= 0) {
            throw new CharLengthError();
        }

        column.setTableName(this.name);
        columns.put(column.getName(), column);
    }

    public void registerPrimaryKey(String columnName) throws DBException {
        ColumnSchema column = columns.get(columnName);

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
        TableSchema targetTable,
        ArrayList<String> refers,
        ArrayList<String> referreds
    ) throws DBException {
        // count unique columns that this table referenced
        HashSet<String> referencedColumns = new HashSet<String>();

        for (int i = 0; i < refers.size(); i++) {
            String sourceColumnName = refers.get(i);
            ColumnSchema sourceColumn = this.columns.get(sourceColumnName);
            String targetColumnName = referreds.get(i);
            ColumnSchema targetColumn = targetTable.columns.get(targetColumnName);

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

    public ArrayList<ColumnSchema> getOrderedColumns() {
        if (columns.size() == 0) {
            return null;
        }

        ArrayList<ColumnSchema> ordered = new ArrayList<ColumnSchema>();
        ordered.addAll(columns.values());
        ordered.sort(new Comparator<ColumnSchema>() {
            @Override
            public int compare(ColumnSchema lhs, ColumnSchema rhs) {
                return lhs.getOrder() - rhs.getOrder();
            }
        });
        return ordered;
    }
}