package io.carbonchain.hiring.java.controller;

import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.exception.ApplicationException;
import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;

import java.util.Arrays;
import java.util.List;

public class ModelsController implements Controller {

  private final AssetRepository assetRepository;
  private final ModelRepository modelRepository;

  public ModelsController(AssetRepository assetRepository, ModelRepository modelRepository) {
    this.assetRepository = assetRepository;
    this.modelRepository = modelRepository;
  }

  @Override
  public String handle(Request request) throws ApplicationException {

    if (request instanceof SmallestScopeSearchRequest) {
      return handle((SmallestScopeSearchRequest) request);
    }

    if (request instanceof GlobalScopeSearchRequest) {
      return handle((GlobalScopeSearchRequest) request);
    }

    throw new ApplicationException("cannot process request");
  }

  private String handle(GlobalScopeSearchRequest request) throws ApplicationException {
    String commodity = request.getCommodity();

    Double emissionIntensity = modelRepository.findGlobalByCommodity(commodity).getEmissionIntensity();
    return "Global emission intensity for " + commodity + " is " + emissionIntensity;
  }

  private String handle(SmallestScopeSearchRequest request) throws ApplicationException {
    String commodity = request.getCommodity();

    Asset asset = assetRepository.findByName(request.getAsset());
    Model[] models = modelRepository.findAllByCommodity(commodity);

    for (String scope : new String[] {asset.getName(), asset.getCountry(), asset.getContinent()}) {
      var model = Arrays.stream(models)
              .filter(m -> m.getScope() == scope)
              .findFirst();

      if (model.isEmpty()) {
        continue;
      }

      return scope + " emission intensity for " + commodity + " is " + model.get().getEmissionIntensity();
    }

    return handle(request.toGlobalScopeSearchRequest());
  }

  private Double averageModels(List<Model> models) {
    Double emissionIntensity = models.stream().map(Model::getEmissionIntensity).reduce(0.0, Double::sum);
    return emissionIntensity / models.size();
  }
}
