# Bank Application System

A multi-threaded, console-based banking application that simulates core banking operations with a focus on thread safety and concurrency handling.

![Bank Application](https://img.shields.io/badge/Bank-Application-blue)
![Java](https://img.shields.io/badge/Java-17-orange)
![Status](https://img.shields.io/badge/Status-Operational-green)

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Console UI Features](#console-ui-features)
- [Threading Model](#threading-model)
- [How to Run](#how-to-run)
- [Sample Operations](#sample-operations)
- [Handling Concurrency](#handling-concurrency)
- [Error Handling](#error-handling)

## Overview

This application demonstrates a banking system that allows users to create accounts, deposit and withdraw funds, transfer money between accounts, and check balances - all in a thread-safe environment that handles concurrent operations properly.

## Features

- **Account Management**: Create bank accounts with unique account numbers
- **Deposit & Withdrawal**: Thread-safe deposit and withdrawal operations
- **Fund Transfers**: Transfer funds between accounts with proper synchronization
- **Balance Inquiry**: Check account balances securely
- **Colorful UI**: Enhanced console interface with colors and formatting
- **Multi-threading**: Execute operations concurrently using thread pool
- **Proper Error Handling**: Comprehensive exception management

## Architecture

The application follows a layered architecture pattern:

```
┌─────────────────┐
│  Presentation   │ Controller layer handling user interaction
├─────────────────┤
│    Service      │ Business logic implementation
├─────────────────┤
│   Repository    │ Data access layer
├─────────────────┤
│     Model       │ Domain objects
└─────────────────┘
```

## Project Structure

```
BankApplication/
├── BankApp.java                  # Application entry point
├── controller/
│   └── BankController.java       # Handles user interactions and menu
├── service/
│   ├── BankService.java          # Banking service interface
│   └── BankServiceImpl.java      # Banking service implementation
├── repository/
│   ├── AccountRepository.java    # Account repository interface
│   └── InMemoryAccountRepository.java # In-memory implementation
├── model/
│   └── Account.java              # Account entity with thread-safe operations
├── exception/
│   ├── AccountNotFoundException.java
│   └── InsufficientFundsException.java
└── util/
    └── ConsoleUtils.java         # Utility for enhanced console display
```

## Console UI Features

The application uses `ConsoleUtils` to provide an enhanced console experience:

- **Color-coded Output**: Different colors for different message types
- **Formatted Headers**: Styled headers for different sections
- **Success/Error Indications**: Visually distinguishable success and error messages
- **Interactive Menus**: Well-formatted menu options
- **Progress Indicators**: Visual feedback for operations
- **Tabular Data**: Data displayed in formatted tables
- **Welcome Banner**: Attractive welcome screen

## Threading Model

The application uses a thread pool (ExecutorService) to handle concurrent operations:

- Core banking operations run in separate threads
- Synchronized blocks prevent race conditions during transfers
- Thread-safe collections store account data
- Atomic operations within the Account class

## How to Run

1. Compile all Java files:
   ```
   javac -d bin d:\Java_Practice\System_Design_13\BankApplication\*.java
   ```

2. Run the application:
   ```
   java -cp bin BankApp
   ```

3. Follow the on-screen menu to interact with the banking system

## Sample Operations

### Creating an Account
```
==== Create Account ====

Enter account number: 1001
✓ Account created successfully
```

### Depositing Funds
```
==== Deposit Funds ====

Account number: 1001
Amount: 500
✓ Deposit successful
```

### Checking Balance
```
==== Check Balance ====

Account number: 1001
┌──────────────────┐
│ Balance: $500.0  │
└──────────────────┘
```

### Transferring Funds
```
==== Transfer Funds ====

From Account: 1001
To Account: 1002
Amount: 200
✓ Transfer successful
```

## Handling Concurrency

The application handles concurrent access to accounts using:

1. **Synchronized Methods**: Core Account methods are synchronized
2. **Lock Ordering**: When transferring between accounts, locks are acquired in a consistent order
3. **Thread Pool**: Operations are executed through a managed thread pool
4. **Thread-safe Collections**: ConcurrentHashMap stores account data

## Error Handling

The application handles various error conditions:

- **Account Not Found**: When operations reference non-existent accounts
- **Insufficient Funds**: When withdrawal/transfer amounts exceed balance
- **Invalid Input**: Validation for input parameters
- **Visual Feedback**: Color-coded error messages in the console UI
