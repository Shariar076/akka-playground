package com.example

import akka.actor.{Actor, Stash, FSM};
import scala.concurrent.Future
import scala.concurrent.Await
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

class Box(name: String, exists: Boolean, size: Int) {
    def getProps = println(s"name: $name, exists: $exists, size: $size")
}

object BoxTraits {
    sealed trait BoxState
    case object Opened extends BoxState
    case object Closed extends BoxState
    sealed trait BoxContent
    case object FullContent extends BoxContent
    case object EmptyContent extends BoxContent
    case object Open
    case object Close

}

class HelloAkka(factory: (String, Boolean, Int) => Future[Box])
    extends FSM[BoxTraits.BoxState, BoxTraits.BoxContent] with Stash {

    import BoxTraits._

    var futureBox: Future[Box] = null
    startWith(Closed, EmptyContent)

    when(Opened) {
        case Event(Close, _) =>
            println(s"Got msg:$Close and FSM actor")
            goto(Closed) using (EmptyContent)
        case Event(op: String, _) =>
            if (op == "getProps") {
                println("Getting Properties ...")
                futureBox.onComplete {
                    case Success(box) => box.getProps
                    case Failure(ex) => println(s"Error while getting properties: $ex")
                }
            }
            stay using (EmptyContent)
    }
    when(Closed) {
        case Event(Open, _) =>
            println(s"Got msg:$Open and FSM actor")
            futureBox = factory("Green", true, 10)
            unstashAll()
            goto(Opened) using (EmptyContent)
        case Event(_, _) =>
            stash()
            stay using (EmptyContent)
    }
    initialize()
} 