package com.z0ltan.tam;

public class Disassembler {
  private String objName;

  public Disassembler() {}

  public Disassembler(final String objName) {
    this.objName = objName;
  }

  private void loadProgram() {

  }

  private void disassemble() {

  }

  public static void main(String[] args) {
    final Disassembler disassembler = null;

    if (args.length == 0) {
      disassembler = new Disassembler(objName);
    } else {
      disassembler = new Disassembler("obj.tam");
    }

    loadProgram();
    disassemble();
  }
}
