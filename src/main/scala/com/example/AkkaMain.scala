//#full-example
package com.example

import akka.actor.{ActorSystem, ActorRefFactory}
import akka.actor.Props
import akka.actor.Actor
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global
import BoxTraits._

class LocalActor extends Actor{
  def receive = {
    case msg: Int => println(msg)
    case _ => println("Unknown message: not Int")
  }
}


object AkkaMain extends App {


  def createBox(name : String, exists: Boolean, size: Int) : Future[Box] = Future{
    new Box(name, exists, size)
  }

  def props(properties: (String, Boolean, Int) => Future[Box]) =
        Props(new HelloAkka(properties))

  // val box = Await.result(createBox, 2 seconds)
  // box.getProps

  val scalafut =  new PythonInvoker()
  scalafut.callPython
  
  // println("Hello World!!")
  // var actorSystem = ActorSystem("ActorSystem")                       // Creating ActorSystem
  // val actorRefSystem = ActorRefFactory("ActorRefSystem")
  // var actor1 = (f: ActorRefFactory) =>
    // f.actorOf(props(createBox))        //Creating actor  
  // var actor1 = actorSystem.actorOf(props(createBox))
  // actor1 ! Open
  // actor1 ! "getProps"
  // actor2 ! 123

}
//#main-class
//#full-example
