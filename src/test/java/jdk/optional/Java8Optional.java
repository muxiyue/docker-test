package jdk.optional;

import java.util.Optional;

public class Java8Optional {



   public static void main(String args[]){
   

      System.out.println(Optional.ofNullable("123").orElseGet(()->"永远不会执行我的"));//延迟计算而已

      System.out.println(Optional.ofNullable(null).orElse("hello,world"));


      // map 返回包装称 Optional   flatmap 不包装，需要自己包装。

      System.out.println(Optional.ofNullable(new Student("csp")).map(Student::getName).orElse("1111"));

      System.out.println(Optional.ofNullable(new Student("csp2")).flatMap(stu -> Optional.ofNullable(stu.getName())).orElse("222"));
   }
}



class Student {
   private String name;

   public Student(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override public String toString() {
      return "Student{" + "name='" + name + '\'' + '}';
   }
}