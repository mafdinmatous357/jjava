#include <LiquidCrystal.h>
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

//char pole [16][2];
int pozice_x=0;
int pozice_y=0;
char stav='c';

void setup() {
  lcd.begin(16, 2);
}

void loop() {
  int sensorReading = analogRead(A0);
  
  
  
    
  /*if(sensorReading>0&&sensorReading<100){pozice_x++;} else if(sensorReading>2500&&sensorReading<2600){pozice_y--;} else if(sensorReading>4000&&sensorReading<4200){pozice_x--;} else if(sensorReading>6300&&sensorReading<6500){stav='r';} else if(sensorReading>9800&&sensorReading<10000){pozice_x++;}*/
    if(sensorReading>0&&sensorReading<100){pozice_x++;}
    if(pozice_x>16)pozice_x=0;
 lcd.setCursor(pozice_x,pozice_y);
    lcd.print(sensorReading);
    delay(100);
}