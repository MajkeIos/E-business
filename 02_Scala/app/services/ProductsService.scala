package services

import models.Product

object ProductsService {
  private var products: Seq[Product] = Seq(
    Product(1, "Sample Product 1", "This is a sample product description 1", 9.99, List(CategoriesService.findById(1).get)),
    Product(2, "Sample Product 2", "This is a sample product description 2", 19.99, List(CategoriesService.findById(2).get)),
  )

  def findAll: Seq[Product] = products

  def findById(id: Long): Option[Product] = products.find(_.id == id)

  def findByCategory(category: String): Seq[Product] = {
    products.filter(_.categories.exists(_.name == category))
  }

  def create(product: Product): Unit = {
    products = products :+ product
  }

  def update(product: Product): Option[Product] = {
    findById(product.id).map { _ =>
      products = products.map { p =>
        if (p.id == product.id) product else p
      }
      product
    }
  }

  def delete(id: Long): Option[Product] = {
    val oldProduct = findById(id)
    products = products.filterNot(_.id == id)
    oldProduct
  }
}
