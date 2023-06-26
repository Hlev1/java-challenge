package io.carbonchain.hiring.java.handler;

import io.carbonchain.hiring.java.models.request.Request;

public interface RequestHandler {
    boolean canHandle(Request request);
    String handle(Request request);
}
