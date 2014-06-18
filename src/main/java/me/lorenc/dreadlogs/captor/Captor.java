package me.lorenc.dreadlogs.captor;

import java.util.List;

public interface Captor<LOG> {

    List<LOG> getCapturedLogs();

}
