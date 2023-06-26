package io.carbonchain.hiring.java.controller;

import io.carbonchain.hiring.java.exception.ApplicationException;
import io.carbonchain.hiring.java.exception.RequestHandlerNotFoundException;
import io.carbonchain.hiring.java.handler.RequestHandler;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class ModelsController implements Controller {

  private final RequestHandler[] requestHandlers;

  public ModelsController(RequestHandler[] requestHandlers) {
    this.requestHandlers = requestHandlers;
  }

  @Override
  public String handle(Request request) throws ApplicationException {
    try {
      var handler = Arrays.stream(requestHandlers)
              .filter(it -> it.canHandle(request))
              .findFirst()
              .get();

      return handler.handle(request);

    } catch (NoSuchElementException e) {
      throw new RequestHandlerNotFoundException(request);
    }
  }

  private Double averageModels(List<Model> models) {
    Double emissionIntensity = models.stream().map(Model::getEmissionIntensity).reduce(0.0, Double::sum);
    return emissionIntensity / models.size();
  }
}
