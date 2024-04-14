# Kotlin

Files necessary to complete third exercise for e-business lab.

### Requirements
- Python 3.x
- Ktor 2.3.10 
- Kotlin 1.9.23

### Run
To run the application on you machine execute following command: \
`./gradlew run`

After it starts you can access the application on `localhost:8080`

### Discord integration
1. Create Discord Bot on [Discord Developer Portal](https://discord.com/developers/applications)
2. Copy bot token and export it as `DISCORD_BOT_TOKEN` environment variable
3. Invite bot to your Discord server
4. Add new webhook to channel you want to send messages and save it as `DISCORD_WEBHOOK_URL` environment variable
5. Run bot using 'python3 main.py' in [discord-bot](discord-bot) directory (need to import necessary modules or use virtual environment in e.g. Pycharm)

### Shop integration
You can change the implementation and use your own Shop, but for this exercise you need to have Shop running on http://localhost:9000. \
To run Shop you can use the one provided in [02_Scala](../02_Scala) directory.
