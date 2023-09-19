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
- Stylish and attractive design.
- Use of templates for rendering pages.

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
