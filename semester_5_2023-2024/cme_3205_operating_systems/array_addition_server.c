#include <stdio.h>      // for puts, printf
#include <string.h>     // for strlen
#include <sys/socket.h> // for socket, bind, listen, accept, recv
#include <arpa/inet.h>  // for inet_addr
#include <unistd.h>     // for write, close
#include <stdlib.h>     // for atoi, malloc, free
#include <ctype.h>      // for isdigit
#include <pthread.h>    // for pthread_create, pthread_join

#define DATA_SIZE 100
#define PORT_NUMBER 60000
#define MAX_COUNT 10

// MSB at address 0. (Most Significant Bit)
char INPUT_STRING[DATA_SIZE];
int  FIRST_ARRAY[DATA_SIZE];
int  SECOND_ARRAY[DATA_SIZE];
int  CARRY_ARRAY[DATA_SIZE];
int  RESULT_ARRAY[DATA_SIZE];

pthread_t THREAD_ARRAY[DATA_SIZE];

int count_num;

const char *SERVER_GREETING = "Hello, this is Array Addition Server!\n";
const char *SERVER_EXIT = "Thank you for Array Addition Server! Good Bye!\n";
const char *ERROR_INVALID_INPUT = "Error: Invalid input length not accepted.\n";
const char *ERROR_INVALID_NUMBER = "ERROR: Please enter a number in bounds (0,999)\n";
const char *ERROR_INVALID_LEN = "Error: Invalid input. Please enter maximum 10 integers separated by spaces.\n";
const char *ERROR_NON_INTEGER = "ERROR: The inputted integer array contains non-integer characters. You must input only integers and empty spaces to separate inputted integers!\n";
const char *ERROR_DIFFERENT_LEN = "ERROR: The number of integers are different for both arrays. You must send equal number of integers for both arrays!\n";

int  initServer();
void handleClient(int server_socket);
void sendMessage(int socket, const char *message);
void resetAll();
int  takeInputs(int client_soc);
int  receiveAndParseArray(int client_soc, int array[]);
void startAddition();
void *additionThread(void *arg);
void sendResult(int client_soc);
void closeConnection(int client_soc);

int main(int argc, char *argv[])
{
    // initialize server
    int server_soc = initServer();
    if (server_soc == -1) // error occurred
        return 1;

    // loop to accept client continuously (not multithreaded)
    // if server accepted a client the client will get in queue
    while (1)
        handleClient(server_soc); // accept client and handle client
        // connect using telnet (telnet localhost PORT_NUMBER)

    return 0;
}

// initialize server. returns server_soc, -1 for error
int initServer()
{
    // variables for server
    int server_soc;
    struct sockaddr_in server;

    // Create socket
    server_soc = socket(AF_INET, SOCK_STREAM, 0);
    if (server_soc == -1)
    {
        puts("Could not create socket");
        return -1;
    }

    // server configurations
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(PORT_NUMBER);

    // Bind
    if (bind(server_soc, (struct sockaddr *)&server, sizeof(server)) < 0)
    {
        puts("Bind failed");
        return -1;
    }
    puts("Socket is bound");

    // Listen maximum 3 client at the same time
    if (listen(server_soc, 3) < 0)
    {
        puts("Listen failed");
        return -1;
    }

    // Accept and incoming connection
    puts("Waiting for incoming connections...\n");
    return server_soc;
}

// returns -1 if any error occurs
void handleClient(int server_socket)
{
    struct sockaddr_in client_adr;
    int client_soc;
    int adr_len = sizeof(struct sockaddr_in);
    client_soc = accept(server_socket, (struct sockaddr *)&client_adr, (socklen_t *)&adr_len);
    if (client_soc == -1)
    {
        puts("An error occurred while accepting client\n");
        return;
    }

    printf("Connection accepted from %s:%d\n", inet_ntoa(client_adr.sin_addr), ntohs(client_adr.sin_port));
    sendMessage(client_soc, SERVER_GREETING);

    resetAll();
    // if inputs received correctly start addition
    if (takeInputs(client_soc))
    {
        puts("Received input correctly.");
        startAddition();
        sendResult(client_soc);
    }
    else
    {
        puts("An error occurred while accepting input.");
    }

    closeConnection(client_soc);
}

void sendMessage(int socket, const char *message)
{
    write(socket, message, strlen(message));
}

void resetAll()
{
    count_num = 0;
    memset(INPUT_STRING, 0, DATA_SIZE);
    memset(FIRST_ARRAY, 0, DATA_SIZE * sizeof(int));
    memset(SECOND_ARRAY, 0, DATA_SIZE * sizeof(int));
    memset(CARRY_ARRAY, 0, DATA_SIZE * sizeof(int));
    memset(RESULT_ARRAY, 0, DATA_SIZE * sizeof(int));
    memset(THREAD_ARRAY, 0, DATA_SIZE * sizeof(pthread_t));
}

