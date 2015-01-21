package Actors

import akka.actor.ActorRef

/**
 * Created by kasonchan on 1/20/15.
 */
case class SessionKeyRequest(c: ActorRef, s: ActorRef)

case class EncryptedToken(c: ActorRef, s: ActorRef, k: String)

case class SessionKeyReply(c: ActorRef, s: ActorRef, k: String, t: EncryptedToken)

case class EncryptedMessage(a: String)

case class ServiceRequest(c: ActorRef, m: EncryptedMessage, t: EncryptedToken)

case class ServiceReply(c: ActorRef, s: ActorRef, r: String)