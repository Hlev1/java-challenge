package io.carbonchain.hiring.java.models.request;

public class SearchRequest extends Request {

    private final String commodity;

    public SearchRequest(String commodity) {
        super(new String[]{commodity});

        this.commodity = commodity;
    }

    public String getCommodity() {
        return commodity;
    }
}
