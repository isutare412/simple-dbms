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

import kr.ac.snu.ids.PRJ1_2_2013_12295.query.*;

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

    public void createTable(CreateQuery query) throws DBException {
        String tableName = query.getTableName();

        // check if the table exists
        if (tableExists(tableName)) {
            throw new TableExistenceError();
        }

        // create table model
        Table table = new Table(tableName);

        // add column type data
        for (Column column : query.getColumns()) {
            table.addColumn(column);
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
            Table referredTable = getTable(targetTableName);

            // check if target table exists
            if (referredTable == null) {
                throw new ReferenceTableExistenceError();
            }

            table.registerForeignKey(referredTable, refers, referreds);
        }

        // save created table model to database
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

    public void showTables() throws DBException {
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(Table.getRangeKey().getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKeyRange(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                throw new ShowTablesNoTable();
            }

            // collect table names
            ArrayList<String> tableNames = new ArrayList<String>();
            do {
                String rawTableName = new String(key.getData(), "UTF-8");
                tableNames.add(rawTableName.substring(Table.getRangeKey().length()));
            } while (cursor.getNext(key, value, LockMode.DEFAULT) == OperationStatus.SUCCESS);

            // print table names
            System.out.println("-------------------------------");
            for (String tableName : tableNames) {
                System.out.println(tableName);
            }
            System.out.println("-------------------------------");
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
}
