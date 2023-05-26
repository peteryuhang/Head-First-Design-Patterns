package ch1;

abstract class Duck {
  protected FlyBehavior flyBehavior;
  protected QuackBehavior quackBehavior;

  public void swim() {
    System.out.println("The duck is swimming!");
  }

  public void performFly() {
    flyBehavior.fly();
  }

  public void performQuack() {
    quackBehavior.quack();
  }
  
  public void setFlyBehavior(FlyBehavior flyBehavior) {
    this.flyBehavior = flyBehavior;
  }

  public void setQuackBehavior(QuackBehavior quackBehavior) {
    this.quackBehavior = quackBehavior;
  }

  abstract void display();
}

class MallarDuck extends Duck {
  MallarDuck() {
    this.flyBehavior = new FlyWithWings();
    this.quackBehavior = new Quack();
  }

  public void display() {
    System.out.println("This is the Mallar duck");
  }
}

class RedheadDuck extends Duck {
  RedheadDuck() {
    this.flyBehavior = new FlyWithWings();
    this.quackBehavior = new Quack();
  }

  public void display() {
    System.out.println("This is the red head duck");
  }
}

class RubberDuck extends Duck {
  RubberDuck() {
    this.flyBehavior = new FlyNoWay();
    this.quackBehavior = new Squeak();
  }

  public void display() {
    System.out.println("This is the rubber duck");
  }
}

class DecoyDuck extends Duck {
  DecoyDuck() {
    this.flyBehavior = new FlyNoWay();
    this.quackBehavior = new MuteQuack();
  }

  public void display() {
    System.out.println("This is the decoy duck");
  }
}

interface FlyBehavior {
  void fly();
}

class FlyWithWings implements FlyBehavior {
  public void fly() {
    System.out.println("The duck is flying with wings!");
  }
}

class FlyNoWay implements FlyBehavior {
  public void fly() {
    System.out.println("The duck cannot fly!");
  }
}

interface QuackBehavior {
  void quack();
}

class Quack implements QuackBehavior {
  public void quack() {
    System.out.println("Quack!");
  }
}

class Squeak implements QuackBehavior {
  public void quack() {
    System.out.println("Squeak!");
  }
}

class MuteQuack implements QuackBehavior {
  public void quack() {
    System.out.println("Can't quack!");
  }
}
