package me.lorenc.dreadlogs.util;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DefaultStringTest {

    @Test
    public void shouldReturnEmptyStringIfNull() throws Exception {
        assertThat(DefaultString.emptyIfNull(null), equalTo(""));
    }

    @Test
    public void shouldReturnStringifyIfNotNull() throws Exception {
        assertThat(DefaultString.emptyIfNull(new Integer(100)), equalTo("100"));
    }

    @Test
    public void shouldReturnEmptyStringIfEmptyMap() throws Exception {
        assertThat(DefaultString.emptyIfEmptyMap(Collections.EMPTY_MAP), equalTo(""));
    }

    @Test
    public void shouldReturnEmptyStringIfNullMap() throws Exception {
        assertThat(DefaultString.emptyIfEmptyMap(null), equalTo(""));
    }

    @Test
    public void shouldStringifyMapInNotEmpty() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");

        assertThat(DefaultString.emptyIfEmptyMap(map), equalTo("{key=value}"));
    }

}
