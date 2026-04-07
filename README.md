# Volunteer Coordination System

A priority-aware, undo-capable volunteer management system built in Java using custom linked list, queue, and stack data structures.

![Java](https://img.shields.io/badge/Java-8%2B-blue) ![Educational](https://img.shields.io/badge/Type-Educational-purple) ![CLI](https://img.shields.io/badge/Interface-CLI-green)

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [Data Structures Used](#data-structures-used)
5. [Getting Started](#getting-started)
6. [Usage](#usage)
7. [Class Reference](#class-reference)
8. [Design Decisions & Complexity Analysis](#design-decisions--complexity-analysis)
9. [Known Limitations](#known-limitations)

---

## Overview

This project is a command-line application for managing volunteers at an event or organisation. Volunteers are registered with a priority tier (High, Medium, or Low) and placed into corresponding queues. When a task is assigned, the system always dequeues from the highest-priority queue first. Every mutating action (register, remove, assign) is recorded on a history stack, enabling a full single-step undo. The entire volunteer roster is stored in a singly-linked list to provide ordered traversal and O(n) search by ID or name.

> **Note:** All underlying data structures (`LinkedList`, `Queue`, `Stack`, `Node`) are custom implementations imported from the `datastructures` package — no `java.util` collections are used.

---

## Features

- Register volunteers with a unique ID, name, and priority level
- Remove volunteers from the active roster by ID
- Search for a volunteer by either ID or full name (case-insensitive)
- Assign a named task to the next available, highest-priority volunteer
- Undo the last action (assignment or removal) in constant time
- Display the full volunteer roster in a formatted table
- Peek at the last recorded action without modifying the history
- Preview the next volunteer in line to receive a task
- Duplicate ID prevention enforced at input time
- Lazy deletion keeps dequeued-but-removed volunteers from being re-assigned

---

## Project Structure

| File | Responsibility |
|---|---|
| `Main.java` | Entry point. Owns the interactive CLI loop, input validation, and dispatches to `VolunteerCoordination`. |
| `VolunteerCoordination.java` | Core service class. Manages all data structures and exposes the public API consumed by `Main`. |
| `Volunteer.java` | Domain model. Holds ID, name, priority level, assigned task, and assignment status. Includes priority constants. |
| `ActionRecord.java` | Lightweight value object recording an action type string (`"ASSIGN"` / `"REMOVE"`) and a reference to the affected volunteer. |
```
project-root/
├── Main.java
├── Volunteer.java
├── VolunteerCoordination.java
├── ActionRecord.java
└── datastructures/
    ├── LinkedList.java
    ├── Node.java
    ├── Queue.java
    └── Stack.java
```

---

## Data Structures Used

| Structure | Where | Why |
|---|---|---|
| `LinkedList<Volunteer>` | `allVolunteers` — master roster | Sequential storage with O(n) search by ID or name. Preserves insertion order for display. |
| `Queue<Volunteer>` × 3 | High / Medium / Low priority queues | FIFO ordering within each tier. Checked high → medium → low to enforce strict priority scheduling. |
| `Stack<ActionRecord>` | `volunteerHistory` | LIFO access makes undo O(1). Each push records exactly one reversible action. |

---
## Getting Started

### Option A — Run the pre-built JAR (recommended)

A compiled, executable JAR is provided in the release. It bundles all classes including the `datastructures` package — no additional dependencies or compilation required.

**Prerequisites:** Java Runtime Environment (JRE) 8 or higher.
```bash
java -jar SmartEventCoordination.jar
```

---

### Option B — Build from source

**Prerequisites:** Java Development Kit (JDK) 8 or higher.

Compile all source files from the project root so cross-package references resolve correctly:
```bash
javac -d out datastructures/*.java *.java
```

Then run:
```bash
java -cp out Main
```

To produce your own JAR matching the release:
```bash
jar cfe SmartEventCoordination.jar Main -C out .
java -jar SmartEventCoordination.jar
```

---

## Usage

On launch the system presents a numbered main menu. Enter the option number and press `Enter`. After each operation completes, press `Enter` again to return to the menu.

| # | Option | Description |
|---|---|---|
| 1 | Register a volunteer | Prompts for a unique ID, name, and priority (HIGH / MEDIUM / LOW). Re-prompts if the ID already exists or the priority string is unrecognised. |
| 2 | Remove a volunteer | Marks the volunteer as assigned (blocking future queue dequeue) and removes them from the master list. Action is recorded on the history stack. |
| 3 | Search for a volunteer | Case-insensitive lookup by ID or name. Prints ID, name, assigned task, priority, and current assignment status. |
| 4 | Assign task to a volunteer | Prompts for a task string. Dequeues the first available volunteer starting from the high-priority queue. Skips already-assigned entries via lazy deletion. |
| 5 | Undo last action | Pops the top `ActionRecord` and reverses it: assignments are cleared and the volunteer is re-enqueued; removals are re-added to the list and re-enqueued. |
| 6 | Display all volunteers | Prints a formatted table of every volunteer in the master list with columns for ID, name, priority, task, and status. |
| 7 | Display the last action | Non-destructively peeks the top of the history stack and prints the action type and affected volunteer. |
| 8 | Display next to be assigned | Peeks the highest-priority available volunteer across all three queues without dequeuing. |
| 9 | Exit | Closes the scanner and terminates the program cleanly. |

### Example Session
```
-------Main Menu-------

1. Register a volunteer
...
Enter your choice (1-7):
1

Enter the volunteer ID:
V001
Enter the volunteer name:
Alice
Enter the volunteer priority (HIGH/MEDIUM/LOW):
HIGH
Volunteer was successfully registered

Enter your choice (1-7):
4

Enter the task to assign:
Setup stage
(Alice — HIGH — is dequeued and assigned "Setup stage")
```

---

## Class Reference

### `Volunteer`

| Member | Type / Signature | Notes |
|---|---|---|
| `PRIORITY_HIGH` | `static final String` | Constant `"high"` |
| `PRIORITY_MEDIUM` | `static final String` | Constant `"medium"` |
| `PRIORITY_LOW` | `static final String` | Constant `"low"` |
| `Volunteer(id, name, priority)` | Constructor | Task defaults to `"Unassigned"`; assigned defaults to `false` |
| `isAssigned()` | `boolean` | Used by queues for lazy deletion |

### `VolunteerCoordination` — Public API

| Method | Description |
|---|---|
| `registerVolunteer(id, name, priority)` | Creates a `Volunteer`, appends to master list, and enqueues into the appropriate priority queue. |
| `removeVolunteer(String id)` | Marks volunteer assigned, removes from list, pushes a `REMOVE` record. Returns `false` if not found. |
| `getVolunteer(String searchTerm)` | Linear search by ID or name (case-insensitive). Returns `null` if not found. |
| `assignTask(String task)` | Delegates to `processQueue` in high → medium → low order. No-op if all queues are empty or exhausted. |
| `undoLastAction()` | Pops history stack and reverses the recorded action. Prints result; prints error if stack is empty. |
| `getAllVolunteers()` | Traverses master list and prints a formatted five-column table. |
| `displayLastAction()` | Peeks history stack and prints action type + volunteer details. |
| `displayNextVolunteer()` | Delegates to `getNextValidVolunteer()` and prints the result. |

---

## Design Decisions & Complexity Analysis

### Three-tier priority queue

Rather than a single priority queue with a comparator, three separate FIFO queues are maintained. Assignment simply checks high → medium → low in sequence. This avoids any comparator complexity and makes the priority logic explicit and easy to extend. The trade-off is three queue objects in memory rather than one.

### Lazy deletion

When a volunteer is removed or assigned a task their `assigned` flag is set to `true`. Stale entries are silently skipped during dequeue operations rather than being proactively purged from every queue. This keeps `removeVolunteer` O(n) (linked list traversal) instead of requiring O(n) queue traversal as well. The cost is that queues may transiently hold dead references until they are encountered during the next assignment pass.

### Undo via action stack

Every mutation pushes an `ActionRecord` onto a `Stack<ActionRecord>`. Undo pops the top record and re-applies the inverse operation in O(1). Only the last action can be undone; there is no multi-level undo (see limitations).

### Complexity Summary

| Operation | Time complexity | Reason |
|---|---|---|
| Register | O(n) | Linked list `add` traverses to tail; queue enqueue is O(1) |
| Remove | O(n) | Linear search + linked list remove |
| Search | O(n) | Linear scan of master list |
| Assign task | O(k) | k = stale entries skipped before a live volunteer is found; amortised O(1) per assignment in a clean queue |
| Undo | O(1) | Stack pop + enqueue |
| Display all | O(n) | Full linked list traversal |

---

## Known Limitations

- **Undo is single-step only.** Repeating option 5 undoes the action created by the previous undo, which may produce unexpected results.
- **Search supports exact full-name matching only.** Partial or substring search is not implemented.
- **Volunteer registration is not recorded on the history stack**, so a fresh registration cannot be undone.
- **Priority cannot be changed after registration** without removing and re-registering the volunteer.
- **All data is in-memory only** — state does not persist between runs.
- The menu prompt still reads *"Enter your choice (1-7)"* despite the menu having nine options. This is a cosmetic bug only and does not affect functionality.
