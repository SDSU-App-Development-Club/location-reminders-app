# Guide to setting up the backend for testing

The backend uses a containerized PostgreSQL database.

If on **Windows**, you'll need to install Docker Desktop and WSL 2.0.  
If on **Mac/Linux**, you'll need to get whatever version of Docker Engine is available for your OS.
You can also use Docker Desktop if you like, but you only need to install the engine for the `docker` command.

On both OSs, the `psql` command must be available on your path. Install the `postgresql` package to get the command,
then look online for how to add it to your PATH environment variable.

If on Windows, **run all commands in WSL**.  
If on Windows, start Docker Desktop.

If on Mac/Linux, ensure that the Docker daemon is running.
Make sure your current user is added to the docker group.

Navigate to the project folder on your computer. 
Ensure with `ls -a` that the `db_setup.sh` and `.env.example` files are visible.   
Run the following command to create your `.env` file and fill in the first six necessary variables.
```shell
cp .env.example .env
```

Next, run the following command:
```shell
./db_setup.sh
```

Keep rerunning the script and fixing your environment until you get the `Postgres Database setup is complete` message.
After that, you can run the TestAppApplication run (the main method in/server/src/main/java/swifties/testapp/TestAppApplication.java).  
**If there are any migration scripts, make sure to run those as well in chronological order.**  
The backend is now running, and you can test the frontend to your heart's content.