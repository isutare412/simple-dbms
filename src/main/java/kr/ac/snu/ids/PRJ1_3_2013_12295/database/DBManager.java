package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
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
        Table table = new Table(tableName);

        // add column type data
        for (Column column : query.getColumns()) {
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
            Table referredTable = getTable(targetTableName);

            // check if target table exists
            if (referredTable == null) {
                throw new ReferenceTableExistenceError();
            }

            // register reference data to table model
            table.registerForeignKey(referredTable, refers, referreds);

            // save reference data to referenced table
            saveTable(referredTable);
        }

        // save created table model to database
        saveTable(table);

        return String.format("\'%s\' table is created", tableName);
    }

    public String dropTable(String tableName) throws DBException {
        Table table = getTable(tableName);

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
            Table referredTable = getTable(referencedName);
            referredTable.removeReferencedBy(tableName);
            saveTable(referredTable);
        }

        // delete table data from database
        deleteTable(tableName);

        return String.format("\'%s\' table is dropped", tableName);
    }

    public String showTables() throws DBException {
        StringBuilder builder = new StringBuilder();

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
        Table table = getTable(tableName);
        if (table == null) {
            throw new NoSuchTable();
        }

        StringBuilder builder = new StringBuilder();;
        builder.append("-----------------------------------------------------------------\n");
        builder.append(String.format("table_name [%s]\n", table.getName()));
        builder.append(String.format("%-28s %-14s %-14s %s\n", "column_name", "type", "null", "key"));
        for (Column column : table.getOrderedColumns()) {
            final DataType dataType = column.getDataType();
            String formattedType = dataType.baseType.name().toLowerCase();
            if (dataType.baseType == BaseType.CHAR) {
                formattedType += String.format("(%d)", dataType.charLength);
            }

            // format keys
            String formattedKey = "";
            boolean isPrimaryKey = column.getPrimaryKey();
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
                column.getNullable() ? "Y" : "N",
                formattedKey
            ));
        }
        builder.append("-----------------------------------------------------------------");
        return builder.toString();
    }

    public String insert(InsertQuery query) throws DBException {

        System.out.println(query.getTableName());
        for (String name : query.getColumnNames()) {
            System.out.println(name);
        }
        for (DataValue value : query.getColumnValues()) {
            System.out.println(value.serialize());
        }

        return "The row is inserted";
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

        // construct table model
        Table table = new Table(tableName);

        // read table reference data
        Cursor cursor = null;
        try {
            cursor = database.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry(Table.getKey(tableName).getBytes("UTF-8"));
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
            DatabaseEntry key = new DatabaseEntry(Column.getKey(tableName).getBytes("UTF-8"));
            DatabaseEntry value = new DatabaseEntry();

            if (cursor.getSearchKey(key, value, LockMode.DEFAULT) == OperationStatus.NOTFOUND) {
                return null;
            }

            do {
                Column column = Column.deserialize(new String(value.getData(), "UTF-8"));
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

    // save table to database without any check. if the table already exists, it is overwrited.
    private void saveTable(Table table) {
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
            for (Column column : table.columns.values()) {
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

    // delete all tableName related key-values. returns true if any key-value is deleted.
    private boolean deleteTable(String tableName) {
        boolean deleted = false;
        deleted |= deleteKeys(Table.getKey(tableName));
        deleted |= deleteKeys(Column.getKey(tableName));
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
}
