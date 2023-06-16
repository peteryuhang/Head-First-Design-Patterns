package ch11;

import java.lang.reflect.*;
/* 
 * Example of protection proxy by using java built-in features
 * 
 * Problems to solve:
 * Customer shouldn't be changing their own HotOrNot rating
 * Customer shouldn't be able to change other customer's personal information
 */
class OwnerInvocationHandler implements InvocationHandler {
  PersonBean person;

  public OwnerInvocationHandler(PersonBean person) {
    this.person = person;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException {
    try {
      if (method.getName().startsWith("get")) {
        return method.invoke(person, args);
      } else if (method.getName().equals("setHotOrNotRating")) {
        throw new IllegalAccessException();
      } else if (method.getName().startsWith("set")) {
        return method.invoke(person, args);
      }
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }
}

class NonOwnerInvocationHandler implements InvocationHandler {
  PersonBean person;

  public NonOwnerInvocationHandler(PersonBean person) {
    this.person = person;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws IllegalAccessException {
    try {
      if (method.getName().startsWith("get")) {
        return method.invoke(person, args);
      } else if (method.getName().equals("setHotOrNotRating")) {
        return method.invoke(person, args);
      } else if (method.getName().startsWith("set")) {
        throw new IllegalAccessException();
      }
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }
}

interface PersonBean {
  String getName();
  String getGender();
  String getInterests();
  int getHotOrNotRating();

  void setName(String name);
  void setGender(String gender);
  void setInterests(String interests);
  void setHotOrNotRating(int rating);
}

class PersonBeanImpl implements PersonBean {
  String name;
  String gender;
  String interests;
  int rating;
  int ratingCount = 0;

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getInterests() {
    return interests;
  }

  public int getHotOrNotRating() {
    if (ratingCount == 0)
      return 0;
    return (rating/ratingCount);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setInterests(String interests) {
    this.interests = interests;
  }

  public void setHotOrNotRating(int rating) {
    this.rating += rating;
    ratingCount++;
  }
}

public class MatchMakingService {
  public static void main(String[] args) {
    MatchMakingService test = new MatchMakingService();
    test.drive();
  }

  public MatchMakingService() {
    // initializeDatabase()
  }

  public void drive() {
    PersonBean joe = new PersonBeanImpl();
    joe.setName("Joe Javabean");

    PersonBean ownerProxy = getOwnerProxy(joe);
    System.out.println("Name is " + ownerProxy.getName());
    ownerProxy.setInterests("bowling, Go");
    System.out.println("Interests set from owner proxy");
    try {
      ownerProxy.setHotOrNotRating(10);
    } catch (Exception e) {
      System.out.println("Can't set rating from owner proxy");
    }
    System.out.println("Rating is " + ownerProxy.getHotOrNotRating());

    PersonBean nonOwnerProxy = getNonOwnerProxy(joe);
    System.out.println("Name is " + nonOwnerProxy.getName());
    try {
      nonOwnerProxy.setInterests("bowling, Go");
    } catch (Exception e) {
      System.out.println("Can't set interests from non owner proxy");
    }
    nonOwnerProxy.setHotOrNotRating(2);
    System.out.println("Rating set from non owner proxy");
    System.out.println("Rating is " + nonOwnerProxy.getHotOrNotRating());
  }

  public PersonBean getOwnerProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance(
      person.getClass().getClassLoader(), 
      person.getClass().getInterfaces(),
      new OwnerInvocationHandler(person)
    );
  }

  public PersonBean getNonOwnerProxy(PersonBean person) {
    return (PersonBean) Proxy.newProxyInstance(
      person.getClass().getClassLoader(), 
      person.getClass().getInterfaces(),
      new NonOwnerInvocationHandler(person)
    );
  }
}
