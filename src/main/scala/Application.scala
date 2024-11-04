import firstdata.ClassCreator
import firstdata.structs.{Client, Order}

import java.io.{File, PrintWriter}

import scala.io.BufferedSource
import scala.util.Using
import scala.collection.mutable.{HashMap, LinkedHashMap}

object Application {
  def calculateResult(clientsFilePath: String, ordersFilePath: String): HashMap[String, Client] = {
    val clientsSource: BufferedSource = scala.io.Source.fromFile(clientsFilePath)
    val ordersSource: BufferedSource = scala.io.Source.fromFile(ordersFilePath)

    val clients = Using(clientsSource) {
      src =>
        val clients = src.getLines.toList.map(ClassCreator.createClientClass)
        for {c <- clients} yield (c.name, c)
    }

    val clientsMap: HashMap[String, Client] = clients.get.to(HashMap)

    val calculationMap: LinkedHashMap[String, HashMap[Int, Order]] = LinkedHashMap()

    Using(ordersSource) {
      src =>
        for (line <- src.getLines) {
          val newOrder: Order = ClassCreator.createOrderClass(line)
          val newOrderCustomHash: Int = newOrder.customHash
          val newOrderOriginalHash: Int = newOrder.originalHash

          var finished: Boolean = false
          var buyerName: String = ""
          var sellerName: String = ""

          if (calculationMap.isEmpty) calculationMap += newOrder.name -> HashMap(newOrderOriginalHash -> newOrder)
          for (client <- calculationMap) {
            client._2.get(newOrderCustomHash) match {
              case Some(oldOrder) => if (newOrder.name != oldOrder.name && !finished) {
                val buyerAndSellerPair: (Client, Client) = {
                  if (newOrder.operationType != "b") {
                    (clientsMap(oldOrder.name), clientsMap(newOrder.name))
                  } else {
                    (clientsMap(newOrder.name), clientsMap(oldOrder.name))
                  }
                }
                val buyer: Client = buyerAndSellerPair._1
                val seller: Client = buyerAndSellerPair._2

                val newBuyerStock = buyer.stocks
                newBuyerStock += newOrder.stockName -> (buyer.stocks(newOrder.stockName) + newOrder.count)
                clientsMap(buyer.name) = Client(
                  buyer.name,
                  buyer.balanceUSD - (newOrder.count * newOrder.price),
                  newBuyerStock
                )
                val newSellerStock = seller.stocks
                newSellerStock += newOrder.stockName -> (seller.stocks(newOrder.stockName) - newOrder.count)
                clientsMap(seller.name) = Client(
                  seller.name,
                  seller.balanceUSD + (newOrder.count * newOrder.price),
                  newSellerStock
                )

                buyerName = buyer.name
                sellerName = seller.name
                finished = true
              }
              case None => calculationMap.get(newOrder.name) match {
                case Some(m) => m += newOrderOriginalHash -> newOrder
                case None => calculationMap += newOrder.name -> HashMap(newOrderOriginalHash -> newOrder)
              }
            }
          }

          if (finished) {
            calculationMap(buyerName).filterInPlace { case (key, _) => key != newOrderCustomHash }
            calculationMap(sellerName).filterInPlace { case (key, _) => key != newOrderCustomHash }
          }
        }
    }

    clientsMap
  }

  def writeResult(path: String, result: HashMap[String, Client]): Unit = {
    val writer = new PrintWriter(new File(path))
    Using(writer) {
      writer =>
        val sortedResult = result.values.toArray.sortBy(_.name)
        for (client <- sortedResult) {
          writer.println(
            client.name
              + " " + client.balanceUSD
              + " " + client.stocks("A")
              + " " + client.stocks("B")
              + " " + client.stocks("C")
              + " " + client.stocks("D"))
        }
    }
  }
}