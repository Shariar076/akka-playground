package com.example

import akka.actor.{Actor, Props}
import akka.util.ByteString

object Listener {
    def props = Props[Listener]
}
class Listener extends  Actor {
    def receive = { //  Receiving message
        case msg: String => println(s"String message received in Listener $msg")
        case byteMsg: ByteString => println(s"ByteString message received in Listener ${byteMsg.utf8String}")
        case _ => println(s"Unknown message: not String or ByteString") // Default case
    }

}
