package kr.ac.snu.ids.PRJ1_3_2013_12295.expression;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class ComparisonPredicate implements Evaluator {
    private enum Operator {
        LESS,
        LESS_EQUAL,
        GREATER,
        GREATER_EQUAL,
        EQUAL,
        NOT_EQUAL,
    }

    private CompOperand left, right;
    private Operator operator;

    public ComparisonPredicate(CompOperand left, CompOperand right, String op) {
        this.left = left;
        this.right = right;

        switch (op) {
            case "<":   operator = Operator.LESS; break;
            case "<=":  operator = Operator.LESS_EQUAL; break;
            case ">":   operator = Operator.GREATER; break;
            case ">=":  operator = Operator.GREATER_EQUAL; break;
            case "=":   operator = Operator.EQUAL; break;
            case "!=":  operator = Operator.NOT_EQUAL; break;
        }
    }

    public LogicValue evaluate(
            InstanceBuffer buffer, ArrayList<TableRow> row) throws DBException {
        DataValue lValue = left.evaluate(buffer, row);
        DataValue rValue = right.evaluate(buffer, row);
        if (lValue.isNull() || rValue.isNull()) {
            return LogicValue.UNKNOWN;
        }

        if (lValue.getDataType().baseType != rValue.getDataType().baseType) {
            throw new WhereIncomparableError();
        }

        int compareResult = lValue.compareTo(rValue);
        switch (operator) {
            case LESS:          return compareResult < 0 ? LogicValue.TRUE : LogicValue.FALSE;
            case LESS_EQUAL:    return compareResult <= 0 ? LogicValue.TRUE : LogicValue.FALSE;
            case GREATER:       return compareResult > 0 ? LogicValue.TRUE : LogicValue.FALSE;
            case GREATER_EQUAL: return compareResult >= 0 ? LogicValue.TRUE : LogicValue.FALSE;
            case EQUAL:         return compareResult == 0 ? LogicValue.TRUE : LogicValue.FALSE;
            case NOT_EQUAL:     return compareResult != 0 ? LogicValue.TRUE : LogicValue.FALSE;
        }
        new Exception().printStackTrace();
        return LogicValue.FALSE;
    }
}