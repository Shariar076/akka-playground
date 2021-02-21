//#full-example
package com.example


import akka.actor.{Actor, ActorSystem}
import akka.util.ByteString
import spray.json.DefaultJsonProtocol
import sun.net.www.http.HttpClient

import scala.language.postfixOps


class LocalActor extends Actor {
    def receive = {
        case msg: Int => println(msg)
        case _ => println("Unknown message: not Int")
    }
}

case class WorkerConfig(cpus: Int, memory: Int)

object WorkerConfigJsonProtocol extends DefaultJsonProtocol {
    implicit val colorFormat = jsonFormat2(WorkerConfig)
}

object AkkaMain extends App {
//    val num = "27.0"
//    print(num.toDouble.toInt)


//    println("Hello World!!")

    val httpClient = new AkkaHttpClient()
    httpClient.getResponse()

//    val tcpActor = new TCPActor();
//    Thread.sleep(1000);
//    tcpActor.send("Hellooo")



}

//#main-class
//#full-example
