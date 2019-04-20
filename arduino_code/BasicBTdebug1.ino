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

#define triac 22 //22
#define zero 17 //23

int brightness = 0;
int percentage = 0;

bool ALARM_FLAG=false;
long ALARM_HOUR=0;
long ALARM_MIN=0;
int ALARM_COUNTER=1;
bool ALARM_IS_SET=false;


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
            Serial.println(millis());
                      Serial.println(ALARM_MIN+ALARM_HOUR);


  if(ALARM_IS_SET){
    if(millis()==ALARM_MIN+ALARM_HOUR){
          Serial.println("triggered");

    }
  }

  
  if (Serial.available()) {
    SerialBT.write(Serial.read());
  }
  if (SerialBT.available()) {
    int a=SerialBT.read();
    if(a==246){//set alarm
      ALARM_FLAG=true;
    }
    if(ALARM_FLAG && ALARM_COUNTER==1){
      //ALARM_HOUR=a*3600000;
      ALARM_COUNTER++;

    }
    if(ALARM_FLAG && ALARM_COUNTER==2){
      ALARM_MIN=millis()+(a*60000);
      ALARM_COUNTER=1;
      ALARM_IS_SET=true;

    }
    if(a==236){//unset lmao
      ALARM_FLAG=false;
    }

    
    Serial.println(a);
  }
  delay(20);

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
  
