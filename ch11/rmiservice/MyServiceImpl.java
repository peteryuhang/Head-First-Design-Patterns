package ch11.rmiservice;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServiceImpl extends UnicastRemoteObject implements MyService {

  /* 
   * Need declaraes exception because UnicastRemoteObject constructor throws the exception
   */
  public MyServiceImpl() throws RemoteException { }

  public String sayHello() {
    return "Server says, 'Hey'";
  }

  public static void main(String[] args) {
    try {
      MyService service = new MyServiceImpl();
      Naming.rebind("RemoteHello", service);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
