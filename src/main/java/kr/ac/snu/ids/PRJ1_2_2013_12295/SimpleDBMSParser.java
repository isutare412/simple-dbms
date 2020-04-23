/* SimpleDBMSParser.java */
/* Generated By:JavaCC: Do not edit this line. SimpleDBMSParser.java */
package kr.ac.snu.ids.PRJ1_2_2013_12295;
import java.io.UnsupportedEncodingException;
import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

import kr.ac.snu.ids.PRJ1_2_2013_12295.database.*;

public class SimpleDBMSParser implements SimpleDBMSParserConstants {
  //constants to distinguish query and print outputs.
  public static final int PRINT_SYNTAX_ERROR = 0;
  public static final int PRINT_CREATE_TABLE = 1;
  public static final int PRINT_DROP_TABLE = 2;
  public static final int PRINT_DESC = 3;
  public static final int PRINT_INSERT = 4;
  public static final int PRINT_DELETE = 5;
  public static final int PRINT_SELECT = 6;
  public static final int PRINT_SHOW_TABLES = 7;

  private static String PROMPT = "DB_2013-12295> ";

  public static DBManager manager;

  public static void main(String args[]) throws ParseException
  {
    manager = new DBManager();

    SimpleDBMSParser parser = new SimpleDBMSParser(System.in);
    System.out.print(PROMPT);

    while (true)
    {
      try
      {
        parser.command();
      }
      catch (DBException dbe)
      {
        System.out.println(dbe.getMessage());
        SimpleDBMSParser.ReInit(System.in);
        System.out.print(PROMPT);
      }
      catch (ParseException e)
      {
        printMessage(PRINT_SYNTAX_ERROR);
        SimpleDBMSParser.ReInit(System.in);
        System.out.print(PROMPT);
      }
      catch (Exception e)
      {
        System.out.println(e.getMessage());
        SimpleDBMSParser.ReInit(System.in);
        System.out.print(PROMPT);
      }
    }

  }

  public static void printMessage(int q)
  {
    //q is given by query() below
    switch(q)
    {
      case PRINT_SYNTAX_ERROR:
        System.out.println("Syntax error");
        break;
      case PRINT_CREATE_TABLE:
        System.out.println("\'CREATE TABLE\' requested");
        break;
      case PRINT_DROP_TABLE:
        System.out.println("\'DROP TABLE\' requested");
        break;
      case PRINT_DESC:
        System.out.println("\'DESC\' requested");
        break;
      case PRINT_INSERT:
        System.out.println("\'INSERT\' requested");
        break;
      case PRINT_DELETE:
        System.out.println("\'DELETE\' requested");
        break;
      case PRINT_SELECT:
        System.out.println("\'SELECT\' requested");
        break;
      case PRINT_SHOW_TABLES:
        System.out.println("\'SHOW TABLES\' requested");
        break;
    }
  }

