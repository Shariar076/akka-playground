package com.example

import akka.Done
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.client.RequestBuilding.Post
import akka.http.scaladsl.unmarshalling.Unmarshal
import jdk.nashorn.internal.codegen.CompilerConstants
import spray.json.DefaultJsonProtocol

import scala.concurrent.duration.DurationInt

case class Config(cpus: Float, memory: Int)

object ConfigJsonProtocol extends DefaultJsonProtocol {
    implicit val configFormat = jsonFormat2(Config)
}

class AkkaHttpClient {

    implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext
            val postRequest = Post(uri = "http://127.0.0.1:5000/" , "data")
//    val postRequest = HttpRequest(
//        method = HttpMethods.POST,
//        uri = "http://127.0.0.1:5000/",
//        entity = HttpEntity(ContentTypes.`application/json`, "data")
//    )
    val getRequest = Get(uri = "http://127.0.0.1:5000/")
    val responseFuture: Future[HttpResponse] = Http(system).singleRequest(postRequest)

    def getResponse(): Unit = {
        responseFuture
            .onComplete {

                case Success(res) =>
                    import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
                    import ConfigJsonProtocol._
                    val futureConfig = Unmarshal(res).to[Config]
                    val result = Await.result(futureConfig, 10.seconds)
                    println(s"GOT VALUABLE RESULT ${result}")
                    system.terminate()
                case Failure(_) => sys.error("something wrong")
            }
    }


}
