package com.nikanenka.java.testing.auto.api.clients;

import com.nikanenka.java.testing.auto.api.utils.LazyAutowired;
import com.nikanenka.java.testing.auto.api.utils.TokenSpecManager;

public abstract class BaseClient {

    @LazyAutowired
    protected TokenSpecManager tokenSpecManager;
}
