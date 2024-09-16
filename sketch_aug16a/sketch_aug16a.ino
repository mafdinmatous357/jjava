#include <LiquidCrystal.h>
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);

int sensorReading = 0;
uint8_t pozice_x=0;
uint8_t pozice_y=0;
char stav='c';

byte heart[8] = {
  0b00000,
  0b01010,
  0b11111,
  0b11111,
  0b11111,
  0b01110,
  0b00100,
  0b00000
};

byte smiley[8] = {
  0b00000,
  0b00000,
  0b01010,
  0b00000,
  0b00000,
  0b10001,
  0b01110,
  0b00000
};

byte frownie[8] = {
  0b00000,
  0b00000,
  0b01010,
  0b00000,
  0b00000,
  0b00000,
  0b01110,
  0b10001
};

byte armsDown[8] = {
  0b00100,
  0b01010,
  0b00100,
  0b00100,
  0b01110,
  0b10101,
  0b00100,
  0b01010
};

byte armsUp[8] = {
  0b00100,
  0b01010,
  0b00100,
  0b10101,
  0b01110,
  0b00100,
  0b00100,
  0b01010
};

void setup() {
    Serial.begin(9600);
    lcd.begin(16, 2);
    lcd.createChar(1, heart);
    lcd.createChar(2, smiley);
}

void loop() {
    sensorReading = analogRead(A0);
    
    if(sensorReading<90) {
        pozice_x++;
    }
    else if(sensorReading<240) {
        pozice_y--;
    } else if(sensorReading<350) {
        pozice_y++;
    } else if(sensorReading<630) {
        pozice_x--;
    } else if(sensorReading<1000) {
        stav='r';
    } else if(sensorReading<10000) {
        
    }
    Serial.println(sensorReading);
    if(pozice_x > 16) {
        pozice_x = 0;
    }
    if(pozice_y > 1) {
        pozice_y = 0;
    }
    
    /*lcd.setCursor(0, 0);
    lcd.print(pozice_x);
    lcd.setCursor(0, 1);
    lcd.print(pozice_y);*/
    lcd.setCursor(pozice_x, pozice_y);
    if(sensorReading == 0U) {
        lcd.write(2);
    }
    else {
        lcd.write(1);
    }
    delay(1000);
}
