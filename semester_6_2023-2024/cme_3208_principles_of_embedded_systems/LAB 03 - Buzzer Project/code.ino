// Global variables
int SOUND_DURATION = 500;      // 500 milliseconds
int SILENCE_DURATION = 50;     // 50 milliseconds
int MELODY[] = {1, 1, 5, 5, 6, 6, 5, 0, 4, 4, 3, 3, 2, 2, 1}; // Melody for "Twinkle Twinkle Little Star"

const int buzzerPin = 9;       // Pin connected to the buzzer
const int buttonPin = 10;       // Pin connected to the push down button

void setup() {
  pinMode(buzzerPin, OUTPUT);  // Set the buzzer pin as an output
  pinMode(buttonPin, INPUT);   // Set the button pin as an input
}

void loop() {
  if (digitalRead(buttonPin) == HIGH) {  // Check if the button is pressed
    for (int i = 0; i < sizeof(MELODY)/sizeof(MELODY[0]); i++) {
      int note = MELODY[i];
      int frequency = getNoteFrequency(note);

      if (frequency > 0) {
        tone(buzzerPin, frequency, SOUND_DURATION);  // Play the note
      } else {
        noTone(buzzerPin);  // No sound for '0'
      }

      delay(SOUND_DURATION + SILENCE_DURATION);  // Wait for the duration of the note plus silence
    }
  }
}

// Function to get the frequency of a note
int getNoteFrequency(int note) {
  switch (note) {
    case 1: return 261;  // Frequency of note "Do (Low)"
    case 2: return 293;  // Frequency of note "Re"
    case 3: return 329;  // Frequency of note "Mi"
    case 4: return 349;  // Frequency of note "Fa"
    case 5: return 392;  // Frequency of note "Sol"
    case 6: return 440;  // Frequency of note "La"
    case 7: return 493;  // Frequency of note "Si"
    case 8: return 523;  // Frequency of note "Do (High)"
    default: return 0;   // Default case: no sound
  }
}
