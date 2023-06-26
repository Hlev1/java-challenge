package io.carbonchain.hiring.java.app;

import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.controller.Controller;
import io.carbonchain.hiring.java.exception.ApplicationException;
import io.carbonchain.hiring.java.middleware.Middleware;

import java.lang.reflect.InvocationTargetException;
import io.carbonchain.hiring.java.exception.TooManyParamsException;
import java.util.HashMap;

public class Core {

  private final HashMap<String, Middleware[]> middlewares;
  private final HashMap<String, Controller> controllers;

  public Core(HashMap<String, Middleware[]> middlewares, HashMap<String, Controller> controllers) {
    this.middlewares = middlewares;
    this.controllers = controllers;
  }

  public String run(String path, String endpoint, String[] params) {
    try {
      Request request = applyMiddleware(endpoint, new Request(params));
      return dispatchController(path, request);
    } catch (ApplicationException e) {
      return "Problem when processing request: " + e.getMessage();
    }
  }

  private Request applyMiddleware(String endpoint, Request request) {
    Middleware[] endpointMiddlewares = middlewares.get(endpoint);
    if (endpointMiddlewares == null) {
      return request;
    }
    for (Middleware middleware : endpointMiddlewares) {
      request = middleware.handle(request);
    }
    return request;
  }

  private String dispatchController(
      String path,
      Request request
  ) throws ApplicationException {
    Controller controller = controllers.get(path);
    if (controller == null) {
      throw new ApplicationException("Path " + path + " has no matching controller");
    }
    return controller.handle(request);
  }
}
