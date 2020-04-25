package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    private String name;
    HashMap<String, Column> columns;

    public Table(String name) {
        this.name = name;
        this.columns = new HashMap<String, Column>();
    }

    public String getName() { return name; }
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

    public String toValue() { return "exists"; }

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

            // check if all primary keys of referenced table are referenced
            if (refers.size() != targetTable.getPrimaryKeyCount()) {
                throw new ReferenceNonPrimaryKeyError();
            }

            // set reference data to the referencing column
            sourceColumn.setReference(targetTable.getName(), targetColumnName);
        }
    }

    public boolean hasColumn(String columnName) {
        return columns.containsKey(columnName);
    }
}