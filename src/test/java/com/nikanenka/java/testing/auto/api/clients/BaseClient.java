package com.nikanenka.java.testing.auto.api.clients;

import com.nikanenka.java.testing.auto.annotations.LazyAutowired;
import com.nikanenka.java.testing.auto.utils.ApiProperties;
import com.nikanenka.java.testing.auto.utils.TokenSpecManager;

public abstract class BaseClient {

    @LazyAutowired
    protected TokenSpecManager tokenSpecManager;

    @LazyAutowired
    protected ApiProperties apiProperties;
}
