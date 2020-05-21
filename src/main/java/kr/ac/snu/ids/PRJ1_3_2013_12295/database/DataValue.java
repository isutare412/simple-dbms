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

    static Pattern datePattern = Pattern.compile("^(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)$");

    public DataValue() {
        isNull = true;
    }

    public DataValue(DataType type) {
        this.type = type;
        isNull = false;
    }

    public void parseData(String rawData) {
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

    public String toString() {
        String str = "";
        if (isNull) {
            str = "NULL";
        }

        switch (type.baseType) {
            case INT:
                str = String.valueOf(intValue);
                break;
            case CHAR:
                str = charValue;
                break;
            case DATE:
                str = dateValue.toString();
            default:
                break;
        }
        return str;
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
