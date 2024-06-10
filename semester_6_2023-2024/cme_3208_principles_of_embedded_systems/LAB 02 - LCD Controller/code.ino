#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Initialize LCD object with address 0x27, 16 columns, and 2 rows
LiquidCrystal_I2C lcd(0x27, 16, 2);

// Global Variables
const int LCD_WIDTH = 16;
const int SCROLL_WAIT_TIME = 200;
bool SCROLL_ENABLED = true;  // Flag to enable/disable scrolling
char SCROLL_DIRECTION = 'L'; // Direction of scrolling ('L' for left, 'R' for right)
char LCD_ROW_1[] = "This text is for row 0 and longer than 16 characters.";
char LCD_ROW_2[] = "ABCD EFGH";

char *row1; // pointer for row1
char *row2; // pointer for row2

void setup() {
    lcd.init();      // Initialize the LCD
    lcd.backlight(); // Turn on the backlight
    row1 = prepareText(LCD_ROW_1); 
    row2 = prepareText(LCD_ROW_2);
}

// if the text is longer than 16 characters 
// it returns original text array pointer
// else it returns a new array with 16 characters 
// and the rest is filled with spaces
char *prepareText(char *originalText) {
    int textLength = strnlen(originalText, LCD_WIDTH); // reads max LCD_WITH length

    if (textLength == LCD_WIDTH) {
        return originalText;
    }
    else {
        char *paddedText = new char[LCD_WIDTH + 1]; // Allocate memory for paddedText dynamically
        memset(paddedText, ' ', LCD_WIDTH);
        strncpy(paddedText, originalText, textLength); // Use strncpy to avoid buffer overflow
        paddedText[LCD_WIDTH] = '\0'; // add null terminator
        return paddedText;
    }
}

void loop() {
    if (SCROLL_ENABLED) {
        scrollText(row1);
        scrollText(row2);    
    }
    
    lcd.clear();

    lcd.setCursor(0, 0);
    lcd.print(row1);

    lcd.setCursor(0, 1);
    lcd.print(row2);

    delay(SCROLL_WAIT_TIME);
}

void scrollText(char *text) {
    // Check the scrolling direction
    if (SCROLL_DIRECTION == 'L') {
        scrollLeft(text); // Scroll text to the left
    }
    else { // SCROLL_DIRECTION == 'R'
        scrollRight(text); // Scroll text to the right
    }
}

void scrollLeft(char *text) {
    int textLength = strlen(text);
    char temp = text[0]; // Store the first character temporarily

    for (int i = 0; i < textLength - 1; i++){
        text[i] = text[i + 1]; // Shift each character to the left
    }

    text[textLength - 1] = temp; // Move the first character to the end
}

void scrollRight(char *text){
    int textLength = strlen(text);
    char temp = text[textLength - 1]; // Store the last character temporarily

    for (int i = textLength - 1; i > 0; i--)    {
        text[i] = text[i - 1]; // Shift each character to the right
    }

    text[0] = temp; // Move the last character to the beginning
}