int TIMER_START = 20;   // time unit
int TIMER_SPEED = 1000;  // miliseconds for each time unit
int ANIMATION_SPEED = 350;

// 7 segment display pins
const int A = 8;
const int B = 10;
const int C = 9;
const int D = 7;
const int E = 5;
const int F = 6;
const int G = 4;
const int DP = 3;

const int LED_PIN = 13;            // LED pin
const int MOTION_SENSOR_PIN = 12;  // HC-SR501 PIR motion sensor pin


void setup() {
  pinMode(MOTION_SENSOR_PIN, INPUT);  // Set HC-SR501 PIR motion sensor pin as input
  // Set the pin mode of the rest as output
  pinMode(LED_PIN, OUTPUT);
  pinMode(A, OUTPUT);
  pinMode(B, OUTPUT);
  pinMode(C, OUTPUT);
  pinMode(D, OUTPUT);
  pinMode(E, OUTPUT);
  pinMode(F, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(DP, OUTPUT);
}

void loop() {
  // Default states
  digitalWrite(LED_PIN, LOW);  // Turns the LED off
  displayDigit(-1);            // Displays only the dot on the 7-segment display

  int motionState = digitalRead(MOTION_SENSOR_PIN);  // Reads the state of the motion sensor

  if (motionState == HIGH) {      // If motion is detected
    digitalWrite(LED_PIN, HIGH);  // Turns the LED on
    int countdown = TIMER_START;  // Use a single variable for countdown

    if (TIMER_START > 15) {
      displayAnimation(ANIMATION_SPEED, TIMER_START - 15, TIMER_SPEED);  // loading animation for TIMER_START - 15 seconds
    }

    countdown = (TIMER_START > 15) ? 15 : TIMER_START;
    for (int i = countdown; i >= 0; i--) {
      displayDigit(i);
      delay(TIMER_SPEED);
    }
  }
}

// Loading animation for 7 segment display
void displayAnimation(int animationDelay, int unit, int unitMS) {
  const int animationSegments[] = { E, B, A, C, F, G };  // Display animation segments in order
  const int numSegments = sizeof(animationSegments) / sizeof(animationSegments[0]);
  int remainingDuration = unit * unitMS;

  int i = 0;
  while (remainingDuration > 0) {
    // Animation loop
    for (int j = 0; j < numSegments; j++){
      digitalWrite(animationSegments[j], HIGH);
    }
    digitalWrite(animationSegments[i], LOW);

    i = (i + 1) % numSegments;  // Move to the next segment, cycling back to the beginning if necessary

    // if there is less remaining duration then the animation delay
    // use that remaining delay instead of using animation delay to extend the duration
    int dly = min(animationDelay, remainingDuration);
    delay(dly);               // Wait for the calculated delay
    remainingDuration -= dly;  // Update the remaining duration
  }
}

// Display digits from 0 to 15 in 7 segment display with 16 based digit symbols
void displayDigit(int i) {
  switch (i) {
    case 0:
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, HIGH);
      digitalWrite(E, LOW);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 1:
      digitalWrite(A, HIGH);
      digitalWrite(B, HIGH);
      digitalWrite(C, HIGH);
      digitalWrite(D, HIGH);
      digitalWrite(E, HIGH);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 2:
      digitalWrite(A, HIGH);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, LOW);
      digitalWrite(G, HIGH);
      digitalWrite(DP, HIGH);
      break;
    case 3:
      digitalWrite(A, HIGH);
      digitalWrite(B, HIGH);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 4:
      digitalWrite(A, LOW);
      digitalWrite(B, HIGH);
      digitalWrite(C, HIGH);
      digitalWrite(D, LOW);
      digitalWrite(E, HIGH);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 5:
      digitalWrite(A, LOW);
      digitalWrite(B, HIGH);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, HIGH);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 6:
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, HIGH);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 7:
      digitalWrite(A, HIGH);
      digitalWrite(B, HIGH);
      digitalWrite(C, LOW);
      digitalWrite(D, HIGH);
      digitalWrite(E, HIGH);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 8:
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 9:
      digitalWrite(A, LOW);
      digitalWrite(B, HIGH);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, HIGH);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 10:  // A
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, HIGH);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 11:  // b
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, HIGH);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, HIGH);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 12:  // C
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, HIGH);
      digitalWrite(E, LOW);
      digitalWrite(F, HIGH);
      digitalWrite(G, HIGH);
      digitalWrite(DP, HIGH);
      break;
    case 13:  // d
      digitalWrite(A, HIGH);
      digitalWrite(B, LOW);
      digitalWrite(C, HIGH);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, LOW);
      digitalWrite(G, LOW);
      digitalWrite(DP, HIGH);
      break;
    case 14:  // E
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, LOW);
      digitalWrite(F, HIGH);
      digitalWrite(G, HIGH);
      digitalWrite(DP, HIGH);
      break;
    case 15:  // F
      digitalWrite(A, LOW);
      digitalWrite(B, LOW);
      digitalWrite(C, LOW);
      digitalWrite(D, LOW);
      digitalWrite(E, HIGH);
      digitalWrite(F, HIGH);
      digitalWrite(G, HIGH);
      digitalWrite(DP, HIGH);
      break;
    default:
      digitalWrite(A, HIGH);
      digitalWrite(B, HIGH);
      digitalWrite(C, HIGH);
      digitalWrite(D, HIGH);
      digitalWrite(E, HIGH);
      digitalWrite(F, HIGH);
      digitalWrite(G, HIGH);
      digitalWrite(DP, HIGH);
  }
}