package Actors

import akka.actor.{Actor, ActorRef}
import akka.event.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
class Key extends Actor {
  val log = Logging(context.system, this)

  override def preStart() = {
    log.debug("Starting")
  }

  def receive = {
    case SessionKeyRequest(c: ActorRef, s: ActorRef) => {
      if (sender() == c) {
        //        TODO: Encrypt token
        val k = "KCS"
        val t = EncryptedToken(c, s, k)

        //        Send session key reply
        val f = Future {
          sender() ! SessionKeyReply(c, s, k, t)
        }

        Await.result(f, 5 seconds)

        log.info("{" + c.path.name + ", " + s.path.name + "}")

        log.info("{" + c.path.name + ", " + s.path.name + ", " + k + ", " + t + "}")
      }
      else {
        //        TODO: Encrypt token
        val k = "NULL"
        val t = EncryptedToken(c, s, k)

        //        Send session key reply
        val f = Future {
          sender() ! SessionKeyReply(c, s, k, EncryptedToken(c, s, k))
        }

        Await.result(f, 5 seconds)

        log.info("{" + c.path.name + ", " + s.path.name + k + ", " + t + "}")
      }
    }
    case m => {
      log.warning("Received unknown message: " + m)
    }
  }
}
