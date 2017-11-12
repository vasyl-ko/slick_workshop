package model

import scala.concurrent.duration.Duration

case class Film(id: Option[Long], title: String, duration: Duration,
                directorId: Long, rating: Double)
