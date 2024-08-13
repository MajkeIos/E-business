from flask import Flask, request, jsonify
from flask_cors import CORS
from gpt_service import analyze_text

app = Flask(__name__)
CORS(app)

@app.route('/analyze', methods=['POST'])
def analyze():
    data = request.json
    user_input = data.get('text')
    if not user_input:
        return jsonify({'error': 'No text provided.'}), 400

    response = analyze_text(user_input)
    return jsonify({'response': response})

if __name__ == '__main__':
    app.run(port=5000, debug=True)