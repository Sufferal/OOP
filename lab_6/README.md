# Lab 6: Architecture and SOLID

## SOLID

SOLID is an acronym for the first five object-oriented design principles by Robert C.
Martin

These principles establish practices that lend to developing software with considerations
for maintaining and extending as the project grows.
Adopting these practices can also
contribute to avoiding code smells, refactoring code, and Agile or Adaptive software
development.

SOLID stands for:

- S - Single-responsiblity Principle
- O - Open-closed Principle
- L - Liskov Substitution Principle
- I - Interface Segregation Principle
- D - Dependency Inversion Principle

## MVC

Model–view–controller (MVC) is a software architectural pattern commonly used for developing user interfaces that divide the related program logic into three interconnected elements. This is done to separate internal representations of information from the ways information is presented to and accepted from the user.

In this case, the View is stateless. It is
simply rendered by the Controller once
the Model is changed. Ex. web page
completely reloaded once you press on the
link.

- The user’s interactions call methods on the
  Controller
- The Controller updates the Model (where
  the model is what actually stores state)
- The Model exposes change listeners to
  notify observers that it has changed
- The View subscribes for changes emitted by
  the model to update itself

### Advantages

1. It keeps business logic separate.
2. Faster development process

### Disadvantages

1. Due to large code controller is unmanageable.
2. Bad testability
3. Increased Complexity
