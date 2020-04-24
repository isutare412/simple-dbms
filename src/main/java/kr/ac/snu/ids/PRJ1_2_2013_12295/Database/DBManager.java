package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
            DatabaseEntry tableValue = new DatabaseEntry(table.getValue().getBytes("UTF-8"));
            if (cursor.putNoOverwrite(tableKey, tableValue) == OperationStatus.KEYEXIST) {
                throw new TableExistenceError();
            }

            // save columns
            for (Column column : table.columns.values()) {
                DatabaseEntry columnKey = new DatabaseEntry(column.getKey().getBytes("UTF-8"));
                DatabaseEntry columnValue = new DatabaseEntry(column.getValue().getBytes("UTF-8"));
                if (cursor.put(columnKey, columnValue) == OperationStatus.KEYEXIST) {
                    throw new TableExistenceError();
                }
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
    // static methods
    /////////////////////////////////////////////////////////////////////////

}
