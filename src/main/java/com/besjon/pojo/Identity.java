/**
  * Copyright 2019 bejson.com 
  */
package com.besjon.pojo;
import java.util.List;

/**
 * Auto-generated: 2019-03-27 17:4:17
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Identity {

    private List<String> methods;
    private Password password;
    public void setMethods(List<String> methods) {
         this.methods = methods;
     }
     public List<String> getMethods() {
         return methods;
     }

    public void setPassword(Password password) {
         this.password = password;
     }
     public Password getPassword() {
         return password;
     }

}