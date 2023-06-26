package io.carbonchain.hiring.java.exception;

import io.carbonchain.hiring.java.models.request.Request;

public class RequestHandlerNotFoundException extends ApplicationException {

    public RequestHandlerNotFoundException(Request request) {
        super("Could not find a suitable RequestHandler to handle the request: " + request.getClass().getName());
    }
}
