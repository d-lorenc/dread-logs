package me.lorenc.dreadlogs.captor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.hamcrest.StringDescription;
import org.junit.Test;


public class PatternMatcherTest {

    @Test
    public void shouldReturnTrueIfMatches() throws Exception {
        assertTrue(PatternMatcher.matches("^[a-z]* value$").matches("matching value"));
    }

    @Test
    public void shouldReturnFalseIfNoMatch() throws Exception {
        assertFalse(PatternMatcher.matches("^[abc]*$").matches("no match"));
    }

    @Test
    public void shouldDescribeMismatch() throws Exception {
        StringDescription description = new StringDescription();

        PatternMatcher.matches("^[abc]*$").describeMismatch("no match", description);

        assertThat(description.toString(), equalTo("was \"no match\""));
    }

    @Test
    public void shouldAppendDescription() throws Exception {
        StringDescription description = new StringDescription();

        PatternMatcher.matches("^[abc]*$").describeTo(description);

        assertThat(description.toString(), equalTo("a string with pattern \"^[abc]*$\""));
    }

}
