package encryption

/**
 * Created by kasonchan on 1/26/15.
 */
trait RSA {
  /**
   * Encrypts the message m with key (e, n) and returns the encrypted message *
   * @param e Integer
   * @param n Integer
   * @param m String
   * @return encryptedMsg List[BigInt]
   */
  def encrypt(e: Int, n: Int = 221, m: String): String = {
    val msg = stringToBigInt(m)

    val encryptedMsg = msg.map(m => (m.pow(e)) % n)

    bigIntToString(encryptedMsg)
  }

  /**
   * Decrypts the message m with the key (d, n) and returns the decrypted
   * message *
   * @param d Integer
   * @param n Integer
   * @param m List[BigInt]
   * @return decryptedMsg List[BigInt]
   */
  def decrypt(d: Int, n: Int = 221, m: String): String = {
    val msg = stringToBigInt(m)

    val decryptedMsg = msg.map(m => (m.pow(d)) % n)

    bigIntToString(decryptedMsg)
  }

  /**
   * Converts a string m to a list of BigInt *
   * @param m String
   * @return List[BigInt]
   */
  def stringToBigInt(m: String): List[BigInt] = {
    m.map(m => BigInt(m)).toList
  }

  /**
   * Convert a list of BigInt to String*
   * @param l List[BigInt]
   * @return String
   */
  def bigIntToString(l: List[BigInt]): String = {
    (l.map(m => m.toChar)).mkString
  }
}
