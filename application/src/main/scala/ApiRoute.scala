import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._
import model.Film

trait ApiRoute extends MyDatabases with FailFastCirceSupport{
  val routes =
    pathSingleSlash {
      complete("hello")
    } ~
      pathPrefix("api") {
        pathPrefix("film") {
          pathSingleSlash{
            get {
              complete("all films")
            }
          } ~
          path("add") {
            post {
              complete("film added")
            }
          } ~
          pathPrefix(LongNumber) { id =>
            get {
              pathSingleSlash {
                onSuccess(filmRepository.getById(id.toLong)) {
                  (result, _, _, _) => complete(result.asJson)
                }
              }
            } ~
            post {
                path("delete") {
                  onSuccess(filmRepository.delete(id.toLong)) {
                    result => complete(StatusCodes.OK)
                  }
                } ~
                pathPrefix("edit" ) {
                  complete("")
                }
            }
          }
        }
      }
}
