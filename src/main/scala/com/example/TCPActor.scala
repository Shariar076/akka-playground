package com.example

import akka.actor.{Actor, ActorSystem, Props}

import akka.util.ByteString
import org.json.JSONObject

class TCPActor {
    var actorSystem = ActorSystem("ActorSystem")
    val listener = actorSystem.actorOf(Listener.props, "tcpListener")
    val tcpActor = actorSystem.actorOf(TCPClient.props("127.0.0.1", 8888, listener), "tcpActor")

    def send(msg:String): Unit ={
        tcpActor ! ByteString(msg)
    }
//    Thread.sleep(1000)
//    tcpActor ! ByteString(testObj.toString)
//
//    Thread.sleep(1000)
//    println("sending another message")
//    tcpActor ! ByteString("another")
//    val actorRefSystem = ActorRefFactory("ActorRefSystem")
//    var actor1 = (f: ActorRefFactory) =>
//        f.actorOf(props(createBox)) //Creating actor
//    var actor1 = actorSystem.actorOf(props(createBox))
//    actor1 ! Open
//    actor1 ! "getProps"
//    actor2 ! 123
}
