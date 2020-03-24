import java.io.InputStream
import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter

import MyFundModel.{Buy, Direction, Row, Sell}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element

object BPS {

  private val browser = JsoupBrowser()

  import net.ruippeixotog.scalascraper.dsl.DSL._
  import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
  import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

  def toMyFund(source: InputStream): Iterator[Row] = {
    val txRows =
      browser.parseInputStream(source) >>
        element("#transactions-table tbody") >>
        elementList("tr") >>
        texts("td")

    txRows.iterator.map(cellsToRow)
  }

  private def cellsToRow(cells: Iterable[String]) = {
    val values = cells.toArray

    val price = BigDecimal(values(3).trim.replace(',', '.'))
    val amount = values(2).toInt

    Row(
      LocalDate.parse(values(4), DateTimeFormatter.ofPattern("dd.MM.yyyy")).atStartOfDay(),
      values(0),
      stringToDirection(values(1)),
      amount,
      price,
      price * amount * 0.0013
    )
  }

  private val stringToDirection: String => Direction = s => s.toUpperCase match {
    case "K" => Buy
    case "S" => Sell
    case _ => throw new IllegalArgumentException
  }
}
