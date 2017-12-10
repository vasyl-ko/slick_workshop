import scala.concurrent.duration.Duration
import io.circe._
import io.circe.generic.JsonCodec
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

package object model {
  @JsonCodec
  case class Film(id: Option[Long], title: String, duration: Duration,
                  directorId: Long, rating: Double)

  case class FilmToGenre(id: Option[Long], filmId: Long, genreId: Long)

  case class FilmToCast(id: Option[Long], filmId: Long, staffId: Long)

  case class FilmToCountry(id: Option[Long], filmId: Long, countryId: Long)

  case class Genre(id: Option[Long], title: String, description: Option[String])

  case class Staff(id: Option[Long], name: String, rate: Double, age: Int)

  case class Country(id: Option[Long], title: String)


  implicit val durationEncoder = new Encoder[Duration] {
    override def apply(a: Duration):Json = Json.fromLong(a.toSeconds)
  }

  implicit val durationDecoder = new Decoder[Json] {
    override def apply(c: HCursor) = 
  }
}