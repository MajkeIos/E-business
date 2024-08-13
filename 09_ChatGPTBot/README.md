# ChatGPT bot

Files necessary to complete ninth exercise for e-business lab.

### Requirements
- Python >= 3.7
- Node.js >= 18.0
- npm >= 6

### Installation

- **Backend**
1. Go to backend directory, create a virtual environment and activate it:

    ```bash
    cd backend
    python -m venv venv
    source venv/bin/activate 
    ```

2. Install the required Python packages:

    ```bash
    pip install -r requirements.txt
    ```

3. Export OPENAI_API_KEY env variable with your OpenAI API key:

    ```bash
    export OPENAI_API_KEY='<YOUR_KEY>'
    ```

4. Start the backend server:

    ```bash
    python app.py
    ```

- **Frontend**
1. Navigate to the `frontend` directory:

    ```bash
    cd frontend
    ```

2. Install the required npm packages:

    ```bash
    npm install
    ```

3. Start the frontend server:

    ```bash
    npm start
    ```

The application should now be running, and you can access it by navigating to `http://localhost:3000` in your web browser.
