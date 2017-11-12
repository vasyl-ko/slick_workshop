import java.util.concurrent.TimeUnit

import slick.ast.BaseTypedType
import slick.jdbc.JdbcType
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.duration._

package object model {

  implicit val durationToLongMapper =
    MappedColumnType.base[Duration, Long](
      (d: Duration) => d.toSeconds,
      (l: Long) => FiniteDuration(l, TimeUnit.SECONDS))
}
