package com.example

import akka.actor.{Actor, ActorRef, Props}


class Asker(connection: ActorRef) extends Actor {

    import akka.io.Tcp._

    connection ! Register(self);

    def receive = {
        case x =>
            val parent = sender()
            connection ! x
            context become { case x => parent ! x; context.unbecome() }
    }
}

trait TcpAskSupport {
    self: Actor =>

    import context.system

    def asker(connection: ActorRef) =
        context.child(connection.path.name + "Asker")
            .getOrElse(system.actorOf(Props(classOf[Asker], connection),
                connection.path.name + "Asker"))
}