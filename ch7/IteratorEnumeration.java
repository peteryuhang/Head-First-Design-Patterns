package ch7;

import java.util.*;

class IteratorEnumeration implements Enumeration {
  private Iterator iter;

  public IteratorEnumeration(Iterator iter) {
    this.iter = iter;
  }

  public boolean hasMoreElements() {
    return iter.hasNext();
  }

  public Object nextElement() {
    return iter.next();
  }
}

class TestDrive {
  public static void main(String[] args) {
    ArrayList list = new ArrayList<>();
    list.add(1);
    list.add(2);
    IteratorEnumeration enumList = new IteratorEnumeration(list.iterator());

    System.out.println("The list's iterator with IteratorEnumeration adapter");
    System.out.println("enumList.hasElements returned: " + enumList.hasMoreElements());  // true
    System.out.println("enumList.nextElement returned: " + enumList.nextElement());      // 1
    System.out.println("enumList.hasElements returned: " + enumList.hasMoreElements());  // true
    System.out.println("enumList.nextElement returned: " + enumList.nextElement());      // 2
    System.out.println("enumList.hasElements returned: " + enumList.hasMoreElements());  // false
  }
}
