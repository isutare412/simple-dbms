/* SimpleDBMSParserTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. SimpleDBMSParserTokenManager.java */
package kr.ac.snu.ids.PRJ1_1_2013_12295;

/** Token Manager. */
public class SimpleDBMSParserTokenManager implements SimpleDBMSParserConstants {

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x3fe0L) != 0L)
         {
            jjmatchedKind = 22;
            return 3;
         }
         return -1;
      case 1:
         if ((active0 & 0x3fe0L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 1;
            return 3;
         }
         return -1;
      case 2:
         if ((active0 & 0x3fa0L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 2;
            return 3;
         }
         if ((active0 & 0x40L) != 0L)
            return 3;
         return -1;
      case 3:
         if ((active0 & 0x3a00L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 3;
            return 3;
         }
         if ((active0 & 0x1a0L) != 0L)
            return 3;
         if ((active0 & 0x400L) != 0L)
         {
            if (jjmatchedPos < 2)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 2;
            }
            return -1;
         }
         return -1;
      case 4:
         if ((active0 & 0x3a00L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 4;
            return 3;
         }
         if ((active0 & 0x400L) != 0L)
         {
            if (jjmatchedPos < 2)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 2;
            }
            return -1;
         }
         return -1;
      case 5:
         if ((active0 & 0x400L) != 0L)
         {
            if (jjmatchedPos < 2)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 2;
            }
            return -1;
         }
         if ((active0 & 0x3a00L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 5;
            return 3;
         }
         return -1;
      case 6:
         if ((active0 & 0x200L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x400L) != 0L)
         {
            if (jjmatchedPos < 2)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 2;
            }
            return -1;
         }
         if ((active0 & 0x3800L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 6;
            return 3;
         }
         return -1;
      case 7:
         if ((active0 & 0x2000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 7;
            return 3;
         }
         if ((active0 & 0x1800L) != 0L)
         {
            if (jjmatchedPos < 6)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 6;
            }
            return -1;
         }
         if ((active0 & 0x200L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 5;
            }
            return -1;
         }
         if ((active0 & 0x400L) != 0L)
         {
            if (jjmatchedPos < 2)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 2;
            }
            return -1;
         }
         return -1;
      case 8:
         if ((active0 & 0x2000L) != 0L)
         {
            jjmatchedKind = 22;
            jjmatchedPos = 8;
            return 3;
         }
         if ((active0 & 0x1800L) != 0L)
         {
            if (jjmatchedPos < 6)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 6;
            }
            return -1;
         }
         if ((active0 & 0x200L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 9:
         if ((active0 & 0x2000L) != 0L)
            return 3;
         if ((active0 & 0x1800L) != 0L)
         {
            if (jjmatchedPos < 6)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 6;
            }
            return -1;
         }
         if ((active0 & 0x200L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      case 10:
         if ((active0 & 0x1800L) != 0L)
         {
            if (jjmatchedPos < 6)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 6;
            }
            return -1;
         }
         if ((active0 & 0x200L) != 0L)
         {
            if (jjmatchedPos < 5)
            {
               jjmatchedKind = 22;
               jjmatchedPos = 5;
            }
            return -1;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 40:
         return jjStopAtPos(0, 15);
      case 41:
         return jjStopAtPos(0, 16);
      case 44:
         return jjStopAtPos(0, 17);
      case 59:
         return jjStopAtPos(0, 14);
      case 95:
         return jjStopAtPos(0, 18);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa1_0(0x280L);
      case 68:
      case 100:
         return jjMoveStringLiteralDfa1_0(0x100L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa1_0(0x20L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa1_0(0x40L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa1_0(0x800L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x100L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 72:
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x40L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x1400L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0xa00L);
      case 88:
      case 120:
         return jjMoveStringLiteralDfa2_0(active0, 0x20L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x80L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x200L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x820L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000L);
      case 84:
      case 116:
         if ((active0 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(2, 6, 3);
         return jjMoveStringLiteralDfa3_0(active0, 0x500L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa4_0(active0, 0x400L);
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x200L);
      case 69:
      case 101:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(3, 8, 3);
         return jjMoveStringLiteralDfa4_0(active0, 0x3000L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x800L);
      case 82:
      case 114:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(3, 7, 3);
         break;
      case 84:
      case 116:
         if ((active0 & 0x20L) != 0L)
            return jjStartNfaWithStates_0(3, 5, 3);
         break;
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x800L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa5_0(active0, 0x400L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x200L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x2200L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x800L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa6_0(active0, 0x400L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa7_0(active0, 0x200L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa7_0(active0, 0x400L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa7_0(active0, 0x3000L);
      case 89:
      case 121:
         return jjMoveStringLiteralDfa7_0(active0, 0x800L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjMoveStringLiteralDfa7_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 32:
         return jjMoveStringLiteralDfa8_0(active0, 0x1800L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x2000L);
      case 76:
      case 108:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(7, 10);
         break;
      case 84:
      case 116:
         return jjMoveStringLiteralDfa8_0(active0, 0x200L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
static private int jjMoveStringLiteralDfa8_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa9_0(active0, 0x200L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa9_0(active0, 0x2000L);
      case 75:
      case 107:
         return jjMoveStringLiteralDfa9_0(active0, 0x1800L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
static private int jjMoveStringLiteralDfa9_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 66:
      case 98:
         return jjMoveStringLiteralDfa10_0(active0, 0x200L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa10_0(active0, 0x1800L);
      case 83:
      case 115:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(9, 13, 3);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
static private int jjMoveStringLiteralDfa10_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 76:
      case 108:
         return jjMoveStringLiteralDfa11_0(active0, 0x200L);
      case 89:
      case 121:
         if ((active0 & 0x800L) != 0L)
            return jjStopAtPos(10, 11);
         else if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(10, 12);
         break;
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
static private int jjMoveStringLiteralDfa11_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 69:
      case 101:
         if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(11, 9);
         break;
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 4;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 19)
                        kind = 19;
                     { jjCheckNAdd(1); }
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 20)
                        kind = 20;
                     { jjCheckNAdd(1); }
                  }
                  break;
               case 1:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  kind = 19;
                  { jjCheckNAdd(1); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  { jjCheckNAdd(3); }
                  break;
               case 3:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 22)
                     kind = 22;
                  { jjCheckNAdd(3); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 4 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, "\73", "\50", "\51", "\54", "\137", null, null, null, null, null, };
static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {0
};

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

    /** Constructor. */
    public SimpleDBMSParserTokenManager(SimpleCharStream stream){

      if (input_stream != null)
        throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);

    input_stream = stream;
  }

  /** Constructor. */
  public SimpleDBMSParserTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  static public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  static private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 4; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  static public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public static void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xffffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    static protected SimpleCharStream  input_stream;

    static private final int[] jjrounds = new int[4];
    static private final int[] jjstateSet = new int[2 * 4];
    private static final StringBuilder jjimage = new StringBuilder();
    private static StringBuilder image = jjimage;
    private static int jjimageLen;
    private static int lengthOfMatch;
    static protected int curChar;
}
