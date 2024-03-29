package kr.ac.snu.ids.PRJ1_3_2013_12295.database;

public class DataType {
    public BaseType baseType;
    public int charLength;

    public DataType() {
        baseType = BaseType.INT;
        charLength = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final DataType other = (DataType)obj;
        if (baseType != other.baseType) {
            return false;
        }
        if (baseType == BaseType.CHAR && charLength != other.charLength) {
            return false;
        }
        return true;
    }
}