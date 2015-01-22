package Actors

import akka.actor.Actor
import akka.event.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
 * Created by kasonchan on 1/20/15.
 */
class Application extends Actor {
  val log = Logging(context.system, this)

  override def preStart() = {
    log.debug("Starting")
  }

  def receive = {
    case ServiceRequest(c, m, t) => {
      //      TODO: Encrypt message
      val r = m match {
        case add(x, y) => add(x, y).toString
        case subtract(x, y) => subtract(x, y).toString
        case multiple(x, y) => multiple(x, y).toString
        case divide(x, y) => divide(x, y).toString
        case alpha(s) => alpha(s)
        case numeric(s) => numeric(s)
      }

      val R = ServiceReply(c, t.s, r)

      val f = Future {
        sender() ! R
      }

      Await.result(f, 5 seconds)

      log.info("{" + c.path.name + ", " + m + ", " + t + "}")
    }
    case m => {
      log.warning("Received unknown message: " + m)
    }
  }

  /**
   * Application server functions
   */

  /**
   * Return the sum of two integers
   * @param x Integer
   * @param y Integer
   * @return x + y
   */
  def add(x: Int, y: Int): Int = x + y

  /**
   * Return the subtraction of two integers
   * @param x Integer
   * @param y Integer
   * @return x - y
   */
  def subtract(x: Int, y: Int): Int = x - y

  /**
   * Return the multiplication of two integers
   * @param x Integer
   * @param y Integer
   * @return x * y
   */
  def multiple(x: Int, y: Int): Int = x * y

  /**
   * Return the division of two integers if y does not equal to 0, otherwise return 0
   * @param x Integer
   * @param y Integer
   * @return x / y
   */
  def divide(x: Int, y: Int): Int = {
    if (y == 0) 0
    else x / y
  }

  /**
   * Return the alpha characters in the string
   * @param s String
   */
  def alpha(s: String): String = {
    val r = s.replaceAll("[^a-zA-Z]", "").toString
    r
  }

  /**
   * Return the numeric characters in the string
   * @param s String
   */
  def numeric(s: String): String = {
    val r = s.replaceAll("[^0-9]", "").toString
    r
  }
}
