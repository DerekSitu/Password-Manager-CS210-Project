#PassMan
## A password manager

note for users: the master password is "password"

Improve your cyber security and never lose a password again!

*PassMan* is a password manager app that remembers your passwords for you, so you can set a unique, difficult-to-crack
password for each site you visit. All you have to do is remember your master password. And don't worry, only your 
encrypted passwords are stored, so even if *PassMan* suffers a data breach, your passwords have to be decrypted first.

## User Stories
- As a user, I want to be able to create a new login and add it to my list of logins
- As a user, I want to be able to view my logins
- As a user, I want to be able to change my passwords
- As a user, I want to be able to delete a login from my list of logins
- As a user, I want to be able to save my logins to a file
- As a user, I want to be able to load my logins from a file

## Phase 4: Task 2
Goal:
- Test and design a class in your model package that is robust. You must have at least one method that throws a checked
 exception.  You must have one test for the case where the exception is expected and another where the exception is not
 expected.
 
 I made the LoginCollection class robust by changing the deleteLogin and getLogin methods to make them throw
 IndexOutOfBoundsExceptions, and added the necessary tests in LoginCollectionTest. Then, in PassMan.doDelete(), I
 caught the IndexOutOfBoundsException and prompted the user to select a valid index. 
 
 ## Phase 4: Task 3
 Now that I know about design principles like coupling and cohesion, I would not make any changes to the relationships
 between my classes because I cannot identify any cases where I have coupling. Overall I like my design and would do it
 again.
