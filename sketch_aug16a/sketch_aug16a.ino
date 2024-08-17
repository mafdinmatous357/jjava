#include <LiquidCrystal.h>
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

char pole [16][2];
int pozice_x=1;
int pozice_y=0;
char stav='l';

void setup() {
  lcd.begin(16, 2);
}

void loop() {
  int sensorReading = analogRead(A0);
  
  for (int a=0; a<=1; a++)
  {
    lcd.setCursor(pozice_x,pozice_y);
    lcd.print(stav);
    delay(1000);
  };
};