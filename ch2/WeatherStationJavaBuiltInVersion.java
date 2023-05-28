package ch2;

import java.util.Observable;
import java.util.Observer;

class WeatherStationJavaBuiltInVersion {
  
}

class WeatherData extends Observable {
  private float temperature;
  private float humidity;
  private float pressure;

  WeatherData() {}

  public void measurementsChanged() {
    /* 
     * The setChanged() is protected. This means you can't even create an instance of the Observable
     * class and compose it with you own object, you have to subclass
     */
    setChanged();
    // consider about pull method
    notifyObservers();
  }

  public void setMeasurements(float temperature, float humidity, float pressure) {
    this.temperature = temperature;
    this.humidity = humidity;
    this.pressure = pressure;
    measurementsChanged();
  }

  public float getTemperature() {
    // ...
    return temperature;
  }

  public float getHumidity() {
    // ...
    return humidity;
  }

  public float getPressure() {
    // ...
    return pressure;
  }
}

interface DisplayElement {
  public void display();
}

class CurrentConditionDisplay implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private float pressure;

  CurrentConditionObserver(Observable observable) {
    observable.addObserver(this);
  }

  public void update(Observable ob, Object arg) {
    if (ob instanceof WeatherData) {
      WeatherData weatherData = (WeatherData)ob;
      this.temperature = weatherData.getTemperature();
      this.humidity = weatherData.getHumidity();
      this.pressure = weatherData.getPressure();
      display();
    }
  }

  public void display() {
    System.out.println(
      "Current conditions: " + temperature + "F degrees and "
                             + humidity + "% humidity and "
                             + pressure + " pressure"
    );
  }
}

class ForecastObserver implements Observer, DisplayElement {
  private float currentPressure = 29.92f;
  private float lastPressure;

  ForecastObserver(Observable observable) {
    observable.addObserver(this);
  }

  public void update(Observable ob, Object arg) {
    if (ob instanceof WeatherData) {
      WeatherData weatherData = (WeatherData)ob;
      lastPressure = currentPressure;
      currentPressure = weatherData.getPressure();
      display();
    }
  }

  public void display() {
    // display code here
  }
}
