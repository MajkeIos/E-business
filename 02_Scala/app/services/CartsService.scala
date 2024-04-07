package services

import models.Cart

object CartsService {
  private var carts: Seq[Cart] = Seq()

  def findAll(): Seq[Cart] = carts

  def findById(id: Long): Option[Cart] = carts.find(_.id == id)

  def create(cart: Cart): Unit = {
    carts = carts :+ cart
  }

  def update(cart: Cart): Option[Cart] = {
    findById(cart.id).map { _ =>
      carts = carts.map { c =>
        if (c.id == cart.id) cart else c
      }
      cart
    }
  }

  def delete(id: Long): Option[Cart] = {
    val oldCart = findById(id)
    carts = carts.filterNot(_.id == id)
    oldCart
  }
}