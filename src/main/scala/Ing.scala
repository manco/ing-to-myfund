import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import MyFundModel.{Buy, Direction, Row, Sell}
import kantan.codecs.{Decoder, strings}
import kantan.codecs.strings.StringDecoder
import kantan.csv._
import kantan.csv.ops._

object Ing {
  private implicit val rowDecoder: RowDecoder[Row] = {

    implicit val buySellDecoder: Decoder[String, Direction, strings.DecodeError, strings.codecs.type] = {
      val stringToDirection: String => Direction = s => s.toUpperCase match {
        case "KUPNO" => Buy
        case "SPRZEDAŻ" => Sell
        case _ => throw new IllegalArgumentException
      }
      StringDecoder.from(StringDecoder.makeSafe("Direction")(stringToDirection))
    }
    implicit val localDateTime: Decoder[String, LocalDateTime, DecodeError, codecs.type] = {
      import kantan.csv.java8._
      localDateTimeDecoder(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss"))
    }
    implicit val bigDecimalComma: Decoder[String, BigDecimal, strings.DecodeError, strings.codecs.type] =
      StringDecoder.from(StringDecoder.makeSafe("BigDecimal")(s => BigDecimal(s.trim.replace(',', '.'))))

    RowDecoder.decoder(0, 2, 3, 4, 5, 7)(Row.apply)
  }

  def toMyFund[A: CsvSource](source: A): Iterator[ReadResult[Row]] =
    source.asCsvReader[Row](rfc.withCellSeparator(';'))
    .iterator
}