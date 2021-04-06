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

  public void xtestFreq() {
    compareContents("samples/decompiled/freq.decompiled", "samples/binary/freq.tam");
  }

  public void xtestMonthsOfYear() {
    compareContents("samples/decompiled/monthsofyear.decompiled", "samples/binary/monthsofyear.tam");
  }

  public void xtestRecord() {
    compareContents("samples/decompiled/record.decompiled", "samples/binary/record.tam");
  }

  public void xtestDate() {
    compareContents("samples/decompiled/date.decompiled", "samples/binary/date.tam");
  }

  public void xtestHello() {
    compareContents("samples/decompiled/hello.decompiled", "samples/binary/hello.tam");
  }

  public void xtestNestedArrays() {
    compareContents("samples/decompiled/nestedarrays.decompiled", "samples/binary/nestedarrays.tam");
  }

  public void xtestReverseLine() {
    compareContents("samples/decompiled/reverse_line.decompiled", "samples/binary/reverse_line.tam");
  }

  public void xtestDates() {
    compareContents("samples/decompiled/dates.decompiled", "samples/binary/dates.tam");
  }

  public void testInc() throws Exception {
    withTextFromSystemIn("99")
      .execute(() -> {
        compareContents("samples/binary/inc.tam", "samples/output/inc.out");
      });
  }

  public void xtestNestedRecords() {
    compareContents("samples/decompiled/nestedrecords.decompiled", "samples/binary/nestedrecords.tam");
  }

  public void xtestString() {
    compareContents("samples/decompiled/string.decompiled", "samples/binary/string.tam");
  }

  public void xtestEcho() {
    compareContents("samples/decompiled/echo.decompiled", "samples/binary/echo.tam");
  }

  public void xtestInsertionSort() {
    compareContents("samples/decompiled/insertion_sort.decompiled", "samples/binary/insertion_sort.tam");
  }

  public void xtestOdd() {
    compareContents("samples/decompiled/odd.decompiled", "samples/binary/odd.tam");
  }

  public void xtestSumProc() {
    compareContents("samples/decompiled/sum_proc.decompiled", "samples/binary/sum_proc.tam");
  }

  public void testEmptycommandeot() throws Exception {
    withTextFromSystemIn("")
      .execute(() -> {
      compareContents("samples/binary/emptycommandeot.tam", "samples/output/emptycommandeot.out");
      });
  }

  public void xtestIteratively() {
    compareContents("samples/decompiled/iteratively.decompiled", "samples/binary/iteratively.tam");
  }

  public void xtestPower() {
    compareContents("samples/decompiled/power.decompiled", "samples/binary/power.tam");
  }

  public void testEmptyCommandSemicolon() throws Exception {
    withTextFromSystemIn("")
      .execute(() -> {
        compareContents("samples/binary/emptycommandsemicolon.tam", "samples/output/emptycommandsemicolon.out");
      });
  }

  public void xtestLeapYear() {
    compareContents("samples/decompiled/leapyear.decompiled", "samples/binary/leapyear.tam");
  }

  public void xtestPrintArray() {
    compareContents("samples/decompiled/print_array.decompiled", "samples/binary/print_array.tam");
  }

  public void xtestFactorial() {
    compareContents("samples/decompiled/factorial.decompiled", "samples/binary/factorial.tam");
  }

  public void testLine() throws Exception {
    withTextFromSystemIn("hello\n")
      .execute(() -> {
        compareContents("samples/binary/line.tam", "samples/output/line.out");
      });
  }

  public void xtestRationals() {
    compareContents("samples/decompiled/rationals.decompiled", "samples/binary/rationals.tam");
  }
}
