#include <Arduino.h>
#include <TouchScreen.h>
#include <LCDWIKI_GUI.h>
#include <LCDWIKI_KBV.h>

#define YP A1
#define XM A2
#define YM 7
#define XP 6

LCDWIKI_KBV mylcd(ILI9341, A3, A2, A1, A0, A4); // width, height, cs, cd, wr, rd, reset
TouchScreen ts = TouchScreen(XP, YP, XM, YM, 300);

// Previous position of the starship
int prevX = -1;
int prevY = -1;

void setup() {
  mylcd.Init_LCD(); // initialize lcd
  mylcd.Fill_Screen(0xFFFF); // display white
  pinMode(13, OUTPUT);
  Serial.begin(9600);
}

void clearStarship(int x, int y) {
  // Clear the area where the starship was located
  int bodyWidth = 20;
  int bodyHeight = 40;
  int noseHeight = 20;

  mylcd.Fill_Rect(x - bodyWidth / 2, y - bodyHeight / 2 - noseHeight, bodyWidth, bodyHeight + noseHeight, 0xFFFF); // Fill with background color (white)

  // set Set_Draw_color for Draw_Pixel
  mylcd.Set_Draw_color(0xFFFF);

  // Clear the nose of the starship
  for (int i = 0; i < noseHeight; i++) {
    mylcd.Draw_Pixel(x, y - bodyHeight / 2 - i); // Fill with background color (white)
    mylcd.Draw_Pixel(x - i, y - bodyHeight / 2 - i);
    mylcd.Draw_Pixel(x + i, y - bodyHeight / 2 - i);
  }
}

void drawStarship(int x, int y) {
  // Draw the body of the starship (rectangle)
  int bodyWidth = 20;
  int bodyHeight = 40;
  mylcd.Fill_Rect(x - bodyWidth / 2, y - bodyHeight / 2, bodyWidth, bodyHeight, 0x0000); // Black color

  // Print a big "M" inside the starship body
  mylcd.Set_Text_colour(0xFFFF); // White color
  mylcd.Set_Text_Back_colour(0x0000); // Black background
  mylcd.Set_Text_Size(2); // Set text size
  mylcd.Print_String("M", x - 6, y - 8); // Adjust position to center the "M"

  // set Set_Draw_color for Draw_Pixel
  mylcd.Set_Draw_color(0x0000);

  // Draw the nose of the starship (triangle)
  int noseHeight = 20;
  for (int i = 0; i < noseHeight; i++) {
    mylcd.Draw_Pixel(x, y - bodyHeight / 2 - i); // Black color
    mylcd.Draw_Pixel(x - i, y - bodyHeight / 2 - i);
    mylcd.Draw_Pixel(x + i, y - bodyHeight / 2 - i);
  }
}

uint8_t cnt = 0;

void loop() {
  digitalWrite(13, HIGH);
  TSPoint p = ts.getPoint(); // get touch point
  digitalWrite(13, LOW);
  pinMode(XM, OUTPUT);
  pinMode(YP, OUTPUT);
  mylcd.Set_Text_Size(2);
  mylcd.Set_Draw_color(0, 0, 0);
  long x = p.x;
  long y = p.y;
  long X = map(x, 200, 920, 0, mylcd.Get_Display_Width());
  long Y = (y - 956) * (320 - 0) / (206 - 956) + 0;

  if (p.z > 10) {
    // Clear the previous starship position
    if (prevX != -1 && prevY != -1) {
      clearStarship(prevX, prevY);
    }

    // Draw the starship at the new position
    drawStarship(X, Y);

    // Update the previous position
    prevX = X;
    prevY = Y;
  }

  mylcd.Print_Number_Int(X, 100, 220, 3, ' ', 10);
  mylcd.Print_Number_Int(Y, 100, 300, 3, ' ', 10);
  mylcd.Set_Text_colour(0xF800); // Red in RGB565 format
  mylcd.Set_Text_Back_colour(0x00ff00);
  mylcd.Set_Text_Size(1);
  cnt = (cnt == 32) ? 0 : cnt;
  if (cnt++ == 0) {
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
