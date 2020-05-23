package kr.ac.snu.ids.PRJ1_3_2013_12295.expression;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class BooleanFactor implements Evaluator {
    private Evaluator evaluator;
    private boolean isNot;

    public BooleanFactor(Evaluator evaluator, boolean isNot) {
        this.evaluator = evaluator;
        this.isNot = isNot;
    }

    public LogicValue evaluate(
            InstanceBuffer buffer, ArrayList<TableRow> row) throws DBException {
        LogicValue result = evaluator.evaluate(buffer, row);
        return isNot ? result.not() : result;
    }
}