package io.carbonchain.hiring.java.domain;

import java.util.Arrays;
import java.util.Objects;

public class ModelRepository {

  private final Model[] models;

  public ModelRepository(Model[] models) {
    this.models = models;
  }

  public Model findGlobalByCommodity(String commodity) {
    for (Model model : models) {
      if (model.isGlobalForCommodity(commodity)) {
        return model;
      }
    }
    return null;
  }

  public Model[] findAllByCommodity(String commodity) {
    return Arrays.stream(models)
            .filter(model -> Objects.equals(model.getCommodity(), commodity))
            .toArray(size -> Arrays.copyOf(models, size));
  }
}