  static final public void command() throws ParseException, DBException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CREATE:
    case DROP:
    case DESC:
    case INSERT:
    case DELETE:
    case SELECT:
    case SHOW:{
      queryList();
      break;
      }
    case EXIT:{
      jj_consume_token(EXIT);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case SEMICOLON:{
        jj_consume_token(SEMICOLON);
        break;
        }
      case END:{
        jj_consume_token(END);
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
manager.close();
      System.exit(0);
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void queryList() throws ParseException, DBException {int q;
    label_1:
    while (true) {
      q = query();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case END:{
        jj_consume_token(END);
printMessage(q);
          //prompt when new query starts
          System.out.print(PROMPT);
        break;
        }
      case SEMICOLON:{
        jj_consume_token(SEMICOLON);
printMessage(q);
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case CREATE:
      case DROP:
      case DESC:
      case INSERT:
      case DELETE:
      case SELECT:
      case SHOW:{
        ;
        break;
        }
      default:
        jj_la1[3] = jj_gen;
        break label_1;
      }
    }
}

  static final public int query() throws ParseException, DBException {int q;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CREATE:{
      createTableQuery();
q = PRINT_CREATE_TABLE;
      break;
      }
    case DROP:{
      dropTableQuery();
q = PRINT_DROP_TABLE;
      break;
      }
    case DESC:{
      descQuery();
q = PRINT_DESC;
      break;
      }
    case INSERT:{
      insertQuery();
q = PRINT_INSERT;
      break;
      }
    case DELETE:{
      deleteQuery();
q = PRINT_DELETE;
      break;
      }
    case SELECT:{
      selectQuery();
q = PRINT_SELECT;
      break;
      }
    case SHOW:{
      showTablesQuery();
q = PRINT_SHOW_TABLES;
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
{if ("" != null) return q;}
    throw new Error("Missing return statement in function");
}

//below regex for queries are similar to the ones in grammar file. 

/// 0. easy ones
  static final public void dropTableQuery() throws ParseException {
    jj_consume_token(DROP);
    jj_consume_token(TABLE);
    tableName();
}

  static final public void descQuery() throws ParseException {
    jj_consume_token(DESC);
    tableName();
}

  static final public void showTablesQuery() throws ParseException {
    jj_consume_token(SHOW);
    jj_consume_token(TABLES);
}

/// 0. easy ones 

/// 1. for createTableQuery()
  static final public 
void createTableQuery() throws ParseException, DBException {Table table;
  String tableName;
    jj_consume_token(CREATE);
    jj_consume_token(TABLE);
    tableName = tableName();
table = new Table(tableName);
    tableElementList(table);
}

  static final public void tableElementList(Table table) throws ParseException, DBException {
    jj_consume_token(LEFT_PAREN);
    tableElement(table);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[5] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      tableElement(table);
    }
    jj_consume_token(RIGHT_PAREN);
}

  static final public void tableElement(Table table) throws ParseException, DBException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEGAL_IDENTIFIER:{
      columnDefinition(table);
      break;
      }
    case PRIMARY:
    case FOREIGN:{
      tableConstraintDefinition(table);
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void columnDefinition(Table table) throws ParseException, DBException {Column column;
  String name;
    name = columnName();
column = new Column(name);
    dataType(column);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      jj_consume_token(NULL);
column.setNullable(false);
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      ;
    }
table.addColumn(column);
}

  static final public void tableConstraintDefinition(Table table) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PRIMARY:{
      primaryKeyConstraint();
      break;
      }
    case FOREIGN:{
      referentialConstraint();
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void primaryKeyConstraint() throws ParseException {
    jj_consume_token(PRIMARY);
    jj_consume_token(KEY);
    columnNameList();
}

  static final public void referentialConstraint() throws ParseException {
    jj_consume_token(FOREIGN);
    jj_consume_token(KEY);
    columnNameList();
    jj_consume_token(REFERENCES);
    tableName();
    columnNameList();
}

  static final public void columnNameList() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    columnName();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      columnName();
    }
    jj_consume_token(RIGHT_PAREN);
}

  static final public void dataType(Column column) throws ParseException {Token charLength;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
column.setDataType(DataType.INT);
      break;
      }
    case CHAR:{
      jj_consume_token(CHAR);
      jj_consume_token(LEFT_PAREN);
      charLength = jj_consume_token(INT_VALUE);
      jj_consume_token(RIGHT_PAREN);
column.setDataType(DataType.CHAR);
      column.setCharLength(Integer.parseInt(charLength.toString()));
      break;
      }
    case DATE:{
      jj_consume_token(DATE);
column.setDataType(DataType.DATE);
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public String tableName() throws ParseException {Token name;
    name = jj_consume_token(LEGAL_IDENTIFIER);
{if ("" != null) return name.toString().toLowerCase();}
    throw new Error("Missing return statement in function");
}

  static final public String columnName() throws ParseException {Token name;
    name = jj_consume_token(LEGAL_IDENTIFIER);
{if ("" != null) return name.toString().toLowerCase();}
    throw new Error("Missing return statement in function");
}

/// 1. for createTableQuery


/// 2. for selectQuery
  static final public 
void selectQuery() throws ParseException {
    jj_consume_token(SELECT);
    selectList();
    tableExpression();
}

  static final public void selectList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ASTERISK:{
      jj_consume_token(ASTERISK);
      break;
      }
    case LEGAL_IDENTIFIER:{
      selectedColumn();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[11] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        selectedColumn();
      }
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void selectedColumn() throws ParseException {
    if (jj_2_1(2)) {
      tableName();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    columnName();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AS:{
      jj_consume_token(AS);
      columnName();
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      ;
    }
}

  static final public void tableExpression() throws ParseException {
    fromClause();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WHERE:{
      whereClause();
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      ;
    }
}

  static final public void fromClause() throws ParseException {
    jj_consume_token(FROM);
    tableReferenceList();
}

  static final public void tableReferenceList() throws ParseException {
    referedTable();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        break label_5;
      }
      jj_consume_token(COMMA);
      referedTable();
    }
}

  static final public void referedTable() throws ParseException {
    tableName();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AS:{
      jj_consume_token(AS);
      tableName();
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      ;
    }
}

  static final public void whereClause() throws ParseException {
    jj_consume_token(WHERE);
    booleanValueExpression();
}

  static final public void booleanValueExpression() throws ParseException {
    booleanTerm();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case OR:{
        ;
        break;
        }
      default:
        jj_la1[17] = jj_gen;
        break label_6;
      }
      jj_consume_token(OR);
      booleanTerm();
    }
}

  static final public void booleanTerm() throws ParseException {
    booleanFactor();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case AND:{
        ;
        break;
        }
      default:
        jj_la1[18] = jj_gen;
        break label_7;
      }
      jj_consume_token(AND);
      booleanFactor();
    }
}

  static final public void booleanFactor() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      ;
    }
    booleanTest();
}

  static final public void booleanTest() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT_VALUE:
    case LEGAL_IDENTIFIER:
    case DATE_VALUE:
    case CHAR_STRING:{
      predicate();
      break;
      }
    case LEFT_PAREN:{
      parenthesizedBooleanExpression();
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void parenthesizedBooleanExpression() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    booleanValueExpression();
    jj_consume_token(RIGHT_PAREN);
}

  static final public void predicate() throws ParseException {
    if (jj_2_2(4)) {
      comparisonPredicate();
    } else {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case LEGAL_IDENTIFIER:{
        nullPredicate();
        break;
        }
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
}

  static final public void comparisonPredicate() throws ParseException {
    compOperand();
    jj_consume_token(COMP_OP);
    compOperand();
}

  static final public void compOperand() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT_VALUE:
    case DATE_VALUE:
    case CHAR_STRING:{
      comparableValue();
      break;
      }
    case LEGAL_IDENTIFIER:{
      if (jj_2_3(2)) {
        tableName();
        jj_consume_token(PERIOD);
      } else {
        ;
      }
      columnName();
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void comparableValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT_VALUE:{
      jj_consume_token(INT_VALUE);
      break;
      }
    case CHAR_STRING:{
      jj_consume_token(CHAR_STRING);
      break;
      }
    case DATE_VALUE:{
      jj_consume_token(DATE_VALUE);
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static final public void nullPredicate() throws ParseException {
    if (jj_2_4(2)) {
      tableName();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    columnName();
    nullOperation();
}

  static final public void nullOperation() throws ParseException {
    jj_consume_token(IS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NOT:{
      jj_consume_token(NOT);
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      ;
    }
    jj_consume_token(NULL);
}

/// 2. for selectQuery


/// 3. for insert,delete
  static final public 
void insertQuery() throws ParseException {
    jj_consume_token(INSERT);
    jj_consume_token(INTO);
    tableName();
    insertColumnsAndSource();
}

  static final public void deleteQuery() throws ParseException {
    jj_consume_token(DELETE);
    jj_consume_token(FROM);
    tableName();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WHERE:{
      whereClause();
      break;
      }
    default:
      jj_la1[25] = jj_gen;
      ;
    }
}

  static final public void insertColumnsAndSource() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEFT_PAREN:{
      columnNameList();
      break;
      }
    default:
      jj_la1[26] = jj_gen;
      ;
    }
    valueList();
}

  static final public void valueList() throws ParseException {
    jj_consume_token(VALUES);
    jj_consume_token(LEFT_PAREN);
    value();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        break label_8;
      }
      jj_consume_token(COMMA);
      value();
    }
    jj_consume_token(RIGHT_PAREN);
}

  static final public void value() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NULL:{
      jj_consume_token(NULL);
      break;
      }
    case INT_VALUE:
    case DATE_VALUE:
    case CHAR_STRING:{
      comparableValue();
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  static private boolean jj_2_1(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_1()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_2()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_3()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla)
 {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return (!jj_3_4()); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_3R_9()
 {
    if (jj_scan_token(LEGAL_IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3R_14()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_scan_token(39)) return false;
    jj_scanpos = xsp;
    if (!jj_scan_token(42)) return false;
    jj_scanpos = xsp;
    if (jj_scan_token(41)) return true;
    return false;
  }

  static private boolean jj_3R_10()
 {
    if (jj_3R_11()) return true;
    if (jj_scan_token(COMP_OP)) return true;
    if (jj_3R_11()) return true;
    return false;
  }

  static private boolean jj_3_3()
 {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_3_2()
 {
    if (jj_3R_10()) return true;
    return false;
  }

  static private boolean jj_3R_15()
 {
    if (jj_scan_token(LEGAL_IDENTIFIER)) return true;
    return false;
  }

  static private boolean jj_3_4()
 {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_3R_13()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) jj_scanpos = xsp;
    if (jj_3R_15()) return true;
    return false;
  }

  static private boolean jj_3R_11()
 {
    Token xsp;
    xsp = jj_scanpos;
    if (!jj_3R_12()) return false;
    jj_scanpos = xsp;
    if (jj_3R_13()) return true;
    return false;
  }

  static private boolean jj_3R_12()
 {
    if (jj_3R_14()) return true;
    return false;
  }

  static private boolean jj_3_1()
 {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public SimpleDBMSParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[29];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x0,0x1dd10,0x0,0x1dd00,0x1dd00,0x0,0x300000,0x2000000,0x300000,0x0,0xe0,0x0,0x80000000,0x8000000,0x80000,0x0,0x8000000,0x4000000,0x20000000,0x2000000,0x0,0x0,0x0,0x0,0x2000000,0x80000,0x0,0x0,0x1000000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x3,0x0,0x3,0x0,0x0,0x10,0x100,0x0,0x0,0x10,0x0,0x10,0x100,0x0,0x0,0x10,0x0,0x0,0x0,0x0,0x784,0x100,0x780,0x680,0x0,0x0,0x4,0x10,0x680,};
	}
  static final private JJCalls[] jj_2_rtns = new JJCalls[4];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SimpleDBMSParser(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SimpleDBMSParser(java.io.InputStream stream, String encoding) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser.  ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new SimpleDBMSParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SimpleDBMSParser(java.io.Reader stream) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new SimpleDBMSParserTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new SimpleDBMSParserTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SimpleDBMSParser(SimpleDBMSParserTokenManager tm) {
	 if (jj_initialized_once) {
	   System.out.println("ERROR: Second call to constructor of static parser. ");
	   System.out.println("	   You must either use ReInit() or set the JavaCC option STATIC to false");
	   System.out.println("	   during parser generation.");
	   throw new Error();
	 }
	 jj_initialized_once = true;
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SimpleDBMSParserTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
	 for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   if (++jj_gc > 100) {
		 jj_gc = 0;
		 for (int i = 0; i < jj_2_rtns.length; i++) {
		   JJCalls c = jj_2_rtns[i];
		   while (c != null) {
			 if (c.gen < jj_gen) c.first = null;
			 c = c.next;
		   }
		 }
	   }
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }

  @SuppressWarnings("serial")
  static private final class LookaheadSuccess extends java.lang.Error {
    @Override
    public Throwable fillInStackTrace() {
      return this;
    }
  }
  static private final LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
	 if (jj_scanpos == jj_lastpos) {
	   jj_la--;
	   if (jj_scanpos.next == null) {
		 jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
	   } else {
		 jj_lastpos = jj_scanpos = jj_scanpos.next;
	   }
	 } else {
	   jj_scanpos = jj_scanpos.next;
	 }
	 if (jj_rescan) {
	   int i = 0; Token tok = token;
	   while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
	   if (tok != null) jj_add_error_token(kind, i);
	 }
	 if (jj_scanpos.kind != kind) return true;
	 if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
	 return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  static private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
	 if (pos >= 100) {
		return;
	 }

	 if (pos == jj_endpos + 1) {
	   jj_lasttokens[jj_endpos++] = kind;
	 } else if (jj_endpos != 0) {
	   jj_expentry = new int[jj_endpos];

	   for (int i = 0; i < jj_endpos; i++) {
		 jj_expentry[i] = jj_lasttokens[i];
	   }

	   for (int[] oldentry : jj_expentries) {
		 if (oldentry.length == jj_expentry.length) {
		   boolean isMatched = true;

		   for (int i = 0; i < jj_expentry.length; i++) {
			 if (oldentry[i] != jj_expentry[i]) {
			   isMatched = false;
			   break;
			 }

		   }
		   if (isMatched) {
			 jj_expentries.add(jj_expentry);
			 break;
		   }
		 }
	   }

	   if (pos != 0) {
		 jj_lasttokens[(jj_endpos = pos) - 1] = kind;
	   }
	 }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[53];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 29; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 53; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 jj_endpos = 0;
	 jj_rescan_token();
	 jj_add_error_token(0, 0);
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  static private boolean trace_enabled;

/** Trace enabled. */
  static final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
	 jj_rescan = true;
	 for (int i = 0; i < 4; i++) {
	   try {
		 JJCalls p = jj_2_rtns[i];

		 do {
		   if (p.gen > jj_gen) {
			 jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
			 switch (i) {
			   case 0: jj_3_1(); break;
			   case 1: jj_3_2(); break;
			   case 2: jj_3_3(); break;
			   case 3: jj_3_4(); break;
			 }
		   }
		   p = p.next;
		 } while (p != null);

		 } catch(LookaheadSuccess ls) { }
	 }
	 jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
	 JJCalls p = jj_2_rtns[index];
	 while (p.gen > jj_gen) {
	   if (p.next == null) { p = p.next = new JJCalls(); break; }
	   p = p.next;
	 }

	 p.gen = jj_gen + xla - jj_la; 
	 p.first = token;
	 p.arg = xla;
  }

  static final class JJCalls {
	 int gen;
	 Token first;
	 int arg;
	 JJCalls next;
  }

}
