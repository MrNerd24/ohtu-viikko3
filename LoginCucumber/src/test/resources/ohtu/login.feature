Feature: User can log in with valid username/password-combination

    Scenario: user can login with correct password
       Given command login is selected
       When  username "pekka" and password "akkep123" are entered
       Then  system will respond with "logged in"
