import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import MyFundModel.{Buy, Direction, Row, Sell}
import kantan.codecs.strings.StringEncoder
import kantan.codecs.{Encoder, strings}
object MyFundModel {
  case class Row(date: LocalDateTime, name: String, direction: Direction, number: Int, price: BigDecimal, commision: BigDecimal)
  sealed trait Direction
  case object Buy extends Direction
  case object Sell extends Direction
}
object MyFundOps {
  import kantan.csv._
  import kantan.csv.java8._
  import kantan.csv.ops._
  private implicit val localDateTimeE: Encoder[String, LocalDateTime, codecs.type] = localDateTimeEncoder(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))//2009-02-11 09:01:14
  private implicit val buySellEncoder: Encoder[String, Direction, strings.codecs.type] = {
    val directionToString: Direction => String = {
      case Buy => "KUPNO"
      case Sell => "SPRZEDAŻ"
    }
    StringEncoder.from(directionToString)
  }
  private implicit val rowEncoder: RowEncoder[Row] = RowEncoder.caseEncoder(0, 1, 2, 3, 4, 5)(Row.unapply)

  implicit class RowOps(val rows: Iterator[Row]) extends AnyVal {
    def writeCsv[A: CsvSink](sink: A): Unit = {
      val writer = sink.asCsvWriter[Row](rfc.withCellSeparator(';'))
      rows.foreach(writer.write)
      writer.close()
    }
  }
}
