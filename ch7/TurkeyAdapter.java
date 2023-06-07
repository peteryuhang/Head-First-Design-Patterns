package ch7;

class TurkeyAdapter implements Duck {
  private Turkey turkey;

  public TurkeyAdapter(Turkey turkey) {
    this.turkey = turkey;
  }

  public void fly() {
    for (int i = 0; i < 5; i++) {
      turkey.fly();
    }
  }

  public void quack() {
    turkey.gobble();
  }
}

interface Turkey {
  public void fly();
  public void gobble();
}

interface Duck {
  public void fly();
  public void quack();
}

class MallardDuck implements Duck {
  public void quack() {
    System.out.println("Quack");
  }

  public void fly() {
    System.out.println("I'm flying");
  }
}

class WildTurkey implements Turkey {
  public void gobble() {
    System.out.println("Gobble");
  }

  public void fly() {
    System.out.println("I'm flying a short distance");
  }
}

class DuckTestDrive {
  public static void main(String[] args) {
    MallardDuck duck = new MallardDuck();

    WildTurkey turkey = new WildTurkey();
    Duck turkeyAdapter = new TurkeyAdapter(turkey);
    
    System.out.println("The Turkey says...");
    turkey.gobble();
    turkey.fly();

    System.out.println("\nThe Duck says...");
    testDuck(duck);
    
    System.out.println("\nThe TurkeyAdapter says...");
    testDuck(turkeyAdapter);
  }

  static void testDuck(Duck duck) {
    duck.quack();
    duck.fly();
  }
}