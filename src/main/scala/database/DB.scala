package database

import actors.EDKey

case class ID(c: String, s: String, k: EDKey)

/**
 * Created by kasonchan on 1/26/15.
 */
trait DB {
  val k = EDKey(139, 221)
  val list = ID("c1", "a", k)
}
