package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.ArrayList;

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
    // table methods
    /////////////////////////////////////////////////////////////////////////

    public void createTable(Table table) throws DBException {
        if (tableExists(table.getName())) {
            throw new TableExistenceError();
        }

        Transaction txn = null;
        Cursor cursor = null;

        try {
            // transaction required for table, column save process.
            txn = environment.beginTransaction(null, null);
            cursor = database.openCursor(null, null);

            // save table name
            DatabaseEntry tableKey = new DatabaseEntry(table.getKey().getBytes("UTF-8"));
            DatabaseEntry tableValue = new DatabaseEntry(table.toValue().getBytes("UTF-8"));
            if (cursor.putNoOverwrite(tableKey, tableValue) == OperationStatus.KEYEXIST) {
                throw new TableExistenceError();
            }

            // save columns
            for (Column column : table.columns.values()) {
                DatabaseEntry columnKey = new DatabaseEntry(column.getKey().getBytes("UTF-8"));
                DatabaseEntry columnValue = new DatabaseEntry(column.toValue().getBytes("UTF-8"));
                cursor.put(columnKey, columnValue);
            }

            // commit transaction
            cursor.close();
            cursor = null;
            txn.commit();
            txn = null;
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

            if (txn != null) {
                txn.abort();
                txn = null;
            }
        }
    }

    public void tableAddPrimaryKeys(Table table, ArrayList<String> columnNames) throws DBException {
        for (String columnName : columnNames) {
            Column column = table.columns.get(columnName);

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
    }

    // add reference constraints to table.
    public void tableAddForeignKeys(
        Table table,
        ArrayList<String> refers,
        ArrayList<String> referreds,
        String targetTableName
    ) throws DBException {
        // the table to reference does not exists
        if (!tableExists(targetTableName)) {
            throw new ReferenceTableExistenceError();
        }

        // size of referencing columns and referenced columns is different
        if (refers.size() != referreds.size()) {
            throw new ReferenceTypeError();
        }

        // read target table from database
        Table referredTable = getTable(targetTableName);
        if (referredTable == null) {
            throw new ReferenceTableExistenceError();
        }

        for (int i = 0; i < refers.size(); i++) {
            String sourceColumnName = refers.get(i);
            Column sourceColumn = table.columns.get(sourceColumnName);
            String targetColumnName = referreds.get(i);
            Column targetColumn = referredTable.columns.get(targetColumnName);

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
            if (refers.size() != referredTable.getPrimaryKeyCount()) {
                throw new ReferenceNonPrimaryKeyError();
            }

            // add reference data to the referencing column
            sourceColumn.putReference(targetTableName, targetColumnName);
        }
    }

    // checks whether table with tableName exists.
    public boolean tableExists(String tableName) {
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(Table.getKey(tableName).getBytes("UTF-8"));

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
    private Table getTable(String tableName) {
        if (!tableExists(tableName)) {
            return null;
        }
        Table table = new Table(tableName);

        // read all columns of the table
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(Column.getKey(tableName).getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKey(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return null;
            }

            do {
                Column column = Column.fromValue(new String(value.getData(), "UTF-8"));
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

    // close handles.
    public void close() {
        if (database != null) {
            database.close();
        };
        if (environment != null) {
            environment.close();
        }
    }
}
