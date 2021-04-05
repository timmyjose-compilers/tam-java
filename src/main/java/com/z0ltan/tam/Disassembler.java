package com.z0ltan.tam;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Arrays;

import java.io.EOFException;
import java.io.IOException;

public class Disassembler {
  private String objName;

  public Disassembler() {}

  public Disassembler(final String objName) {
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

  private String toText(final Instruction instr) {
    StringBuffer sb = new StringBuffer();

    switch (instr.op) {
      case Machine.Opcodes.LOADOp:
        sb.append("LOAD(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(Machine.Registers.registerName(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.LOADAOp:
        sb.append("LOADA ")
          .append(instr.d)
          .append("[")
          .append(Machine.Registers.registerName(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.LOADIOp:
        sb.append("LOADI(").append(instr.n).append(")");
        break;

      case Machine.Opcodes.LOADLOp:
        sb.append("LOADL ").append(instr.d);
        break;

      case Machine.Opcodes.STOREOp:
        sb.append("STORE(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(Machine.Registers.registerName(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.STOREIOp:
        sb.append("STOREI(")
          .append(instr.n)
          .append(")");
        break;

      case Machine.Opcodes.CALLOp:
        if (instr.r == Machine.Registers.PBr) {
          sb.append("CALL ").append(Machine.Primitives.primitiveName(instr.d));
        } else {
          sb.append("CALL(")
            .append(Machine.Registers.registerName(instr.n))
            .append(") ")
            .append(instr.d)
            .append("[")
            .append(Machine.Registers.registerName(instr.r))
            .append("]");
        }
        break;

      case Machine.Opcodes.CALLIOp:
        sb.append("CALLI");
        break;

      case Machine.Opcodes.RETURNOp:
        sb.append("RETURN(")
          .append(instr.n)
          .append(") ")
          .append(instr.d);
        break;

      case Machine.Opcodes.PUSHOp:
        sb.append("PUSH ").append(instr.d);
        break;

      case Machine.Opcodes.POPOp:
        sb.append("POP(")
          .append(instr.n)
          .append(") ")
          .append(instr.d);
        break;

      case Machine.Opcodes.JUMPOp:
        sb.append("JUMP ")
          .append(instr.d)
          .append("[")
          .append(Machine.Registers.registerName(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.JUMPIOp:
        sb.append("JUMPI");
        break;

      case Machine.Opcodes.JUMPIFOp:
        sb.append("JUMPIF(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(Machine.Registers.registerName(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.HALTOp:
        sb.append("HALT");
        break;
    }

    return sb.toString();
  }

  private void disassemble() {
    for (int addr = Machine.CB; addr < Machine.CT; addr++) {
      System.out.printf("%d: %s\n", addr, toText(Machine.code[addr]));
    }
  }

  public static void main(String[] args) {
    Disassembler disassembler = null;

    if (args == null || args.length == 0) {
      disassembler = new Disassembler("obj.tam");
    } else {
      disassembler = new Disassembler(args[0]);
    }

    disassembler.loadProgram();
    if (Machine.CT != Machine.CB) {
      disassembler.disassemble();
    }
  }
}
