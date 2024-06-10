# Simple Database Query System

## Problem Description

<br>The system accepts a CSV file (`students.csv`) as input for the initial database. 
<br>After loading the file, the records sorted by their index. 
<br>Support SQL queries with a restricted command set for simplicity. 
<br>The resulting dataset stored as a JSON file.

## Simplified SQL
The simplified SQL format is as follows:
```
SELECT {ALL|column_name} FROM STUDENTS WHERE {column_name|=,!=,<,>,<=,>=,!<,!>,AND,OR} ORDER BY {ASC|DSC}
INSERT INTO STUDENT VALUES(val1,val2,val3,...)
DELETE FROM STUDENT WHERE {column_name|=,!=,<,>,<=,>=,!<,!>,AND,OR}
exit
```
- At most two conditions can be bound by an AND or OR keyword.
- No space between column names is allowed in the query.
- For string values like name, the condition operators are limited to {=, !=}.

## Usage
- Accept a query from the user.
- Provide an error message if the query doesn’t fit the given SQL format.
- Execute the query and store the result in a JSON file.
- Wait for another query or quit if the user enters "exit".
- Queries are case-insensitive.
- Print an error message if a column or table is dropped and used in a later query.

## Examples
```
SELECT name,lastname FROM STUDENTS WHERE grade !< 40 ORDER BY ASC
SELECT name FROM STUDENTS WHERE grade > 40 AND name = ‘John’ ORDER BY DSC
INSERT INTO STUDENT VALUES(15000,Ali,Veli,ali.veli@spacex.com,20)
DELETE FROM STUDENT WHERE name = ‘John’ and grade <= 20
```