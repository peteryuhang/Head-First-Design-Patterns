package ch4;


class PizzaStore {
  SimplePizzaFactory factory;

  public PizzaStore(SimplePizzaFactory factory) {
    this.factory = factory;
  }

  public Pizza orderPizza(String type) {
    Pizza pizza;

    pizza = factory.createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();

    return pizza;
  }
}

class SimplePizzaFactory {
  public Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
      pizza = new CheesePizza();
    } else if (type.equals("pepperoni")) {
      pizza = new PepperoniPizza();
    } else if (type.equals("veggie")) {
      pizza = new VeggiePizza();
    }

    return pizza;
  }
}

class Pizza {
  public void prepare() {
    System.out.println("Preparing pizza....");
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
}

class CheesePizza extends Pizza {}
class VeggiePizza extends Pizza {}
class PepperoniPizza extends Pizza {}
