#include <Arduino.h>
#include <TouchScreen.h>
#include <LCDWIKI_GUI.h>
#include <LCDWIKI_KBV.h>

#define YP A1
#define XM A2
#define YM 7
#define XP 6

LCDWIKI_KBV mylcd(ILI9341,A3,A2,A1,A0,A4);//width,height,cs,cd,wr,rd,reset
TouchScreen ts = TouchScreen(XP, YP, XM, YM, 300);

// put function declarations here:
int myFunction(int, int);

void setup() {
  mylcd.Init_LCD(); //initialize lcd
    mylcd.Fill_Screen(0xFFFF); //display white
    pinMode(13, OUTPUT);
  Serial.begin(9600);
}

uint8_t cnt = 0;

void loop() {
  digitalWrite(13, HIGH);
  TSPoint p = ts.getPoint(); //get touch point
  digitalWrite(13, LOW);
  pinMode(XM, OUTPUT);
  pinMode(YP, OUTPUT);  
  mylcd.Set_Text_Size(2);
  mylcd.Set_Draw_color (0,0,0);
  long x = p.x;
  long y = p.y;
  long X = map(x,200,920,0,mylcd.Get_Display_Width());
  long Y =(y - 956) * (320 - 0) / (206 - 956) + 0;
  if (p.z > 10) {
    mylcd.Draw_Pixel(X,Y);
  }
  mylcd.Print_Number_Int (X,100,220,3,' ',10);
  mylcd.Print_Number_Int (Y,100,300,3,' ',10);
  mylcd.Set_Text_colour(0xF800); // Red in RGB565 format
  mylcd.Set_Text_Back_colour(0x00ff00);
  mylcd.Set_Text_Size(1);
  cnt = (cnt==32) ? 0 : cnt;
  if (cnt++==0) {
    Serial.print(x);
    Serial.print("=");
    Serial.print(X);
    Serial.print(" ");
    Serial.print(y);
    Serial.print("=");
    Serial.print(Y);
    Serial.print(" ");
    Serial.print(p.z);
    Serial.println(" ");
  }
}
