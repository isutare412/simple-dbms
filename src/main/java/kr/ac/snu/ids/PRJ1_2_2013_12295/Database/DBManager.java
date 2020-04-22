package database;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class DBManager {
    private Environment environment;
    private Database database;

    public DBManager() {
        // instantiate EnvironmentConfig
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);

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

    public void close() {
        if (database != null) {
            database.close();
        };
        if (environment != null) {
            environment.close();
        }
    }
}
