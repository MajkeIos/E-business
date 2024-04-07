package services

import models.Category

object CategoriesService {
  private var categories: Seq[Category] = Seq(
    Category(1, "Electronics"),
    Category(2, "Clothing")
  )

  def findAll: Seq[Category] = categories

  def findById(id: Long): Option[Category] = categories.find(_.id == id)

  def create(category: Category): Unit = {
    categories = categories :+ category
  }

  def update(category: Category): Option[Category] = {
    findById(category.id).map { _ =>
      categories = categories.map { c =>
        if (c.id == category.id) category else c
      }
      category
    }
  }

  def delete(id: Long): Option[Category] = {
    val oldCategory = findById(id)
    categories = categories.filterNot(_.id == id)
    oldCategory
  }
}