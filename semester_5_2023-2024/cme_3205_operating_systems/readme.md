# Array Addition Server

This project is a simple array addition server implemented in C language. The server listens on a specified port (default: 60000) and performs addition operations on integer arrays received from telnet clients.

## Features

- Accepts two integer arrays from a telnet client
- Performs addition operation on the arrays
- Outputs the result of the addition to the telnet client
- Handles error scenarios, such as unequal array lengths, non-integer inputs, and numbers exceeding the limit (0-999)
- Supports multithreading for efficient addition operations

## Requirements

- Linux operating system

## Example Usage
<br>
telnet localhost 60000
Hello, this is Array Addition Server!
Please enter the first array for addition:
105 449 445 842 292 655 959 6 404 149
Please enter the second array for addition:
999 601 78 502 156 482 805 670 834 27
The result of array addition are given below:
1 105 50 524 344 449 138 764 677 238 176
Thank you for Array Addition Server! Good Bye!
