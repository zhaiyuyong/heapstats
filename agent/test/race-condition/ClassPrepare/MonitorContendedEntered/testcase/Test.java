/*
 * Copyright (C) 2017 Nippon Telegraph and Telephone Corporation
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.net.*;
import java.nio.file.*;
import java.lang.reflect.*;


public class Test implements Runnable{

  public void run(){
    try{
      synchronized(Test.class){
        System.out.println("Sleep at " + Thread.currentThread().getName());
        Thread.sleep(3000);
      }
    }
    catch(InterruptedException e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws Exception{
    (new Thread(new Test())).start();
    (new Thread(new Test())).start();

    ClassLoader loader = new URLClassLoader(new URL[]{
                     Paths.get(System.getProperty("java.class.path"), "dynload")
                          .toUri()
                          .toURL()});
    Class<?> target = loader.loadClass("DynLoad");
    Method targetMethod = target.getMethod("call");
    Object targetObj = target.newInstance();
    targetMethod.invoke(targetObj);
  }
}
