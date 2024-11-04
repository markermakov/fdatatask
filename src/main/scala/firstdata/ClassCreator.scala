package firstdata

import firstdata.structs.{Client, Order}

import scala.annotation.tailrec
import scala.collection.mutable

object ClassCreator {
  def createClientClass(str: String): Client = {
    val arrParams: Array[String] = parseString(str.toList).filter(_.nonEmpty)
    Client(arrParams(0), arrParams(1).toInt,
      mutable.HashMap("A" -> arrParams(2).toInt, "B" -> arrParams(3).toInt, "C" -> arrParams(4).toInt, "D" -> arrParams(5).toInt))
  }

  def createOrderClass(str: String): Order = {
    val arrParams: Array[String] = parseString(str.toList).filter(_.nonEmpty)
    Order(arrParams(0), arrParams(1), arrParams(2), arrParams(3).toInt, arrParams(4).toInt)
  }

  @tailrec
  private def parseString(str: List[Char], acc: Array[String] = Array(), accArg: String = ""): Array[String] = {
    if (str.tail == Nil) acc ++ Array(accArg ++ str.head.toString)
    else if (str.head.isLetterOrDigit) parseString(str.tail, acc, accArg :+ str.head)
    else parseString(str.tail, acc :+ accArg)
  }
}