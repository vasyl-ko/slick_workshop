import model._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.Future


class StaffTable(tag: Tag) extends Table[Staff](tag, "staff") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val name = column[String]("name")
  val rate = column[Double]("rate")
  val age = column[Int]("age")

  def * = (id.?, name, rate, age) <> (Staff.apply _ tupled, Staff.unapply)
}

object StaffTable {
  val table = TableQuery[StaffTable]
}

class StaffRepository(db: Database) {
  def create(staff: Staff): Future[Staff] =
    db.run(StaffTable.table returning StaffTable.table += staff)
}
