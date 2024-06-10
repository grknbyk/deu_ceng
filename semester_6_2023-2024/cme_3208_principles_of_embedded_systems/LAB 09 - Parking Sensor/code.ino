#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Define pins for HC-SR04
const int trigPin = 4;
const int echoPin = 5;
const int buzzerPin = 3;
const int ledPin = 2;

// Define constants
const int BUZZER_LED_DURATION_MAX = 1000;
const int BUZZER_LED_DURATION_MIN = 10;
const int DISTANCE_MAX = 50;
const int DISTANCE_MIN = 5;
const int UPDATE_DURATION_LCD = 200;
int CURRENT_DISTANCE = 0;
int CURRENT_DURATION = 0;

LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  // Set pin modes
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(buzzerPin, OUTPUT);
  pinMode(ledPin, OUTPUT);
  // Initialize LCD
  lcd.init();
  lcd.backlight();
}

void loop() {
  CURRENT_DISTANCE = getDistance(); // Get distance in cm

  if (CURRENT_DISTANCE <= DISTANCE_MIN) { // If distance is less than minimum
    CURRENT_DURATION = 0; // Set to 0 duration
  } else if (CURRENT_DISTANCE > DISTANCE_MAX) { // If distance is greater than maximum
    CURRENT_DURATION = BUZZER_LED_DURATION_MAX;
  } else { // If distance is within range
    CURRENT_DURATION = calculateDuration(); // Calculate duration
  }

  updateLCD(CURRENT_DISTANCE, CURRENT_DURATION);
  
  controlBuzzerAndLED(CURRENT_DURATION);
}

int getDistance() {
  long duration;
  int distance;

  digitalWrite(trigPin, LOW); 
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  duration = pulseIn(echoPin, HIGH);
  distance = duration * 0.034 / 2;

  return distance;
}

// Calculate duration based on distance
int calculateDuration() {
  return ( (CURRENT_DISTANCE-DISTANCE_MIN) * (BUZZER_LED_DURATION_MAX-BUZZER_LED_DURATION_MIN) / (DISTANCE_MAX-DISTANCE_MIN) ) + BUZZER_LED_DURATION_MIN;
}

void updateLCD(int distance, int duration) {
  static unsigned long lastUpdateTime = 0; // Last update time
  unsigned long currentMillis = millis(); // Current time

  // if not enough time has passed since last update, return else update LCD
  if (currentMillis - lastUpdateTime < (unsigned long) UPDATE_DURATION_LCD)
    return;

  lastUpdateTime = currentMillis; // Update last update time

  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("DISTANCE: ");
  lcd.print(distance);
  lcd.print(" cm");
  lcd.setCursor(0, 1);
  lcd.print("DURATION: ");
  lcd.print(duration);
  lcd.print(" ms");
}

void controlBuzzerAndLED(int duration) {
  static unsigned long lastBuzzTime = 0;
  static bool isBuzzing = false;

  if (duration == 0) { // If duration is 0, turn on LED and buzzer non-stop
    digitalWrite(ledPin, HIGH);
    tone(buzzerPin, 455);
    isBuzzing = true;
  } 
  // If duration is within range, turn on LED and buzzer for duration
  else if (BUZZER_LED_DURATION_MIN <= duration && duration <= BUZZER_LED_DURATION_MAX) {
    if (!isBuzzing && millis() - lastBuzzTime >= (unsigned long)duration) {
      digitalWrite(ledPin, HIGH);
      tone(buzzerPin, 455);
      lastBuzzTime = millis();
      isBuzzing = true;
    } else if (isBuzzing && millis() - lastBuzzTime >= (unsigned long)duration) {
      digitalWrite(ledPin, LOW);
      noTone(buzzerPin);
      lastBuzzTime = millis();
      isBuzzing = false;
    }
  } else { // If duration is out of range, turn off LED and buzzer
    digitalWrite(ledPin, LOW);
    noTone(buzzerPin);
    isBuzzing = false;
  }
}