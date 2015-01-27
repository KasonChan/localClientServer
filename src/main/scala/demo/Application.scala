package demo

import actors._
import akka.actor.{ActorRef, ActorSystem, Inbox, Props}
import akka.util.Timeout

import scala.concurrent.duration._

/**
 * Created by kasonchan on 1/20/15.
 */
object Application {

  def main(args: Array[String]) {

    // Declare actor system
    val system = ActorSystem("ClientServer")

    // Declare inbox of the actor system
    val inbox = Inbox.create(system);

    val c1: ActorRef = system.actorOf(Props[Client], "c1")
    val c2: ActorRef = system.actorOf(Props[Client], "c2")
    val c3: ActorRef = system.actorOf(Props[Client], "c3")

    val k: ActorRef = system.actorOf(Props(classOf[Key]), "k")
    val a: ActorRef = system.actorOf(Props[Application], "a")

    implicit val timeout = Timeout(5.second)

    c1.tell(SessionKeyRequest(c1, a), c1)
    k.tell(SessionKeyRequest(c1, a), c1)

    c2.tell(SessionKeyRequest(c1, a), c2)
    k.tell(SessionKeyRequest(c1, a), c2)

    c3.tell(SessionKeyRequest(c3, a), c3)
    k.tell(SessionKeyRequest(c3, a), c3)

    system.shutdown()
  }
}
