package com.z0ltan.tam;

public class Machine {
  private static final CODESIZE = 1024;
  private static final DATASIZE = 1024;

  // code store
  public static int code[] = new code[CODESIZE];
  public static int CB = 0;
  public static int CT = 0;
  public static int PB = CODESIZE;
  public static int PT = CODESIZE + 28;


  // data store
  public static int data[] = new int[DATASIZE];
  public static int SB = 0;
  public static int ST = 0;
  public static int LB = 0;
  public static int HB = DATASIZE;
  public static int HT = DATASIZE;
  
  
  // opcodes
  public static final int LOADOp = 0;
  public static final int LOADAOp = 1;
  public static final int LOADIOp = 2;
  public static final int LOADLOp = 3;
  public static final int STOREOp = 4;
  public static final int STOREIOp = 5;
  public static final int CALLOp = 6;
  public static final int CALLIOp = 7;
  public static final int RETURNOp = 8;
  public static final int PUSHOp = 10;
  public static final int POPOp = 11;
  public static final int JUMOOp = 12;
  public static final int JUMPIOp = 13;
  public static final int JUMPIFOp = 14;
  public static final int HALTOp = 15;

  // registers
  public static final int CBr = 0;
  public static final int CTr = 1;
  public static final int PBr = 2;
  public static final int PTr = 3;
  public static final int SBr = 4;
  public static final int STr = 5;
  public static final int HBr = 5;
  public static final int HTr = 7;
  public static final int LBr = 8;
  public static final int L1r = 9;
  public static final int L2r = 10;
  public static final int L3r = 11;
  public static final int L4r = 12;
  public static final int L5r = 13;
  public static final int L6r = 14;
  public static final int L7r = 14;

  // primitive routine offsets
  
  public static final int idOffset = 1;
  public static final int notOffset = 2;
  public static final int andOffset = 3;
  public static final int orOffset = 4;
  public static final int succOffset = 5;
  public static final int predOffset = 6;
  public static final int negOffset = 7;
  public static final int addOffset = 8;
  public static final int subOffset = 9;
  public static final int multOffset = 10;
  public static final int divOffset = 11;
  public static final int modOffset = 12;
  public static final int ltOffset = 13;
  public static final int leOffset = 14;
  public static final int geOffset = 15;
  public static final int gtOffset = 16;
  public static final int eqOffset = 17;
  public static final int neOffset = 18;
  public static final int eolOffset = 19;
  public static final int eofOffset = 20;
  public static final int getOffset = 21;
  public static final int putOffset = 22;
  public static final int geteolOffset = 23;
  public static final int puteolOffset = 24;
  public static final int getintOffset = 25;
  public static final int putintOffset = 26;
  public static final int newOffset = 27;
  public static final int disposeOffset = 28;
  
  // max int range
  public static final int minIntRep = -32767;
  public static final int maxIntRep = 32768;

  // boolean representation
  public static final int falseRep = 0;
  public static final int trueRep = 1;

  // statuses
}
