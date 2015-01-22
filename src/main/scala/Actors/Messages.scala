package Actors

import akka.actor.ActorRef

/**
 * Created by kasonchan on 1/20/15.
 */
case class SessionKeyRequest(c: ActorRef, s: ActorRef)

case class EncryptedToken(c: ActorRef, s: ActorRef, k: String)

case class SessionKeyReply(c: ActorRef, s: ActorRef, k: String,
                           t: EncryptedToken)

case class EncryptedMessage(a: String)

case class ServiceRequest(c: ActorRef, m: Service, t: EncryptedToken)

case class ServiceReply(c: ActorRef, s: ActorRef, r: String)

/**
 * Application server functions
 */
abstract class Service

case class add(x: Int, y: Int) extends Service

case class subtract(x: Int, y: Int) extends Service

case class multiple(x: Int, y: Int) extends Service

case class divide(x: Int, y: Int) extends Service

case class alpha(s: String) extends Service

case class numeric(s: String) extends Service
