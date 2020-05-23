package kr.ac.snu.ids.PRJ1_3_2013_12295.query;

import kr.ac.snu.ids.PRJ1_3_2013_12295.expression.*;

public class DeleteQuery {
    private String tableName;
    private Evaluator evalutator;

    public DeleteQuery(String tableName, Evaluator evaluator) {
        this.tableName = tableName;
        this.evalutator = evaluator;
    }

    public String getTableName() { return tableName; }
    public Evaluator getEvalutor() { return evalutator; }
}