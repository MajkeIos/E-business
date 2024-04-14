import os
import discord
import requests

intents = discord.Intents.default()
intents.messages = True
intents.message_content = True

client = discord.Client(intents=intents)


@client.event
async def on_ready():
    print(f'Logged in as {client.user.name}')


@client.event
async def on_message(message):
    if message.author == client.user:
        return

    if message.channel.name == 'requests' and message.content.startswith('!'):
        request = message.content[1:] # remove ! from the beginning of the request
        ktor_url = 'http://localhost:8080/discord-bot-message'
        requests.post(ktor_url, data=request)


TOKEN = os.environ.get('DISCORD_BOT_TOKEN')
client.run(TOKEN)
