package Model;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

public class TwoWaySerialComm{
	private PlatformModel model;
    private SerialWriter sw;
    public TwoWaySerialComm(PlatformModel model){
        this.model = model;
    }
    
    public void connect ( String portName ) throws Exception{  
    	try{
    		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        	try{
        		CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
        		if ( commPort instanceof SerialPort ){
        			SerialPort serialPort = (SerialPort) commPort;
        			serialPort.setSerialPortParams(11520,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          
        			InputStream in = serialPort.getInputStream();
        			OutputStream out = serialPort.getOutputStream();
        			(new Thread(new SerialReader(in, model))).start();
        			 sw = new SerialWriter(out,model);
        			
        		}
//        		else{Pmodel.notifyError("Error: Only serial ports are handled by this example.",true);}
        	}catch(gnu.io.PortInUseException e){System.out.println("Puerto Ocupado!!");/*Pmodel.notifyError("Error: Port is currently in use",true);*/}
    	}catch(gnu.io.NoSuchPortException e){System.out.println("Puerto no conectado!!");/*Pmodel.notifyError("Error: Port is not connected",true);*/}
   }

    public void sendMessage(){
    	sw.sendMessage();
    }
    
    class SerialReader implements Runnable{
        InputStream in; 
        private PlatformModel model;
        
        public SerialReader (InputStream in,PlatformModel model){
        	this.in = in;
            this.model = model;
        }
        
        public void readData(){

        	BufferedReader buffer= new BufferedReader(new InputStreamReader(in));
            try {
            	if (buffer.ready()){
            		while(buffer.read() != '('){
            			
            		}
            		String aux = '('+buffer.readLine();
        			//System.out.println(aux);
        			
        			String delims = "(,)";
        			String[] tokens = aux.split(delims);
        			if(tokens[tokens.length-1].equals(")") && tokens.length==10){
            			//"(;d1;d2.....;)"
        				System.out.println(tokens[0]+","+tokens[1]+","+tokens[2]+","+tokens[3]+","+tokens[4]+","+tokens[5]);
        				model.setCurrent(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2]),Float.parseFloat(tokens[3]),Float.parseFloat(tokens[4]),Float.parseFloat(tokens[5]));
        				
        			}

            	}
            }catch (IOException e){}            
        }
		public void run() {
			while(true){
				readData();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
 	}
    
    class SerialWriter{
        OutputStream out;
        private PlatformModel model;
        //double auxiliar;
        int anguloAEnviar=0;
        public SerialWriter (OutputStream out, PlatformModel model){
            this.out = out;
            this.model = model;  
            //this.model.register(this);
        }
        
        private void sendData(char data){
        	try {
				this.out.write((byte)(data));
			} catch (IOException e) {
				e.printStackTrace();
			}        			   			  	
        }
        
        public void sendMessage(){
        	String mensaje="("+
        			Float.toString(model.getX())+
        			","+
        			Float.toString(model.getY())+
        			','+
        			Float.toString(model.getZ())+
        			','+
        			Float.toString(model.getYawPoint())+
        			','+
        			Float.toString(model.getPitchPoint())+
        			','+
        			Float.toString(model.getRollPoint())+
        			','+
        			Float.toString(model.getKP(GradoDeLibertad.YAW))+
        			','+
        			Float.toString(model.getKI(GradoDeLibertad.YAW))+
        			','+
        			Float.toString(model.getKD(GradoDeLibertad.YAW))+
        			','+
        			Float.toString(model.getKP(GradoDeLibertad.PITCH))+
        			','+
        			Float.toString(model.getKI(GradoDeLibertad.PITCH))+
        			','+
        			Float.toString(model.getKD(GradoDeLibertad.PITCH))+
        			','+
        			Float.toString(model.getKP(GradoDeLibertad.ROLL))+
        			','+
        			Float.toString(model.getKI(GradoDeLibertad.ROLL))+
        			','+
        			Float.toString(model.getKD(GradoDeLibertad.ROLL))+
					','+
					Float.toString(model.isPIDActive()?1:0)+
					")";
        	for(char c : mensaje.toCharArray() ){
        		sendData(c);
        	}
        }

    }
}

