package actors

import akka.actor.Actor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 * Mixin the log member into actor
 */
class Client extends Actor with akka.actor.ActorLogging {
  override def preStart() = {
    log.debug("Starting")
  }

  def receive = {
    data("Default")
  }

  //  k = key
  def data(k: String): Receive = {
    case SessionKeyReply(c, s, nk, t) => {

      context.become(data(nk))

      //      TODO: Encrypt message
      val m = add(2, 3)
      val M = ServiceRequest(c, m, t)

      //      Send service request
      val f = Future {
        s ! M
      }

      Await.result(f, 5 seconds)

      log.info("{" + c.path.name + ", " + s.path.name + ", " + nk + ", " + t + "}")
    }
    case ServiceReply(c, s, r) => {
      log.info("{" + c.path.name + ", " + s.path.name + ", " + r + "}")
    }
    case m => {
      log.warning("Received unknown message: " + m)
    }
  }
}
