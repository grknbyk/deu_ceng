#include <time.h>

const int LED_PIN = A1;
const int LIGHT_PIN = A0;
const int MOTOR_CONTROL_PIN = 5; 
const int LEFT_PIN = A4;
const int RIGHT_PIN = A5;

const int LED_WAIT_DURATION_MIN = 2000;
const int LED_WAIT_DURATION_MAX = 6000;
const int LED_ON_DURATION_MIN = 2000;
const int LED_ON_DURATION_MAX = 6000;
const int LIGHT_SENSOR_OUTPUT_INTERVAL = 1000;

unsigned long previousMillis = 0;
unsigned long ledDuration = 0;
unsigned long lastSensorReadMillis = 0;
bool ledState = false;

void setup() {
  pinMode(LIGHT_PIN, INPUT);
  pinMode(LED_PIN, OUTPUT);
  pinMode(MOTOR_CONTROL_PIN, OUTPUT);
  pinMode(LEFT_PIN, OUTPUT);
  pinMode(RIGHT_PIN, OUTPUT);

  Serial.begin(9600);
  srand(time(0));
  setRandomLEDState();

  digitalWrite(LEFT_PIN, HIGH);
  digitalWrite(RIGHT_PIN, LOW);
}

void loop() {
  unsigned long currentMillis = millis();
  int lightValue = analogRead(LIGHT_PIN);

  if ((currentMillis - previousMillis) >= ledDuration) {
    setRandomLEDState();
    previousMillis = currentMillis;
  }

  if (currentMillis - lastSensorReadMillis >= LIGHT_SENSOR_OUTPUT_INTERVAL) {
    Serial.print("Light sensor output is ");
    Serial.println(lightValue);
    lastSensorReadMillis = currentMillis;
  }

  if (lightValue > 400) {
    analogWrite(MOTOR_CONTROL_PIN, 0);
  } else {
    analogWrite(MOTOR_CONTROL_PIN, 152);
  }
}

void setRandomLEDState() {
  if (ledState) {
    ledDuration = random(LED_WAIT_DURATION_MIN, LED_WAIT_DURATION_MAX);
    digitalWrite(LED_PIN, LOW);
    Serial.print("\nLED is turned OFF for ");
    Serial.print(ledDuration);
    Serial.println(" milliseconds.");
  } else {
    ledDuration = random(LED_ON_DURATION_MIN, LED_ON_DURATION_MAX);
    digitalWrite(LED_PIN, HIGH);
    Serial.print("\nLED is turned ON for ");
    Serial.print(ledDuration);
    Serial.println(" milliseconds.");
  }
  ledState = !ledState;
}