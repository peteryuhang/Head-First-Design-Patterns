package ch2;

import java.util.*;


class WeaterStationProject {
  public static void main(String[] args) {
    WeatherData weatherData = new WeatherData();

    CurrentConditionObserver currentDisplay = new CurrentConditionObserver(weatherData);
    StatisticsObserver statisticsDisplay = new StatisticsObserver(weatherData);
    ForecastObserver ForecastDisplay = new ForecastObserver(weatherData);

    weatherData.notifyObserver();
  }
}

interface Subject {
  public void registerObserver(Observer ob);
  public void removeObserver(Observer ob);
  public void notifyObserver();
}

interface Observer {
  public void update(float temp, float humidity, float pressure);
}

interface DisplayElement {
  public void display();
}

class WeatherData implements Subject {
  private Set<Observer> observers = new HashSet<>();

  public void registerObserver(Observer ob) {
    observers.add(ob);
  }

  public void removeObserver(Observer ob) {
    observers.remove(ob);
  }

  public float getTemperature() {
    // ...
    return 0.0F;
  }

  public float getHumidity() {
    // ...
    return 0.0F;
  }

  public float getPressure() {
    // ...
    return 0.0F;
  }

  // measurementsChanged
  public void notifyObserver() {
    float temp = getTemperature();
    float humidity = getHumidity();
    float pressure = getPressure();

    for (Observer ob : observers) {
      ob.update(temp, humidity, pressure);
    }
  }
}

class CurrentConditionObserver implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private float pressure;

  /* 
   * In the future we may want to un-register ourselves as an observer
   * and it would be handy to already have a reference to the subject
   */
  private Subject weatherData;

  CurrentConditionObserver(Subject weatherData) {
    this.weatherData = weatherData;
    this.weatherData.registerObserver(this);
  }

  public void update(float temp, float humidity, float pressure) {
    this.temperature = temp;
    this.humidity = humidity;
    this.pressure = pressure;
    display();
  }

  public void display() {
    System.out.println(
      "Current conditions: " + temperature + "F degrees and "
                             + humidity + "% humidity and "
                             + pressure + " pressure"
    );
  }
}

class StatisticsObserver implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private float pressure;
  private Subject weatherData;

  StatisticsObserver(Subject weatherData) {
    this.weatherData = weatherData;
    this.weatherData.registerObserver(this);
  }

  public void update(float temp, float humidity, float pressure) {
    this.temperature = temp;
    this.humidity = humidity;
    this.pressure = pressure;
    display();
  }

  public void display() {
    System.out.println(
      "Statistics: " + temperature + "F degrees and "
                     + humidity + "% humidity and "
                     + pressure + " pressure"
    );
  }
}

class ForecastObserver implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private float pressure;
  private Subject weatherData;

  ForecastObserver(Subject weatherData) {
    this.weatherData = weatherData;
    this.weatherData.registerObserver(this);
  }

  public void update(float temp, float humidity, float pressure) {
    this.temperature = temp;
    this.humidity = humidity;
    this.pressure = pressure;
    display();
  }

  public void display() {
    System.out.println(
      "Forecast: " + temperature + "F degrees and "
                   + humidity + "% humidity and "
                   + pressure + " pressure"
    );
  }
}

