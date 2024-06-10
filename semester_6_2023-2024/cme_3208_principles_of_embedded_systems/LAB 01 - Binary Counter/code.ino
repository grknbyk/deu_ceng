const int ledPins[] = {6, 5, 4, 3, 2};
const int numOfLeds = sizeof(ledPins) / sizeof(ledPins[0]);

int INCREMENT_VALUE = 1; // increment value
int WAIT_TIME = 500;     // wait time in milliseconds

int counterValue = 0;

void setup() {
  // set pin modes
  for (int i = 0; i < numOfLeds; i++) {
    pinMode(ledPins[i], OUTPUT);
  }
}

void loop() {
  displayCounter(counterValue);
  counterValue = (counterValue + INCREMENT_VALUE) % 32; 
  delay(WAIT_TIME); 
}

// display the current counter value on the LEDs
void displayCounter(int value) {
  // iterate through the LEDs
  for (int i = 0; i < numOfLeds; i++) {
    // if binary value has 1 at i-th position, turn on the LED
 	if (value & (1 << i)) {
      digitalWrite(ledPins[i], HIGH);
    } else {
      digitalWrite(ledPins[i], LOW);
    }
  }
}
