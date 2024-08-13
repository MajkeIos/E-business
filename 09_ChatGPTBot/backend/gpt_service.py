import os
from openai import OpenAI

client = OpenAI(
    api_key= os.getenv("OPENAI_API_KEY")
)

def analyze_text(text):
    response = client.chat.completions.create(
        messages=[
            {
                "role": "user",
                "content": text,
            }
        ],
        model="gpt-3.5-turbo",
    )
    return response.choices[0].message.content