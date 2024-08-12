import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function Basket() {
    const [basket, setBasket] = useState(null);

    useEffect(() => {
        const savedBasketId = localStorage.getItem('basketId');
        if (savedBasketId) {
            // Fetch basket data
            axios.get(`http://localhost:8080/v1/baskets/${savedBasketId}`)
                .then(response => setBasket(response.data))
                .catch(error => console.error('Error fetching basket:', error));
        }
    }, []);

    return (
        <div>
            <h2>Basket</h2>
            {!basket ? (
                <p>Loading...</p>
            ) : (
                <>
                    {basket.items.length > 0 ? (
                        <ul>
                            {basket.items.map(item => (
                                <li key={item.id}>
                                    {item.quantity}x {item.product.name}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>Basket is empty</p>
                    )}
                </>
            )}
            <Link to="/payment">
                <button disabled={!basket || basket.items.length === 0}>Proceed to Payment</button>
            </Link>
            <Link to="/">
                <button>Go back to products</button>
            </Link>
        </div>
    );
}

export default Basket;