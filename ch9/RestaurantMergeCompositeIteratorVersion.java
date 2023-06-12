package ch9;

import java.util.*;

// ================================ Iterator ================================
class CompositeIterator implements java.util.Iterator {
  Stack stack = new Stack();

  public CompositeIterator(java.util.Iterator iterator) {
    stack.push(iterator);
  }

  // return next leaf node composite instead of every composite
  public Object next() {
    if (hasNext()) {
      java.util.Iterator iterator = (java.util.Iterator)stack.peek();
      MenuComponent menuComponent = (MenuComponent)iterator.next();

      if (menuComponent instanceof Menu) {
        stack.push(menuComponent.createIterator());
        /* 
         * Add this to print submenu item(s) only once
         * 
         * stack.push(menuComponent.createIterator()) will add the nested menu's iterator into multiple
         * stack along the iteration, early termination can resolve this problem
         */
        return next();
      }

      return menuComponent;
    }
    
    return null;
  }

  public boolean hasNext() {
    if (stack.empty()) {
      return false;
    } else {
      java.util.Iterator iterator = (java.util.Iterator)stack.peek();
      if (!iterator.hasNext()) {
        stack.pop();
        return hasNext();
      }
      return true;
    }
  }

  public void remove() {
    throw new UnsupportedOperationException();
  }
}

class NullIterator implements java.util.Iterator {
  public Object next() {
      return null;
  }

  public boolean hasNext() {
    return false;
  }
}

// ================================ Menu ================================
abstract class MenuComponent {
  public void add(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public void remove(MenuComponent menuComponent) {
    throw new UnsupportedOperationException();
  }

  public MenuComponent getChild(int i) {
    throw new UnsupportedOperationException();
  }

  public String getName() {
    throw new UnsupportedOperationException();
  }

  public String getDescription() {
    throw new UnsupportedOperationException();
  }

  public double getPrice() {
    throw new UnsupportedOperationException();
  }

  public boolean isVegetarian() {
    throw new UnsupportedOperationException();
  }

  public abstract java.util.Iterator createIterator();

  public void print() {
    throw new UnsupportedOperationException();
  }
}

class MenuItem extends MenuComponent {
  String name;
  String description;
  boolean vegetarian;
  double price;

  public MenuItem(String name,
                  String description,
                  boolean vegetarian,
                  double price) {
    this.name = name;
    this.description = description;
    this.vegetarian = vegetarian;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public double getPrice() {
    return price;
  }

  public boolean isVegetarian() {
    return vegetarian;
  }

  public java.util.Iterator createIterator() {
    return new NullIterator();
  }

  public void print() {
    System.out.print("  " + getName());
    if (isVegetarian()) {
      System.out.print("(v)");
    }
    System.out.println(", " + getPrice());
    System.out.println("    -- " + getDescription());
  }
}

class Menu extends MenuComponent {
  ArrayList menuComponents = new ArrayList();
  String name;
  String description;

  public Menu(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public void add(MenuComponent menuComponent) {
    menuComponents.add(menuComponent);
  }

  public void remove(MenuComponent menuComponent) {
    menuComponents.remove(menuComponent);
  }

  public MenuComponent getChild(int i) {
    return (MenuComponent)menuComponents.get(i);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public java.util.Iterator createIterator() {
    return new CompositeIterator(menuComponents.iterator());
  }

  public void print() {
    System.out.print("\n" + getName());
    System.out.println(", " + getDescription());
    System.out.println("-------------------------------");

    java.util.Iterator iter = menuComponents.iterator();
    while (iter.hasNext()) {
      MenuComponent menuComponent = (MenuComponent)iter.next();
      menuComponent.print();
    }
  }
}

class Waitress {
  MenuComponent allMenus;

  public Waitress(MenuComponent allMenus) {
    this.allMenus = allMenus;
  }

  public void printMenu() {
    allMenus.print();
  }

  public void printVegetarianMenu() {
    java.util.Iterator iterator = allMenus.createIterator();
    System.out.println("\nVEGETARIAN MENU\n----");
    while (iterator.hasNext()) {
      MenuComponent menuComponent = (MenuComponent)iterator.next();
      try {
        if (menuComponent != null && menuComponent.isVegetarian()) {
          menuComponent.print();
        }
      } catch(UnsupportedOperationException e) {}
    }
  }

  // other methods here
}

public class RestaurantMergeCompositeIteratorVersion {
  public static void main(String[] args) {
    MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
    MenuComponent dinerMenu = new Menu("DINER MENU", "Lunch");
    MenuComponent cafeMenu = new Menu("CAFE MENU", "Dinner");
    MenuComponent dessertMenu = new Menu("DESSERT MENU", "Dessert of course");

    MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");

    allMenus.add(pancakeHouseMenu);
    allMenus.add(dinerMenu);
    allMenus.add(cafeMenu);

    dinerMenu.add(
      new MenuItem(
        "Pasta",
        "Spaghetti with Marinara Sauce",
        true,
        3.89)
    );

    dessertMenu.add(
      new MenuItem(
        "Apple Pie",
        "Apple pie with a flakey crust, topped with vanilla icecream",
        true,
        1.59)
    );

    dessertMenu.add(
      new MenuItem(
        "Mongo Pie",
        "Mongo pie with a flakey crust, topped with vanilla icecream",
        true,
        1.69)
    );
    dinerMenu.add(dessertMenu);

    Waitress waitress = new Waitress(allMenus);

    waitress.printVegetarianMenu();
  }
}
