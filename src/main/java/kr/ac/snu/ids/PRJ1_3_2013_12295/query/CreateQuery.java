package kr.ac.snu.ids.PRJ1_3_2013_12295.query;

import java.util.ArrayList;

import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;

public class CreateQuery {
    public class ReferenceConstraint {
        private ArrayList<String> referencings;
        private ArrayList<String> referenceds;
        private String targetTableName;

        public ReferenceConstraint() {
            referencings = new ArrayList<String>();
            referenceds = new ArrayList<String>();
        }

        public ArrayList<String> getReferencings() { return referencings; }
        public ArrayList<String> getReferenceds() { return referenceds; }
        public String getTargetTableName() { return targetTableName; }

        public void setReferencings(ArrayList<String> refers) { referencings = refers; }
        public void setReferenceds(ArrayList<String> referreds) { referenceds = referreds; }
        public void setTargetTableName(String name) { targetTableName = name; }
    }

    private String tableName;
    private ArrayList<Column> columns;
    private ArrayList<String> primaryKeyColumns;
    private int primaryKeyAddCount;
    private ArrayList<ReferenceConstraint> references;

    public CreateQuery(String tableName) {
        this.tableName = tableName;
        columns = new ArrayList<Column>();
        primaryKeyColumns = new ArrayList<String>();
        primaryKeyAddCount = 0;
        references = new ArrayList<ReferenceConstraint>();
    }

    public String getTableName() { return tableName; }
    public ArrayList<Column> getColumns() { return columns; }
    public ArrayList<String> getPrimaryKeyColumns() { return primaryKeyColumns; }
    public int getPrimaryKeyAddCount() { return primaryKeyAddCount; }
    public ArrayList<ReferenceConstraint> getReferences() { return references; }

    public void addColumn(Column column) {
        column.setOrder(columns.size());
        columns.add(column);
    }
    public void addPrimaryKeyColumn(String columnName) {
        primaryKeyColumns.add(columnName);
        primaryKeyAddCount++;
    }
    public void addReference(ReferenceConstraint reference) { references.add(reference); }
}
