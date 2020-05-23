package kr.ac.snu.ids.PRJ1_3_2013_12295.expression;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;

public class BooleanTerm implements Evaluator {
    private ArrayList<Evaluator> evaluators;

    public BooleanTerm(ArrayList<Evaluator> evaluators) {
        this.evaluators = evaluators;
    }

    public LogicValue evaluate(
            InstanceBuffer buffer, ArrayList<TableRow> row) throws DBException {
        LogicValue curValue = evaluators.get(0).evaluate(buffer, row);
        if (curValue == LogicValue.FALSE) {
            return curValue;
        }

        for (int i = 1; i < evaluators.size(); i++) {
            LogicValue nextValue = evaluators.get(i).evaluate(buffer, row);
            curValue = curValue.and(nextValue);
            if (curValue == LogicValue.FALSE) {
                return curValue;
            }
        }
        return curValue;
    }
}
