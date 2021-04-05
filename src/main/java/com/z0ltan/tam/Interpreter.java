package com.z0ltan.tam;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

import java.io.IOException;

public class Interpreter {
  private String objName;

  public Interpreter() {}

  public Interpreter(final String objName) {
    this.objName = objName;
  }

  private void loadProgram() {
    try (final DataInputStream is = new DataInputStream(new FileInputStream(objName))) {
      int addr = 0;
      Instruction instr = null;

      while (true) {
        instr = Instruction.readInstruction(is);
        if (instr == null) {
          break;
        }
        Machine.code[addr++] = instr;
      } 
      Machine.CT = addr;
    } catch (IOException ex) {
      Machine.CT = Machine.CB;
      System.err.println("Could not read from file: " + objName + ": " + ex.getLocalizedMessage());
    } 
  }

  int content(final int r) {
    switch (r) {
      case Machine.Registers.CBr:
        return Machine.CB;

      case Machine.Registers.CTr:
        return Machine.CT;

      case Machine.Registers.PBr:
        return Machine.PB;

      case Machine.Registers.PTr:
        return Machine.PT;

      case Machine.Registers.SBr:
        return Machine.SB;

      case Machine.Registers.STr:
        return Machine.ST;

      case Machine.Registers.HBr:
        return Machine.HB;

      case Machine.Registers.HTr:
        return Machine.HT;

      case Machine.Registers.LBr:
        return Machine.LB;

      case Machine.Registers.L1r:
        return Machine.data[Machine.LB];

      case Machine.Registers.L2r:
        return Machine.data[Machine.data[Machine.LB]];

      case Machine.Registers.L3r:
        return Machine.data[Machine.data[Machine.data[Machine.LB]]];

      case Machine.Registers.L4r:
        return Machine.data[Machine.data[Machine.data[Machine.data[Machine.LB]]]];

      case Machine.Registers.L5r:
        return Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.LB]]]]];

      case Machine.Registers.L6r:
        return Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.LB]]]]]];

      case Machine.Registers.L7r:
        return Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.data[Machine.LB]]]]]]];

      default:
        Machine.status = Machine.VMStatuses.failedInvalidInstruction;
        return -1;
    }
  }

  private boolean checkSpace(int space) {
    if (Machine.HT - Machine.ST < space) {
      Machine.status = Machine.VMStatuses.failedDatastoreFull;
      return false;
    }
    return true;
  }

  private int checkOverflow(int val) {
    if (val < Machine.minIntRep || val > Machine.maxIntRep) {
      Machine.status = Machine.VMStatuses.failedOverflow;
      return 0;
    }
    return val;
  }

  private boolean checkEqual(int lhsStartAddr, int rhsStartAddr, int n) {
    for (int idx = 0; idx < n; idx++) {
      if (Machine.data[lhsStartAddr + idx] != Machine.data[rhsStartAddr + idx]) {
        return false;
      }
    }
    return true;
  }

  private char readChar() {
    try {
      return (char)System.in.read();
    } catch (Exception ex) {
      Machine.status = Machine.VMStatuses.failedIOError;
      return (char)-1;
    }
  }

  private int readInt() {
    try (final Scanner in = new Scanner(System.in)) {
      return in.nextInt();
    } catch (Exception ex) {
      Machine.status = Machine.VMStatuses.failedIOError;
      return -1;
    }
  }

  private int toInt(final boolean b) {
    return b == true ? Machine.trueRep : Machine.falseRep;
  }

  private boolean isTrue(final int val) {
    if (val == Machine.trueRep) {
      return true;
    } else if (val == Machine.falseRep) {
      return false;
    }
    Machine.status = Machine.VMStatuses.failedInvalidDatum;
    return false;
  }

  private void callPrimitive(final int offset) {
    switch (offset)  {
      case Machine.Primitives.idOffset:
        break;

      case Machine.Primitives.notOffset:
        {
          int val = Machine.data[Machine.ST - 1];
          Machine.data[Machine.ST -1] = toInt(!isTrue(val));
        }
        break;

      case Machine.Primitives.andOffset:
        {
          Machine.ST -= 2;
          boolean lhs = isTrue(Machine.data[Machine.ST]);
          boolean rhs = isTrue(Machine.data[Machine.ST + 1]);
          Machine.data[Machine.ST++] = toInt(lhs && rhs);
        }
        break;

      case Machine.Primitives.orOffset: 
        {
          Machine.ST -= 2;
          boolean lhs = isTrue(Machine.data[Machine.ST]);
          boolean rhs = isTrue(Machine.data[Machine.ST + 1]);
          Machine.data[Machine.ST++] = toInt(lhs || rhs);
        }
        break;

      case Machine.Primitives.succOffset:
        {
          Machine.ST--;
          Machine.data[Machine.ST++] = checkOverflow(Machine.data[Machine.ST] + 1);
        }
        break;

      case Machine.Primitives.predOffset:
        {
          Machine.ST--;
          Machine.data[Machine.ST++] = checkOverflow(Machine.data[Machine.ST] - 1);
        }
        break;

      case Machine.Primitives.negOffset:
        {
          Machine.ST--;
          Machine.data[Machine.ST++] = -Machine.data[Machine.ST];
        }
        break;

      case Machine.Primitives.addOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = checkOverflow(lhs + rhs);
        }
        break;

      case Machine.Primitives.subOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = checkOverflow(lhs - rhs);
        }
        break;

      case Machine.Primitives.multOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = checkOverflow(lhs * rhs);
        }
        break;

      case Machine.Primitives.divOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          
          if (rhs == 0) {
            Machine.status = Machine.VMStatuses.failedZeroDivide;
          } else {
            Machine.data[Machine.ST++] = lhs / rhs;
          }
        }
        break;

      case Machine.Primitives.modOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];

          if (rhs == 0) {
            Machine.status = Machine.VMStatuses.failedZeroDivide;
          } else {
            Machine.data[Machine.ST++] = lhs % rhs;
          }
        }
        break;

      case Machine.Primitives.ltOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = toInt(lhs < rhs);
        }
        break;

      case Machine.Primitives.leOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = toInt(lhs <= rhs);
        }
        break;

      case Machine.Primitives.geOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = toInt(lhs >= rhs);
        }
        break;

      case Machine.Primitives.gtOffset:
        {
          Machine.ST -= 2;
          final int lhs = Machine.data[Machine.ST];
          final int rhs = Machine.data[Machine.ST + 1];
          Machine.data[Machine.ST++] = toInt(lhs > rhs);
        }
        break;

      case Machine.Primitives.eqOffset:
        {
          Machine.ST -= 3;
          final int lhsStartAddr = Machine.data[Machine.ST];
          final int rhsStartAddr = Machine.data[Machine.ST + 1];
          final int n = Machine.data[Machine.ST + 2];
          Machine.data[Machine.ST++] = toInt(checkEqual(lhsStartAddr, rhsStartAddr, n));
        }
        break;

      case Machine.Primitives.neOffset:
        {
          Machine.ST -= 3;
          final int lhsStartAddr = Machine.data[Machine.ST];
          final int rhsStartAddr = Machine.data[Machine.ST + 1];
          final int n = Machine.data[Machine.ST + 2];
          Machine.data[Machine.ST++] = toInt(!checkEqual(lhsStartAddr, rhsStartAddr, n));

        }
        break;

      case Machine.Primitives.eolOffset:
        {
          final char c = readChar();
          Machine.data[Machine.ST++] = (c == '\n') ? Machine.trueRep : Machine.falseRep;
        }
        break;

      case Machine.Primitives.eofOffset:
        {
          final int d = readInt();
          Machine.data[Machine.ST++] = (d == -1) ? Machine.trueRep : Machine.falseRep;
        }
        break;

      case Machine.Primitives.getOffset:
        {
          Machine.ST--;
          final int address = Machine.data[Machine.ST];
          char c = readChar();
          Machine.data[address] = c;
        }
        break;

      case Machine.Primitives.putOffset:
        {
          Machine.ST--;
          final char c = (char)Machine.data[Machine.ST];
          System.out.printf("%c", c);
        }
        break;

      case Machine.Primitives.geteolOffset:
        {
          char c = (char)0;
          while (c != '\n') {
            c = readChar();
          }
        }
        break;

      case Machine.Primitives.puteolOffset:
        {
          System.out.println();
        }
        break;

      case Machine.Primitives.getintOffset:
        {
          Machine.ST--;
          final int address = Machine.data[Machine.ST];
          final int d = readInt();
          Machine.data[address] = d;
        }
        break;

      case Machine.Primitives.putintOffset:
        {
          Machine.ST--;
          final int d = Machine.data[Machine.ST];
          System.out.printf("%d", d);
        }
        break;

      case Machine.Primitives.newOffset:
        {
          Machine.ST--;
          final int n = Machine.data[Machine.ST];
          checkSpace(n);
          final int address = Machine.HT;
          Machine.HT -= n;
          Machine.data[Machine.ST++] = address;
        }
        break;

      case Machine.Primitives.disposeOffset:
        {
          Machine.ST -= 2;
        }
        break;

      default:
        {
          Machine.status = Machine.VMStatuses.failedInvalidCodeAddress;
        }
        break;
    }
  }

  private void interpret() {
    Machine.CP = Machine.CB;
    Machine.LB = Machine.SB;
    Machine.status = Machine.VMStatuses.running;

    do {
      final Instruction instr = Machine.code[Machine.CP];
      int op = instr.op;
      int r = instr.r;
      int n = instr.n;
      int d = instr.d;

      switch (op) {
        case Machine.Opcodes.LOADOp:
          {
            checkSpace(n);
            final int address = d + content(r);
            for (int idx = 0; idx < n; idx++) {
              Machine.data[Machine.ST + idx] = Machine.data[address + idx];
            }
            Machine.ST += n;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.LOADAOp:
          {
            checkSpace(1);
            final int address = d + content(r);
            Machine.data[Machine.ST++] = address;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.LOADIOp:
          {
            checkSpace(n);
            Machine.ST--;
            final int address = Machine.data[Machine.ST];
            for (int idx = 0; idx < n; idx++) {
              Machine.data[Machine.ST + idx] = Machine.data[address + idx];
            }
            Machine.ST += n;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.LOADLOp:
          {
            checkSpace(1);
            Machine.data[Machine.ST++] = d;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.STOREOp:
          {
            final int address = d + content(r);
            Machine.ST -= n;
            for (int idx = 0; idx < n; idx++) {
              Machine.data[address + idx] = Machine.data[Machine.ST + idx];
            }
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.STOREIOp:
          {
            Machine.ST--;
            final int address = Machine.data[Machine.ST];
            Machine.ST -= n;
            for (int idx = 0; idx < n; idx++) {
              Machine.data[address + idx] = Machine.data[Machine.ST + idx];
            }
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.CALLOp:
          {
            final int address = d + content(r);
            if ((address >= Machine.PB) && (address <= Machine.PT)) {
              callPrimitive(address - Machine.PB);
              Machine.CP++;
            } else {
              checkSpace(Machine.linkDataSize);
              Machine.data[Machine.ST] = content(n);
              Machine.data[Machine.ST + 1] = Machine.LB;
              Machine.data[Machine.ST + 2] = Machine.CP + 1;
              Machine.LB = Machine.ST;
              Machine.ST += Machine.linkDataSize;
              Machine.CP = address;
            }
          }
          break;

        case Machine.Opcodes.CALLIOp:
          {
            checkSpace(Machine.linkDataSize);
            Machine.ST -= Machine.closureSize;
            final int address = Machine.data[Machine.ST + 1];
            Machine.data[Machine.ST + 1] = Machine.LB;
            Machine.data[Machine.ST + 1] = Machine.CP + 1;
            Machine.CP = address;
          }
          break;

        case Machine.Opcodes.RETURNOp:
          {
            final int address = Machine.LB - d;
            Machine.CP = Machine.data[Machine.LB + 2];
            Machine.LB = Machine.data[Machine.LB + 1];
            Machine.ST -= n;
            for (int idx = 0; idx < n; idx++) {
              Machine.data[address + idx] = Machine.data[Machine.ST + idx];
            }
            Machine.ST = address + n;
          }
          break;

        case Machine.Opcodes.PUSHOp:
          {
            checkSpace(d);
            Machine.ST += d;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.POPOp:
          {
            final int address = Machine.ST - n - d;
            Machine.ST -= n;
            for (int idx = 0; idx < n; idx++) {
              Machine.data[address + idx] = Machine.data[Machine.ST + idx];
            }
            Machine.ST = address + n;
            Machine.CP++;
          }
          break;

        case Machine.Opcodes.JUMPOp:
          {
            final int address = d + content(r);
            Machine.CP = address;
          }
          break;

        case Machine.Opcodes.JUMPIOp:
          {
            Machine.ST--;
            final int address = Machine.data[Machine.ST];
            Machine.CP = address;
          }
          break;

        case Machine.Opcodes.JUMPIFOp:
          {
            Machine.ST--;
            final int value = Machine.data[Machine.ST];
            Machine.ST--;
            final int address = Machine.data[Machine.ST];

            if (value == n) {
              Machine.CP = address;
            } else {
              Machine.CP++;
            }
          }
          break;

        case Machine.Opcodes.HALTOp:
          {
            Machine.status = Machine.VMStatuses.halted;
          }
          break;
      }
    } while (Machine.status == Machine.VMStatuses.running);
  }

  private void showStatus() {
    System.out.println();
    switch (Machine.status) {
      case Machine.VMStatuses.notStarted:
        System.out.println("Program has not started execution.");
        break;

      case Machine.VMStatuses.running:
        System.out.println("Program is still running.");
        break;

      case Machine.VMStatuses.halted:
        System.out.println("Program has halted normally.");
        break;

      case Machine.VMStatuses.failedDatastoreFull:
        System.out.println("Program has failed due to storage exhaustion.");
        break;

      case Machine.VMStatuses.failedInvalidCodeAddress:
        System.out.println("Program has failed due to an invalid code address.");
        break;

      case Machine.VMStatuses.failedInvalidInstruction:
        System.out.println("Program has failed due to an invalid instruction.");
        break;

      case Machine.VMStatuses.failedOverflow:
        System.out.println("Program has failed due to overflow.");
        break;

      case Machine.VMStatuses.failedZeroDivide:
        System.out.println("Program has failed due to an attempt to divide by zero.");
        break;

      case Machine.VMStatuses.failedIOError:
        System.out.println("Program has failed due to an IO error.");
        break;

      case Machine.VMStatuses.failedInvalidDatum:
        System.out.println("Program has failed due to an invalid datum.");
        break;
    }
  }

  public static void main(String[] args) {
    Interpreter interpreter = null;

    if (args == null || args.length != 1) {
      interpreter = new Interpreter("obj.tam");
    } else {
      interpreter = new Interpreter(args[0]);
    }

    interpreter.loadProgram();
    if (Machine.CB != Machine.CT) {
      interpreter.interpret();
      interpreter.showStatus();
    }
  }
}
