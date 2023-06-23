package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.Request;

public class SmallestScopeSearchModelsMiddleware implements Middleware {

    @Override
    public Request handle(Request request) {
        return request;
    }
}
