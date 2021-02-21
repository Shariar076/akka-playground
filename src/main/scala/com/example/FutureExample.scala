package com.example

import akka.actor.Props

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class FutureExample {

    def createBox(name: String, exists: Boolean, size: Int): Future[Box] = Future {
        new Box(name, exists, size)
    }

    def props(properties: (String, Boolean, Int) => Future[Box]) =
        Props(new HelloAkka(properties))

//    USAGE
//    val box = Await.result(createBox, 2 seconds)
//     box.getProps


}
