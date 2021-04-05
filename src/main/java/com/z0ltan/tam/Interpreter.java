package com.z0ltan.tam;

import java.io.DataInputStream;
import java.io.FileInputStream;

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
        Machine.status = Machine.VMStatus.failedInvalidInstruction;
        return -1;
    }
  }

  private boolean checkSpace(int space) {
    if (Machine.HT - Machine.ST < space) {
      Machine.status = Machine.VMStatus.failedDatastoreFull;
      return false;
    }
    return true;
  }

  private void interpret() {
    Machine.CP = Machine.CB;
    Machine.LB = Machine.SB;
    Machine.status = Machine.VMStatus.running;

    int addr = Machine.CB;
    do {
      final Instruction instr = Machine.code[addr];
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
              Machine.data[Machine.ST+idx] = Machine.data[address + idx];
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
          }
          break;

        case Machine.Opcodes.LOADLOp:
          break;

        case Machine.Opcodes.STOREOp:
          break;

        case Machine.Opcodes.STOREIOp:
          break;

        case Machine.Opcodes.CALLOp:
          break;

        case Machine.Opcodes.CALLIOp:
          break;

        case Machine.Opcodes.RETURNOp:
          break;

        case Machine.Opcodes.PUSHOp:
          break;

        case Machine.Opcodes.POPOp:
          break;

        case Machine.Opcodes.JUMOOp:
          break;

        case Machine.Opcodes.JUMPIOp:
          break;

        case Machine.Opcodes.JUMPIFOp:
          break;

        case Machine.Opcodes.HALTOp:
          {
            Machine.status = Machine.VMStatus.halted;
          }
          break;
      }
    } while (Machine.status == Machine.VMStatus.running);
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
    }
  }
}
