package kr.ac.snu.ids.PRJ1_2_2013_12295.database;

public enum DataType {
    INT,
    CHAR,
    DATE;

    public static DataType getEnum(String value) {
        for (DataType type : values())
            if (type.name().equalsIgnoreCase(value)) return type;
        throw new IllegalArgumentException();
    }
}