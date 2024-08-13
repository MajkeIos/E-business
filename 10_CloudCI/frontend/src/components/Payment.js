import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

function Payment() {
    const [basketId] = useState(localStorage.getItem('basketId')); 
    const [amount, setAmount] = useState(0);
    const [paymentMethod, setPaymentMethod] = useState('CreditCard');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (basketId) {
            axios.get(`http://13.51.206.235:8080/v1/baskets/${basketId}/value`)
                .then(response => {
                    setAmount(response.data);
                })
                .catch(error => console.error('Error fetching basket value:', error));
        }
    }, [basketId]);

    const handlePayment = () => {
        axios.post(`http://13.51.206.235:8080/v1/payments?basketId=${basketId}&amount=${amount}&paymentMethod=${paymentMethod}`)
            .then(response => {
                console.log('Payment successful:', response.data);

                localStorage.removeItem('basketId');
                createNewBasket();
                displayMessage('Payment successful');
            })
            .catch(error => { 
                console.error('Error processing payment:', error)
                displayMessage('Payment failed');
            });
    };

    const createNewBasket = () => {
        axios.post('http://13.51.206.235:8080/v1/baskets')
            .then(response => {
                localStorage.setItem('basketId', response.data.id);
            })
            .catch(error => console.error('Error creating basket:', error));
    };

    const displayMessage = (message) => {
        setMessage(message);
        setTimeout(() => {
            setMessage('');
            navigate('/');
        }, 2000);
    };

    return (
        <div>
            <h2>Payment</h2>
            {message && <div className="message">{message}</div>}
            <div>
                <label htmlFor="amount">Amount:</label>
                <input id="amount" type="number" value={amount} onChange={e => setAmount(e.target.value)} />
            </div>
            <div>
                <label htmlFor="paymentMethod">Payment Method: </label>
                <select id="paymentMethod" value={paymentMethod} onChange={e => setPaymentMethod(e.target.value)}>
                    <option value="CreditCard">Credit Card</option>
                    <option value="PayPal">PayPal</option>
                </select>
            </div>
            <button onClick={handlePayment}>Pay</button>
            <Link to="/basket"><button>Go back to basket</button></Link>
            <Link to="/"><button>Go back to products</button></Link>
        </div>
    );
}

export default Payment;
