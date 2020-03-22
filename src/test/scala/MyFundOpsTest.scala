import java.io.ByteArrayOutputStream
import java.time.LocalDateTime

import MyFundModel.{Buy, Row, Sell}
import utest._

object MyFundOpsTest extends TestSuite {
  override val tests = Tests {
    test("encode in myfund format") {
      import MyFundOps._
      val rows: Iterator[Row] = List[Row](
        Row(
          LocalDateTime.of(2009, 2, 11, 9, 1, 14),
          "PEKAO", Buy, 90, BigDecimal(101.3), BigDecimal(35.57)
        ),
        Row(
          LocalDateTime.of(2009, 6, 12, 0, 0),
          "POLAQUA", Sell, 200, BigDecimal(23.5), BigDecimal(18.33)
        )
      ).iterator

      val result = {
        val buf = new ByteArrayOutputStream(128)
        rows.writeCsv(buf)
        buf.toString
      }

      result ==> "2009-02-11 09:01:14;PEKAO;KUPNO;90;101.3;35.57\r\n2009-06-12 00:00:00;POLAQUA;SPRZEDAŻ;200;23.5;18.33\r\n"
    }
  }
}
