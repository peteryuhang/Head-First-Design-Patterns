package ch11.rmiservice;

import java.rmi.*;


/* 
 * To get this demo, open three therminals, run below command
 * 
 * 
 * >$ rmic MyServiceImpl             # generate stubs(client helper)
 * 
 * >$ rmiregistry                    # run rmiregistry on terminal 1            (On server)
 * >$ java MyServiceImpl             # start the remote service on terminal 2   (On server)
 * >$ java MyServiceClient           # let the client make a call on terminal 3 (On client)
 */
public class MyServiceClient {
  public static void main(String[] args) {
    new MyServiceClient().go();
  }

  public void go() {
    try {
      MyService service = (MyService) Naming.lookup("rmi://127.0.0.1/RemoteHello");

      String s = service.sayHello();

      System.out.println(s);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
