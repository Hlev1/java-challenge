package io.carbonchain.hiring.java.handler;

import io.carbonchain.hiring.java.domain.Asset;
import io.carbonchain.hiring.java.domain.AssetRepository;
import io.carbonchain.hiring.java.domain.Model;
import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SmallestScopeSearchRequest;

import java.util.Arrays;

public class SmallestScopeSearchRequestHandler implements RequestHandler {

    private final GlobalScopeRequestHandler globalScopeRequestHandler;
    private final AssetRepository assetRepository;
    private final ModelRepository modelRepository;

    public SmallestScopeSearchRequestHandler(
            AssetRepository assetRepository,
            ModelRepository modelRepository,
            GlobalScopeRequestHandler globalScopeRequestHandler
    ) {
        this.assetRepository = assetRepository;
        this.modelRepository = modelRepository;
        this.globalScopeRequestHandler = globalScopeRequestHandler;
    }

    @Override
    public boolean canHandle(Request request) {
        return request instanceof SmallestScopeSearchRequest;
    }

    @Override
    public String handle(Request request) {
        var searchRequest = (SmallestScopeSearchRequest) request;

        String commodity = searchRequest.getCommodity();

        Asset asset = assetRepository.findByName(searchRequest.getAsset());

        if (asset == null) {
            return handle(searchRequest.toGlobalScopeSearchRequest());
        }

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


        return handle(searchRequest.toGlobalScopeSearchRequest());
    }

    private String handle(GlobalScopeSearchRequest request) {
        return globalScopeRequestHandler.handle(request);
    }
}
