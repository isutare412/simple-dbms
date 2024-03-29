/* SimpleDBMSParserTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. SimpleDBMSParserTokenManager.java */
package kr.ac.snu.ids.PRJ1_3_2013_12295;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.util.ArrayList;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import kr.ac.snu.ids.PRJ1_3_2013_12295.database.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.query.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.exception.*;
import kr.ac.snu.ids.PRJ1_3_2013_12295.expression.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class SimpleDBMSParserTokenManager implements SimpleDBMSParserConstants {

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0x2000000000000L) != 0L)
            return 36;
         if ((active0 & 0x200000000L) != 0L)
            return 37;
         if ((active0 & 0x7ffffff0L) != 0L)
         {
            jjmatchedKind = 40;
            return 35;
         }
         return -1;
      case 1:
         if ((active0 & 0x4c000000L) != 0L)
            return 35;
         if ((active0 & 0x33fffff0L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 1;
            return 35;
         }
         return -1;
      case 2:
         if ((active0 & 0x11bfdfd0L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 40;
               jjmatchedPos = 2;
            }
            return 35;
         }
         if ((active0 & 0x22402020L) != 0L)
            return 35;
         return -1;
      case 3:
         if ((active0 & 0xbed300L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 3;
            return 35;
         }
         if ((active0 & 0x11012cd0L) != 0L)
            return 35;
         return -1;
      case 4:
         if ((active0 & 0xa0200L) != 0L)
            return 35;
         if ((active0 & 0xb4d100L) != 0L)
         {
            if (jjmatchedPos != 4)
            {
               jjmatchedKind = 40;
               jjmatchedPos = 4;
            }
            return 35;
         }
         return -1;
      case 5:
         if ((active0 & 0xb00000L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 5;
            return 35;
         }
         if ((active0 & 0x6d100L) != 0L)
            return 35;
         return -1;
      case 6:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 6;
            return 35;
         }
         if ((active0 & 0x300000L) != 0L)
            return 35;
         return -1;
      case 7:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 7;
            return 35;
         }
         return -1;
      case 8:
         if ((active0 & 0x800000L) != 0L)
         {
            jjmatchedKind = 40;
            jjmatchedPos = 8;
            return 35;
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
      case 9:
         return jjStopAtPos(0, 2);
      case 32:
         return jjStopAtPos(0, 1);
      case 39:
         return jjStartNfaWithStates_0(0, 49, 36);
      case 40:
         return jjStopAtPos(0, 34);
      case 41:
         return jjStopAtPos(0, 35);
      case 42:
         return jjStopAtPos(0, 31);
      case 44:
         return jjStopAtPos(0, 36);
      case 46:
         return jjStopAtPos(0, 37);
      case 59:
         return jjStartNfaWithStates_0(0, 33, 37);
      case 95:
         return jjStopAtPos(0, 48);
      case 65:
      case 97:
         return jjMoveStringLiteralDfa1_0(0x28000000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa1_0(0x140L);
      case 68:
      case 100:
         return jjMoveStringLiteralDfa1_0(0x4c80L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa1_0(0x10L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa1_0(0x10200000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa1_0(0x40003020L);
      case 75:
      case 107:
         return jjMoveStringLiteralDfa1_0(0x400000L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa1_0(0x3000000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa1_0(0x4000000L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa1_0(0x800000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa1_0(0x18000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa1_0(0x20200L);
      case 86:
      case 118:
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 87:
      case 119:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      default :
         return jjMoveNfa_0(1, 0);
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
         return jjMoveStringLiteralDfa2_0(active0, 0x60280L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0xc0c800L);
      case 72:
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x90040L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa2_0(active0, 0x20003020L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x2200000L);
      case 82:
      case 114:
         if ((active0 & 0x4000000L) != 0L)
            return jjStartNfaWithStates_0(1, 26, 35);
         return jjMoveStringLiteralDfa2_0(active0, 0x10100500L);
      case 83:
      case 115:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(1, 27, 35);
         else if ((active0 & 0x40000000L) != 0L)
            return jjStartNfaWithStates_0(1, 30, 35);
         break;
      case 85:
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 88:
      case 120:
         return jjMoveStringLiteralDfa2_0(active0, 0x10L);
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
         return jjMoveStringLiteralDfa3_0(active0, 0x40L);
      case 66:
      case 98:
         return jjMoveStringLiteralDfa3_0(active0, 0x20200L);
      case 68:
      case 100:
         if ((active0 & 0x20000000L) != 0L)
            return jjStartNfaWithStates_0(2, 29, 35);
         break;
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x80100L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x100010L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa3_0(active0, 0x104c000L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x10010400L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x200000L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x1800L);
      case 84:
      case 116:
         if ((active0 & 0x20L) != 0L)
         {
            jjmatchedKind = 5;
            jjmatchedPos = 2;
         }
         else if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(2, 25, 35);
         return jjMoveStringLiteralDfa3_0(active0, 0x2080L);
      case 89:
      case 121:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(2, 22, 35);
         break;
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
      case 65:
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x100L);
      case 67:
      case 99:
         if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(3, 11, 35);
         break;
      case 69:
      case 101:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(3, 7, 35);
         return jjMoveStringLiteralDfa4_0(active0, 0xa0d000L);
      case 76:
      case 108:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(3, 24, 35);
         return jjMoveStringLiteralDfa4_0(active0, 0x20200L);
      case 77:
      case 109:
         if ((active0 & 0x10000000L) != 0L)
            return jjStartNfaWithStates_0(3, 28, 35);
         return jjMoveStringLiteralDfa4_0(active0, 0x100000L);
      case 79:
      case 111:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(3, 13, 35);
         break;
      case 80:
      case 112:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(3, 10, 35);
         break;
      case 82:
      case 114:
         if ((active0 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(3, 6, 35);
         return jjMoveStringLiteralDfa4_0(active0, 0x80000L);
      case 84:
      case 116:
         if ((active0 & 0x10L) != 0L)
            return jjStartNfaWithStates_0(3, 4, 35);
         break;
      case 85:
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x40000L);
      case 87:
      case 119:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(3, 16, 35);
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
         return jjMoveStringLiteralDfa5_0(active0, 0x100000L);
      case 67:
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x8000L);
      case 69:
      case 101:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 4;
         }
         else if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(4, 19, 35);
         return jjMoveStringLiteralDfa5_0(active0, 0x60000L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x200000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x801000L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x4100L);
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
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(5, 8, 35);
         else if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(5, 14, 35);
         return jjMoveStringLiteralDfa6_0(active0, 0x800000L);
      case 71:
      case 103:
         return jjMoveStringLiteralDfa6_0(active0, 0x200000L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x100000L);
      case 83:
      case 115:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(5, 17, 35);
         else if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(5, 18, 35);
         break;
      case 84:
      case 116:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(5, 12, 35);
         else if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(5, 15, 35);
         break;
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
      case 78:
      case 110:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(6, 21, 35);
         return jjMoveStringLiteralDfa7_0(active0, 0x800000L);
      case 89:
      case 121:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(6, 20, 35);
         break;
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
      case 67:
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x800000L);
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
      case 69:
      case 101:
         return jjMoveStringLiteralDfa9_0(active0, 0x800000L);
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
      case 83:
      case 115:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(9, 23, 35);
         break;
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
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
   jjnewStateCnt = 36;
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
               case 37:
                  if ((0x2400L & l) != 0L)
                  {
                     if (kind > 32)
                        kind = 32;
                  }
                  else if ((0x100000200L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 1:
                  if ((0xffffff7b00002600L & l) != 0L)
                  {
                     if (kind > 51)
                        kind = 51;
                  }
                  else if (curChar == 39)
                     { jjCheckNAddTwoStates(14, 15); }
                  if ((0xfc00ff7a00002600L & l) != 0L)
                  {
                     if (kind > 50)
                        kind = 50;
                  }
                  else if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 39)
                        kind = 39;
                     { jjCheckNAddStates(3, 6); }
                  }
                  if ((0x7000000000000000L & l) != 0L)
                  {
                     if (kind > 38)
                        kind = 38;
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 46)
                        kind = 46;
                     { jjCheckNAdd(19); }
                  }
                  else if ((0x2400L & l) != 0L)
                  {
                     if (kind > 3)
                        kind = 3;
                  }
                  else if (curChar == 33)
                     { jjCheckNAdd(9); }
                  else if (curChar == 59)
                     { jjCheckNAddStates(0, 2); }
                  if (curChar == 60)
                     { jjCheckNAdd(9); }
                  else if (curChar == 62)
                     { jjCheckNAdd(9); }
                  else if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 36:
                  if ((0xffffff7b00002600L & l) != 0L)
                     { jjCheckNAddTwoStates(14, 15); }
                  else if (curChar == 39)
                  {
                     if (kind > 42)
                        kind = 42;
                  }
                  break;
               case 0:
                  if (curChar == 10 && kind > 3)
                     kind = 3;
                  break;
               case 2:
                  if ((0x2400L & l) != 0L && kind > 3)
                     kind = 3;
                  break;
               case 3:
                  if (curChar == 59)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 4:
                  if ((0x100000200L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 5:
                  if (curChar == 10 && kind > 32)
                     kind = 32;
                  break;
               case 6:
                  if (curChar == 13)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if ((0x2400L & l) != 0L && kind > 32)
                     kind = 32;
                  break;
               case 8:
                  if ((0x7000000000000000L & l) != 0L && kind > 38)
                     kind = 38;
                  break;
               case 9:
                  if (curChar == 61 && kind > 38)
                     kind = 38;
                  break;
               case 10:
                  if (curChar == 62)
                     { jjCheckNAdd(9); }
                  break;
               case 11:
                  if (curChar == 60)
                     { jjCheckNAdd(9); }
                  break;
               case 12:
                  if (curChar == 33)
                     { jjCheckNAdd(9); }
                  break;
               case 13:
                  if (curChar == 39)
                     { jjCheckNAddTwoStates(14, 15); }
                  break;
               case 14:
                  if ((0xffffff7b00002600L & l) != 0L)
                     { jjCheckNAddTwoStates(14, 15); }
                  break;
               case 15:
                  if (curChar == 39 && kind > 42)
                     kind = 42;
                  break;
               case 16:
                  if ((0xfc00ff7a00002600L & l) != 0L && kind > 50)
                     kind = 50;
                  break;
               case 17:
                  if ((0xffffff7b00002600L & l) != 0L && kind > 51)
                     kind = 51;
                  break;
               case 18:
                  if ((0x280000000000L & l) == 0L)
                     break;
                  if (kind > 46)
                     kind = 46;
                  { jjCheckNAdd(19); }
                  break;
               case 19:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 39)
                     kind = 39;
                  { jjCheckNAdd(19); }
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 39)
                     kind = 39;
                  { jjCheckNAddStates(3, 6); }
                  break;
               case 21:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 22:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 24:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 25:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 26:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 27:
                  if (curChar == 45)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 28:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 29:
                  if ((0x3ff000000000000L & l) != 0L && kind > 41)
                     kind = 41;
                  break;
               case 30:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 31:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 32:
                  if ((0x3ff000000000000L & l) != 0L && kind > 43)
                     kind = 43;
                  break;
               case 33:
                  if ((0x3ff000000000000L & l) != 0L && kind > 44)
                     kind = 44;
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
               case 1:
                  if ((0x7fffffffffffffffL & l) != 0L)
                  {
                     if (kind > 51)
                        kind = 51;
                  }
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 40)
                        kind = 40;
                     { jjCheckNAdd(35); }
                  }
                  else if ((0x78000001f8000001L & l) != 0L)
                  {
                     if (kind > 50)
                        kind = 50;
                  }
                  break;
               case 36:
               case 14:
                  if ((0x7fffffffffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(14, 15); }
                  break;
               case 16:
                  if ((0x78000001f8000001L & l) != 0L && kind > 50)
                     kind = 50;
                  break;
               case 17:
                  if ((0x7fffffffffffffffL & l) != 0L && kind > 51)
                     kind = 51;
                  break;
               case 34:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 40)
                     kind = 40;
                  { jjCheckNAdd(35); }
                  break;
               case 35:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 40)
                     kind = 40;
                  { jjCheckNAdd(35); }
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
      if ((i = jjnewStateCnt) == (startsAt = 36 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, "\52", null, "\73", "\50", "\51", "\54", "\56", null, null, 
null, null, null, null, null, null, null, null, "\137", "\47", null, null, null, };
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
static final int[] jjnextStates = {
   4, 6, 7, 19, 21, 30, 33, 
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

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedPos == 0 && jjmatchedKind > 52)
   {
      jjmatchedKind = 52;
   }
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

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
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
    for (i = 36; i-- > 0;)
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
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x1ffffffffffff1L, 
};
static final long[] jjtoSkip = {
   0xeL, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    static protected SimpleCharStream  input_stream;

    static private final int[] jjrounds = new int[36];
    static private final int[] jjstateSet = new int[2 * 36];
    private static final StringBuilder jjimage = new StringBuilder();
    private static StringBuilder image = jjimage;
    private static int jjimageLen;
    private static int lengthOfMatch;
    static protected int curChar;
}
