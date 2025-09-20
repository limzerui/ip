# Kobe User Guide

Kobe is a friendly, minimal, command‑line task manager chatbot. It lets you track Todos, Deadlines, and Events, mark/unmark them, delete them, list all tasks, and search by keyword. Your tasks are saved automatically to disk between runs.

## Quick Start

- Requirements: Java 17 or above.
- Run from your IDE: open the project and run the `kobe.Kobe` main class.

When Kobe starts, you’ll see a greeting and a prompt. Type a command and press Enter.

## Features

- All commands are case-insensitive for the command word (e.g., `LIST` works), but arguments keep their case.
- Indexes shown in lists are 1-based.

### List tasks: `list`
Shows all tasks in your list.

Example: `list`

Output:
```
____________________________________________________________
 Here are the tasks in your list:
 1.[T][ ] read book
 2.[D][ ] return book (by: June 6th)
____________________________________________________________
```

### Add a todo: `todo <description>`
Adds a simple task without time constraints.

Example: `todo read book`

Output:
```
____________________________________________________________
 Got it. I've added this task:
   [T][ ] read book
 Now you have 1 task in the list.
____________________________________________________________
```

### Add a deadline: `deadline <description> /by <time>`
Adds a task that is due by a specific time.

Example: `deadline return book /by June 6th`

Output:
```
____________________________________________________________
 Got it. I've added this task:
   [D][ ] return book (by: June 6th)
 Now you have 2 tasks in the list.
____________________________________________________________
```

### Add an event: `event <description> /from <start> /to <end>`
Adds a task that spans a time period.

Example: `event project meeting /from Mon 2pm /to Mon 4pm`

Output:
```
____________________________________________________________
 Got it. I've added this task:
   [E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
 Now you have 3 tasks in the list.
____________________________________________________________
```

### Mark a task: `mark <index>`
Marks the specified task as done.

Example: `mark 1`

Output:
```
____________________________________________________________
 Nice! I've marked this task as done:
   [T][X] read book
____________________________________________________________
```

### Unmark a task: `unmark <index>`
Marks the specified task as not done.

Example: `unmark 1`

Output:
```
____________________________________________________________
 OK, I've marked this task as not done yet:
   [T][ ] read book
____________________________________________________________
```

### Delete a task: `delete <index>`
Deletes the specified task.

Example: `delete 2`

Output:
```
____________________________________________________________
 Noted. I've removed this task:
   [D][ ] return book (by: June 6th)
 Now you have 2 tasks in the list.
____________________________________________________________
```

### Find tasks: `find <keyword>`
Shows tasks whose descriptions contain the keyword (case-insensitive).

Example: `find book`

Output:
```
____________________________________________________________
 Here are the matching tasks in your list:
 1.[T][X] read book
 2.[D][X] return book (by: June 6th)
____________________________________________________________
```

### Exit: `bye`
Exits Kobe.

Output:
```
____________________________________________________________
 Bye. Hope to see you again soon!
____________________________________________________________
```

## Data Storage

- Location: `data/kobe.txt` (created automatically if missing).
- Format: one task per line in a simple `type | done | description | ...` format.
- You usually don’t need to edit this file manually.

## Common Errors

- Empty descriptions: e.g., `todo` with no description → “The description of a todo cannot be empty.”
- Missing parts: e.g., `deadline return book` without `/by` → “OOPS!!! Deadline must include /by <time>.”
- Invalid index: e.g., `mark 999` when you have 2 tasks → “Please enter a number between 1 and 2.”
- Unknown command: any command word not recognized (try `list`, `todo`, `deadline`, `event`, `mark`, `unmark`, `delete`, `find`, `bye`).

## Command Summary

- `list`
- `todo <description>`
- `deadline <description> /by <time>`
- `event <description> /from <start> /to <end>`
- `mark <index>`
- `unmark <index>`
- `delete <index>`
- `find <keyword>`
- `bye`
