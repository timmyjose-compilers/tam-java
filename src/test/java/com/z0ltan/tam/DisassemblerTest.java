package com.z0ltan.tam;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class DisassemblerTest {
  public void testFoo() throws Exception {
    final String expected = tapSystemOut(() ->  {
      System.out.println("Hi!");
    });
    assertEquals("Hi!\n", expected);
  }

  public void testCapitalise() throws Exception {
    final String cmpFile = "samples/decompiled/capitalise.decompiled";
    final String cmpString = Files.readString(Paths.get(cmpFile));

    final Disassembler disassembler = new Disassembler();
    final String decompiledString = tapSystemOut(() -> {
      disassembler.main(null);
    });

    //assertEquals(cmpString, decompiledString);
  }
}

