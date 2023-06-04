package ch5;


public class ChocolateBoilerOptimized2 {
  private boolean empty;
  private boolean boiled;

  // Move to an eagerly created instance to avoid multithreading problem
  private static ChocolateBoilerOptimized2 instance = new ChocolateBoilerOptimized2();

  private ChocolateBoilerOptimized2() {
    empty = true;
    boiled = false;
  }

  public static synchronized ChocolateBoilerOptimized2 getInstance() {
    return instance;
  }

  public void fill() {
    if (isEmpty()) {
      empty = false;
      boiled = false;
    }
  }

  public void drain() {
    if (!isEmpty() && isBoiled()) {
      empty = true;
    }
  }

  public void boil() {
    if (!isEmpty() && !isBoiled()) {
      boiled = true;
    }
  }

  public boolean isEmpty() {
    return empty;
  }

  public boolean isBoiled() {
    return boiled;
  }
}
