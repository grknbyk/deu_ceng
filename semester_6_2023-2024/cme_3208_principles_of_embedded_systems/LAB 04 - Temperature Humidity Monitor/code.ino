#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <DHT.h>

char LCD_LANGUAGE = 'E';  // E for English, T for Turkish

const int buttonPin = 3;
const int dht11Pin = 2;
DHT dht(dht11Pin, DHT11);
LiquidCrystal_I2C lcd(0x27, 16, 2);

int buttonState = 0;
int previousButtonState = 0;

void setup() {
  dht.begin();
  lcd.init();
  lcd.backlight();
  pinMode(buttonPin, INPUT);
}

void loop() {
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  if (isnan(h) || isnan(t)) {
    lcd.setCursor(0, 0);
    lcd.print("Failed to read");
    return;
  }

  buttonState = digitalRead(buttonPin);

  if (buttonState != previousButtonState && buttonState == HIGH) {
    changeLanguage();
  }

  previousButtonState = buttonState; 

  displayOnLCD(h, t);

  delay(50);
}

void changeLanguage() {
  if (LCD_LANGUAGE == 'E') {
    LCD_LANGUAGE = 'T';
  } else {
    LCD_LANGUAGE = 'E';
  }
}

void displayOnLCD(float h, float t) {
  lcd.clear();

  if (LCD_LANGUAGE == 'E') {
    lcd.setCursor(0, 0);
    lcd.print("TEM(F):");
    float f = t * 9 / 5 + 32;
    if (f < 100)
      lcd.print(" ");
    if (f < 10)
      lcd.print(" ");
    lcd.print(f, 2);
    lcd.setCursor(14, 0);
    lcd.print("EN");
    lcd.setCursor(0, 1);
    lcd.print("HUM(%): ");
    lcd.print(h, 2);
  } else {
    lcd.print("SIC(C):");
    if (t < 100)
      lcd.print(" ");
    if (t < 10)
      lcd.print(" ");
    lcd.print(t, 2);
    lcd.setCursor(14, 0);
    lcd.print("TR");
    lcd.setCursor(0, 1);
    lcd.print("NEM(%): ");
    lcd.print(h, 2);
  }
}
