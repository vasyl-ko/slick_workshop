package model

import slick.lifted.Tag
import slick.jdbc.PostgresProfile.api._


case class Country(id: Option[Long], title: String)

class CountryTable(tag: Tag) extends Table[Country](tag, "countries") {
  val id = column[Long]("id", O.PrimaryKey)
  val title = column[String]("title")

  def * = (id.?, title) <> (Country.apply _ tupled, Country.unapply)
}

