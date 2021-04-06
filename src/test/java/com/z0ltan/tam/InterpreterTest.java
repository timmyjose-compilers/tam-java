package com.z0ltan.tam;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.hamcrest.MatcherAssert.assertThat;
import static com.z0ltan.tam.matchers.ContentMatcher.contentIsSame;

public class InterpreterTest {
  private void compareContents(final String tamFile, final String outputFile) {
    try {
      final String expectedOutput = Files.readString(Paths.get(outputFile));
      final String generatedOutput = tapSystemOut(() -> {
        Interpreter.main(new String[] { tamFile });
      });

      assertThat(generatedOutput, contentIsSame(expectedOutput));
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public void testCapitalise() throws Exception {
    withTextFromSystemIn("Hello")
      .execute(() -> {
        compareContents("samples/binary/capitalise.tam", "samples/output/capitalise.out");
      });
  }

  public void testFreq() throws Exception {
    withTextFromSystemIn("hello world\n")
      .execute(() -> {
        compareContents("samples/binary/freq.tam", "samples/output/freq.out");
      });
  }

  public void testMonthsOfYear() throws Exception {
    withTextFromSystemIn("2021\n04\n06\n")
      .execute(() -> {
        compareContents("samples/binary/monthsofyear.tam", "samples/output/monthsofyear.out");
      });
  }

  public void testRecord() {
    compareContents("samples/binary/record.tam", "samples/output/record.out");
  }

  public void testDate() throws Exception {
    withTextFromSystemIn("2021\n04\n06\n")
      .execute(() -> {
        compareContents("samples/binary/date.tam", "samples/output/date.out");
      });
  }

  public void testHello() {
    compareContents("samples/binary/hello.tam", "samples/output/hello.out");
  }

  public void testNestedArrays() throws Exception {
    withTextFromSystemIn("1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n")
      .execute(() -> {
        compareContents("samples/binary/nestedarrays.tam", "samples/output/nestedarrays.out");
      });
  }

  public void testDates() throws Exception {
    withTextFromSystemIn("04\n04\n11\n22\n01\n28\n12\n31\n")
      .execute(() -> {
        compareContents("samples/binary/dates.tam", "samples/output/dates.out");
      });
  }

  public void testInc() throws Exception {
    withTextFromSystemIn("99")
      .execute(() -> {
        compareContents("samples/binary/inc.tam", "samples/output/inc.out");
      });
  }

  public void testNestedRecords() {
    compareContents("samples/binary/nestedrecords.tam", "samples/output/nestedrecords.out");
  }

  public void testString() throws Exception {
    withTextFromSystemIn("Hello, world!\n")
      .execute(() -> {
        compareContents("samples/binary/string.tam", "samples/output/string.out");
      });
  }

  public void testEcho() throws Exception {
    withTextFromSystemIn("Hello, world! Nice to meet you!\n")
      .execute(() -> {
        compareContents("samples/binary/echo.tam", "samples/output/echo.out");
      });
  }

  public void testInsertionSort() throws Exception {
    withTextFromSystemIn("1\n2\n3\n5\n4\n34\n2\n1\n2\n3\n")
      .execute(() -> {
        compareContents("samples/binary/insertion_sort.tam", "samples/output/insertion_sort.out");
      });
  }

  public void testOdd() throws Exception {
    withTextFromSystemIn("99\n")
      .execute(() -> {
        compareContents("samples/binary/odd.tam", "samples/output/odd.out");
      });
  }

  public void testSumProc() throws Exception {
    withTextFromSystemIn("11\n14\n")
      .execute(() -> {
        compareContents("samples/binary/sum_proc.tam", "samples/output/sum_proc.out");
      });
  }

  public void testEmptycommandeot() throws Exception {
    withTextFromSystemIn("")
      .execute(() -> {
      compareContents("samples/binary/emptycommandeot.tam", "samples/output/emptycommandeot.out");
      });
  }

  public void testIteratively() throws Exception {
    withTextFromSystemIn("1\n2\n11\n22\n9\n0\n78\n23\n45\n233\n")
      .execute(() -> {
        compareContents("samples/binary/iteratively.tam", "samples/output/iteratively.out");
      });
  }

  public void testPower() throws Exception {
    withTextFromSystemIn("11\n3\n")
      .execute(() -> {
        compareContents("samples/binary/power.tam", "samples/output/power.out");
      });
  }

  public void testEmptyCommandSemicolon() throws Exception {
    withTextFromSystemIn("")
      .execute(() -> {
        compareContents("samples/binary/emptycommandsemicolon.tam", "samples/output/emptycommandsemicolon.out");
      });
  }

  public void testLeapYear() throws Exception {
    withTextFromSystemIn("2020\n11\n01\n")
      .execute(() -> {
        compareContents("samples/binary/leapyear.tam", "samples/output/leapyear.out");
      });
  }

  public void testPrintArray() {
    compareContents("samples/binary/print_array.tam", "samples/output/print_array.out");
  }

  public void testFactorial() throws Exception {
    withTextFromSystemIn("5\n")
      .execute(() -> {
        compareContents("samples/binary/factorial.tam", "samples/output/factorial.out");
      });
  }

  public void testLine() throws Exception {
    withTextFromSystemIn("hello\n")
      .execute(() -> {
        compareContents("samples/binary/line.tam", "samples/output/line.out");
      });
  }

  public void testRationals() throws Exception { 
    withTextFromSystemIn("2\n3\n3\n4\n")
      .execute(() -> {
        compareContents("samples/binary/rationals.tam", "samples/output/rationals.out");
      });
  }
}
