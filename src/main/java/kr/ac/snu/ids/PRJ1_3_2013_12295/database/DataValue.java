package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.lang.StringBuilder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValue {
    private DataType type;
    private int intValue;
    private String charValue;
    private DateValue dateValue;
    private boolean isNull;

    public static final char charWrapper = '\'';
    public static final String nullToken = "NULL";
    private static final Pattern datePattern = Pattern.compile("^(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)$");

    public DataValue() {
        isNull = true;
    }

    public DataValue(DataType type) {
        this.type = type;
        isNull = false;
    }

    public LogicValue isEqual(DataValue other) {
        if (this.isNull || other.isNull) {
            return LogicValue.UNKNOWN;
        }

        if (!type.equals(other.type)) {
            return LogicValue.FALSE;
        }

        switch (type.baseType) {
            case INT:
                return intValue == other.intValue ? LogicValue.TRUE : LogicValue.FALSE;
            case CHAR:
                return charValue.equals(other.charValue) ? LogicValue.TRUE : LogicValue.FALSE;
            case DATE:
                return dateValue.equals(other.dateValue) ? LogicValue.TRUE : LogicValue.FALSE;
            default:
                return LogicValue.FALSE;
        }
    }

    public int compareTo(DataValue other) {
        switch (type.baseType) {
            case INT:
                return intValue - other.intValue;
            case CHAR:
                return charValue.compareTo(other.charValue);
            case DATE:
                return dateValue.compareTo(other.dateValue);
            default:
                new Exception().printStackTrace();
                return 0;
        }
    }

    public DataType getDataType() { return type; }
    public boolean isNull() { return isNull; }

    public void setCharLength(int length) {
        type.charLength = length;
        if (charValue.length() > length) {
            charValue = charValue.substring(0, length);
        }
    }

    public String serialize() {
        String str = "";
        if (isNull) {
            return DataValue.nullToken;
        }

        switch (type.baseType) {
            case INT:
                str = String.valueOf(intValue);
                break;
            case CHAR:
                str = String.format("%c%s%c",
                    DataValue.charWrapper, charValue, DataValue.charWrapper);
                break;
            case DATE:
                str = dateValue.toString();
                break;
            default:
                break;
        }
        return str;
    }

    public void deserialize(String rawData) {
        if (rawData.equals(DataValue.nullToken)) {
            isNull = true;
            return;
        }
        isNull = false;

        try {
            switch (type.baseType) {
                case INT:
                    intValue = Integer.parseInt(rawData);
                    break;
                case CHAR:
                    charValue = rawData.substring(1, rawData.length()-1);
                    break;
                case DATE:
                    Matcher matcher = DataValue.datePattern.matcher(rawData);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        int month = Integer.parseInt(matcher.group(2));
                        int day = Integer.parseInt(matcher.group(3));
                        dateValue = new DateValue(year, month, day);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}

class DateValue {
    int year;
    int month;
    int day;

    public DateValue(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final DateValue other = (DateValue)obj;
        return year == other.year && month == other.month && day == other.day;
    }

    public int compareTo(DateValue other) {
        int yearResult = year - other.year;
        if (yearResult != 0) {
            return yearResult;
        }
        int monthResult = month - other.month;
        if (monthResult != 0) {
            return monthResult;
        }
        return day - other.day;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(year));
        builder.append("-");
        builder.append(String.valueOf(month));
        builder.append("-");
        builder.append(String.valueOf(day));
        return builder.toString();
    }
}