// return 1 if successfully received inputs, else return 0
int takeInputs(int client_soc)
{
    // take input for the first array
    sendMessage(client_soc, "Please enter the first array for addition:\n");
    int count1 = receiveAndParseArray(client_soc, FIRST_ARRAY);
    if (count1 == 0)
    {
        return 0; // Error handling already done in receiveAndParseArray
    }

    // reset input string array
    memset(INPUT_STRING, 0, DATA_SIZE);

    // take input for the second array
    sendMessage(client_soc, "Please enter the second array for addition:\n");
    int count2 = receiveAndParseArray(client_soc, SECOND_ARRAY);
    if (count2 == 0)
    {
        return 0; // Error handling already done in receiveAndParseArray
    }

    // check if both array has same count of number
    if (count1 != count2)
    {
        sendMessage(client_soc, ERROR_DIFFERENT_LEN);
        return 0;
    }

    count_num = count1;
    return 1;
}

int receiveAndParseArray(int client_soc, int array[])
{
    int bytes_received = recv(client_soc, INPUT_STRING, DATA_SIZE+1, 0);

    // ignore characters (\r, ' ', \n)
    while (bytes_received > 0 && (INPUT_STRING[bytes_received - 1] == '\r' ||
                                  INPUT_STRING[bytes_received - 1] == ' ' ||
                                  INPUT_STRING[bytes_received - 1] == '\n'))
        bytes_received--;
        
    // check it is not empty and len is not greater than data size
    if (bytes_received <= 0 || bytes_received > DATA_SIZE)
    {
        sendMessage(client_soc, ERROR_INVALID_INPUT);
        return 0;
    }

    // Null-terminate the received string data.
    INPUT_STRING[bytes_received] = '\0';

    // count numbers
    int count = 0;

    // split the input with delimiter whitespace
    char *token = strtok(INPUT_STRING, " ");
    while (token != NULL && count <= MAX_COUNT)
    {
        // check is it a full of digit
        for (int i = 0; i < strlen(token); i++)
        {
            if (!isdigit(token[i]))
            {
                sendMessage(client_soc, ERROR_NON_INTEGER);
                return 0;
            }
        }

        // convert str to int
        int num = atoi(token);
        // check num is between 0 and 999
        if (num > 999 || num < 0)
        {
            sendMessage(client_soc, ERROR_INVALID_NUMBER);
            return 0;
        }

        // assign it to the array
        array[count] = num;
        count++;
        token = strtok(NULL, " ");
    }

    // if there is more than max count
    if (count > MAX_COUNT)
    {
        sendMessage(client_soc, ERROR_INVALID_LEN);
        return 0;
    }

    return count;
}

void *additionThread(void *arg)
{
    int index = *(int *)arg;

    // Perform addition operation between elements of FIRST_ARRAY and SECOND_ARRAY
    RESULT_ARRAY[index] = FIRST_ARRAY[index] + SECOND_ARRAY[index];

    // Check for carry
    if (RESULT_ARRAY[index] > 999)
    {
        RESULT_ARRAY[index] %= 1000;
        CARRY_ARRAY[index] = 1;
    }

    free(arg);
    pthread_exit(NULL);
}

void startAddition()
{
    int numThreads = 0;

    // Create threads for each index of the arrays that contain integers
    for (int i = 0; i < count_num; i++)
    {
        // create individual int variable to prevent
        // using same or previous i variable
        int *threadIndex = malloc(sizeof(int));
        *threadIndex = i;
        pthread_create(&THREAD_ARRAY[numThreads], NULL, additionThread, (void *)threadIndex);
        numThreads++;
    }

    // Wait for all threads to finish
    for (int j = 0; j < numThreads; j++)
    {
        int threadIndex = j;
        pthread_join(THREAD_ARRAY[threadIndex], NULL);
    }

    // adding other carries
    for (int k = 0; k < count_num; k++)
    {
        RESULT_ARRAY[k] += CARRY_ARRAY[k + 1];
    }

    puts("Addition operation done.");
}

void sendResult(int client_soc)
{
    sendMessage(client_soc, "The result of array addition are given below:\n");

    // print result array
    char str[5];

    if (CARRY_ARRAY[0] > 0)
        sprintf(str, "%d ", 1);
    sendMessage(client_soc, str);

    for (int i = 0; i < count_num; i++)
    {
        //sprintf(str, " %03d", RESULT_ARRAY[i]); // adds zero for smaller numbers. e.g (3 -> 003, 43 -> 043)
        sprintf(str, "%d ", RESULT_ARRAY[i]);
        sendMessage(client_soc, str);
    }

    sendMessage(client_soc, "\n");
}

void closeConnection(int client_soc)
{
    sendMessage(client_soc, SERVER_EXIT);
    puts("Connection closed.\n");
    close(client_soc);
}