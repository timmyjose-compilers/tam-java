package com.z0ltan.tam;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class InterpreterTest extends TestCase {
  public InterpreterTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite( InterpreterTest.class );
  }

  public void testInterpreter() {
    assertTrue(true);
  }
}


