package io.carbonchain.hiring.java.domain;

public class Asset {

  private final String name;
  private final String country;
  private final String continent;

  public Asset(String name, String country, String continent) {
    this.name = name;
    this.country = country;
    this.continent = continent;
  }

  public boolean nameMatches(String term) {
    return name.toLowerCase().equals(term.toLowerCase());
  }

  public String getName() { return name; }

  public String getCountry() { return country; }

  public String getContinent() { return continent; }
}
