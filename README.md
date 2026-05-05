# 🎾 Tennis Kata

> > 🔴 **Disclosure:** the README and the test suite and the project pom were co-authored with [Claude](https://claude.ai) as part of a near-TDD approach — tests and documentation were iterated alongside the production code rather than written after the fact.

A Java implementation of the [tennis scoring kata](http://en.wikipedia.org/wiki/Tennis#Scoring). Given a sequence of balls won by two players (e.g. `ABABAA`), the app prints the score after each ball and the final winner.

## Requirements

- Java 17+
- Maven 3.6+

## Build

```bash
mvn clean package
```

## Run

```bash
java -jar target/tennis-kata-1.0-SNAPSHOT.jar
```

You'll be prompted to enter a play chain — a sequence of exactly two distinct characters, e.g. `ABABAA` or `UUUIII`.

## Test

```bash
mvn test
```

## Example

```
Please enter the play chain: ABABAA
Player A : 15 / Player B : 0
Player A : 15 / Player B : 15
Player A : 30 / Player B : 15
Player A : 30 / Player B : 30
Player A : 40 / Player B : 30
Player A wins the game
```

## Project layout

```
src/main/java/com/tennis/kata/
├── TennisGame.java          # entry point + game loop
├── model/                   # Score, Player, Game
├── engine/                  # input validation
└── exception/               # domain exceptions
```