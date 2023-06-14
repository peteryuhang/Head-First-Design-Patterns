package ch11;

import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;



/* 
 * Ways for making demo
 * 
 * 1. Generate the class file for each interface/class defined here
 * 2. Generate stubs(client helper)
 *    >$ rmic ch11.GumballMachine
 * 3. Run rmiregistry on terminal 1 (On server)
 *    >$ rmiregistry
 * 4. Start the remote service on terminal 2 (On server)
 *    >$ java ch11.GumballMachineTestDrive 127.0.0.1 100
 * 5. let the client make a call on terminal 3 (On client)
 *    >$ java ch11.GumballMonitorTestDrive
 */



/* 
 * Remote Interface
 * 
 * All returned type need to be primitive or Serializable
 */
interface GumballMachineRemote extends Remote {
  public int getGumballCount() throws RemoteException;
  public String getLocation() throws RemoteException;
  public State getCurrentState() throws RemoteException;
}

class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
  private State noQuarterState;
  private State hasQuarterState;
  private State soldOutState;
  private State soldState;
  private State winnerState;
  private State state = soldOutState;

  private String location;
  private int numberOfBall = 0;

  public GumballMachine(String location, int numberOfBall) throws RemoteException {
    noQuarterState = new NoQuarterState(this);
    hasQuarterState = new HasQuarterState(this);
    soldOutState = new SoldOutState(this);
    soldState = new SoldState(this);
    winnerState = new WinnerState(this);
    this.numberOfBall = numberOfBall;
    this.location = location;

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

  public void refill(int balls) {
    numberOfBall += balls;

    System.out.println("Gumball machine has been refilled with " + balls + " gumballs!");

    if (numberOfBall > 0 && state == soldOutState) {
      state = noQuarterState;
    }
  }

  public int getGumballCount() {
    return numberOfBall;
  }

  public String getLocation() {
    return location;
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

  public State getWinnerState() {
    return winnerState;
  }

  public State getCurrentState() {
    return state;
  }
}

/* 
 * We need to make sure all state can be transferred over the network
 * so need to extend the Serializable interface to make return type(State)
 * serializable
 */
interface State extends Serializable {
  public void insertQuarter();
  public void ejectQuarter();
  public void turnCrank();
  public void dispense();
}

class NoQuarterState implements State {
  /* 
   * We don't want the entire gumball machine serialized and transferred
   * with the State object
   * 
   * The transient keywork tells the JVM not to serialize this field
   */
  transient GumballMachine gumballMachine;

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
  Random randomWinner = new Random(System.currentTimeMillis());
  /* 
   * We don't want the entire gumball machine serialized and transferred
   * with the State object
   * 
   * The transient keywork tells the JVM not to serialize this field
   */
  transient GumballMachine gumballMachine;

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
    int winner = randomWinner.nextInt(10);
    if ((winner == 0) && (gumballMachine.getGumballCount() > 1)) {
      gumballMachine.setState(gumballMachine.getWinnerState());
    } else {
      gumballMachine.setState(gumballMachine.getSoldState());
    }
  }

  public void dispense() {
    System.out.println("No gumball dispensed");
  }
}

class SoldOutState implements State {
  /* 
   * We don't want the entire gumball machine serialized and transferred
   * with the State object
   * 
   * The transient keywork tells the JVM not to serialize this field
   */
  transient GumballMachine gumballMachine;

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
  /* 
   * We don't want the entire gumball machine serialized and transferred
   * with the State object
   * 
   * The transient keywork tells the JVM not to serialize this field
   */
  transient GumballMachine gumballMachine;

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

class WinnerState implements State {
  /* 
   * We don't want the entire gumball machine serialized and transferred
   * with the State object
   * 
   * The transient keywork tells the JVM not to serialize this field
   */
  transient GumballMachine gumballMachine;

  public WinnerState(GumballMachine gumballMachine) {
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
    System.out.println("YOU'RE A WINNER! You get two gumballs for your quarter");
    gumballMachine.releaseBall();
    if (gumballMachine.getGumballCount() == 0) {
      gumballMachine.setState(gumballMachine.getSoldOutState());
    } else {
      gumballMachine.releaseBall();
      if (gumballMachine.getGumballCount() > 0) {
        gumballMachine.setState(gumballMachine.getNoQuarterState());
      } else {
        System.out.println("Oops, out of gumballs!");
        gumballMachine.setState(gumballMachine.getSoldOutState());
      }
    }
  }
}

class GumballMonitor {
  GumballMachineRemote machine;

  public GumballMonitor(GumballMachineRemote machine) {
    this.machine = machine;
  }

  public void report() {
    try {
      System.out.println("Gumball Machine: " + machine.getLocation());
      System.out.println("Current inventory: " + machine.getGumballCount() + " gumballs");
      System.out.println("Current state: " + machine.getCurrentState());
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}

class GumballMachineTestDrive {
  public static void main(String[] args) {
    GumballMachineRemote gumballMachine = null;
    int count;
    if (args.length < 2) {
      System.out.println("GumballMachine <name> <inventory>");
      System.exit(1);
    }

    try {
      count = Integer.parseInt(args[1]);
      gumballMachine = new GumballMachine(args[0], count);
      Naming.rebind("//" + args[0] + "/gumballmachine", gumballMachine);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}

class GumballMonitorTestDrive {
  public static void main(String[] args) {
    String[] location = {
      "rmi://127.0.0.1/gumballmachine"
    };

    GumballMonitor[] monitor = new GumballMonitor[location.length];

    for (int i = 0; i < location.length; i++) {
      try {
        GumballMachineRemote machine = (GumballMachineRemote) Naming.lookup(location[i]);
        monitor[i] = new GumballMonitor(machine);
        System.out.println(monitor[i]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    for (int i = 0; i < monitor.length; i++) {
      monitor[i].report();
    }
  }
}
