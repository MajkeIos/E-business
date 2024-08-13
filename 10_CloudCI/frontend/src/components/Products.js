import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function Products() {
    const [products, setProducts] = useState([]);
    const [basket, setBasket] = useState(null);
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        axios.get('http://13.51.206.235:8080/v1/products')
            .then(response => {
                setProducts(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching products:', error);
                setLoading(false);
            });

        const savedBasketId = localStorage.getItem('basketId');
        if (savedBasketId) {
            axios.get(`http://13.51.206.235:8080/v1/baskets/${savedBasketId}`)
                .then(response => setBasket(response.data))
                .catch(error => console.error('Error fetching basket:', error));
        } else {
            axios.post('http://13.51.206.235:8080/v1/baskets')
                .then(response => {
                    setBasket(response.data);
                    localStorage.setItem('basketId', response.data.id);
                })
                .catch(error => console.error('Error creating basket:', error));
        }
    }, []);

    const addToBasket = (productId) => {
        if (!basket) {
            console.error("No basket set");
            return;
        }

        axios.post(`http://13.51.206.235:8080/v1/baskets/${basket.id}/items?productId=${productId}&quantity=1`)
            .then(response => {
                setBasket(response.data);
                setMessage('Product added to basket');
                setTimeout(() => setMessage(''), 3000);
            })
            .catch(error => {
                console.error('Error adding item to basket:', error);
                setMessage('Failed to add product to basket. Please try again');
                setTimeout(() => setMessage(''), 3000);
            });
    };

    return (
        <div>
            <h2>Products</h2>
            {message && <div className="message">{message}</div>}

            {loading ? (
                <p>Loading...</p>
            ) : (
                <ul>
                    {products.length > 0 ? (
                        products.map(product => (
                            <li key={product.id}>
                                {product.name} - ${product.price}
                                <button onClick={() => addToBasket(product.id)}>Buy</button>
                            </li>
                        ))
                    ) : (
                        <p>No products available</p>
                    )}
                </ul>
            )}

            <Link to="/basket"><button>Go to Basket</button></Link>
        </div>
    );
}

export default Products;