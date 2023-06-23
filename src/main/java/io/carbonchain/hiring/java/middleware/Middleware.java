package io.carbonchain.hiring.java.middleware;

import io.carbonchain.hiring.java.Request;

public interface Middleware {
  public Request handle(Request request);
}
