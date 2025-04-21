# Flight Search Filter Test

This project contains an automated test written in **Java** using **Selenium WebDriver** and **TestNG**. It verifies the correct behavior of flight search filters on the [FlightNetwork](https://www.flightnetwork.com/) website.

## ✅ What does this test do?

The test automates the following flow:

1. Opens the FlightNetwork website.
2. Searches for flights from **New York** to **London**.
3. Verifies that the cheapest and standard flight results are displayed.
4. Applies the **"Maximum one stop"** filter.
5. Unchecks the **Air Canada** airline filter and confirms that no results from that airline appear.

## 🧪 Tools & Technologies

- Java 17+
- Maven
- TestNG
- Selenium WebDriver
- IntelliJ IDEA (optional, but recommended)

## 🚀 How to run the test

1. **Clone the repository**:

```bash
git clone https://github.com/lbett/Testing.git


Run the test with Maven
 mvn -Dtest=FlightSearchTest test


Project structure
src
└── test
    └── java
        └── com.flightnetwork
            └── FlightSearchTest.java
        └── flightnetwork.pages
            └── WebDriverFactory.java