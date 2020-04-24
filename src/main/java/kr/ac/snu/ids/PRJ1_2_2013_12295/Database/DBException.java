package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

@SuppressWarnings("serial")
public class DBException extends Exception {
    public DBException(String msg) {
        super(msg);
    }
}

////////////////////////////////////////////////////////////////
// Create table exceptions
////////////////////////////////////////////////////////////////

@SuppressWarnings("serial")
class DuplicateColumnDefError extends DBException {
    public DuplicateColumnDefError() {
        super("Create table has failed: column definition is duplicated");
    }
}

@SuppressWarnings("serial")
class DuplicatePrimaryKeyDefError extends DBException {
    public DuplicatePrimaryKeyDefError() {
        super("Create table has failed: primary key definition is duplicated");
    }
}

@SuppressWarnings("serial")
class TableExistenceError extends DBException {
    public TableExistenceError() {
        super("Create table has failed: table with the same name already exists");
    }
}

@SuppressWarnings("serial")
class ReferenceTypeError extends DBException {
    public ReferenceTypeError() {
        super("Create table has failed: foreign key references wrong type");
    }
}

@SuppressWarnings("serial")
class ReferenceTableExistenceError extends DBException {
    public ReferenceTableExistenceError() {
        super("Create table has failed: foreign key references non existing table");
    }
}

@SuppressWarnings("serial")
class ReferenceColumnExistenceError extends DBException {
    public ReferenceColumnExistenceError() {
        super("Create table has failed: foreign key references non existing column");
    }
}

@SuppressWarnings("serial")
class ReferenceNonPrimaryKeyError extends DBException {
    public ReferenceNonPrimaryKeyError() {
        super("Create table has failed: foreign key references non primary key column");
    }
}

@SuppressWarnings("serial")
class NonExistingColumnDefError extends DBException {
    public NonExistingColumnDefError(String columnName) {
        super("Create table has failed: \'" + columnName + "\' does not exists in column definition");
    }
}

@SuppressWarnings("serial")
class NonExistingPrimaryKeyError extends DBException {
    public NonExistingPrimaryKeyError() {
        super("Create table has failed: primary key does not exists in table definition");
    }
}

@SuppressWarnings("serial")
class CharLengthError extends DBException {
    public CharLengthError() {
        super("Char length should be over 0");
    }
}
