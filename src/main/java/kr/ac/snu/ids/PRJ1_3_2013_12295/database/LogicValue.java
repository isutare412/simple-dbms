package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

public enum LogicValue {
    TRUE,
    FALSE,
    UNKNOWN;

    public LogicValue not() {
        switch (this) {
            case UNKNOWN:
                return UNKNOWN;
            case TRUE:
                return FALSE;
            case FALSE:
                return TRUE;
        }
        new Exception().printStackTrace();;
        return FALSE;
    }

    public LogicValue and(LogicValue other) {
        if (this == FALSE || other == FALSE) {
            return FALSE;
        } else if (this == UNKNOWN || other == UNKNOWN) {
            return UNKNOWN;
        }
        return TRUE;
    }

    public LogicValue or(LogicValue other) {
        if (this == TRUE || other == TRUE) {
            return TRUE;
        } else if (this == UNKNOWN || other == UNKNOWN) {
            return UNKNOWN;
        }
        return FALSE;
    }
}
