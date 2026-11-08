# DAA Assignment 4 — Dynamic Programming and String Algorithms

**Student:** Alikhan Zhambayev  
**Group:** SE-2517  
**University:** Astana IT University  
**Course:** Design and Analysis of Algorithms

## Project Overview

This project implements and compares five algorithms for edit distance and string matching.

The assignment is based on a City Search & Spell-Check scenario. Edit Distance measures the difference between two strings, while string matching finds occurrences of a pattern inside a text.

The project includes algorithm implementations, operation counters, reproducible input generators, JUnit 5 tests, benchmark experiments, CSV results and performance plots.

## Implemented Algorithms

| Algorithm | Time Complexity | Extra Space |
|---|---|---|
| Recursive Edit Distance | Exponential in the worst case | O(n+m) stack |
| DP Table | O(nm) | O(nm) |
| DP TwoRows | O(nm) | O(min(n,m)) |
| Naive String Search | O(nm) | O(1), excluding results |
| KMP String Search | O(n+m) | O(m), excluding results |

The string search implementations return all occurrence indices, including overlapping matches.

## Project Structure

```text
DAA_Assignment4/
├── pom.xml
├── README.md
├── REPORT.md
├── src/
│   ├── main/java/
│   │   ├── algorithms/
│   │   │   ├── EditDistance.java
│   │   │   └── StringSearch.java
│   │   ├── metrics/
│   │   │   ├── Metrics.java
│   │   │   ├── Result.java
│   │   │   └── CsvWriter.java
│   │   └── bench/
│   │       ├── InputGenerator.java
│   │       └── Benchmark.java
│   └── test/java/
│       └── algorithms/
│           ├── EditDistanceTest.java
│           └── StringSearchTest.java
└── results/
    ├── results.csv
    └── plots/
        ├── 01_recursive_calls.png
        ├── 02_dp_time.png
        ├── 03_search_comparisons.png
        ├── 04_search_time.png
        ├── 05_search_random_comparisons.png
        └── 06_comparison_ratio.png
```

## Requirements

- Java JDK 17 or newer
- Apache Maven
- IntelliJ IDEA or another Java IDE
- Git

## Build and Run

Clone the repository:

```bash
git clone https://github.com/alihan77756213006-blip/DAA_Assignment4-.git
cd DAA_Assignment4-
```

Compile the project:

```bash
mvn clean compile
```

Run all JUnit 5 tests:

```bash
mvn clean test
```

Run benchmark experiments:

```bash
mvn compile exec:java
```

The benchmark writes its output to:

```text
results/results.csv
```

## Testing

JUnit 5 tests verify:

- Reference edit distances such as `kitten` → `sitting`.
- Empty strings and identical strings.
- Agreement between all three Edit Distance implementations on 100 random cases.
- Correct LPS arrays.
- Overlapping string matches.
- Empty-pattern exceptions and patterns longer than the text.
- Agreement between Naive and KMP on 100 random cases.

