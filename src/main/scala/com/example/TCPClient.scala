package com.example

import akka.actor.{Actor, ActorRef, Props}
import akka.io.{IO, Tcp}

import java.net.InetSocketAddress
import akka.pattern.ask
import akka.util.Timeout
import akka.util.ByteString

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object TCPClient {
    def props(host: String, port: Int, listener: ActorRef) =
        Props(classOf[TCPClient], new InetSocketAddress(host, port), listener)
}

class TCPClient(remote: InetSocketAddress, listener: ActorRef) extends Actor {

    import Tcp._
    import context.system

    val manager = IO(Tcp)
    manager ! Connect(remote)

    override def receive: Receive = {
        case CommandFailed(con: Connect) =>
            println("Connection failed")
            println(con.failureMessage.toString)
            context stop self

        case c@Connected(remote, local) =>
            //            val listener = context.actorOf(Listener.props, "tcpListener")
            //            listener ! c
            val connection = sender()
            connection ! Register(self)
            context.become {
                case data: ByteString =>
                    implicit val timeout = Timeout(5 seconds)

                    val future = (connection ? Write(data, NoAck)).mapTo[ByteString]
                    val msg = Await.result(future, 10 seconds)
                    println(s"Message received in TCPClient: ${msg.utf8String}")
                case CommandFailed(w: Write) =>
                    // O/S buffer was full
                    listener ! "write failed"
                case Received(rdata) =>
                    listener ! rdata
                case "close" =>
                    connection ! Close
                case _: ConnectionClosed =>
                    listener ! "connection closed"
                    context.stop(self)
            }
    }
}