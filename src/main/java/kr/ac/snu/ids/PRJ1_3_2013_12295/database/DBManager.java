package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.lang.StringBuilder;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import kr.ac.snu.ids.PRJ1_3_2013_12295.query.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class DBManager {
    private Environment environment;
    private Database database;

    public DBManager() {
        // instantiate EnvironmentConfig
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);

        // instantiate Environment. make directory if not exists.
        File dbDir = new File("db/");
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }
        environment  = new Environment(dbDir, envConfig);

        // intantiate Database
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(true);
        database  = environment.openDatabase(null, "DatabaseHolder", dbConfig);
    }

    /////////////////////////////////////////////////////////////////////////
    // query methods
    /////////////////////////////////////////////////////////////////////////

    public String createTable(CreateQuery query) throws DBException {
        String tableName = query.getTableName();

        // check if the table exists
        if (tableExists(tableName)) {
            throw new TableExistenceError();
        }

        // create table model
        TableSchema table = new TableSchema(tableName);

        // add column type data
        for (ColumnSchema column : query.getColumns()) {
            table.addColumn(column);
        }

        // check primary key definition statement is duplicated
        if (query.getPrimaryKeyAddCount() > 1) {
            throw new DuplicatePrimaryKeyDefError();
        }

        // register primary keys
        for (String pkColumnName : query.getPrimaryKeyColumns()) {
            table.registerPrimaryKey(pkColumnName);
        }

        // check if primary key exists
        if (table.getPrimaryKeyCount() <= 0) {
            throw new NonExistingPrimaryKeyError();
        }

        // register foreign keys
        for (CreateQuery.ReferenceConstraint referenceData : query.getReferences()) {
            String targetTableName = referenceData.getTargetTableName();
            ArrayList<String> refers = referenceData.getReferencings();
            ArrayList<String> referreds = referenceData.getReferenceds();

            // the table to reference does not exists
            if (!tableExists(targetTableName)) {
                throw new ReferenceTableExistenceError();
            }

            // size of referencing columns and referenced columns is different
            if (refers.size() != referreds.size()) {
                throw new ReferenceTypeError();
            }

            // read target table from database
            TableSchema referredTable = getTable(targetTableName);

            // check if target table exists
            if (referredTable == null) {
                throw new ReferenceTableExistenceError();
            }

            // register reference data to table model
            table.registerForeignKey(referredTable, refers, referreds);

            // save reference data to referenced table
            saveTableSchema(referredTable);
        }

        // save created table model to database
        saveTableSchema(table);

        return String.format("\'%s\' table is created", tableName);
    }

    public String dropTable(String tableName) throws DBException {
        TableSchema table = getTable(tableName);

        // check if the table exists
        if (table == null) {
            throw new NoSuchTable();
        }

        // check if any other table references the table
        HashSet<String> referecings = table.getReferencedBy();
        if (referecings.size() > 0) {
            throw new DropReferencedTableError(tableName);
        }

        // remove reference data of the table is referencing
        HashSet<String> referenceds = table.getReferencing();
        for (String referencedName : referenceds) {
            TableSchema referredTable = getTable(referencedName);
            referredTable.removeReferencedBy(tableName);
            saveTableSchema(referredTable);
        }

        // delete table data from database
        deleteRows(tableName);
        deleteTable(tableName);

        return String.format("\'%s\' table is dropped", tableName);
    }

    public String showTables() throws DBException {
        StringBuilder builder = new StringBuilder();

        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(TableSchema.getRangeKey().getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKeyRange(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                throw new ShowTablesNoTable();
            }

            // collect table names
            ArrayList<String> tableNames = new ArrayList<String>();
            do {
                String rawTableName = new String(key.getData(), "UTF-8");
                tableNames.add(rawTableName.substring(TableSchema.getRangeKey().length()));
            } while (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS);

            // print table names
            builder.append("-------------------------------\n");
            for (String tableName : tableNames) {
                builder.append(String.format("%s\n", tableName));
            }
            builder.append("-------------------------------");
        } catch (DBException e) {
            // handled by callee
            throw e;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return builder.toString();
    }

    public String descTable(String tableName) throws DBException {
        TableSchema table = getTable(tableName);
        if (table == null) {
            throw new NoSuchTable();
        }

        StringBuilder builder = new StringBuilder();;
        builder.append("-----------------------------------------------------------------\n");
        builder.append(String.format("table_name [%s]\n", table.getName()));
        builder.append(String.format("%-28s %-14s %-14s %s\n", "column_name", "type", "null", "key"));
        for (ColumnSchema column : table.getOrderedColumns()) {
            final DataType dataType = column.getDataType();
            String formattedType = dataType.baseType.name().toLowerCase();
            if (dataType.baseType == BaseType.CHAR) {
                formattedType += String.format("(%d)", dataType.charLength);
            }

            // format keys
            String formattedKey = "";
            boolean isPrimaryKey = column.isPrimary();
            boolean isForeignKey = column.getReference() != null;
            if (isPrimaryKey && isForeignKey) {
                formattedKey = "PRI/FOR";
            } else if (isPrimaryKey) {
                formattedKey = "PRI";
            } else if (isForeignKey) {
                formattedKey = "FOR";
            }

            builder.append(
                String.format("%-28s %-14s %-14s %s\n",
                column.getName(),
                formattedType,
                column.isNullable() ? "Y" : "N",
                formattedKey
            ));
        }
        builder.append("-----------------------------------------------------------------");
        return builder.toString();
    }

    public String insert(InsertQuery query) throws DBException {
        final String tableName = query.getTableName();
        final TableSchema tableSchema = getTable(tableName);

        // check the table exists
        if (tableSchema == null) {
            throw new NoSuchTable();
        }

        ArrayList<String> columnNames = query.getColumnNames();
        ArrayList<DataValue> columnValues = query.getColumnValues();

        // check column count
        if (columnNames == null || columnNames.size() == 0) {
            // check number of column values
            if (tableSchema.columns.size() != columnValues.size()) {
                throw new InsertTypeMismatchError();
            }
        } else {
            // check number of column values with number of column names
            if (columnValues.size() != columnNames.size()) {
                throw new InsertTypeMismatchError();
            }

            // check size of columns
            if (columnValues.size() > tableSchema.columns.size()) {
                throw new InsertTypeMismatchError();
            }

            // check for column existence for each given column names
            for (String columnName : columnNames) {
                if (!tableSchema.columns.containsKey(columnName)) {
                    throw new InsertColumnExistenceError(columnName);
                }
            }
        }

        // rearrange columnValues by table schema order
        if (columnNames != null && columnNames.size() != 0) {
            ArrayList<DataValue> reorderedValues = new ArrayList<>();
            for (ColumnSchema columnSchema : tableSchema.getOrderedColumns()) {
                // dataValue is null if not given by InsertQuery
                int index = columnNames.indexOf(columnSchema.getName());
                DataValue dataValue = index == -1 ? new DataValue() : columnValues.get(index);
                reorderedValues.add(dataValue);
            }
            columnValues = reorderedValues;
        }

        // construct new table row model
        TableRow newRow = new TableRow(tableSchema);

        // check type of each column values
        ArrayList<ColumnSchema> columnSchemas = tableSchema.getOrderedColumns();
        for (int i = 0; i < columnSchemas.size(); i++) {
            ColumnSchema columnSchema = columnSchemas.get(i);
            DataValue dataValue = columnValues.get(i);

            // limit charater length of value by type
            if (!dataValue.isNull() && dataValue.getDataType().baseType == BaseType.CHAR) {
                dataValue.setCharLength(columnSchema.getDataType().charLength);
            }

            // check each column type with value type
            if (!dataValue.isNull() &&
                    !columnSchema.getDataType().equals(dataValue.getDataType())) {
                throw new InsertTypeMismatchError();
            }

            // check if the column is nullable
            if (!columnSchema.isNullable() && dataValue.isNull()) {
                throw new InsertColumnNonNullableError(columnSchema.getName());
            }

            // add DataValue into new TableRow
            newRow.addValue(dataValue);
        }

        // read table instance from database
        final TableInstance tableInstance = getInstance(tableSchema);

        // check primary key contraints
        if (tableInstance.contains(newRow)) {
            throw new InsertDuplicatePrimaryKeyError();
        }

        // check referential contraints
        for (String targetTableName : tableSchema.getReferencing()) {
            HashMap<ColumnSchema, DataValue> foreignKey = newRow.getForeignKey(targetTableName);
            if (foreignKey == null || foreignKey.size() == 0) {
                continue;
            }

            // do not need to check referential contraints for all null values
            boolean allNull = true;
            for (DataValue foreignKeyValue : foreignKey.values()) {
                allNull &= foreignKeyValue.isNull();
            }
            if (allNull) {
                continue;
            }

            // build reference data for checking
            TableSchema targetSchema = getTable(targetTableName);
            ArrayList<String> referColumnNames = new ArrayList<>();
            ArrayList<DataValue> referColumnValues = new ArrayList<>();
            for (Entry<ColumnSchema, DataValue> entry : foreignKey.entrySet()) {
                String targetColumnName = entry.getKey().getReference().getColumnName();
                ColumnSchema columnSchema = targetSchema.columns.get(targetColumnName);
                referColumnNames.add(columnSchema.getName());
                referColumnValues.add(entry.getValue());
            }

            // check the value of foreign key exists in the target table instance
            TableInstance targetInstance = getInstance(targetSchema);
            if (!targetInstance.valueExists(referColumnNames, referColumnValues)) {
                throw new InsertReferentialIntegrityError();
            }
        }

        // now write new row to the database
        saveRow(newRow);

        return "The row is inserted";
    }

    public String select(SelectQuery query) throws DBException {
        // read table instances from the database
        ArrayList<TableInstance> targetInstances = new ArrayList<>();
        ArrayList<String> targetTableNames = query.getTableNames();
        for (String tableName : targetTableNames) {
            TableSchema tableSchema = getTable(tableName);
            TableInstance tableInstace = getInstance(tableSchema);
            if (tableInstace == null) {
                throw new SelectTableExistenceError(tableName);
            }
            targetInstances.add(tableInstace);
        }

        // build cartisian product from instances
        InstanceBuffer buffer = new InstanceBuffer(targetInstances);

        // register alias to InstanceBuffer
        for (Entry<String, String> entry : query.getAlias().entrySet()) {
            buffer.registerAlias(entry.getKey(), entry.getValue());
        }

        // exclude all records that does not match where clause
        buffer.filter(query.getEvalutor());

        String formattedTable = null;
        if (query.isAsterisk()) {
            ArrayList<String> tableHeader = buffer.getTableHeader();
            ArrayList<ArrayList<String>> tableBody = buffer.getTableBody();
            formattedTable = formatTable(tableHeader, tableBody);
        } else {
            ArrayList<SelectedColumn> selectedColumns = query.getSelectedColumns();
            ArrayList<String> tableHeader = buffer.getTableHeader(selectedColumns);
            ArrayList<ArrayList<String>> tableBody = buffer.getTableBody(selectedColumns);
            formattedTable = formatTable(tableHeader, tableBody);
        }

        return formattedTable;
    }

    public String delete(DeleteQuery query) throws DBException {
        final String tableName = query.getTableName();
        final TableSchema tableSchema = getTable(tableName);

        // check the table exists
        if (tableSchema == null) {
            throw new NoSuchTable();
        }

        // read table instance from the database
        TableInstance instance = getInstance(tableSchema);
        if (instance == null) {
            throw new NoSuchTable();
        }

        ArrayList<TableInstance> instances = new ArrayList<>();
        instances.add(instance);

        // build cartisian product from instances
        InstanceBuffer buffer = new InstanceBuffer(instances);

        // exclude all records that does not match where clause
        buffer.filter(query.getEvalutor());

        // read all table instances which reference the deleted table
        HashMap<String, TableInstance> referTableInstances = new HashMap<>();
        for (String referTableName : tableSchema.getReferencedBy()) {
            TableSchema referTableSchema = getTable(referTableName);
            TableInstance referTableInstance = getInstance(referTableSchema);
            referTableInstances.put(referTableName, referTableInstance);
        }

        // check for referential constraint
        LinkedList<ArrayList<TableRow>> deleteCandidates = buffer.getRecords();
        Iterator<ArrayList<TableRow>> iter = deleteCandidates.iterator();
        int canceledByConstraint = 0;
        while (iter.hasNext()) {
            TableRow tableRow = iter.next().get(0);
            HashMap<ColumnSchema, DataValue> primaryKeys = tableRow.getPrimaryKey();

            // will collect all column schemas which reference the row
            ArrayList<ColumnSchema> referencingColumns = new ArrayList<>();
            ArrayList<TableRow> referencingRows = new ArrayList<>();

            // check if any table row is referencing deletion candidate rows
            for (TableInstance referTable : referTableInstances.values()) {
                // build reference data for existence check
                ArrayList<String> columnNames = new ArrayList<>();
                ArrayList<DataValue> columnValues = new ArrayList<>();
                for (Entry<ColumnSchema, DataValue> entry : primaryKeys.entrySet()) {
                    ColumnSchema referColumn = referTable.getColumnReferencing(entry.getKey());
                    columnNames.add(referColumn.getName());
                    columnValues.add(entry.getValue());
                }

                // collect column schemas which reference the row
                TableRow matchingRow = referTable.getMatchingRow(columnNames, columnValues);
                if (matchingRow != null) {
                    referencingRows.add(matchingRow);
                    for (String columnName : columnNames) {
                        referencingColumns.add(referTable.getColumnSchema(columnName));
                    }
                }
            }

            // reference exists
            if (referencingColumns.size() != 0) {
                boolean isAllNullable = true;
                for (ColumnSchema referColumn : referencingColumns) {
                    isAllNullable &= referColumn.isNullable();
                }

                if (!isAllNullable) {
                    // exclude deletion candidate row
                    iter.remove();
                    canceledByConstraint++;
                } else {
                    // update all foreign keys to null
                    for (TableRow referRow : referencingRows) {
                        deleteRow(referRow);
                        for (ColumnSchema columnSchema : referencingColumns) {
                            DataValue value = referRow.getDataValue(columnSchema);
                            if (value != null) {
                                value.setNull();
                            }
                        }
                        saveRow(referRow);
                    }
                }
            }
        }

        // delete cadidates
        for (ArrayList<TableRow> singleRow : deleteCandidates) {
            deleteRow(singleRow.get(0));
        }

        StringBuilder builder = new StringBuilder();
        if (canceledByConstraint > 0) {
            builder.append(canceledByConstraint);
            builder.append(" row(s) not deleted due to referential integrity\n");
        }
        builder.append(deleteCandidates.size());
        builder.append(" row(s) are deleted");
        return builder.toString();
    }

    // close handles.
    public void close() {
        if (database != null) {
            database.close();
        };
        if (environment != null) {
            environment.close();
        }
    }

    /////////////////////////////////////////////////////////////////////////
    // helper functions
    /////////////////////////////////////////////////////////////////////////

    // checks whether table with tableName exists.
    private boolean tableExists(String tableName) {
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(TableSchema.getKey(tableName).getBytes("UTF-8"));

            if (cursor.getSearchKey(key, null, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return true;
    }

    // Construct complete table from database. If the table does not exists, returns null.
    private TableSchema getTable(String tableName) {
        if (!tableExists(tableName)) {
            return null;
        }

        // construct table model
        TableSchema table = new TableSchema(tableName);

        // read table reference data
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(TableSchema.getKey(tableName).getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKey(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return null;
            }

            do {
                table.deserialize(new String(value.getData(), "UTF-8"));
            } while (cursor.getNextDup(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        // read column data of the table
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(ColumnSchema.getKey(tableName).getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKey(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return null;
            }

            do {
                ColumnSchema column = ColumnSchema.deserialize(new String(value.getData(), "UTF-8"));
                if (column == null) {
                    return null;
                }
                table.addColumn(column);
            } while (cursor.getNextDup(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return table;
    }

    // read table instance from database
    private TableInstance getInstance(TableSchema schema) {
        if (schema == null) {
            return null;
        }

        // construct table instance model from TableSchema
        TableInstance tableInstance = new TableInstance(schema);

        // read instance data of the table
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(TableRow.getKey(schema.getName()).getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKey(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return tableInstance;
            }

            do {
                tableInstance.readRow(new String(value.getData(), "UTF-8"));
            } while (cursor.getNextDup(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return tableInstance;
    }

    // save table to database without any check. if the table already exists, it is overwrited.
    private void saveTableSchema(TableSchema table) {
        deleteTable(table.getName());

        // save table model to database
        Transaction txn = null;
        Cursor cursor = null;

        try {
            // transaction required for table, column save process.
            txn = environment.beginTransaction(null, null);
            cursor = database.openCursor(null, null);

            // save table name
            DatabaseEntry tableKey = new DatabaseEntry(table.getKey().getBytes("UTF-8"));
            DatabaseEntry tableValue = new DatabaseEntry(table.serialize().getBytes("UTF-8"));
            if (cursor.putNoOverwrite(tableKey, tableValue) == OperationStatus.KEYEXIST) {
                throw new TableExistenceError();
            }

            // save columns
            for (ColumnSchema column : table.columns.values()) {
                DatabaseEntry columnKey = new DatabaseEntry(column.getKey().getBytes("UTF-8"));
                DatabaseEntry columnValue = new DatabaseEntry(column.serialize().getBytes("UTF-8"));
                cursor.put(columnKey, columnValue);
            }

            // commit transaction
            cursor.close();
            cursor = null;
            txn.commit();
            txn = null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }

            if (txn != null) {
                txn.abort();
                txn = null;
            }
        }
    }

    // save TableRow into database without any check.
    private void saveRow(TableRow tableRow) {
        // save columns
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry rowKey = new DatabaseEntry(tableRow.getKey().getBytes("UTF-8"));
            DatabaseEntry rowValue = new DatabaseEntry(tableRow.serialize().getBytes("UTF-8"));
            cursor.put(rowKey, rowValue);

            cursor.close();
            cursor = null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
    }

    // delete TableRow from database without any check. returns true if deleted.
    private boolean deleteRow(TableRow tableRow) {
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(tableRow.getKey().getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry(tableRow.serialize().getBytes("UTF-8"));

            if (cursor.getSearchBoth(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return false;
            }
            cursor.delete();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return true;
    }

    // delete all rows with the tableName from the database
    private boolean deleteRows(String tableName) {
        return deleteKeys(TableRow.getKey(tableName));
    }

    // delete all tableName related key-values. returns true if any key-value is deleted.
    private boolean deleteTable(String tableName) {
        boolean deleted = false;
        deleted |= deleteKeys(TableSchema.getKey(tableName));
        deleted |= deleteKeys(ColumnSchema.getKey(tableName));
        //deleted |= deleteKeys(TableRow.getKey(tableName));
        return deleted;
    }

    // delete all key-value pairs with keyName key. returns true if any key is deleted.
    private boolean deleteKeys(String keyName) {
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(keyName.getBytes("UTF-8"));

            if (cursor.getSearchKey(key, null, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return false;
            }

            do {
                cursor.delete();
            } while (cursor.getNextDup(key, null, LockMode.DEFAULT) == OperationStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return true;
    }

    // returns pretty formated string from given header, body
    private String formatTable(ArrayList<String> header, ArrayList<ArrayList<String>> body) {
        ArrayList<Integer> columnMaxSize = new ArrayList<>();
        for (int i = 0; i < header.size(); i++) {
            int maxSize = 0;
            int headerLength = header.get(i).length();
            maxSize = maxSize > headerLength ? maxSize : headerLength;

            for (ArrayList<String> line : body) {
                int bodyLength = line.get(i).length();
                maxSize = maxSize > bodyLength ? maxSize : bodyLength;
            }
            columnMaxSize.add(maxSize);
        }

        String divider = "+";
        for (int i = 0; i < columnMaxSize.size(); i++) {
            divider += String.format("-%s-+", "-".repeat(columnMaxSize.get(i)));
        }

        StringBuilder builder = new StringBuilder();
        builder.append(divider + "\n");
        builder.append('|');
        for (int i = 0; i < header.size(); i++) {
            String headerStr = header.get(i);
            builder.append(' ');
            builder.append(headerStr);
            int sizeDiff = columnMaxSize.get(i) - headerStr.length();
            if (sizeDiff > 0) {
                builder.append(" ".repeat(sizeDiff));
            }
            builder.append(" |");
        }
        builder.append('\n');
        builder.append(divider + "\n");

        for (ArrayList<String> line : body) {
            builder.append('|');
            for (int i = 0; i < line.size(); i++) {
                String columnStr = line.get(i);
                builder.append(' ');
                builder.append(columnStr);
                int sizeDiff = columnMaxSize.get(i) - columnStr.length();
                if (sizeDiff > 0) {
                    builder.append(" ".repeat(sizeDiff));
                }
                builder.append(" |");
            }
            builder.append('\n');
        }
        builder.append(divider);
        return builder.toString();
    }
}
