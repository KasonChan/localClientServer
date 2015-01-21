package Actors

import akka.actor.Actor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
class Application extends Actor {
  def receive = {
    case ServiceRequest(c, m, t) => {
      //      TODO: Encrypt message
      val r = "Result"
      val R = ServiceReply(c, t.s, r)

      val f = Future {
        sender() ! R
      }

      Await.result(f, 5 seconds)

      println(self.path.name + ": {" + c.path.name + ", " + m + ", " + t + "}")
    }
    case m => println(self.path.name + ": " + m)
  }
}
