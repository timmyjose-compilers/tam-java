package com.z0ltan.tam;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.EOFException;
import java.io.IOException;

public class Instruction {
  public int op;
  public int r;
  public int n;
  public int d;

  public Instruction() {}

  public Instruction(final int op, final int r, final int n, final int d) {
    this.op = op;
    this.r = r;
    this.n = n;
    this.d = d;
  }

  public void writeInstruction(final DataOutputStream os) throws IOException {
    os.writeInt(this.op);
    os.writeInt(this.r);
    os.writeInt(this.n);
    os.writeInt(this.d);
  }

  public static Instruction readInstruction(final DataInputStream is) throws IOException {
    final Instruction instr = new Instruction();

    try {
      instr.op = is.readInt();
      instr.r = is.readInt();
      instr.n = is.readInt();
      instr.d = is.readInt();
    } catch (EOFException ex) {
      return null;
    }

    return instr;
  }

  @Override
  public String toString() {
    return "Instruction { op = " + Machine.Opcodes.opcodeName(this.op) 
      + ", r = " + Machine.Registers.registerName(this.r) 
      + ", n = " + this.n 
      + ", d = " + this.d + " }";
  }
}
