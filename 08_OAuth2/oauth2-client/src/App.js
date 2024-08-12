import React, { useState } from 'react';
import axios from 'axios';

function App() {
    const [registerUsername, setRegisterUsername] = useState('');
    const [registerPassword, setRegisterPassword] = useState('');
    const [loginUsername, setLoginUsername] = useState('');
    const [loginPassword, setLoginPassword] = useState('');
    const [registerMessage, setRegisterMessage] = useState('');
    const [loginMessage, setLoginMessage] = useState('');
    const [registerMessageColor, setRegisterMessageColor] = useState('');
    const [loginMessageColor, setLoginMessageColor] = useState('');

    const register = async () => {
        try {
            const response = await axios.post('http://localhost:8080/v1/auth/register', {
                username: registerUsername,
                password: registerPassword
            });
            console.log(response.data);
            setRegisterMessage('Registration successful!');
            setRegisterMessageColor('green');
        } catch (error) {
            if (error.response) {
                setRegisterMessage(error.response.data.message || 'An error occurred');
            } else if (error.request) {
                setRegisterMessage('No response received from the server');
            } else {
                setRegisterMessage('An error occurred');
            }
            setRegisterMessageColor('red');
        }
    };

    const login = async () => {
        try {
            const response = await axios.post('http://localhost:8080/v1/auth/login', {
                username: loginUsername,
                password: loginPassword
            });
            console.log(response.data);
            setLoginMessage('Login successful!');
            setLoginMessageColor('green');
        } catch (error) {
            if (error.response) {
                setLoginMessage(error.response.data.message || 'An error occurred');
            } else if (error.request) {
                setLoginMessage('No response received from the server');
            } else {
                setLoginMessage('An error occurred');
            }
            setLoginMessageColor('red');
        }
    };

    return (
        <div>
            <div>
                <h1>Register</h1>
                <input
                    type="text"
                    value={registerUsername}
                    onChange={(e) => setRegisterUsername(e.target.value)}
                    placeholder="Username"
                />
                <input
                    type="password"
                    value={registerPassword}
                    onChange={(e) => setRegisterPassword(e.target.value)}
                    placeholder="Password"
                />
                <button onClick={register}>Register</button>
                {registerMessage && <p style={{ color: registerMessageColor }}>{registerMessage}</p>}
            </div>

            <div>
                <h1>Login</h1>
                <input
                    type="text"
                    value={loginUsername}
                    onChange={(e) => setLoginUsername(e.target.value)}
                    placeholder="Username"
                />
                <input
                    type="password"
                    value={loginPassword}
                    onChange={(e) => setLoginPassword(e.target.value)}
                    placeholder="Password"
                />
                <button onClick={login}>Login</button>
                {loginMessage && <p style={{ color: loginMessageColor }}>{loginMessage}</p>}
            </div>
        </div>
    );
}

export default App;