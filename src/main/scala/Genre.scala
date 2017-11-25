

import model._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import scala.concurrent.Future


class GenreTable(tag: Tag) extends Table[Genre](tag, "genres") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val title = column[String]("title")
  val description = column[Option[String]]("description")

  def * = (id.?, title, description) <> (Genre.apply _ tupled, Genre.unapply)
}

object GenreTable{
  val table = TableQuery[GenreTable]
}

class GenreRepository(db: Database) {
  def create(genre: Genre): Future[Genre] =
    db.run(GenreTable.table returning GenreTable.table += genre)
}