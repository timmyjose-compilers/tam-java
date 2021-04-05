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
      case  Machine.idOffset:
        return "id";

      case  Machine.notOffset:
        return "not";

      case  Machine.andOffset:
        return "and";

      case  Machine.orOffset:
        return "or";

      case  Machine.succOffset:
        return "succ";

      case  Machine.predOffset:
        return "pred";

      case  Machine.negOffset:
        return "neg";

      case  Machine.addOffset:
        return "add";

      case  Machine.subOffset:
        return "sub";

      case  Machine.multOffset:
        return "mult";

      case  Machine.divOffset:
        return "div";

      case  Machine.modOffset:
        return "mod";

      case  Machine.ltOffset:
        return "lt";

      case  Machine.leOffset:
        return "le";

      case  Machine.geOffset:
        return "ge";

      case  Machine.gtOffset:
        return "gt";

      case  Machine.eqOffset:
        return "eq";

      case  Machine.neOffset:
        return "ne";

      case  Machine.eolOffset:
        return "eol";

      case  Machine.eofOffset:
        return "eof";

      case  Machine.getOffset:
        return "get";

      case  Machine.putOffset:
        return "put";

      case  Machine.geteolOffset:
        return "geteol";

      case  Machine.puteolOffset:
        return "puteol";

      case  Machine.getintOffset:
        return "getint";

      case  Machine.putintOffset:
        return "putint";

      case  Machine.newOffset:
        return "new";

      case  Machine.disposeOffset:
        return "dispose";

      default:
        throw new IllegalStateException("invalid offset for primitive routines: " + offset);
    }
  }

  private String registerFor(int r) {
    switch (r) {
      case Machine.CBr:
        return "CB";

      case Machine.CTr:
        return "CT";

      case Machine.PBr:
        return "PB";

      case Machine.PTr:
        return "PT";

      case Machine.SBr:
        return "SB";

      case Machine.STr:
        return "ST";

      case Machine.HBr:
        return "HB";

      case Machine.HTr:
        return "HT";

      case Machine.LBr:
        return "LB";

      case Machine.L1r:
        return "L1";

      case Machine.L2r:
        return "L2";

      case Machine.L3r:
        return "L3";

      case Machine.L4r:
        return "L4";

      case Machine.L5r:
        return "L5";

      case Machine.L6r:
        return "L6";

      case Machine.L7r:
        return "L7";

      default:
        throw new IllegalStateException("invalid register: " + r);
    }
  }

  private String toText(final Instruction instr) {
    StringBuffer sb = new StringBuffer();

    switch (instr.op) {
      case Machine.LOADOp:
        sb.append("LOAD(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.LOADAOp:
        sb.append("LOADA ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.LOADIOp:
        sb.append("LOADI(").append(instr.n).append(")");
        break;

      case Machine.LOADLOp:
        sb.append("LOADL ").append(instr.d);
        break;

      case Machine.STOREOp:
        sb.append("STORE(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.STOREIOp:
        sb.append("STOREI(")
          .append(instr.n)
          .append(")");
        break;

      case Machine.CALLOp:
        if (instr.r == Machine.PBr) {
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

      case Machine.CALLIOp:
        sb.append("CALLI");
        break;

      case Machine.RETURNOp:
        sb.append("RETURN(")
          .append(instr.n)
          .append(") ")
          .append(instr.d);
        break;

      case Machine.PUSHOp:
        sb.append("PUSH ").append(instr.d);
        break;

      case Machine.POPOp:
        sb.append("POP(")
          .append(instr.n)
          .append(") ")
          .append(instr.d);
        break;

      case Machine.JUMOOp:
        sb.append("JUMP ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.JUMPIOp:
        sb.append("JUMPI");
        break;

      case Machine.JUMPIFOp:
        sb.append("JUMPIF(")
          .append(instr.n)
          .append(") ")
          .append(instr.d)
          .append("[")
          .append(registerFor(instr.r))
          .append("]");
        break;

      case Machine.HALTOp:
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
