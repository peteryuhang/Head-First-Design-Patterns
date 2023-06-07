- **The Adapter Pattern**
  - Converts the interface of a class into another interface the clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces
  - There are two forms of the Adapter Pattern: object and class adapters. Class adapters require multiple inheritance
- **The Facade Pattern**
  - Provides a unified interface to a set of interface in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use
  - Implementing a facade requires that we compose the facade with its subsystem and use delegation to perform the work of the facade
- One of the most important things to remember about a pattern is its **intent**
- **Design Principle**
  - **Least Knowledge**: Talk only to your immediate friends
    ```java
    // not follow the principle
    public float getTemp() {
      Thermometer thermometer = station.getThermometer();
      return thermometer.getTemperature();
    }

    // following the principle, this reduce the number of class we're dependent on
    public float getTemp() {
      return station.getTemperature();
    }
    ```
- An **adapter** wraps an object to change its interface, a **decorator** wraps an object to add new behaviors and responsibilities, and a **facade** wraps a set of objects to simplify