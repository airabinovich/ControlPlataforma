#include <Servo.h>
#include <TimerThree.h>                                 
#include "I2Cdev.h"
#include "MPU6050_6Axis_MotionApps20.h"
// Arduino Wire library is required if I2Cdev I2CDEV_ARDUINO_WIRE implementation
// is used in I2Cdev.h
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
    #include "Wire.h"
#endif

//PIN 18 INTERRUPCION 5 USADO POR LA IMU
#define trigPin 12                                     // Pin 12 trigger output blanco
#define echoPin 19                                     // Pin 19 echo naranja, interrupcion 4
#define TIMER_US 500000                                // 500000 uS timer duration 50ms



//usado para almacenar los valores que llegan del c8051f350
char character2;
String serial2celdas="";
char bufferserial2[25];


//-----------------------------------------------------------

int numero[6];
long i = 0;
int j=0;
Servo servos[6];
char * pch;
String response;
int x=0;

float euler0anterior;
float yawcal=0;
char euler0[10];
char euler1[10];
char euler2[10];
char axisX[10];
char axisY[10];
char axisZ[10];
char dist[10];



String euler0st;
String euler1st;
String euler2st;
String axisXst;
String axisYst;
String axisZst;
String distst;

char byteRead;
char buffer[24];

int write_enable=0;


MPU6050 mpu;
// MPU control/status vars
bool dmpReady = false;  // set true if DMP init was successful
uint8_t mpuIntStatus;   // holds actual interrupt status byte from MPU
uint8_t devStatus;      // return status after each device operation (0 = success, !0 = error)
uint16_t packetSize;    // expected DMP packet size (default is 42 bytes)
uint16_t fifoCount;     // count of all bytes currently in FIFO
uint8_t fifoBuffer[64]; // FIFO storage buffer

// orientation/motion vars
Quaternion q;           // [w, x, y, z]         quaternion container
VectorInt16 aa;         // [x, y, z]            accel sensor measurements
VectorInt16 aaReal;     // [x, y, z]            gravity-free accel sensor measurements
VectorInt16 aaWorld;    // [x, y, z]            world-frame accel sensor measurements
VectorFloat gravity;    // [x, y, z]            gravity vector
float euler[3];         // [psi, theta, phi]    Euler angle container
float ypr[3];           // [yaw, pitch, roll]   yaw/pitch/roll container and gravity vector




// ================================================================
// ===               INTERRUPT DETECTION ROUTINE                ===
// ================================================================

volatile bool mpuInterrupt = false;     // indicates whether MPU interrupt pin has gone high
void dmpDataReady() {
    mpuInterrupt = true;
}



// -------------------------------------------------------------------------------------------------------------
// timerIsr() 50uS second interrupt ISR()
// Called every time the hardware timer 1 times out.
// -------------------------------------------------------------------------------------------------------------
void timerIsr() //cada vez que el timer desborda, mando el pulso
{
 digitalWrite(trigPin, LOW); 
 delayMicroseconds(2); 

 digitalWrite(trigPin, HIGH);
 delayMicroseconds(10); 
 
 digitalWrite(trigPin, LOW);

                           
}



// ---------------------------------------------------------------------------------------------------------------
// echo_interrupt() External interrupt from HC-SR04 echo signal. 
// Called every time the echo signal changes state.
//
// Note: this routine does not handle the case where the timer
//       counter overflows which will result in the occassional error.
// ---------------------------------------------------------------------------------------------------------------

volatile long echo_start = 0;                         // Records start of echo pulse 
volatile long echo_end = 0;                           // Records end of echo pulse
volatile float echo_duration = 0;                     // Duration - difference between end and start
volatile int trigger_time_count = 0;                  // Count down counter to trigger pulse time

void echo_interrupt()
{
  switch (digitalRead(echoPin))                     // Test to see if the signal is high or low
  {
    case HIGH:                                      // High so must be the start of the echo pulse
      echo_end = 0;                                 // Clear the end time
      echo_start = micros();                        // Save the start time
      break;
      
    case LOW:                                       // Low so must be the end of hte echo pulse
      echo_end = micros();                          // Save the end time
      echo_duration = echo_end - echo_start;        // Calculate the pulse duration
      break;
  }
}



