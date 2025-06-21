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
