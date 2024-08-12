describe('Shop Application Tests', () => {
    beforeEach(() => {
        cy.visit('http://localhost:3000');
    });

    it('should load the products page successfully', () => {
        cy.contains('Shop');
        cy.contains('Products');
    });

    it('should display the list of products', () => {
        cy.get('ul').should('exist');
        cy.get('li').should('have.length.greaterThan', 0);
    });

    it('should add a product to the basket', () => {
        cy.get('button').first().click();
        cy.get('.message').should('contain', 'Product added to basket');
    });

    it('should navigate to the basket', () => {
        cy.get('button').first().click();
        cy.contains('Go to Basket').click();
        cy.url().should('include', '/basket');
        cy.contains('Basket');
    });

    it('should display items in the basket', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.get('ul').should('exist');
        cy.get('li').should('exist');
    });

    it('should display correct item quantity in the basket', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.get('li').first().should('contain', '1x');
    });

    it('should navigate to the payment page', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.url().should('include', '/payment');
        cy.contains('Payment');
    });

    it('should display the correct amount in payment', () => {
        const mockAmount = 10; 
        cy.intercept('GET', `http://localhost:8080/v1/baskets/*/value`, {
            statusCode: 200,
            body: mockAmount
        }).as('getBasketValue');
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.wait('@getBasketValue');
        cy.get('input[type="number"]').should('have.value', mockAmount.toString());
    });

    it('should handle payment success', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.intercept('POST', 'http://localhost:8080/v1/payments*', {
            statusCode: 200
        }).as('paymentRequest');
        cy.get('button').contains('Pay').click();
        cy.wait('@paymentRequest');
        cy.contains('Payment successful');
    });

    it('should navigate back to product page after payment', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.intercept('POST', 'http://localhost:8080/v1/payments*', {
            statusCode: 200
        }).as('paymentRequest');
        cy.get('button').contains('Pay').click();
        cy.wait('@paymentRequest');
        cy.url().should('include', '/');
        cy.contains('Products');
    });

    it('should allow changing payment method', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.get('select').select('PayPal').should('have.value', 'PayPal');
    });

    it('should handle multiple items in the basket', () => {
        const testProductId = 1; 
        for (let i = 0; i < 3; i++) {
            addTestProductToBasket(testProductId);
        }
        cy.contains('Go to Basket').click();
        cy.get('li').should('have.length', 1);
        cy.get('li').first().should('contain', '3x');
    });

    it('should clear basket and confirm payment', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.intercept('POST', 'http://localhost:8080/v1/payments*', {
            statusCode: 200
        }).as('paymentRequest');
        cy.get('button').contains('Pay').click();
        cy.wait('@paymentRequest');
        cy.get('ul').should('not.exist');
    });

    it('should show an error message for failed payment', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.intercept('POST', 'http://localhost:8080/v1/payments*', {
            statusCode: 500
        }).as('paymentRequest');
        cy.get('button').contains('Pay').click();
        cy.wait('@paymentRequest');
        cy.contains('Payment failed');
    });

    it('should show an error message when no basket exists', () => {
        cy.get('button').contains('Go to Basket').click(); 
        cy.contains('Basket').should('exist');
    });

    it('should show an error message when adding products to the basket fails', () => {
        cy.intercept('POST', 'http://localhost:8080/v1/baskets/*/items*', {
            statusCode: 500,
        }).as('addItemRequest');
        
        cy.get('button').first().click();
        cy.wait('@addItemRequest');
        
        cy.get('.message').should('contain', 'Failed to add product to basket. Please try again');
    });

    it('should show a message when the basket is empty', () => {
        cy.intercept('GET', '/v1/baskets/*', {
            statusCode: 200,
            body: {
                items: []
            }
        });
        cy.get('button').contains('Go to Basket').click(); 
        cy.contains('Loading...').should('not.exist');
        cy.get('ul').should('not.exist'); 
        cy.contains('Basket is empty').should('be.visible');
    });

    it('should navigate back to products from basket', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Go back to products').click();
        cy.url().should('include', '/');
        cy.contains('Products');
    });

    it('should keep the basket state after navigating to payment and back', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.intercept('POST', 'http://localhost:8080/v1/payments*', {
            statusCode: 200,
            body: { success: true }
        }).as('paymentRequest');
        cy.get('button').contains('Pay').click();
        cy.wait('@paymentRequest');
        cy.contains('Go back to products').click();
        cy.contains('Go to Basket').click();
        cy.get('li').should('exist');
    });

    it('should maintain the current amount value when navigating back from payment', () => {
        const mockAmount = 10; 
        cy.intercept('GET', `http://localhost:8080/v1/baskets/*/value`, {
            statusCode: 200,
            body: mockAmount
        }).as('getBasketValue');
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.contains('Proceed to Payment').click();
        cy.wait('@getBasketValue');
        cy.get('input[type="number"]').should('have.value', mockAmount);
        cy.get('button').contains('Go back to basket').click();
        cy.contains('Proceed to Payment').click();
        cy.get('input[type="number"]').should('have.value', mockAmount);
    });

    it('should show no products available when products list is empty', () => {
        cy.intercept('GET', 'http://localhost:8080/v1/products', {
            statusCode: 200,
            body: [] 
        }).as('getProducts');
        cy.reload();
        cy.wait('@getProducts');
        cy.contains('No products available');
    });

    it('should show loading state while fetching products', () => {
        cy.intercept('GET', 'http://localhost:8080/v1/products', {
            delay: 1000,
            statusCode: 200,
            body: []
        }).as('getProducts');
        cy.reload();
        cy.contains('Loading...');
        cy.wait('@getProducts');
        cy.contains('Products');
    });

    it('should navigate through product and basket pages', () => {
        addTestProductToBasket(1);
        cy.contains('Go to Basket').click();
        cy.url().should('include', '/basket');
        cy.contains('Proceed to Payment').click();
        cy.url().should('include', '/payment');
        cy.contains('Go back to basket').click();
        cy.url().should('include', '/basket');
        cy.contains('Go back to products').click();
        cy.url().should('include', '/');
    });

    it('should clear success message after a timeout', () => {
        addTestProductToBasket(1);
        cy.get('.message').should('contain', 'Product added to basket');
        cy.get('.message').should('not.exist');
    });
});

function addTestProductToBasket(productId) {
    cy.get('button').contains('Buy').first().click();
}