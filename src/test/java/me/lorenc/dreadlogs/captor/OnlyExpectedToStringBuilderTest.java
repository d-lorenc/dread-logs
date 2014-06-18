package me.lorenc.dreadlogs.captor;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import me.lorenc.dreadlogs.captor.OnlyExpectedToStringBuilder;

import org.junit.Before;
import org.junit.Test;

public class OnlyExpectedToStringBuilderTest {

    private OnlyExpectedToStringBuilder builder;

    @Before
    public void before() throws Exception {
        builder = new OnlyExpectedToStringBuilder();
    }

    @Test
    public void shouldReturnEmptyString() throws Exception {
        String string = builder.toString();

        assertThat(string, equalTo(""));
    }

    @Test
    public void shouldAppendValueWithLabel() throws Exception {
        builder.append("label", "value");

        String string = builder.toString();

        assertThat(string, equalTo("label:[value]"));
    }

    @Test
    public void shouldAppendedNullValue() throws Exception {
        builder.append("label", null);

        String string = builder.toString();

        assertThat(string, equalTo("label:[]"));
    }

    @Test
    public void shouldAppendIfConditionTrue() throws Exception {
        builder.appendIf(true, "label", "value");

        String string = builder.toString();

        assertThat(string, equalTo("label:[value]"));
    }

    @Test
    public void shouldNotAppendIfConditionFalse() throws Exception {
        builder.appendIf(false, "label", "value");

        String string = builder.toString();

        assertThat(string, equalTo(""));
    }

    @Test
    public void shouldAppendClassNameValue() throws Exception {
        builder.appendIf(true, "label", String.class);

        String string = builder.toString();

        assertThat(string, equalTo("label:[java.lang.String]"));
    }

    @Test
    public void shouldAppendNullClass() throws Exception {
        builder.appendIf(true, "label", (Class<?>) null);

        String string = builder.toString();

        assertThat(string, equalTo("label:[]"));
    }

    @Test
    public void shouldAppendEmptyMap() throws Exception {
        builder.appendIf(true, "label", new HashMap<String, Object>());

        String string = builder.toString();

        assertThat(string, equalTo("label:[]"));
    }

    @Test
    public void shouldAppendNonEmptyMap() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("key", "value");
        builder.appendIf(true, "label", map);

        String string = builder.toString();

        assertThat(string, equalTo("label:[{key=value}]"));
    }

}
