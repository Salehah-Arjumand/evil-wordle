# 🧠 Evil Wordle – The Most Unfair Wordle You'll Ever Play

This project is a Scala implementation of **Evil Wordle**, a twisted version of the classic Wordle game. Instead of picking a secret word at the start, this evil version adapts on the fly to make it as hard as possible for the player to guess the word.

> 🧨 It's Wordle... but the computer cheats.

---

## 📂 Function Overview

### `get_wordle_list(): List[String]`
Reads a text file containing 5-letter words and returns them as a list.

---

### `removeOne[A](list: List[A], elem: A): List[A]`
Polymorphic function that removes **one** occurrence of `elem` from the list, if present.

Examples:
```scala
removeOne(List(1, 2, 3, 2, 1), 3) => List(1, 2, 2, 1)
removeOne(List(1, 2, 3, 2, 1), 2) => List(1, 3, 2, 1)
removeOne(List(1, 2, 3, 2, 1), 1) => List(2, 3, 2, 1)
removeOne(List(1, 2, 3, 2, 1), 0) => List(1, 2, 3, 2, 1)
```

---

### `score(secret: String, guess: String): List[Tip]`
Calculates Wordle-style feedback for a guess:

- `Correct`: letter is in the correct position
- `Present`: letter is in the word but wrong position
- `Absent`: letter is not in the word

Handles repeated letters using:
- `pool(secret, guess)`: collects unmatched letters from `secret`
- `aux(...)`: recursively matches letters with pool management

Examples:
```scala
score("chess", "caves") => List(Correct, Absent, Absent, Present, Correct)
score("chess", "swiss") => List(Absent, Absent, Absent, Correct, Correct)
```

---

### `eval(tip: Tip): Int`
Converts a feedback `Tip` to a numeric score:

- `Correct` → 10  
- `Present` → 1  
- `Absent` → 0

Examples:
```scala
eval(Correct) => 10
eval(Present) => 1
eval(Absent)  => 0
```

---

### `iscore(secret: String, guess: String): Int`
Calculates the overall score of a guess by applying `score`, then summing using `eval`.

Examples:
```scala
iscore("chess", "caves") => 21
iscore("chess", "swiss") => 20
```

---

### `evil(secrets: List[String], guess: String): List[String]`
Returns a list of secret words that produce the **lowest `iscore`** for the given guess — to make the game as hard as possible.

Uses a helper function to:
- Keep track of lowest score found so far
- Accumulate all secrets matching that score

Examples (from a different word list):
```scala
evil(secrets, "stent").length => 1907
evil(secrets, "hexes").length => 2966
evil(secrets, "horse").length => 1203
evil(secrets, "hoise").length => 971
evil(secrets, "house").length => 1228
```

---

### `frequencies(words: List[String]): Map[Char, Double]`
Calculates the rarity (obscurity) of each character in the word list using:

```scala
frequency(c) = 1 - (occurrences of c / total characters)
```

Examples:
```scala
frequencies(secrets).get('y') => 0.968
frequencies(secrets).get('e') => 0.897
```

---

### `rank(freqMap: Map[Char, Double], word: String): Double`
Computes a **rank score** for a word by summing obscurity values of its letters. Higher score means more rare letters.

Examples:
```scala
rank(frequencies(secrets), "adobe") => 4.67
rank(frequencies(secrets), "gaffe") => 4.74
rank(frequencies(secrets), "fuzzy") => 4.89
```

---

### `ranked_evil(secrets: List[String], guess: String): List[String]`
Returns the **most evil** secret words — those with lowest `iscore` and most obscure letters (highest rank).

Examples (from an external dataset):
```scala
ranked_evil(secrets, "beats") => List("fuzzy")    // iscore: 0
ranked_evil(secrets, "vitae") => List("fuzzy")    // iscore: 0
ranked_evil(secrets, "bento") => List("fuzzy")    // iscore: 0
ranked_evil(secrets, "belts") => List("fuzzy")    // iscore: 0
ranked_evil(secrets, "abbey") => List("whizz")    // iscore: 0
ranked_evil(secrets, "afear") => List("buzzy")    // iscore: 0
ranked_evil(secrets, "zincy") => List("jugum")    // iscore: 0
ranked_evil(secrets, "zippy") => List("chuff")    // iscore: 0
```

> ⚠️ These results were generated from a different word list and may vary with your dataset.

---

## 🚀 How to Run

1. Clone this repository  
2. Open the project in **IntelliJ IDEA**  
3. Ensure **Scala 2.13+** is installed  
4. Add the 5-letter word list file to the project  
5. Run the program and play Evil Wordle!

---

## 📌 Disclaimer

Some sample outputs are from a different word list used for testing and may not match results on your dataset.

---
