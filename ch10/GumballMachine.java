package ch10;

class GumballMachine {
  private State noQuarterState;
  private State hasQuarterState;
  private State soldOutState;
  private State soldState;
  private State state = soldOutState;
  private int numberOfBall = 0;

  public GumballMachine(int numberOfBall) {
    noQuarterState = new NoQuarterState(this);
    hasQuarterState = new HasQuarterState(this);
    soldOutState = new SoldOutState(this);
    soldState = new SoldState(this);
    this.numberOfBall = numberOfBall;
    if (numberOfBall > 0) {
      state = noQuarterState;
    }
  }

  public void insertQuarter() {
    state.insertQuarter();
  }

  public void ejectQuarter() {
    state.ejectQuarter();
  }

  public void turnCrank() {
    state.turnCrank();
    state.dispense();
  }

  public void setState(State state) {
    this.state = state;
  }

  public void releaseBall() {
    System.out.println("A gumball comes rolling out the slot...");
    if (numberOfBall != 0) {
      numberOfBall = numberOfBall - 1;
    }
  }

  public int getGumballCount() {
    return numberOfBall;
  }

  public State getNoQuarterState() {
    return noQuarterState;
  }

  public State getHasQuarterState() {
    return hasQuarterState;
  }

  public State getSoldOutState() {
    return soldOutState;
  }

  public State getSoldState() {
    return soldState;
  }

  public State getCurrentState() {
    return state;
  }
}

interface State {
  public void insertQuarter();
  public void ejectQuarter();
  public void turnCrank();
  public void dispense();
}

class NoQuarterState implements State {
  GumballMachine gumballMachine;

  public NoQuarterState(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    gumballMachine.setState(gumballMachine.getHasQuarterState());
    System.out.println("You inserted a quarter");
  }

  public void ejectQuarter() {
    System.out.println("You haven't inserted a quarter");
  }

  public void turnCrank() {
    System.out.println("You turned but there is no quarter");
  }

  public void dispense() {
    System.out.println("You need to pay first");
  }
}

class HasQuarterState implements State {
  GumballMachine gumballMachine;

  public HasQuarterState(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("You can't insert another quarter");
  }

  public void ejectQuarter() {
    System.out.println("Quarter returned");
    gumballMachine.setState(gumballMachine.getNoQuarterState());
  }

  public void turnCrank() {
    System.out.println("You turned...");
    gumballMachine.setState(gumballMachine.getSoldState());
  }

  public void dispense() {
    System.out.println("No gumball dispensed");
  }
}

class SoldOutState implements State {
  GumballMachine gumballMachine;

  public SoldOutState(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("You can't insert a quarter, the machine is sold out");
  }

  public void ejectQuarter() {
    System.out.println("You can't eject, you haven't inserted a quarter yet");
  }

  public void turnCrank() {
    System.out.println("You turned, but there are no gumballs");
  }

  public void dispense() {
    System.out.println("No gumball dispensed");
  }
}

class SoldState implements State {
  GumballMachine gumballMachine;

  public SoldState(GumballMachine gumballMachine) {
    this.gumballMachine = gumballMachine;
  }

  public void insertQuarter() {
    System.out.println("Please wait, we're already giving you a gumball");
  }

  public void ejectQuarter() {
    System.out.println("Sorry, you already turned the crank");
  }

  public void turnCrank() {
    System.out.println("Turning twice doesn't give you another gumball!");
  }

  public void dispense() {
    gumballMachine.releaseBall();
    if (gumballMachine.getGumballCount() > 0) {
      gumballMachine.setState(gumballMachine.getNoQuarterState());
    } else {
      System.out.println("Oops, out of gumballs!");
      gumballMachine.setState(gumballMachine.getSoldOutState());
    }
  }
}
 