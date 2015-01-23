package demo

import actors._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
object Application {

  def main(args: Array[String]) {

    // Declare actor system
    val system = ActorSystem("ClientServer")

    val c1: ActorRef = system.actorOf(Props[Client], "c1")
    val k: ActorRef = system.actorOf(Props(classOf[Key]), "k")
    val a: ActorRef = system.actorOf(Props[Application], "a")

    implicit val timeout = Timeout(5.second)

    val f1 = Future {
      k.tell(SessionKeyRequest(c1, a), c1)
    }

    Await.result(f1, 5 seconds)

    system.shutdown()
  }
}
