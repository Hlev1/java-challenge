package io.carbonchain.hiring.java;

public class Request {

  private final String commodity;
  private final String asset;

  public Request(String commodity, String asset) {
    this.commodity = commodity;
    this.asset = asset;
  }

  public String getCommodity() {
    return commodity;
  }
}
