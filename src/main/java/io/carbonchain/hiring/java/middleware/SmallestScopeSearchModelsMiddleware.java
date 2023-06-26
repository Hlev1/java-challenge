package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;

public class SmallestScopeSearchModelsMiddleware implements Middleware {

    @Override
    public Request handle(Request request) {
        var params = request.getParams();
        if (params.length == 2) {
            return new SmallestScopeSearchRequest(params[0], params[1]);
        }

        return request;
    }
}
