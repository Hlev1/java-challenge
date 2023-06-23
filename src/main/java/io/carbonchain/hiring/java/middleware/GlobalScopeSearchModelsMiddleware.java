package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;

public class GlobalScopeSearchModelsMiddleware implements Middleware {

    @Override
    public Request handle(Request request) {
        var params = request.getParams();
        if (params.length > 1) {
            return request;
        }

        return new GlobalScopeSearchRequest(params[0]);
    }
}
