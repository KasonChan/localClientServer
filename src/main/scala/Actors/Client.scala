package Actors

import akka.actor.Actor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
class Client extends Actor {
  def receive = {
    data("Default")
  }

  //  k = key
  def data(k: String): Receive = {
    case SessionKeyReply(c, s, nk, t) => {

      context.become(data(nk))

      //      TODO: Encrypt message
      val m = EncryptedMessage("What")
      val M = ServiceRequest(c, m, t)

      //      Send service request
      val f = Future {
        s ! M
      }

      Await.result(f, 5 seconds)

      println(self.path.name + ": {" + c.path.name + ", " + s.path.name + ", " + nk + t + "}")
    }
    case ServiceReply(c, s, r) => {
      println(self.path.name + ": {" + c.path.name + ", " + s.path.name + ", " + r + "}")
    }
    case m => {
      println(self.path.name + ": [Pattern mismatched] " + m + " " + k)
    }
  }
}
