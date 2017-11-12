package model

import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

case class Genre(id: Option[Long], title: String, description: Option[String])

class GenreTable(tag: Tag) extends Table[Genre](tag, "genres") {
  val id = column[Long]("id", O.PrimaryKey)
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