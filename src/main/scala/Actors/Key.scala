package Actors

import akka.actor.{Actor, ActorRef}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
class Key extends Actor {
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

        println(self.path.name + ": {" + c.path.name + ", " + s.path.name + "}")

        println(self.path.name + ": {" + c.path.name + ", " + s.path.name + ", " + k + ", " + t + "}")
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

        println(self.path.name + ": {" + c.path.name + ", " + s.path.name + k + ", " + t + "}")
      }
    }
  }
}
