# 📊 Expense Tracker with Analytics

A modern, desktop-based Expense Tracking application built with Java Swing. It allows users to easily log daily expenses, categorize them, and provides beautiful data visualizations to help you understand your spending habits.

## ✨ Features

- **Intuitive UI:** Clean and responsive interface featuring a custom-built, animated Dark Mode toggle.
- **Smart Data Entry:** Automatically pre-fills today's date, clears forms after submission, and handles input validation.
- **Instant Search & Filtering:** Quickly find past expenses by typing keywords; the table updates in real-time.
- **Local Data Persistence:** All your expenses are saved securely to your local machine (`expenses.txt`).
- **Currency Formatting:** Automatically formats your spending amounts into Indian Rupees (₹).
- **Advanced Analytics:** Uses `JFreeChart` to generate rich visualizations:
  - **Pie Charts:** See your expense distribution by category (Food, Transport, Utilities, etc.).
  - **Bar Charts:** Compare your total spending across different categories.
  - **Time Comparison:** Compare "This Month vs Last Month" and "This Year vs Last Year" side-by-side.

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK) 8** or higher.
- **VS Code** (with the Extension Pack for Java) or any other standard Java IDE (IntelliJ, Eclipse).

### Running the App
1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/Narenderreddy30/Expense-Tracker-with-Analytics.git
   ```
2. Open the project folder in your IDE.
3. Run the `src/Main.java` file.

*(Optional Command Line Method)*:
```bash
# Compile the code
javac -cp "lib/*" src/*.java -d bin
# Run the application
java -cp "bin;lib/*" Main
```

## 🛠️ Built With
- **Java (AWT/Swing)** - Core framework for the desktop GUI.
- **JFreeChart** - Library used for rendering analytics and comparison charts.

## 📸 Screenshots
<img width="1264" height="894" alt="Screenshot 2026-09-23 105724" src="https://github.com/user-attachments/assets/2b406e8e-ad91-4127-86e4-e622ef68bcb0" />
<img width="1261" height="920" alt="Screenshot 2026-09-23 105740" src="https://github.com/user-attachments/assets/eb9c17bd-67e7-47b0-bd11-e18df678c8a2" />

