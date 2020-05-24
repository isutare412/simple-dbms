package kr.ac.snu.ids.PRJ1_3_2013_12295.query;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.snu.ids.PRJ1_3_2013_12295.expression.*;

public class SelectQuery {
    // select clause
    private boolean isAsterisk;
    private ArrayList<SelectedColumn> selectedColumns;

    // from clause
    private ArrayList<String> tableNames;
    private HashMap<String, String> alias;

    // where clause
    private Evaluator evaluator;

    public SelectQuery() {
        isAsterisk = false;
        selectedColumns = new ArrayList<>();
        tableNames = new ArrayList<>();
        alias = new HashMap<>();
    }

    public void setAsterisk() { isAsterisk = true; }
    public void addSelectedColumn(SelectedColumn column) { selectedColumns.add(column); }
    public void addFromTable(String tableName) { tableNames.add(tableName); }
    public void addAlias(String origin, String as) { alias.put(origin, as); }
    public void setEvaluator(Evaluator evaluator) { this.evaluator = evaluator; }

    public boolean isAsterisk() { return isAsterisk; }
    public ArrayList<SelectedColumn> getSelectedColumns() { return selectedColumns; }
    public ArrayList<String> getTableNames() { return tableNames; }
    public HashMap<String, String> getAlias() { return alias; }
    public Evaluator getEvalutor() { return evaluator; }
}