package ch8;

abstract class CaffeineBeverage {
  final public void prepareRecipe() {
    boilWater();
    brew();
    pourInCup();
    addCondiments();
  }

  public void boilWater() {
    System.out.println("Boiling water");
  }

  public void pourInCup() {
    System.out.println("Pouring into cup");
  }

  abstract void brew();

  abstract void addCondiments();
}

class Tea extends CaffeineBeverage {
  public void brew() {
    System.out.println("Steeping the tea");
  }

  public void addCondiments() {
    System.out.println("Adding Lemon");
  }
}

class Coffee extends CaffeineBeverage {
  public void brew() {
    System.out.println("Dripping Coffee through filter");
  }

  public void addCondiments() {
    System.out.println("Adding Sugar and Milk");
  }
}

class TestDrive {
  public static void main(String[] args) {
    Tea tea = new Tea();
    tea.prepareRecipe();

    Coffee coffee = new Coffee();
    coffee.prepareRecipe();
  }
}
