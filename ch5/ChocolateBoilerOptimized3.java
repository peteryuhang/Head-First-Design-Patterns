package ch5;


public class ChocolateBoilerOptimized3 {
  private boolean empty;
  private boolean boiled;

  /* 
   * The volatile keyword ensures that multiple threads handle the uniqueinstance
   * variable correctly when it is being initialized to the singleton instance
   * 
   * volatile only available JDK 1.5 or after
   */
  private volatile static ChocolateBoilerOptimized3 instance;

  private ChocolateBoilerOptimized3() {
    empty = true;
    boiled = false;
  }

  public static ChocolateBoilerOptimized3 getInstance() {
    if (instance == null) {
      synchronized(ChocolateBoilerOptimized3.class) {
        if (instance == null) {
          instance = new ChocolateBoilerOptimized3();
        }
      }
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