// ================================================================
// ===                      INITIAL SETUP                       ===
// ================================================================

void setup() {
    // join I2C bus (I2Cdev library doesn't do this automatically)
    #if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
        Wire.begin();
        TWBR = 24; // 400kHz I2C clock (200kHz if CPU is 8MHz)
    #elif I2CDEV_IMPLEMENTATION == I2CDEV_BUILTIN_FASTWIRE
        Fastwire::setup(400, true);
    #endif

    // initialize serial communication
    // (115200 chosen because it is required for Teapot Demo output, but it's
    // really up to you depending on your project)
    Serial.begin(115200);

    Serial2.begin(115200); //usado para recibir del c8051f350 el valor de las celdas, conecatdo a los pins 16 y 17

    // load and configure the DMP
    //Serial.println(F("Initializing DMP..."));
    devStatus = mpu.dmpInitialize();

    // supply your own gyro offsets here, scaled for min sensitivity
    mpu.setXGyroOffset(220);
    mpu.setYGyroOffset(76);
    mpu.setZGyroOffset(-85);
    mpu.setZAccelOffset(1788); // 1688 factory default for my test chip

    // make sure it worked (returns 0 if so)
    if (devStatus == 0) {
            // turn on the DMP, now that it's ready
            //Serial.println(F("Enabling DMP..."));
            mpu.setDMPEnabled(true);
    
            // enable Arduino interrupt detection
           // Serial.println(F("Enabling interrupt detection (Arduino external interrupt 0)..."));
            attachInterrupt(5, dmpDataReady, RISING); //arduino mega pin 18
            mpuIntStatus = mpu.getIntStatus();
    
            // set our DMP Ready flag so the main loop() function knows it's okay to use it
            Serial.println(F("DMP ready! Waiting for first interrupt..."));
            dmpReady = true;
    
            // get expected DMP packet size for later comparison
            packetSize = mpu.dmpGetFIFOPacketSize();
    } else {
            // ERROR!
            // 1 = initial memory load failed
            // 2 = DMP configuration updates failed
            // (if it's going to break, usually the code will be 1)
            Serial.print(F("DMP Initialization failed (code "));
            Serial.print(devStatus);
            Serial.println(F(")"));
    }
    
    
    
  //-------------------------------------- CONFIGURACION PARA EL SENSOR DE DISTANCIA  -----------------------------------------------//
  
  pinMode(trigPin, OUTPUT);                           //se usan para el sensor de distancia (altura)
  pinMode(echoPin, INPUT);                           
  attachInterrupt(4, echo_interrupt, CHANGE);         //cuando llega el pulso de vuelta del sensor de distancia, interrupcion 4 = pin 19
  
  Timer3.initialize(TIMER_US);                        // Initialise timer 3, para el sensor de distancia, cada TIMER_US mando el pulso al sensor
  Timer3.attachInterrupt( timerIsr );                 // Attach interrupt to the timer service routine 
  //---------------------------------------------------------------------------------------------------------------------------------//

}



// ================================================================
// ===                    MAIN PROGRAM LOOP                     ===
// ================================================================

