#define LED_PIN 13
#define LIGHT_SENSOR_PIN A1

int LED_ACTIVATION_VALUE = 330;
int SERIAL_OUTPUT_FREQUENCY = 500;  // Time between serial monitor outputs (in milliseconds)

void setup() {
  pinMode(LED_PIN, OUTPUT);
  pinMode(LIGHT_SENSOR_PIN, INPUT);
  Serial.begin(9600);  // Initialize serial communication for monitoring
}

void loop() {
  int sensorValue = analogRead(LIGHT_SENSOR_PIN);
  Serial.print("LIGHT SENSOR: ");
  Serial.println(sensorValue);

  Serial.print("LED   STATUS: ");
  if (sensorValue > LED_ACTIVATION_VALUE) { // bright environment
    digitalWrite(LED_PIN, LOW);  // LED off 
    Serial.println("OFF");
  } else { // dark environment
    digitalWrite(LED_PIN, HIGH);  // LED on 
    Serial.println("ON");
  }
  
  Serial.println();

  delay(SERIAL_OUTPUT_FREQUENCY);
}