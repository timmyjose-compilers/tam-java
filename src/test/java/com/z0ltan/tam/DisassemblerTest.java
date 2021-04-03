package com.z0ltan.tam;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DisassemblerTest extends TestCase {
  public DisassemblerTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite( DisassemblerTest.class );
  }

  public void testDisassembler() {
    assertTrue(true);
  }

  public void testCapitalise() {
    final String cmpFile = "samples/expected/capitalise.tam";
  }
}

