package ch4;

import java.util.*;

// ====================================== creator classes ======================================
abstract class PizzaStore {
  public Pizza orderPizza(String type) {
    Pizza pizza;

    // Decoupling the implementation of the product from its use
    pizza = createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();

    return pizza;
  }

  protected abstract Pizza createPizza(String type);
}

class NYPizzaStore extends PizzaStore {
  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new NYStyleCheesePizza();
    } else if (type.equals("pepperoni")) {
      pizza = new NYStylePepperoniPizza();
    } else if (type.equals("veggie")) {
      pizza = new NYStyleVeggiePizza();
    }

    return pizza;
  }
}

class ChicagoPizzaStore extends PizzaStore {
  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new ChicagoStyleCheesePizza();
    } else if (type.equals("pepperoni")) {
      pizza = new ChicagoStylePepperoniPizza();
    } else if (type.equals("veggie")) {
      pizza = new ChicagoStyleVeggiePizza();
    }

    return pizza;
  }
}

class CaliforniaPizzaStore extends PizzaStore {
  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new CaliforniaStyleCheesePizza();
    } else if (type.equals("pepperoni")) {
      pizza = new CaliforniaStylePepperoniPizza();
    } else if (type.equals("veggie")) {
      pizza = new CaliforniaStyleVeggiePizza();
    }

    return pizza;
  }
}

// ====================================== product classes ======================================
abstract class Pizza {
  String name;
  String dough;
  String sauce;
  ArrayList toppings = new ArrayList();

  public void prepare() {
    System.out.println("Preparing " + name);
    System.out.println("Tossing dough...");
    System.out.println("Adding sauce...");
    System.out.println("Adding topping...");
    for (int i = 0; i < toppings.size(); i++) {
      System.out.println("  " + toppings.get(i));
    }
  }

  public void bake() {
    System.out.println("Baking pizza....");
  }

  public void cut() {
    System.out.println("Cutting pizza....");
  }

  public void box() {
    System.out.println("Boxing pizza....");
  }

  public String getName() {
    return name;
  }
}

class NYStyleCheesePizza extends Pizza {
  public NYStyleCheesePizza() {
    name = "NY style sauce and Cheese Pizza";
    dough = "Thin Crust Dough";
    sauce = "Marinara Sauce";

    toppings.add("Grated Reggiano Cheese");
  }
}
class NYStyleVeggiePizza extends Pizza {}
class NYStylePepperoniPizza extends Pizza {}

class ChicagoStyleCheesePizza extends Pizza {
  public ChicagoStyleCheesePizza() {
    name = "Chicago style Deep Dish Cheese Pizza";
    dough = "Extra Thick Crust Dough";
    sauce = "Plum Tomato Sauce";

    toppings.add("Shredded Mozzarella Cheese");
  }
}
class ChicagoStyleVeggiePizza extends Pizza {}
class ChicagoStylePepperoniPizza extends Pizza {}

class CaliforniaStyleCheesePizza extends Pizza {}
class CaliforniaStyleVeggiePizza extends Pizza {}
class CaliforniaStylePepperoniPizza extends Pizza {}

public class PizzaStoreV1 {
  public static void main(String[] args) {
    PizzaStore nyStore = new NYPizzaStore();
    PizzaStore chicagoStore = new ChicagoPizzaStore();

    Pizza pizza = nyStore.orderPizza("cheese");
    System.out.println("Ethan ordered a " + pizza.getName() + "\n");

    pizza = chicagoStore.orderPizza("cheese");
    System.out.println("Joel ordered a " + pizza.getName() + "\n");
  }
}
