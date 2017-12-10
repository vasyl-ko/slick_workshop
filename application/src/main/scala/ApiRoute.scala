import akka.http.scaladsl.server.Directives._

trait ApiRoute {
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
          pathPrefix(Segment) { id =>
            get {
              pathSingleSlash {
                complete(s"here your film with id: $id")
              }
            } ~
            post {
                path("delete") {
                  complete(s"film with id: $id was deleted")
                } ~
                path("edit") {
                  complete(s"film with id: $id was edited")
                }
            }
          }
        }
      }
}
