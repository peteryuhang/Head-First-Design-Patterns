
package ch5;


public class ChocolateBoilerOptimized1 {
  private boolean empty;
  private boolean boiled;
  private static ChocolateBoilerOptimized1 instance = null;

  private ChocolateBoilerOptimized1() {
    empty = true;
    boiled = false;
  }

  // Adding synchronized to force every thread to wait its turn before it can enter the method
  public static synchronized ChocolateBoilerOptimized1 getInstance() {
    // lazy instantiation
    if (instance == null) {
      instance = new ChocolateBoilerOptimized1();
    }

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
