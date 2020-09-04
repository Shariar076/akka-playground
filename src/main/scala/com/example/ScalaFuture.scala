package com.example

import scala.collection.parallel.CollectionConverters._
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.{Success, Failure}
import java.lang.Exception

class ScalaFuture{
    def sum(x: Int = 10): Int = if(x == 1) 1 else x+sum(x-1)
    def futureFunc1(msg: Boolean): Future[Boolean] =  Future{
        println("Inside futureFunc1")
        msg
    }
    def futureFunc2(msg: Boolean): Future[Boolean] =  Future{
        println("Inside futureFunc2")
        msg
    }
    def waitForFuture(msg: Boolean): Future[Unit] =  Future{
        println("Waiting For the Future")
        Thread.sleep(2000)
        println("The future is here")
    }
    def futureFactory(num: Int): Future[Option[Int]] =  Future{
        if(num < 10) {
        println(s"Got order: $num items, which will be provided in future")
        Thread.sleep(2000)
        Some(num)
        }
        else {
        // throw new Exception("Too many items ordered")
        println("Too many items ordered")
        None
        }
    }
    def getItems(quantity: Int): Future[Boolean] = Future {
        println(s"Got $quantity items")
        if(quantity > 0) true else false
    }
    def main = {

        println(sum())
        //parallel data
        // val list = (1 to 10).toList
        // println(list)
        // for( i <- list.par) {
        //   println(sum(i))
        // }

        // blocking call
        // val items= Await.result(futureFactory(1), 4 seconds)
        // println(s"Got $items items")



        // non-blocking call
        // futureFactory(5).onComplete{
        //   case Success(res) => {
        //     getItems(res.getOrElse(0)).onComplete{
        //       case Success(isSuccess) => println(s"Successfully got: $isSuccess") 
        //     }
        //     //${Await.result(getItems(res.getOrElse(0)), 4 seconds)}
        //   }
        //   case Failure(ex) => println(s"Got exception: $ex")
        // }

        // for {
        //   items <- futureFactory(5) 
        //   isSuccess <- getItems(items.getOrElse(0))} yield {
        //     println(s"Got items: $isSuccess")
        //     }

        // val getingItems: Future[Boolean] = futureFactory(5).flatMap(qty => getItems(qty.getOrElse(0)))
        // val isSuccess = Await.result(getingItems, 4 seconds)
        // println(s"Got items: $isSuccess")
        // val getingItems: Future[Future[Boolean]] = futureFactory(5).map(
        //                                                                   qty => getItems(qty.getOrElse(0)).flatMap(
        //                                                                     isSuccess1 => futureFunc1(isSuccess1).flatMap(
        //                                                                       isSuccess2 => futureFunc2(isSuccess2)
        //                                                                     )))
        // val isSuccess = Await.result(Await.result(getingItems, 4 seconds), 4 seconds)
        // println(s"Got items: $isSuccess")

        // val futureOrders = List(
        //     futureFactory(9),
        //     futureFactory(5),
        //     futureFactory(13),
        //     futureFactory(15)
        // )
        // val foldLeftRes = Future.foldLeft(futureOrders)(1){
        //   case (acc, qty) => acc + qty.getOrElse(0)
        // }

        // val foldLeftRes = Future.reduceLeft(futureOrders){
        //   case (acc, qty) => acc.map(aMap => aMap + qty.getOrElse(0))
        // }
        // val zipPlain = futureFactory(4) zip getItems(3)

        // zipPlain.onComplete{
        //     case Success(totQty) => println(s"Finally got $totQty items")
        //     case Failure(ex) => println(s"Finally got exeption $ex")
        // }

        // val f1 = Future{
        //     Thread.sleep(1000)
        //     println("Hello")
        // }
        
        // Thread.sleep(5000)
    }
}