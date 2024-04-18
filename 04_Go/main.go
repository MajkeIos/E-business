package main

import (
	"e-business/shop/controllers"
	"e-business/shop/database"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()
	db, err := database.InitDatabase()
	if err != nil {
		e.Logger.Fatal(err)
	}

	productController := &controllers.ProductController{DB: db}

	e.POST("/v1/products", productController.CreateProduct)
	e.GET("/v1/products", productController.GetProducts)
	e.GET("/v1/products/:id", productController.GetProduct)
	e.PUT("/v1/products/:id", productController.UpdateProduct)
	e.DELETE("/v1/products/:id", productController.DeleteProduct)

	e.Logger.Fatal(e.Start(":8080"))
}
