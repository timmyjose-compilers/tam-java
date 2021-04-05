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

  private String primiiveRoutineFor(int offset) {
    switch (offset) {
      case  Machine.Primitives.idOffset:
        return "id";

      case  Machine.Primitives.notOffset:
        return "not";

      case  Machine.Primitives.andOffset:
        return "and";

      case  Machine.Primitives.orOffset:
        return "or";

      case  Machine.Primitives.succOffset:
        return "succ";

      case  Machine.Primitives.predOffset:
        return "pred";

      case  Machine.Primitives.negOffset:
        return "neg";

      case  Machine.Primitives.addOffset:
        return "add";

      case  Machine.Primitives.subOffset:
        return "sub";

      case  Machine.Primitives.multOffset:
        return "mult";

      case  Machine.Primitives.divOffset:
        return "div";

      case  Machine.Primitives.modOffset:
        return "mod";

      case  Machine.Primitives.ltOffset:
        return "lt";

      case  Machine.Primitives.leOffset:
        return "le";

      case  Machine.Primitives.geOffset:
        return "ge";

      case  Machine.Primitives.gtOffset:
        return "gt";

      case  Machine.Primitives.eqOffset:
        return "eq";

      case  Machine.Primitives.neOffset:
        return "ne";

      case  Machine.Primitives.eolOffset:
        return "eol";

      case  Machine.Primitives.eofOffset:
        return "eof";

      case  Machine.Primitives.getOffset:
        return "get";

      case  Machine.Primitives.putOffset:
        return "put";

      case  Machine.Primitives.geteolOffset:
        return "geteol";

      case  Machine.Primitives.puteolOffset:
        return "puteol";

      case  Machine.Primitives.getintOffset:
        return "getint";

      case  Machine.Primitives.putintOffset:
        return "putint";

      case  Machine.Primitives.newOffset:
        return "new";

      case  Machine.Primitives.disposeOffset:
        return "dispose";

      default:
        throw new IllegalStateException("invalid offset for primitive routines: " + offset);
    }
  }

  private String registerFor(int r) {
    switch (r) {
      case Machine.Registers.CBr:
        return "CB";

      case Machine.Registers.CTr:
        return "CT";

      case Machine.Registers.PBr:
        return "PB";

      case Machine.Registers.PTr:
        return "PT";

      case Machine.Registers.SBr:
        return "SB";

      case Machine.Registers.STr:
        return "ST";

      case Machine.Registers.HBr:
        return "HB";

      case Machine.Registers.HTr:
        return "HT";

      case Machine.Registers.LBr:
        return "LB";

      case Machine.Registers.L1r:
        return "L1";

      case Machine.Registers.L2r:
        return "L2";

      case Machine.Registers.L3r:
        return "L3";

      case Machine.Registers.L4r:
        return "L4";

      case Machine.Registers.L5r:
        return "L5";

      case Machine.Registers.L6r:
        return "L6";

      case Machine.Registers.L7r:
        return "L7";

      default:
        throw new IllegalStateException("invalid register: " + r);
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
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.LOADAOp:
        sb.append("LOADA ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
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
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.Opcodes.STOREIOp:
        sb.append("STOREI(")
          .append(instr.n)
          .append(")");
        break;

      case Machine.Opcodes.CALLOp:
        if (instr.r == Machine.Registers.PBr) {
          sb.append("CALL ").append(primiiveRoutineFor(instr.d));
        } else {
          sb.append("CALL(")
            .append(registerFor(instr.n))
            .append(") ")
            .append(instr.d)
            .append("[")
            .append(registerFor(instr.r))
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

      case Machine.Opcodes.JUMOOp:
        sb.append("JUMP ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
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
          .append(registerFor(instr.r))
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
