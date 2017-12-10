import model._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag
import implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Duration

class FilmTable(tag: Tag) extends Table[Film](tag, "films") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val title = column[String]("title")
  val duration = column[Duration]("duration")
  val directorId = column[Long]("director_id")
  val rating = column[Double]("rating")

  val directorFk = foreignKey("director_id_fk", directorId, TableQuery[StaffTable])(_.id)

  def * = (id.?, title, duration, directorId, rating) <> (Film.apply _ tupled, Film.unapply)
}

object FilmTable {
  val table = TableQuery[FilmTable]
}

class FilmRepository(db: Database) {
  def create(film: Film, genreIds: List[Long],
             actorIds: List[Long], countryIds: List[Long]): Future[Film] = {
    val query =
      (FilmTable.table returning FilmTable.table += film).flatMap { insertedFilm =>
        (FilmToGenreTable.table ++= genreIds.map(genreId => FilmToGenre(None, insertedFilm.id.get, genreId))).andThen(
          (FilmToCountryTable.table ++= countryIds.map(countryId => FilmToCountry(None, insertedFilm.id.get, countryId))).andThen(
            FilmToCastTable.table ++= actorIds.map(actorId => FilmToCast(None, insertedFilm.id.get, actorId))
          )
        ).andThen(DBIO.successful(insertedFilm))
      }
    db.run(query)
  }


  def getById(filmId: Long): Future[(Film, Seq[Genre], Seq[Country], Seq[Staff])] = {
    val query = for {
      film <- FilmTable.table.filter(_.id === filmId).result.head
      genres <- GenreTable.table.filter(_.id in (
        FilmToGenreTable.table.filter(_.filmId === filmId).map(_.genreId))
      ).result
      countries <- CountryTable.table.filter(_.id in (
        FilmToCountryTable.table.filter(_.filmId === filmId).map(_.countryId))
      ).result
      actors <- StaffTable.table.filter(_.id in (
        FilmToCastTable.table.filter(_.filmId === filmId).map(_.staffId))
      ).result
    } yield (film, genres, countries, actors)

    db.run(query)
  }

  def delete(filmId: Long): Future[Int] =
    db.run(FilmTable.table.filter(_.id === filmId).delete)

  def update(film: Film): Future[Int] =
    db.run(FilmTable.table.filter(_.id === film.id).update(film))

  def getFilmWithDirector(filmId: Long): Future[(Film, Staff)] = {
    val query = FilmTable.table join StaffTable.table on (_.directorId === _.id)
    db.run(
      query.result.head
    )
  }
}



class FilmToGenreTable(tag: Tag) extends Table[FilmToGenre](tag, "film_to_genre") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val filmId = column[Long]("film_id")
  val genreId = column[Long]("genre_id")

  val filmIdFk = foreignKey("film_id_fk", filmId, TableQuery[FilmTable])(_.id)
  val genreIdFk = foreignKey("genre_id_fk", genreId, TableQuery[GenreTable])(_.id)

  def * = (id.?, filmId, genreId) <> (FilmToGenre.apply _ tupled, FilmToGenre.unapply)
}

object FilmToGenreTable {
  val table = TableQuery[FilmToGenreTable]
}


class FilmToCastTable(tag: Tag) extends Table[FilmToCast](tag, "film_to_cast") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val filmId = column[Long]("film_id")
  val staffId = column[Long]("staff_id")

  val filmIdFk = foreignKey("film_id_fk", filmId, TableQuery[FilmTable])(_.id)
  val staffIdFk = foreignKey("staff_id_fk", staffId, TableQuery[StaffTable])(_.id)

  def * = (id.?, filmId, staffId) <> (FilmToCast.apply _ tupled, FilmToCast.unapply)
}

object FilmToCastTable {
  val table = TableQuery[FilmToCastTable]
}


class FilmToCountryTable(tag: Tag) extends Table[FilmToCountry](tag, "film_to_country") {
  val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  val filmId = column[Long]("film_id")
  val countryId = column[Long]("staff_id")

  val filmIdFk = foreignKey("film_id_fk", filmId, TableQuery[FilmTable])(_.id)
  val countryIdFk = foreignKey("country_id_fk", countryId, TableQuery[CountryTable])(_.id)

  def * = (id.?, filmId, countryId) <> (FilmToCountry.apply _ tupled, FilmToCountry.unapply)
}

object FilmToCountryTable {
  val table = TableQuery[FilmToCountryTable]
}
