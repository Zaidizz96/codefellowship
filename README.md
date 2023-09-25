# CodeFellowship App

## Description

CodeFellowship is an application allows users to create accounts, log in, and connect with other developers. Users can share their profiles, including basic information, and interact with others in the coding community.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- User authentication with BCrypt password hashing.
- User registration with the ability to set username, password, firstName, lastName, dateOfBirth, bio, and other fields.
- Homepage with basic site information.
- Links to login and signup pages.
- Automatic login for registered users.
- Display of the user's username when logged in.
- Logout functionality.
- ability of the authenticated user to create thiere own posts , in addition to ability to edit theire own user information .
- any 404 not found error will be successfully appear to the user
- User Following: Users can follow other users. Following a user allows their posts to show up in the logged-in user's feed, where they can see what all of their followed users have posted recently.
- feeds page whaere you can see all followed user posts 
- Stylish and attractive design.
- Use of templates for rendering pages.
- 

## Getting Started

### Installation

1. Clone this repository to your local machine.

   ```shell
   git clone https://github.com/yourusername/CodeFellowship.git
   ```
2. Build and run the application.
```shell
   cd CodeFellowship
   ./gradlew build
   ./gradlew bootRun
  ```
3. Open a web browser and access the app at http://localhost:8080.


## Usage

1. Access the homepage at the root route (/) to see basic information about the site.
2. you will see to button `log in` and `signup` . 
3. before accessing any of the routes of the site , you need to sign up.
4. after this you will be redirected to the home page with your information .
5. if you visit the sign another time , you can login to the page .
6. if you click `log out` you cant reach any page unless you login again
7. if you logged in to the page , your user information and your previously added posts from you will appear in you profile page.
8. you can add your posts and edit your user information
8. do not try to edit other user information , because that is will cause error.
9. by going to viewed users profile pages , you can follow them by cklick on `follow` that allow you to see their posts in feed page 
