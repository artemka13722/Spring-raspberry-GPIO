package com.raspberry.raspberry.DHT11;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DhtData {
  private double temperature;
  private double humidity;

  @Override
  public String toString() {
    return "Temperature: " + temperature + " C\nHumidity: " + humidity + "%";
  }
}
