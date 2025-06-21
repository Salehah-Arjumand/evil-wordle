# 🧠 Evil Wordle – The Most Unfair Wordle You'll Ever Play

This project is a Scala implementation of **Evil Wordle**, a twisted version of the classic Wordle game. Instead of picking a secret word at the start, this evil version adapts on the fly to make it as hard as possible for the player to guess the word.

> 🧨 It's Wordle... but the computer cheats.

---

## 🎯 Features

- **Adaptive Secret Words** – The program doesn’t commit to a secret word until forced to.
- **Accurate Wordle Feedback** – Implements real Wordle-style feedback (Correct, Present, Absent).
- **Greedy Filtering** – Filters word lists to return the most misleading set possible.
- **Obscurity-Based Ranking** – Ranks candidate words by character rarity to select the most confusing option.

---

## 🛠️ Technologies Used

- **Scala 2.13+**
- Pure functional programming (no `var`, no `return`)
- File I/O with `Source.fromFile`
- Lists, Maps, Recursion, Pattern Matching

---

## 📂 Function Overview

### `get_wordle_list()`
Reads a text file containing 5-letter words and returns a list of valid words.

---

### `removeOne[A](list: List[A], elem: A): List[A]`
Polymorphic function that removes **one** occurrence of `elem` from a list if present.

Examples:
```scala
removeOne(List(1,2,3,2,1), 3) => List(1, 2, 2, 1)
removeOne(List(1,2,3,2,1), 2) => List(1, 3, 2, 1)
removeOne(List(1,2,3,2,1), 1) => List(2, 3, 2, 1)
removeOne(List(1,2,3,2,1), 0) => List(1, 2, 3, 2, 1)

---

### `score(secret: String, guess: String): List[Tip]`
Calculates Wordle-style feedback based on the comparison of the guess with the secret word. The output is a list of `Tip` values representing:

- `Correct`: letter is in the correct position
- `Present`: letter exists in the word but is in the wrong position
- `Absent`: letter is not in the word at all

It handles repeated letters correctly by:
- First calculating a `pool` of unmatched letters from the secret
- Then recursively applying the logic using a helper `aux` function

Examples:
```scala
score("chess", "caves") => List(Correct, Absent, Absent, Present, Correct)
score("chess", "swiss") => List(Absent, Absent, Absent, Correct, Correct)

