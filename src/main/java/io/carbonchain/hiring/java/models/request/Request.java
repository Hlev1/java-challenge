package io.carbonchain.hiring.java.models.request;

public class Request {

  private String[] params;

  public Request(String[] params) {
    this.params = params;
  }

  public String[] getParams() {
    return params;
  }
}