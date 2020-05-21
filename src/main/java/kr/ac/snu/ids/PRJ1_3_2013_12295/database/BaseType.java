package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

public enum BaseType {
    INT,
    CHAR,
    DATE;

    public static BaseType getEnum(String value) {
        for (BaseType type : values())
            if (type.name().equalsIgnoreCase(value)) return type;
        throw new IllegalArgumentException();
    }
}