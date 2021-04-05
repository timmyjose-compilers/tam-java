package com.z0ltan.tam.matchers;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.Description;

public class ContentMatcher extends TypeSafeMatcher<String> {
  private String expected;

  public ContentMatcher(final String expected)  {
    this.expected = expected;
  }

  @Override
  public boolean matchesSafely(String actual) {
    return compress(this.expected).equals(compress(actual));
  }

  public void describeTo(final Description description) {
    description.appendText("is equal to " + this.expected);
  }

  public static Matcher contentIsSame (final String expected) {
    return new ContentMatcher(expected);
  }

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
}
