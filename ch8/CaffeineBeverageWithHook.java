package ch8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

abstract class CaffeineBeverageWithHook {
  final public void prepareRecipe() {
    boilWater();
    brew();
    pourInCup();
    if (customerWantsCondiments()) {
      addCondiments();
    }
  }

  public void boilWater() {
    System.out.println("Boiling water");
  }

  public void pourInCup() {
    System.out.println("Pouring into cup");
  }

  abstract void brew();

  abstract void addCondiments();

  // hook which subclass can override this method, but doesn't have to
  public boolean customerWantsCondiments() {
    return true;
  }
}

class TeaWithHook extends CaffeineBeverageWithHook {
  public void brew() {
    System.out.println("Steeping the tea");
  }

  public void addCondiments() {
    System.out.println("Adding Lemon");
  }
}

class CoffeeWithHook extends CaffeineBeverageWithHook {
  public void brew() {
    System.out.println("Dripping Coffee through filter");
  }

  public void addCondiments() {
    System.out.println("Adding Sugar and Milk");
  }

  // implement & customize the hook
  public boolean customerWantsCondiments() {
    String answer = getUserInput();

    if (answer.toLowerCase().startsWith("y")) {
      return true;
    } else {
      return false;
    }
  }

  public String getUserInput() {
    String answer = null;

    System.out.println("Would you like milk and sugar with your coffee (y/n)? ");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    try {
      answer = in.readLine();
    } catch (IOException ioe) {
      System.err.println("IO error trying to read your answer");
    }

    if (answer == null) {
      return "no";
    }

    return answer;
  }
}

class TestDrive {
  public static void main(String[] args) {
    TeaWithHook teaHook = new TeaWithHook();
    CoffeeWithHook coffeeHook = new CoffeeWithHook();

    System.out.println("\nMaking tea...");
    teaHook.prepareRecipe();

    System.out.println("\nMaking coffee...");
    coffeeHook.prepareRecipe();
  }
}
