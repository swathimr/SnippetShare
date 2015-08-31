
##Basic

This Snippet Sharing Aaplication serves as a platform for users to create boards, add snippets to it and share it with other people across the web. A snippet can be anything including web links, stories, experiences, questions. It is a web application intended for sharing snippets either in private or public. This was done by a group of 4 people as part of Enterprise Application Development course at San Jose State University.

Through this project, we have implemented a SnippetShare application to demonstrate RESTful Web services, persistence, access control and dependency injections. The user can create snippets and share them across the application depending on its access levels - public or private. The boards are created based on a category. The user can select from an available list of categories. User can then create snippets and place it in private or public boards and add categories to the snippets as well. If it is a private board, the user can invite people to join the board and restrict the board to just a group of users. With a public board, users across the application have access to view and comment on the snippets.


##Features

1. Account creation and profile Management : 
This includes basic operations for creating a user account, updating user profile info, view and delete the account which accounts to basic CRUD operation for users. User can also login through Facebook. Only registered users can add snippets to boards. A user can invite only registered users to his/her private board.

2. Create board with title and category : 
User can create a new board under a category and make it public or private. The user can create a new snippet, and has access to comment on them.

3. Users must be able to create either private and public boards.
The public boards are visible to all the registered users. If it is private, he can invite friends to join the Board and use it to create new snippets and comment on them.

4. Share snippets on the board with information like Title, tags, content and URL
The snippets will be placed on the board with the owner of each snippet getting the choice to place it on any available board.

5. Public boards are visible to everyone but can be edited by the owner.

6. Only the owner of the snippet gets to edit or delete the snippet

7. Users can search for boards, snippets and other users.


##Developing

1. Technologies used:
Front end : Bootstrap , Thymeleaf , Javascript , Jquery,HTML5,CSS
Backend : MongoDB to store user, board and snippet data, Spring MVC , Spring Boot , Restful , Maven, AOP
REST APIs are written to store and fetch data. 

##Tools

Eclipse and IntelliJ
RoboMongo and MongoLab to host the database
Postman REST Client for testing the REST APIs
 
