# 🔒 PROG5121 Portfolio of Evidence (PoE) - Part 1

An advanced, object-oriented Java console application simulating a secure Account Registration and User Login management system. The system enforces strict layout formats, credential validation rules, and comprehensive crash protection protocols using automated unit testing frameworks.

---

## ✨ System Features
* **Format-Enforced Registration:** Restricts usernames to a maximum length of 5 characters and requires the inclusion of an underscore (`_`).
* **Complex Password Complexity:** Enforces security protocols requiring a minimum length of 8 characters, at least one uppercase letter, one digit, and one special character.
* **Encapsulated Authentication:** Employs strict data encapsulation within a dedicated `Login` class, isolating validation parameters from the driver program.
* **Automated JUnit Verification:** Includes a robust `LoginTest.java` suite to verify login status matching patterns programmatically.

---

## 🛠️ Project Architecture
* **`PROG5121_PoE_Part1.java`**: The main driver program managing terminal greeting menus and runtime input data routing streams.
* **`Login.java`**: The operational blueprint handling core credential regex calculations and state checks.
* **`LoginTest.java`**: Automated JUnit unit test cases ensuring strict logic compliance with course matrices.

---

## 🚀 Getting Started & Execution
1. Open the project inside **Apache NetBeans IDE**.
2. To run automated tests, right-click `LoginTest.java` in the left pane and select **Test File** (`Ctrl + F6`).
3. To launch the interactive system, open `PROG5121_PoE_Part1.java`, right-click inside the blank code area, and select **Run File** (`Shift + F6`).
