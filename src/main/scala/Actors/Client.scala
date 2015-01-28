package actors

import akka.actor.{Actor, ActorRef}

/**
 * Created by kasonchan on 1/20/15.
 * Mixin the log member into actor
 */
class Client extends Actor with akka.actor.ActorLogging {
  private val privateK = EDKey(139, 221)

  override def preStart() = {
    log.debug("Starting")
  }

  def receive = {
    data("Default", EDKey(-1, -1))
  }

  def data(ns: String, nk: EDKey): Receive = {
    case SessionKeyRequest(c: ActorRef, s: ActorRef) => {
      if (c == self) {
        context.become(data(s.path.name, EDKey(0, 0)))
        log.info("{" + c.path.name + ", " + s.path.name + "}")
      }
      else {
        context.become(data("NULL", EDKey(-1, -1)))
        log.info("{" + c.path.name + ", " + s.path.name + "}")
      }
    }

    case SessionKeyReply(c, s, k, t) => {
      context.become(data(s.path.name, k))

      if ((c == self) && (ns == s.path.name)) {
        val m = add(2, 3)
        val M = ServiceRequest(c, m, t)

        //      Send service request
        s ! M
      }
      else {
        log.warning("Received unknown session key reply message")
      }

      context.become(data("Default", EDKey(-1, -1)))
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
