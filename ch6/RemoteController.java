package ch6;

// ========================================= Invoker =========================================
class RemoteControl {
  Command[] onCommands;
  Command[] offCommands;
  Command undoCommand;

  public RemoteControl() {
    onCommands = new Command[7];
    offCommands = new Command[7];

    Command noCommand = new NoCommand();
    for (int i = 0; i < 7; i++) {
      onCommands[i] = noCommand;
      offCommands[i] = noCommand;
    }
    undoCommand = noCommand;
  }

  public void setCommand(int slot, Command onCommand, Command offCommand) {
    onCommands[slot] = onCommand;
    offCommands[slot] = offCommand;
  }

  public void onButtonWasPushed(int slot) {
    onCommands[slot].execute();
    undoCommand = onCommands[slot];
  }

  public void offButtonWasPushed(int slot) {
    offCommands[slot].execute();
    undoCommand = offCommands[slot];
  }

  public void undoButtonWasPushed() {
    undoCommand.undo();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("\n------ Remote Control ------\n");
    for (int i = 0; i < onCommands.length; i++) {
      sb.append("[slot " + i + "] " + onCommands[i].getClass().getName() + "  " + offCommands[i].getClass().getName() + "\n");
    }
    sb.append("[undo] " + undoCommand.getClass().getName() + "\n");

    return sb.toString();
  }
}


// ========================================= Command =========================================
interface Command {
  public void execute();
  public void undo();
}

class LightOffCommand implements Command {
  Light light;

  public LightOffCommand(Light light) {
    this.light = light;
  }

  public void execute() {
    light.off();
  }

  public void undo() {
    light.on();
  }
}

class LightOnCommand implements Command {
  Light light;

  public LightOnCommand(Light light) {
    this.light = light;
  }

  public void execute() {
    light.on();
  }

  public void undo() {
    light.off();
  }
}

class CeilingFanOffCommand implements Command {
  CeilingFan ceilingFan;

  public CeilingFanOffCommand(CeilingFan ceilingFan) {
    this.ceilingFan = ceilingFan;
  }

  public void execute() {
    ceilingFan.off();
  }

  public void undo() {
    ceilingFan.on();
  }
}

class CeilingFanOnCommand implements Command {
  CeilingFan ceilingFan;

  public CeilingFanOnCommand(CeilingFan ceilingFan) {
    this.ceilingFan = ceilingFan;
  }

  public void execute() {
    ceilingFan.on();
  }

  public void undo() {
    ceilingFan.off();
  }
}

class GarageDoorUpCommand implements Command {
  GarageDoor garageDoor;

  public GarageDoorUpCommand(GarageDoor garageDoor) {
    this.garageDoor = garageDoor;
  }

  public void execute() {
    garageDoor.up();
  }

  public void undo() {
    garageDoor.down();
  }
}

class GarageDoorDownCommand implements Command {
  GarageDoor garageDoor;

  public GarageDoorDownCommand(GarageDoor garageDoor) {
    this.garageDoor = garageDoor;
  }

  public void execute() {
    garageDoor.down();
  }

  public void undo() {
    garageDoor.up();
  }
}

class StereoOnWithCDCommand implements Command {
  Stereo stereo;

  public StereoOnWithCDCommand(Stereo stereo) {
    this.stereo = stereo;
  }

  public void execute() {
    stereo.on();
    stereo.setCd();
    stereo.setVolume(11);
  }

  public void undo() {
    stereo.off();
  }
}

class StereoOffCommand implements Command {
  Stereo stereo;

  public StereoOffCommand(Stereo stereo) {
    this.stereo = stereo;
  }

  public void execute() {
    stereo.off();
  }

  public void undo() {
    stereo.on();
    stereo.setCd();
    stereo.setVolume(11);
  }
}

/* 
 * If we need push one button to perform multiple action, can consider MacroCommand
 * 
 * You can decide dynamically which Commands you wanna go into 
 */
class MacroCommand implements Command {
  Command[] commands;

  public MacroCommand(Command[] commands) {
    this.commands = commands;
  }

