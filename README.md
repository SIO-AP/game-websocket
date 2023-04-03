# WebSocket Server for Cybersecurity Quiz

This project is a WebSocket server coded in Java using the Kryonet library. It allows clients to create games for our online cybersecurity quiz.

**Installation**

To run the server, you must first install Java and the Kryonet library.

    Download and install Java by following the instructions for your operating system.
    You can find add dependencies in the `/lib` folder

The server is now listening for incoming connections on port 54555.

**Features**

The server allows clients to connect and create games for the cybersecurity quiz. Once a game is created, other players can join the game. The quiz questions are sent to clients and answers are verified by the server.

The server uses threads to manage connections and games simultaneously, allowing multiple games to be played at the same time.

**License**

This project is licensed under the MIT License. Please refer to the LICENSE file for more information.
