package com.z0ltan.tam;

import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class DisassemblerTest {
  private String compress(final String str) {
    final StringBuffer sb = new StringBuffer();

    for (char c : str.toCharArray()) {
      if (Character.isWhitespace(c)) {
        continue;
      }
      sb.append(c);
    }

    return sb.toString();
  }

  private void assertContentEquals(final String expected, final String actual) {
    final String expectedCompressed = compress(expected);
    final String actualCompressed = compress(actual);

    assertEquals(expectedCompressed, actualCompressed);
  }

  private void compareContents(final String decompiledFile, final String tamFile) {
    try {
      final String cmpString = Files.readString(Paths.get(decompiledFile));

      final String decompiledString = tapSystemOut(() -> {
        Disassembler.main(new String[] { tamFile });
      });

      assertContentEquals(cmpString, decompiledString);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public void testCapitalise() throws Exception {
    compareContents("samples/decompiled/capitalise.decompiled", "samples/binary/capitalise.tam");
  }

  public void testFreq() {
    compareContents("samples/decompiled/freq.decompiled", "samples/binary/freq.tam");
  }

  public void testMonthsOfYear() {
    compareContents("samples/decompiled/monthsofyear.decompiled", "samples/binary/monthsofyear.tam");
  }

  public void testRecord() {
    compareContents("samples/decompiled/record.decompiled", "samples/binary/record.tam");
  }

  public void testDate() {
    compareContents("samples/decompiled/date.decompiled", "samples/binary/date.tam");
  }

  public void testHello() {
    compareContents("samples/decompiled/hello.decompiled", "samples/binary/hello.tam");
  }

  public void testNestedArrays() {
    compareContents("samples/decompiled/nestedarrays.decompiled", "samples/binary/nestedarrays.tam");
  }

  public void testReverseLine() {
    compareContents("samples/decompiled/reverse_line.decompiled", "samples/binary/reverse_line.tam");
  }

  public void testDates() {
    compareContents("samples/decompiled/dates.decompiled", "samples/binary/dates.tam");
  }

  public void testInc() {
    compareContents("samples/decompiled/inc.decompiled", "samples/binary/inc.tam");
  }

  public void testNestedRecords() {
    compareContents("samples/decompiled/nestedrecords.decompiled", "samples/binary/nestedrecords.tam");
  }

  public void testString() {
    compareContents("samples/decompiled/string.decompiled", "samples/binary/string.tam");
  }

  public void testEcho() {
    compareContents("samples/decompiled/echo.decompiled", "samples/binary/echo.tam");
  }

  public void testInsertionSort() {
    compareContents("samples/decompiled/insertion_sort.decompiled", "samples/binary/insertion_sort.tam");
  }

  public void testOdd() {
    compareContents("samples/decompiled/odd.decompiled", "samples/binary/odd.tam");
  }

  public void testSumProc() {
    compareContents("samples/decompiled/sum_proc.decompiled", "samples/binary/sum_proc.tam");
  }

  public void testEmptycommandeot() {
    compareContents("samples/decompiled/emptycommandeot.decompiled", "samples/binary/emptycommandeot.tam");
  }

  public void testIteratively() {
    compareContents("samples/decompiled/iteratively.decompiled", "samples/binary/iteratively.tam");
  }

  public void testPower() {
    compareContents("samples/decompiled/power.decompiled", "samples/binary/power.tam");
  }

  public void testEmptyCommandSemicolon() {
    compareContents("samples/decompiled/emptycommandsemicolon.decompiled", "samples/binary/emptycommandsemicolon.tam");
  }

  public void testLeapYear() {
    compareContents("samples/decompiled/leapyear.decompiled", "samples/binary/leapyear.tam");
  }

  public void testPrintArray() {
    compareContents("samples/decompiled/print_array.decompiled", "samples/binary/print_array.tam");
  }

  public void testFactorial() {
    compareContents("samples/decompiled/factorial.decompiled", "samples/binary/factorial.tam");
  }

  public void testLine() {
    compareContents("samples/decompiled/line.decompiled", "samples/binary/line.tam");
  }

  public void testRationals() {
    compareContents("samples/decompiled/rationals.decompiled", "samples/binary/rationals.tam");
  }
}
