# 🔒 PROG5121 Portfolio of Evidence (PoE) - QuickChat Application

An advanced, object-oriented Java console application simulating a secure Account Registration, User Authentication, and Instant Messaging management system. The application enforces strict validation rules, robust runtime crash protection, and comprehensive test coverage.

---

## ✨ System Features

### 🔐 Part 1: Registration & Authentication
* **Format-Enforced Registration:** Restricts usernames to a maximum length of 5 characters and strictly requires the inclusion of an underscore (`_`).
* **Complex Password Complexity:** Enforces strict security criteria requiring a minimum length of 8 characters, at least one uppercase letter, one digit, and one special character.
* **Encapsulated Authentication:** Employs clean data encapsulation within a dedicated `Login` class, completely isolating validation logic parameters from the main driver thread.
* **Automated JUnit Verification:** Includes a robust `LoginTest.java` suite to verify system login behaviors programmatically.

### 💬 Part 2: QuickChat Messaging Module
* **State-Coupled Access Rule:** Enforces a secure logical layer ensuring users can only initialize and broadcast messages after completing a successful login verification.
* **Format-Validated Recipients:** Validates cell numbers to ensure compliance with South African standards, accepting either a standard 10-digit format (`083...`) or a 12-digit international format starting with country code `+27`.
* **String Metric Guard:** Enforces a maximum character string bound of 250 characters on all message text payloads, printing clear validation flags upon successful capture.
* **Cryptographic Tally Hash:** Autogenerates an explicit, ALL-CAPS verification token string hash for every tracking entry by concatenating the first two digits of its random Message ID, the loop index counter sequence, and the first and last words of the text body.

---

## 🛠️ Project Architecture

* **`PROG5121_PoE_Part1.java`**: The core driver orchestrator handling interactive console selection loops, scan buffers, and sequential transaction workflows.
* **`Login.java`**: The backend authentication module handling credential evaluations, regex formatting lookups, and system greeting responses.
* **`Message.java`**: The message object blueprint class containing tracking ID randomizers, cell format verification states, and uppercase hash calculations.
* **`LoginTest.java` / `MessageTest.java`**: Automated JUnit 5 unit test suites ensuring strict structural logic compliance with core course matrices.

---

## 🚀 Getting Started & Execution

1. Launch **Apache NetBeans IDE**.
2. Go to **File** -> **Open Project** and open this directory workspace folder.
3. **To Run Automated Tests:** Expand your project's `Test Packages`, right-click either `LoginTest.java` or `MessageTest.java`, and select **Test File** (`Ctrl + F6`) to verify the 100% passing green bars.
4. **To Launch the Live Terminal Application:** Open `PROG5121_PoE_Part1.java`, right-click anywhere inside the empty code background canvas editing area, and select **Run File** (`Shift + F6`).