void loop() {
    // if programming failed, don't try to do anything
    if (!dmpReady) return;

     serial2celdas=""; //reinicio la variable con
     
 // wait for MPU interrupt or extra packet(s) available
 while (!mpuInterrupt && fifoCount < packetSize) {
               
      
      
      if ( Serial.available() ){ //cada vez que llega algo de labview
               
              
               
               if(write_enable){
                       //si se termino de calibrar la imu, y esta habilitada la escritura de motores, envio los valores de la imu a labview
                       dtostrf(ypr[0] * 180/M_PI-yawcal,1,2,euler0);
                       euler0st=String(euler0);
                       dtostrf(ypr[1] * 180/M_PI,1,2,euler1);
                       euler1st=String(euler1);
                       dtostrf(ypr[2] * 180/M_PI,1,2,euler2);
                       euler2st=String(euler2);
                       
                       dtostrf(aaWorld.x,1,2,axisX);
                       axisXst=String(axisX);
                       dtostrf(aaWorld.y,1,2,axisY);
                       axisYst=String(axisY);
                       dtostrf(aaWorld.z,1,2,axisZ);
                       axisZst=String(axisZ);
            
                       dtostrf(10*echo_duration / 58,1,2,dist);
                       distst=String(dist);
                       
           
                 
                       Serial2.write("/"); //envio al c8051f350 el caracter /. el dispositivo al recibir esta letra devuelve el valor de las celdas de carga (4 valores)
                     
                       
                        while(!(Serial2.available())){}
                 
                       
                          Serial2.readBytesUntil('*', bufferserial2, 32); //guardo en buffer lo que lei del c8051f350
                         

                    
                       
                     
                       //serial2_celdas tiene este formato:    /500 500 500 500  donde 500 es el valor de la lectura
                       
                       response=euler0st+"/"+euler1st+"/"+euler2st+"/"+axisXst+"/"+axisYst+"/"+axisZst+"/"+distst+bufferserial2;

                       
                       Serial.println(response);
               
               } //if(write_enable)
               else{
                     // si la imu no se calibro, no muestro los valores de la imu, muestro el mensaje de calibracion
                      Serial.println("Espere por favor, calibrando IMU...");
               }
               
             
             Serial.readBytesUntil('-', buffer, 24); //guardo en buffer lo que lei de labview
             
              //tomo lo que recibi, y lo convierto a entero para escribirlo en los motores
              pch = strtok (buffer," "); //buffer es lo que llega de labview
              while (pch != NULL)
              {
                      numero[i]=atoi(pch); //guardo el int en el arreglo
  
                      if(write_enable)
                         {servos[i].write(numero[i]);}  //si la calibracion esta finalizada, escribo el valor de cada servo en su lugar correspondiente
                      else{ //si la calibracion no finalizo
             
                              if((ypr[0]-euler0anterior)==0){  //me fijo si la calibracion esta lista
                                      yawcal=ypr[0]*180/M_PI; //guardo el offset necesario, para restarlo a las medidas de yaw y evitar el drift
                                      write_enable=1;   //si la calibracion esta lista, seteo el flag que indica fin de calibracion
                                      for(j=0;j<6;j++){  //inicializo los motores
                                      pinMode(j+2, OUTPUT); 
                                      servos[j].attach(j+2);
                                    }
                                  }
             
                       } 
               
               
               
                pch = strtok (NULL, " ");
                i++;
                i=i%6;
               
              }//while (pch != NULL)
             
      }  //if ( Serial.available() )     
      
}//while (!mpuInterrupt && fifoCount < packetSize) 

    // reset interrupt flag and get INT_STATUS byte
    mpuInterrupt = false;
    mpuIntStatus = mpu.getIntStatus();

    // get current FIFO count
    fifoCount = mpu.getFIFOCount();

    // check for overflow (this should never happen unless our code is too inefficient)
    if ((mpuIntStatus & 0x10) || fifoCount == 1024) {
            // reset so we can continue cleanly
            mpu.resetFIFO();
            Serial.println(F("FIFO overflow!"));

    // otherwise, check for DMP data ready interrupt (this should happen frequently)
    } else if (mpuIntStatus & 0x02) {
      
        // wait for correct available data length, should be a VERY short wait
        while (fifoCount < packetSize) fifoCount = mpu.getFIFOCount();

        // read a packet from FIFO
        mpu.getFIFOBytes(fifoBuffer, packetSize);
        
            // track FIFO count here in case there is > 1 packet available
            // (this lets us immediately read more without waiting for an interrupt)
            fifoCount -= packetSize;


            // display Euler angles in degrees
            euler0anterior=ypr[0];
            mpu.dmpGetQuaternion(&q, fifoBuffer);
            mpu.dmpGetGravity(&gravity, &q);
            mpu.dmpGetYawPitchRoll(ypr, &q, &gravity);
      
      
        
            // display initial world-frame acceleration, adjusted to remove gravity
            // and rotated based on known orientation from quaternion
            mpu.dmpGetQuaternion(&q, fifoBuffer);
            mpu.dmpGetAccel(&aa, fifoBuffer);
            mpu.dmpGetGravity(&gravity, &q);
            mpu.dmpGetLinearAccel(&aaReal, &aa, &gravity);
            mpu.dmpGetLinearAccelInWorld(&aaWorld, &aaReal, &q);
      
  


    }//else if (mpuIntStatus & 0x02) 
} //LOOP()









