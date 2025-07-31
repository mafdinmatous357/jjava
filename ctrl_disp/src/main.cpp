#include <Arduino.h>
#include <TouchScreen.h>
#include <LCDWIKI_GUI.h>
#include <LCDWIKI_KBV.h>
#include <ArduinoSTL.h>
#include <list>

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

class Starship {
private:
  LCDWIKI_KBV &lcd;
  int bodyWidth;
  int bodyHeight;
  int noseHeight;
  uint16_t drawColor;
  uint16_t clearColor;

  void setDrawColor(uint16_t color) {
    lcd.Set_Draw_color(color);
  }

public:
  Starship(LCDWIKI_KBV &lcdRef, int bodyW, int bodyH, int noseH, uint16_t drawCol, uint16_t clearCol)
      : lcd(lcdRef), bodyWidth(bodyW), bodyHeight(bodyH), noseHeight(noseH), drawColor(drawCol), clearColor(clearCol) {}

  void draw(int x, int y) {
    // Draw the body of the starship (rectangle)
    lcd.Fill_Rect(x - bodyWidth / 2, y - bodyHeight / 2, bodyWidth, bodyHeight, drawColor);

    // Print a big "M" inside the starship body
    lcd.Set_Text_colour(clearColor); // Text color
    lcd.Set_Text_Back_colour(drawColor); // Background color
    lcd.Set_Text_Size(2); // Text size
    lcd.Print_String("M", x - 6, y - 8); // Center the "M"

    // Draw the nose of the starship (triangle)
    setDrawColor(drawColor);
    for (int i = 0; i < noseHeight; i++) {
      lcd.Draw_Pixel(x, y - bodyHeight / 2 - i);
      lcd.Draw_Pixel(x - i, y - bodyHeight / 2 - i);
      lcd.Draw_Pixel(x + i, y - bodyHeight / 2 - i);
    }
  }

  void clear(int x, int y) {
    // Clear the area where the starship was located
    lcd.Fill_Rect(x - bodyWidth / 2, y - bodyHeight / 2 - noseHeight, bodyWidth, bodyHeight + noseHeight, clearColor);

    // Clear the nose of the starship
    setDrawColor(clearColor);
    for (int i = 0; i < noseHeight; i++) {
      lcd.Draw_Pixel(x, y - bodyHeight / 2 - i);
      lcd.Draw_Pixel(x - i, y - bodyHeight / 2 - i);
      lcd.Draw_Pixel(x + i, y - bodyHeight / 2 - i);
    }
  }
};

Starship starship(mylcd, 20, 40, 20, 0x0000, 0xFFFF); // Black draw color, white clear color

class Fireball {
private:
  LCDWIKI_KBV &lcd;
  int x, y;
  int radius;
  uint16_t color;

public:
  Fireball(LCDWIKI_KBV &lcdRef, int startX, int startY, int r, uint16_t col)
      : lcd(lcdRef), x(startX), y(startY), radius(r), color(col) {}

  void draw() {
    lcd.Set_Draw_color(color);
    lcd.Fill_Circle(x, y, radius);
  }

  void clear() {
    lcd.Set_Draw_color(0xFFFF);
    lcd.Fill_Circle(x, y, radius); // Clear with white background
  }

  void move(int dy) {
    clear();
    y += dy;
    draw();
  }

  bool isOffScreen() {
    return y < 0;
  }
};
std::list<Fireball> fireballs;

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
    // Clear the previous starship position using the Starship class
    if (prevX != -1 && prevY != -1) {
      starship.clear(prevX, prevY);
    }

    // Draw the starship at the new position using the Starship class
    starship.draw(X, Y);

    // Update the previous position
    prevX = X;
    prevY = Y;

    // Create a fireball at the starship's position
    fireballs.push_back(Fireball(mylcd, X, Y - 40, 5, 0xF800)); // Red fireball
  }

  // Move and draw fireballs
  for (auto it = fireballs.begin(); it != fireballs.end();) {
    it->move(-5); // Move fireball upwards
    if (it->isOffScreen()) {
      it = fireballs.erase(it); // Remove fireball if off-screen
    } else {
      ++it;
    }
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
