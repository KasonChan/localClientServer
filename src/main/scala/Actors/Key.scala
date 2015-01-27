package actors

import akka.actor.{Actor, ActorRef}
import akka.event.Logging
import database.DB
import encryption.RSA

/**
 * Created by kasonchan on 1/20/15.
 */
class Key extends Actor with DB with RSA {

  val log = Logging(context.system, this)

  override def preStart() = {
    log.debug("Starting")
  }

  def receive = {
    case SessionKeyRequest(c: ActorRef, s: ActorRef) => {

      if ((list.c == c.path.name) && (list.s == s.path.name)) {

        //        TODO: Encrypt token
        val k = list.k
        val t = EncryptedToken(c, s, k)

        //        Send session key reply
        sender() ! SessionKeyReply(c, s, k, t)

        log.info("{" + c.path.name + ", " + s.path.name + ", " + k + ", " + t + "}")
      }
      else {

        //        TODO: Encrypt token
        val k = EDKey(-1, -1)
        val t = EncryptedToken(c, s, k)

        //        Send session key reply
        sender() ! SessionKeyReply(c, s, k, EncryptedToken(c, s, k))

        log.info("{" + c.path.name + ", " + s.path.name + ", " + k + ", " + t + "}")
      }
    }
    case m => {
      log.warning("Received unknown message: " + m)
    }
  }
}
