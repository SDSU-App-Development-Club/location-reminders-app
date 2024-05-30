# Guide to setting up the backend for testing

The backend uses a containerized PostgreSQL database.

If on **Windows**, you'll need to install Docker Desktop and WSL 2.0.  
If on **Mac/Linux**, you'll need to get whatever version of Docker Engine is available for your OS.

If on Windows, you'll need to run all commands in WSL.
If on Windows, start Docker Desktop.

Navigate to the project folder on your computer, and ensure with `ls` that the `docker-compose.yaml`
file is visible. Next, run the following command:
```shell
docker compose up -d
```
The `-d` flag tells Docker to run as a background daemon. If you want Docker to run in the
foreground (be tied to the terminal), then run without `-d`.

When you are done playing around with the backend, run the following command to free up RAM:
```shell
docker compose down
```