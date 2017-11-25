import java.util.concurrent.TimeUnit

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._

package object implicits {

  implicit val durationToLongMapper =
    MappedColumnType.base[Duration, Long](
      (d: Duration) => d.toSeconds,
      (l: Long) => FiniteDuration(l, TimeUnit.SECONDS))

  implicit val localDateTimeToJavaSqlTimestampMapper =
    MappedColumnType.base[java.time.LocalDateTime, java.sql.Timestamp](java.sql.Timestamp.valueOf, _.toLocalDateTime)

}
