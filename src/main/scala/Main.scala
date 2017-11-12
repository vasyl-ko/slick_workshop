import model._
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val db =
    Database.forURL(
      "jdbc:postgresql://127.0.0.1/filmoteka?user=postgres&password=root"
    )

  override def main(args: Array[String]): Unit = {
    //init()

  }

  def init(): Unit = {
    Await.result(db.run(CountryTable.table.schema.create), Duration.Inf)
    Await.result(db.run(StaffTable.table.schema.create), Duration.Inf)
    Await.result(db.run(GenreTable.table.schema.create), Duration.Inf)
    Await.result(db.run(FilmTable.table.schema.create), Duration.Inf)
    Await.result(db.run(FilmToGenreTable.table.schema.create), Duration.Inf)
    Await.result(db.run(FilmToCastTable.table.schema.create), Duration.Inf)
    Await.result(db.run(FilmToCountryTable.table.schema.create), Duration.Inf)
  }
}
