//This example code is in the Public Domain (or CC0 licensed, at your option.)
//By Evandro Copercini - 2018
//
//This example creates a bridge between Serial and Classical Bluetooth (SPP)
//and also demonstrate that SerialBT have the same functionalities of a normal Serial

#include "BluetoothSerial.h"
#include "soc/soc.h"
#include "soc/rtc_cntl_reg.h"
#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled! Please run `make menuconfig` to and enable it
#endif

BluetoothSerial SerialBT;

#define triac 23 //22
#define zero 32 //23

int brightness = 0;
int percentage = 0;

void setup() {
  WRITE_PERI_REG(RTC_CNTL_BROWN_OUT_REG, 0); //disable brownout detector
  Serial.begin(115200);
  SerialBT.begin("ESP32test"); //Bluetooth device name
  Serial.println("The device started, now you can pair it with bluetooth!");
  pinMode(triac, OUTPUT);
  pinMode(zero, INPUT);
  //Serial.println("Exit setup");
}

void loop() {

  //Serial.println(digitalRead(zero));
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    percentage = SerialBT.read();

    Serial.println(percentage);
  }
  //delay(20);
  brightness = map(percentage, 0, 100, 9800, 1000);
  //Serial.println(brightness);
  if (!digitalRead(zero)) {
    //Serial.println("In zero");
    delayMicroseconds(brightness);
    digitalWrite(triac, HIGH);
    delayMicroseconds(100);
    digitalWrite(triac, LOW);
  }
}
