package com.naked.logs.matcher;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class PatternMatcher extends TypeSafeMatcher<String> {

    @Factory
    public static Matcher<String> matches(String regex) {
        return new PatternMatcher(Pattern.compile(regex));
    }

    private final Pattern pattern;

    public PatternMatcher(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean matchesSafely(String item) {
        return pattern.matcher(item).matches();
    }

    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(String.valueOf(item)).appendText("\"");
    }

    public void describeTo(Description description) {
        description.appendText("a string with pattern \"").appendText(String.valueOf(pattern)).appendText("\"");
    }

}
