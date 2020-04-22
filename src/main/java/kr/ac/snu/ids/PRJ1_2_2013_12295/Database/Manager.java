package kr.ac.snu.ids.PRJ1_2_2013_12295.Database;

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

public class Manager {
    public Environment environment;
    public Database database;

    Manager() {
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
}