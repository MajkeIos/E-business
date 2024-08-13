import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [input, setInput] = useState('');
  const [messages, setMessages] = useState([]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userMessage = { sender: 'user', text: input };
    const newMessages = [...messages, userMessage];
    setMessages(newMessages);
    setInput('');

    try {
      const res = await axios.post('http://localhost:5000/analyze', { text: input });
      const botMessage = { sender: 'bot', text: res.data.response };

      setMessages([...newMessages, botMessage]);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <h1>Chat with AI</h1>
        <div className="chat-box">
          {messages.map((msg, index) => (
            <div key={index} className={msg.sender === 'user' ? 'message user' : 'message bot'}>
              <p>{msg.text}</p>
            </div>
          ))}
        </div>
        <form onSubmit={handleSubmit}>
          <input 
            type="text" 
            value={input}
            onChange={(e) => setInput(e.target.value)}
          />
          <button type="submit">Send</button>
        </form>
      </header>
    </div>
  );
}
export default App;