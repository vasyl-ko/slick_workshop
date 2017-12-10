//import model._
//import slick.jdbc.PostgresProfile.api._
//
//import scala.concurrent.Await
//import scala.concurrent.duration._
//
//object Main {
//  val db = Database.forURL(
//    "jdbc:postgresql://127.0.0.1/filmoteka?user=postgres&password=root"
//  )
//
//  val countryRepository = new CountryRepository(db)
//  val genreRepository = new GenreRepository(db)
//  val staffRepository = new StaffRepository(db)
//  val filmRepository = new FilmRepository(db)
//
//  def main(args: Array[String]): Unit = {
////    init()
//    databaseFill()
//  }
//
//  def init(): Unit = {
//    Await.result(db.run(CountryTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(StaffTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(GenreTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(FilmTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(FilmToGenreTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(FilmToCastTable.table.schema.create), Duration.Inf)
//    Await.result(db.run(FilmToCountryTable.table.schema.create), Duration.Inf)
//  }
//
//  def databaseFill(): Unit = {
//    for (i <- 1 to 5) {
//      Await.result(countryRepository.create(Country(Some(i), s"Country #$i")), Duration.Inf)
//    }
//
//    for (i <- 1 to 10) {
//      Await.result(genreRepository.create(Genre(Some(i), s"Genre #$i", Some(s"Description #$i"))), Duration.Inf)
//    }
//
//    for (i <- 1 to 20) {
//      Await.result(staffRepository.create(Staff(Some(i), s"Staff #$i", i % 5 + 1, 20 + i)), Duration.Inf)
//    }
//
//    for (i <- 1 to 100) {
//      Await.result(filmRepository.create(
//        Film(Some(i), s"Film #$i", (i * 10).seconds, i % 5 + 1, i % 5), List(i % 10 + 1, i % 5 + 1), List(i % 7 + 1, i % 10 + 1), List(i % 5 + 1)
//      ), Duration.Inf)
//    }
//  }
//}
