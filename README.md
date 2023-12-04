# ChatApp
This is a simple real-time chat application where users can join different chat rooms or create their own chat rooms. Users can send and receive messages in real-time within a chat room. The application is implemented in Java and uses networking for communication.

# Functional Requirements
Allow users to create/join chat rooms by entering a unique room ID.
Enable users to send and receive messages in real-time within a chat room.
Display a list of active users in the chat room.
Allow users to exit the chat room.
Provide options for listing active users and chat messages.

# Key Features
Behavioral Pattern: Utilizes the Observer Pattern to notify clients of new messages or user activities.
Creational Pattern: Uses Singleton to manage the state of the chat rooms.
Structural Pattern: Applies the Adapter Pattern to allow the system to work with different types of client communication protocols (WebSocket, HTTP, etc.).
OOP Principles: Applies encapsulation, polymorphism, and inheritance effectively.

# Project Structure
ChatApp/Client.java: Contains the main client application logic.
ChatApp/ChatServer.java: Implements the chat server logic to handle client connections.
ChatApp/ClientHandler.java: Manages the communication between the server and a specific client.
ChatApp/ChatRoom.java: Represents the chat room, handling observers, messages, and active users.
ChatApp/Observer.java: Defines the Observer interface for updating clients.
ChatApp/Subject.java: Defines the Subject interface for managing observers.

# How to Run
Compile all Java files in the ChatApp package.
Run the ChatServer class to start the server.
Run multiple instances of the Client class to simulate different users joining the chat.
Usage
Enter your username and the desired room ID when prompted.
Choose options to send messages, list active users, list chat messages, or exit the chat.
