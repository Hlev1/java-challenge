package io.carbonchain.hiring.java.handler;

import io.carbonchain.hiring.java.domain.ModelRepository;
import io.carbonchain.hiring.java.models.request.GlobalScopeSearchRequest;
import io.carbonchain.hiring.java.models.request.Request;
import io.carbonchain.hiring.java.models.request.SearchRequest;

public class GlobalScopeRequestHandler implements RequestHandler {

    private final ModelRepository modelRepository;

    public GlobalScopeRequestHandler(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public boolean canHandle(Request request) {
        return request instanceof GlobalScopeSearchRequest;
    }

    @Override
    public String handle(Request request) {
        String commodity = ((SearchRequest) request).getCommodity();

        Double emissionIntensity = modelRepository.findGlobalByCommodity(commodity).getEmissionIntensity();
        return "Global emission intensity for " + commodity + " is " + emissionIntensity;
    }
}
