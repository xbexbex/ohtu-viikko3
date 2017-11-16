Feature: A new user account can be created if a proper unused username and password are given

  Scenario: creation is successful with valid username and password
    Given command new user is selected
    When a valid username "asdf" and password "asdfasdf1" and matching password confirmation are entered
    Then a new user is created

  Scenario: creation fails with too short username and valid password
    Given command new user is selected
    When incorrect username "a" and password "asdfasdf2" and matching password confimation are entered
    Then user is not created and error "username should be at least 3 characters long" is reported

  Scenario: creation fails with correct username and too short password
    Given command new user is selected
    When a valid username "asdf" and password "asdf1" and matching password confirmation are entered
    Then user is not created and error "password should be at least 8 characters long" is reported

  Scenario: creation fails with already taken username and valid password
    Given command new user is selected
    When a valid username "jukka" and password "asdfasdf1" and matching password confirmation are entered
    Then user is not created and error "username is already taken" is reported

  Scenario: creation fails when password and password confirmation do not match
    Given command new user is selected
    When a valid username "asdf" and password "asdfasdf1" and password confirmation "asdfasdf2" are entered
    Then user is not created and error "password and password confirmation do not match" is reported

  Scenario: user can login with successfully generated account
    Given user with username "asdfasdf" with password "asdfasdf123" is successfully created
    And login is selected
    When correct username "asdfasdf" and password "asdfasdf123" are given
    Then user is logged in

  Scenario: user can not login with account that is not successfully created
    Given user with username "as" and password "df" is tried to be created
    And login is selected
    When nonexistent username "as" and password "df" are given
    Then user is not logged in and error message is given
