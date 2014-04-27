package com.naked.logs.captor;

import java.util.List;

public interface Captor<LOG> {

    List<LOG> getCapturedLogs();

}
