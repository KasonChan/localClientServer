package Main

import Actors._
import akka.actor.{ActorRef, ActorSystem, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

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

    val f1 = Future {
      k.tell(SessionKeyRequest(c1, a), c1)
    }

    f1 onComplete {
      case Success(s) => println("main: c1 sent and received k")
      case Failure(t) => println("main: " + t.getMessage)
    }

    system.shutdown()
  }
}
