package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.expression.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.query.SelectedColumn;

public class InstanceBuffer {
    private ArrayList<TableInstance> instances;
    private HashMap<String, TableInstance> instanceLookup;
    private HashMap<String, String> alias;
    private LinkedList<ArrayList<TableRow>> records;

    public InstanceBuffer(ArrayList<TableInstance> instances) {
        this.instances = instances;
        instanceLookup = new HashMap<>();
        for (TableInstance instance : instances) {
            instanceLookup.put(instance.getTableName(), instance);
        }
        alias = new HashMap<>();

        // build cartesian product
        records = new LinkedList<>();
        int expectedSize = 0;
        for (TableRow row : instances.get(0).getRows()) {
            ArrayList<TableRow> singleRow = new ArrayList<>();
            singleRow.add(row);
            records.add(singleRow);
            expectedSize++;
        }
        for (int i = 1; i < instances.size(); i++) {
            ArrayList<TableRow> rows = instances.get(i).getRows();
            records = cartesian(records, rows);
            expectedSize *= rows.size();
        }

        if (records.size() != expectedSize) {
            System.err.println("Cartesian product size is not correct");
            System.exit(1);
        }
    }

    private TableInstance getTableInstance(String name) {
        String tableName = alias.get(name);
        if (tableName != null)
            return instanceLookup.get(tableName);
        else
            return instanceLookup.get(name);
    }

    private ColumnSchema getColumnSchema(String columnName) throws DBException {
        ColumnSchema resultSchema = null;
        int columnCount = 0;
        for (TableInstance instance : instances) {
            ColumnSchema columnSchema = instance.getColumnSchema(columnName);
            if (columnSchema != null) {
                resultSchema = columnSchema;
                columnCount++;
            }
        }

        if (columnCount == 0) {
            throw new WhereColumnNotExist();
        } else if (columnCount > 1) {
            throw new WhereAmbiguousReference();
        }
        return resultSchema;
    }

    public ColumnSchema getColumnSchema(String tableName, String columnName) throws DBException {
        if (tableName == null) {
            return getColumnSchema(columnName);
        }

        String realName = solveAlias(tableName);
        if (realName == null) {
            throw new WhereTableNotSpecified();
        }

        TableInstance instance = instanceLookup.get(realName);
        if (instance == null) {
            throw new WhereTableNotSpecified();
        }

        ColumnSchema columnSchema = instance.getColumnSchema(columnName);
        if (columnSchema == null) {
            throw new WhereColumnNotExist();
        }
        return columnSchema;
    }

    public LinkedList<ArrayList<TableRow>> getRecords() {
        return records;
    }

    public ArrayList<String> getTableHeader() {
        // find ColumnSchemas whose names are duplicated
        HashSet<String> needAlias = new HashSet<>();
        HashMap<String, Integer> columnWordCount = new HashMap<>();
        for (TableInstance instance : instances) {
            for (ColumnSchema columnSchema : instance.getTableSchema().getOrderedColumns()) {
                int count = columnWordCount.getOrDefault(columnSchema.getName(), 0);
                count++;
                columnWordCount.put(columnSchema.getName(), count);
                if (count > 1) {
                    needAlias.add(columnSchema.getName());
                }
            }
        }

        ArrayList<String> tableHeader = new ArrayList<>();
        for (TableInstance instance : instances) {
            for (ColumnSchema columnSchema : instance.getTableSchema().getOrderedColumns()) {
                String colString = "";
                if (needAlias.contains(columnSchema.getName())) {
                    String tableName = columnSchema.getTableName();
                    for (Entry<String, String> entry : alias.entrySet()) {
                        if (entry.getValue().equals(columnSchema.getTableName())) {
                            tableName = entry.getKey();
                            break;
                        }
                    }
                    colString += String.format("%s.", solveAlias(tableName));
                }
                colString += columnSchema.getName();
                tableHeader.add(colString.toUpperCase());
            }
        }
        return tableHeader;
    }

