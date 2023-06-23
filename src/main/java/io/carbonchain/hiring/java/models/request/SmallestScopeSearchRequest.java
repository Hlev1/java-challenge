package io.carbonchain.hiring.java.models.request;

public class SmallestScopeSearchRequest extends GlobalScopeSearchRequest {

    private final String asset;

    public SmallestScopeSearchRequest(String commodity, String asset) {
        super(commodity);

        this.asset = asset;
    }

    public String getAsset() { return asset; }
}
