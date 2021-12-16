# Sportsbet Coding challange

### Assumptions
* As we are supporting multiple sports and we want to avoid collisions in positions, we should assume that we already know the sport before any other operations. 

### Solutions
* `Player`: pure player data.
* `PlayerRepository`: store and manage players.
* `AbstractSport`: contains logic to operate the depth chart. We use `LinkedList` to handle the depth chart. This is because we can easily add a player to a certain spot in the `LinkedList` and it will shift other items to the right by updating the pointer (cost O(1)).
* `NFL` and `MLB`: currently have 2 sports, but we can add more sports by extending `AbstractSport` and create corresponding position `enum` classes.
* `App`: Run a sample case.

### Running tests and the app
* Requirements: Java 8+
* Running tests
```
./gradlew test
```
* Build the app
```
./gradlew clean build
```
* Running the app
```
java -jar ./build/libs/depthchart-1.0-SNAPSHOT.jar
```