    public ArrayList<String> getTableHeader(
            ArrayList<SelectedColumn> selectedColumns) throws DBException {
        // find ColumnSchemas whose names are duplicated
        HashSet<String> needAlias = new HashSet<>();
        HashMap<String, Integer> columnWordCount = new HashMap<>();
        for (SelectedColumn selectedColumn : selectedColumns) {
            String tableName = selectedColumn.tableName;
            String columnName = selectedColumn.columnName;

            // check column existence
            ColumnSchema columnSchema = null;
            try {
                columnSchema = getColumnSchema(tableName, columnName);
            } catch (DBException e) {
                throw new SelectColumnResolveError(columnName);
            }

            int count = columnWordCount.getOrDefault(columnSchema.getName(), 0);
            count++;
            columnWordCount.put(columnSchema.getName(), count);
            if (count > 1) {
                needAlias.add(columnSchema.getName());
            }
        }

        ArrayList<String> tableHeader = new ArrayList<>();
        for (SelectedColumn selectedColumn : selectedColumns) {
            String tableName = selectedColumn.tableName;
            String columnName = selectedColumn.columnName;
            String aliasName = selectedColumn.alias;

            if (aliasName != null) {
                tableHeader.add(aliasName);
            } else {
                String newName = "";
                if (needAlias.contains(columnName)) {
                    newName += String.format("%s.", solveAlias(tableName));
                }
                newName += columnName;
                tableHeader.add(newName.toUpperCase());
            }
        }

        return tableHeader;
    }

    public ArrayList<ArrayList<String>> getTableBody() {
        ArrayList<ArrayList<String>> tableBody = new ArrayList<>();
        for (ArrayList<TableRow> record : records) {
            ArrayList<String> recordLine = new ArrayList<>();
            for (TableRow row : record) {
                for (DataValue value : row.getDataValues()) {
                    recordLine.add(value.serializeWithoutQuote());
                }
            }
            tableBody.add(recordLine);
        }
        return tableBody;
    }

    public ArrayList<ArrayList<String>> getTableBody(
            ArrayList<SelectedColumn> selectedColumns) {
        HashSet<ColumnSchema> toSelect = new HashSet<>();
        try {
            for (SelectedColumn selectedColumn : selectedColumns) {
                ColumnSchema columnSchema =
                    getColumnSchema(selectedColumn.tableName, selectedColumn.columnName);
                toSelect.add(columnSchema);
            }
        } catch (DBException e) {
            return null;
        }

        ArrayList<ArrayList<String>> tableBody = new ArrayList<>();
        for (ArrayList<TableRow> record : records) {
            ArrayList<String> recordLine = new ArrayList<>();
            for (TableRow row : record) {
                for (ColumnSchema columnSchema : row.getTableSchema().getOrderedColumns()) {
                    if (toSelect.contains(columnSchema)) {
                        recordLine.add(row.getDataValue(columnSchema).serializeWithoutQuote());
                    }
                }
            }
            tableBody.add(recordLine);
        }
        return tableBody;
    }

    public void registerAlias(String origin, String alias) {
        if (!instanceLookup.containsKey(origin)) {
            return;
        }
        this.alias.put(alias, origin);
    }

    public String solveAlias(String tableName) {
        if (instanceLookup.containsKey(tableName)) {
            return tableName;
        }
        return alias.get(tableName);
    }

    public void filter(Evaluator eval) throws DBException {
        if (eval == null) {
            return;
        }

        Iterator<ArrayList<TableRow>> iter = records.iterator();
        while (iter.hasNext()) {
            ArrayList<TableRow> rows = iter.next();
            LogicValue result = eval.evaluate(this, rows);
            if (result != LogicValue.TRUE) {
                iter.remove();
            }
        }
    }

    public int columnsWithName(String columnName) {
        int count = 0;
        for (TableInstance instance : instanceLookup.values()) {
            if (instance.getColumnSchema(columnName) != null) {
                count++;
            }
        }
        return count;
    }

    private int indexOf(TableInstance instance) {
        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i) == instance) {
                return i;
            }
        }
        return -1;
    }

    private static LinkedList<ArrayList<TableRow>>
    cartesian(LinkedList<ArrayList<TableRow>> lhs, ArrayList<TableRow> rhs) {
        LinkedList<ArrayList<TableRow>> result = new LinkedList<>();
        for (ArrayList<TableRow> iValue : lhs) {
            for (int j= 0; j < rhs.size(); j++) {
                ArrayList<TableRow> temp = new ArrayList<>(iValue);
                temp.add(rhs.get(j));
                result.add(temp);
            }
        }
        return result;
    }
}