  public void execute() {
    for (int i = 0; i < commands.length; i++) {
      commands[i].execute();
    }
  }

  public void undo() {
    for (int i = 0; i < commands.length; i++) {
      commands[i].undo();
    }
  }
}

/* 
 * Implement a command that does nothing, which is called null object
 * 
 * So we no need to check the command existance everytime before we execute any action
 */
class NoCommand implements Command {
  public void execute() {
    System.out.println("There is no command need to be executed...");
  }

  public void undo() {
    System.out.println("There is no command need to be undo...");
  }
}

// ========================================= Receiver =========================================
class Light {
  private String loc;

  public Light(String loc) {
    this.loc = loc;
  }

  public void off() {
    System.out.println("The light in " + loc + " has been turned off...");
  }

  public void on() {
    System.out.println("The light in " + loc + " has been turned on...");
  }
}

class CeilingFan {
  private String loc;

  public CeilingFan(String loc) {
    this.loc = loc;
  }

  public void off() {
    System.out.println("The ceiling fan in " + loc + " has been turned off...");
  }

  public void on() {
    System.out.println("The ceiling fan in " + loc + " has been turned on...");
  }
}

class GarageDoor {
  private String loc;

  public GarageDoor(String loc) {
    this.loc = loc;
  }

  public void up() {
    System.out.println("The garage door has been turned up...");
  }

  public void down() {
    System.out.println("The garage door has been turned down...");
  }
}

class Stereo {
  private String name;

  public Stereo(String name) {
    this.name = name;
  }

  public void on() {
    System.out.println("The stereo in " + name + " has been turned on...");
  }

  public void off() {
    System.out.println("The stereo in " + name + " has been turned off...");
  }

  public void setCd() {
    System.out.println("The stereo in " + name + " has been setCd...");
  }

  public void setRadio() {
    System.out.println("The stereo in " + name + " has been setRadio...");
  }

  public void setVolume(int value) {
    System.out.println("The stereo in " + name + " has been setVolume, volume value " + value + "...");
  }
}

public class RemoteController {
  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();

    Light livingRoomLight = new Light("Living Room");
    Light kitchenLight = new Light("Kitchen");
    CeilingFan ceilingFan = new CeilingFan("Living Room");
    GarageDoor garageDoor = new GarageDoor("");
    Stereo stereo = new Stereo("Living Room");

    LightOnCommand livingRoomLightOn = new LightOnCommand(livingRoomLight);
    LightOffCommand livingRoomLightOff = new LightOffCommand(livingRoomLight);
    LightOnCommand kitchenLightOn = new LightOnCommand(kitchenLight);
    LightOffCommand kitchenLightOff = new LightOffCommand(kitchenLight);

    CeilingFanOnCommand ceilingFanOn = new CeilingFanOnCommand(ceilingFan);
    CeilingFanOffCommand ceilingFanOff = new CeilingFanOffCommand(ceilingFan);

    GarageDoorUpCommand garageDoorUp = new GarageDoorUpCommand(garageDoor);
    GarageDoorDownCommand garageDoorDown = new GarageDoorDownCommand(garageDoor);

    StereoOnWithCDCommand stereoOnWithCD = new StereoOnWithCDCommand(stereo);
    StereoOffCommand stereoOff = new StereoOffCommand(stereo);

    remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff);
    remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff);
    remoteControl.setCommand(2, ceilingFanOn, ceilingFanOff);
    remoteControl.setCommand(3, stereoOnWithCD, stereoOff);

    System.out.println(remoteControl);

    remoteControl.onButtonWasPushed(0);
    remoteControl.offButtonWasPushed(0);
    System.out.println(remoteControl);
    remoteControl.undoButtonWasPushed();;
    remoteControl.onButtonWasPushed(1);
    remoteControl.offButtonWasPushed(1);
    remoteControl.onButtonWasPushed(2);
    remoteControl.offButtonWasPushed(2);
    remoteControl.onButtonWasPushed(3);
    remoteControl.offButtonWasPushed(3);
  }
}
