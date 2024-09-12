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
    lcd.begin(16, 2);
    lcd.createChar(1, heart);
    lcd.createChar(2, smiley);
}

void loop() {
    sensorReading = analogRead(A0);
    
    if(sensorReading<100) {
        pozice_x++;
    }
    else if(sensorReading<2500) {
        lcd.setCursor(8, 1);
        lcd.print(sensorReading);
    } else if(sensorReading<2600) {
        pozice_y--;
    } else if(sensorReading<4200) {
        pozice_x--;
    } else if(sensorReading<6500) {
        stav='r';
    } else if(sensorReading<10000) {
        pozice_x++;
    }
    
    if(pozice_x > 16) {
        pozice_x = 0;
    }
    if(pozice_y > 1) {
        pozice_y = 0;
    }
    
    lcd.setCursor(0, 0);
    lcd.print(pozice_x);
    lcd.setCursor(0, 1);
    lcd.print(pozice_y);
    lcd.setCursor(14, 1);
    if(sensorReading == 0U) {
        lcd.write(2);
    }
    else {
        lcd.write(1);
    }
    delay(100);
}
