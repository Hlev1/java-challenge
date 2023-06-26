package io.carbonchain.hiring.java.app;

import io.carbonchain.hiring.java.controller.ModelsController;
import io.carbonchain.hiring.java.controller.Controller;
import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.handler.RequestHandler;
import io.carbonchain.hiring.java.middleware.GlobalScopeSearchModelsMiddleware;
import io.carbonchain.hiring.java.middleware.Middleware;
import io.carbonchain.hiring.java.middleware.SmallestScopeSearchModelsMiddleware;

import java.util.Arrays;
import java.util.HashMap;

public class Application {

  public static void main(String[] args) throws Exception {
    if (args.length < 3) {
      throw new Exception("Incorrect number of parameters");
    }

    AssetRepository assetRepository = Application.prepareAssetRepository();
    ModelRepository modelRepository = Application.prepareModelRepository();

    RequestHandler[] requestHandlers = new RequestHandler[]{};

    HashMap<String, Middleware[]> middlewares = new HashMap<>();
    middlewares.put(
      "search",
      new Middleware[]{
        new GlobalScopeSearchModelsMiddleware(),
        new SmallestScopeSearchModelsMiddleware()
      }
    );

    HashMap<String, Controller> controllers = new HashMap<>();
    controllers.put("models", new ModelsController(requestHandlers));

    Core core = new Core(middlewares, controllers);
    String out = core.run(args[0], args[1], Arrays.copyOfRange(args, 2, args.length));
    System.out.println(out);
  }

  private static AssetRepository prepareAssetRepository() {
    return new AssetRepository(new Asset[]{
        new Asset("Khetri", "India", "Asia"),
        new Asset("Cerro Verde", "Peru", "South America"),
        new Asset("El Abra", "Chile", "South America"),
        new Asset("Red Dog", "USA", "North America"),
        new Asset("Antamina", "Peru", "South America"),
        new Asset("Tara", "Ireland", "Europe"),
    });
  }

  private static ModelRepository prepareModelRepository() {
    return new ModelRepository(new Model[]{
        new Model("Copper", null, 13.4),
        new Model("Copper", "India", 18.223),
        new Model("Copper", "Chile", 9.23),
        new Model("Copper", "South America", 11.12),
        new Model("Zinc", null, 5.33),
        new Model("Zinc", "USA", 3.45),
        new Model("Zinc", "North America", 3.98),
        new Model("Zinc", "South America", 6.13),
    });
  }
}
