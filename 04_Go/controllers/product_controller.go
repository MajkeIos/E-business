package controllers

import (
	"e-business/shop/models"
	"net/http"

	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
)

type ProductController struct {
	DB *gorm.DB
}

func (pc *ProductController) CreateProduct(c echo.Context) error {
	product := new(models.Product)
	if err := c.Bind(product); err != nil {
		return echo.NewHTTPError(http.StatusBadRequest, err.Error())
	}
	pc.DB.Create(&product)
	return c.JSON(http.StatusCreated, product)
}

func (pc *ProductController) GetProducts(c echo.Context) error {
	var products []models.Product
	if result := pc.DB.Find(&products); result.Error != nil {
		return echo.NewHTTPError(http.StatusInternalServerError, result.Error.Error())
	}
	return c.JSON(http.StatusOK, products)
}

func (pc *ProductController) GetProduct(c echo.Context) error {
	id := c.Param("id")
	var product models.Product
	result := pc.DB.First(&product, id)
	if result.Error != nil {
		return echo.NewHTTPError(http.StatusNotFound, result.Error.Error())
	}
	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) UpdateProduct(c echo.Context) error {
	id := c.Param("id")
	var product models.Product
	if err := pc.DB.First(&product, id).Error; err != nil {
		return echo.NewHTTPError(http.StatusNotFound, err.Error())
	}
	if err := c.Bind(&product); err != nil {
		return echo.NewHTTPError(http.StatusBadRequest, err.Error())
	}
	pc.DB.Save(&product)
	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) DeleteProduct(c echo.Context) error {
	id := c.Param("id")
	result := pc.DB.Delete(&models.Product{}, id)
	if result.Error != nil {
		return echo.NewHTTPError(http.StatusInternalServerError, result.Error.Error())
	}
	return c.NoContent(http.StatusNoContent)
}
