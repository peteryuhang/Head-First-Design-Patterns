package ch9;

import java.util.*;

// ================================ Iterator ================================
class DinerMenuIterator implements java.util.Iterator {
  MenuItem[] items;
  int position = 0;

  public DinerMenuIterator(MenuItem[] items) {
    this.items = items;
  }

  public Object next() {
    MenuItem menuItem = items[position];
    position = position + 1;
    return menuItem;
  }

  public boolean hasNext() {
    if (position >= items.length || items[position] == null) {
      return false;
    } else {
      return true;
    }
  }

  public void remove() {
    if (position <= 0) {
      throw new IllegalStateException(
        "You can't remove an item until you've done at least one next()"
      );
    }

    if (items[position-1] != null) {
      for (int i = position-1; i < (items.length-1); i++) {
        items[i] = items[i+1];
      }
      items[items.length-1] = null;
    }
  }
}


// ================================ Menu ================================
interface Menu {
  public java.util.Iterator createIterator();
}

class PancakeHouseMenu implements Menu {
  ArrayList menuItems;

  public PancakeHouseMenu() {
    menuItems = new ArrayList();

    addItem(
      "K&B's Pancake Breakfast",
      "Pancake with scrambled eggs, and toast",
      true,
      2.99
    );

    addItem(
      "Regular Pancake Breakfast",
      "Pancake with fried eggs, sausage",
      false,
      2.99
    );

    addItem(
      "Blueberry Pancake",
      "Pancake made with fresh blueberries",
      true,
      3.49
    );

    addItem(
      "Waffles",
      "Waffles, with your choice of blueberries or strawberries",
      true,
      3.59
    );
  }

  public void addItem(String name, String desc, boolean vegetarian, double price) {
    MenuItem menuItem = new MenuItem(name, desc, vegetarian, price);
    menuItems.add(menuItem);
  }

  public java.util.Iterator createIterator() {
    return menuItems.iterator();
  }

  // other menu methods here
}

class DinerMenu implements Menu {
  static final int MAX_ITEMS = 6;
  int numberOfItems = 0;
  MenuItem[] menuItems;

  public DinerMenu() {
    menuItems = new MenuItem[MAX_ITEMS];

    addItem(
      "Vegetarian BLT",
      "Fakin' Bacon with lettuce & tomato on whole wheat",
      true,
      2.99
    );

    addItem(
      "BLT",
      "Bacon with lettuce & tomato on whole wheat",
      false,
      2.99
    );

    addItem(
      "Soup of the day",
      "Soup of the day, with a side of potato salad",
      false,
      3.29
    );

    addItem(
      "Hotdog",
      "A Hot Dog, with saurkraut, relish, onions, topped with cheese",
      false,
      3.05
    );
  }

  public void addItem(String name, String desc, boolean vegetarian, double price) {
    MenuItem menuItem = new MenuItem(name, desc, vegetarian, price);
    if (numberOfItems >= MAX_ITEMS) {
      System.err.println("Sorry, menu is full! Can't add item to menu");
    } else {
      menuItems[numberOfItems] = menuItem;
      numberOfItems = numberOfItems + 1;
    }
  }

  // public MenuItem[] getMenuItems() {
  //   return menuItems;
  // }

  public java.util.Iterator createIterator() {
    return new DinerMenuIterator(menuItems);
  }

  // other menu methods here
}

class MenuItem {
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
}

class Waitress {
  Menu pancakeHouseMenu;
  Menu dinerMenu;

  public Waitress(Menu pancakeHouseMenu, Menu dinerMenu) {
    this.pancakeHouseMenu = pancakeHouseMenu;
    this.dinerMenu = dinerMenu;
  }

  public void printMenu() {
    java.util.Iterator pancakeIterator = pancakeHouseMenu.createIterator();
    java.util.Iterator dinerIterator = dinerMenu.createIterator();

    System.out.println("MENU\n---\nBREAKFAST");
    printMenu(pancakeIterator);
    System.out.println("\nLUNCH");
    printMenu(dinerIterator);
  }

  public void printMenu(java.util.Iterator iter) {
    while (iter.hasNext()) {
      MenuItem menuItem = (MenuItem) iter.next();
      System.out.print(menuItem.getName() + ", ");
      System.out.print(menuItem.getPrice() + " -- ");
      System.out.println(menuItem.getDescription());
    }
  }

  // other methods here
}

public class RestaurantMergeImprovedVersion {
  public static void main(String[] args) {
    Menu pancakeHouseMenu = new PancakeHouseMenu();
    Menu dinerMenu = new DinerMenu();

    Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu);

    waitress.printMenu();
  }
}
