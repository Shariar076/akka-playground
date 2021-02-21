package com.example

import akka.actor.{Actor, ActorRef, Props}
import akka.actor.TypedActor.{dispatcher, self}
import akka.io.{IO, Tcp}
import akka.pattern.ask
import akka.util.{ByteString, Timeout}

import java.net.InetSocketAddress
import scala.concurrent.duration.{FiniteDuration, SECONDS}

object Client {
  def props() =
    Props(classOf[Client])
}

class Client extends Actor with TcpAskSupport {

  import akka.io.Tcp._
  import context.system

  IO(Tcp) ! Connect(new InetSocketAddress("127.0.0.1", 8888))
  implicit val timeout = Timeout(new FiniteDuration(5, SECONDS))

  def receive = {

    case CommandFailed(_: Connect) =>
      println("connect failed")
      context stop self

    case c @ Connected(remote, local) =>
      println("Connected" + c)
      val connection = sender()
      asker(connection) ? Write(ByteString("GET /\n", "UTF-8"), NoAck) onComplete {
          case x => println("Response" + x)
      } 
    case x => println("[ERROR] Received" + x)    
  }
}