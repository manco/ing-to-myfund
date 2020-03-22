import java.time.{LocalDateTime, Month}

import MyFundModel.{Buy, Row}
import utest._

object IngTest extends utest.TestSuite {

  val tests = Tests {
    test("that can produce myfund Row"){
      val input = "26-08-2019 09-00-00;386864383;CDPROJEKT;Kupno;10;236,00;2360,00;7,08;2367,08"

      val rows = Ing.toMyFund(input).toList

      rows.head ==> Right(Row(
        LocalDateTime.of(2019, Month.AUGUST, 26, 9, 0), // "2019-08-26 09:00:00"
        "CDPROJEKT", Buy, 10, BigDecimal(236.00), BigDecimal(7.08)))
      }
    }
}
