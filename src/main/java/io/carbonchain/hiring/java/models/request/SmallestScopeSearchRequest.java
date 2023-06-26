package io.carbonchain.hiring.java.models.request;

public class SmallestScopeSearchRequest extends SearchRequest {

    private final String asset;

    public SmallestScopeSearchRequest(String commodity, String asset) {
        super(commodity);

        this.asset = asset;
    }

    public String getAsset() { return asset; }

    public GlobalScopeSearchRequest toGlobalScopeSearchRequest() {
        return new GlobalScopeSearchRequest(getCommodity());
    }
}
