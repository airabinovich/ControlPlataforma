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
import java.util.HashMap;

public class TwoWaySerialComm{
	private PlatformModel model;
    private SerialWriter sw;
    private static int portIndex = -1;
    private static ArrayList<String> puertosLinux;
    private static ArrayList<String> puertosWin;
    private HashMap<String, ArrayList<String>> puertos;
    private static String OS;
    
    public TwoWaySerialComm(PlatformModel model){
        this.model = model;
        
        OS = System.getProperty("os.name").toLowerCase();
        OS = OS.substring(0, OS.indexOf(' ') > 0 ? OS.indexOf(' ') : OS.length());
        System.out.println("Sistema:"+OS);
        
        puertos = new HashMap<String,ArrayList<String>>();
        
        puertosLinux = new ArrayList<String>();
        puertosLinux.add("/dev/ttyUSB0");
        puertosLinux.add("/dev/ttyACM0");
        puertos.put("linux", puertosLinux);
        
        puertosWin = new ArrayList<String>();
        for(int i = 0; i < 20; i++){
        	puertosWin.add("COM"+Integer.toString(i));
        }
        
        puertos.put("windows", puertosWin);
    }
    
    private String getNextPort(){
    	portIndex++;
    	try{
    		System.out.println("puerto entregado: "+puertos.get(OS).get(portIndex));
    		return puertos.get(OS).get(portIndex);
    	}catch(IndexOutOfBoundsException ex){
    		return null;
    	}
	}
    
    private CommPortIdentifier tryConnection(String port) throws Exception{
    	CommPortIdentifier portIdentifier;
    	try{
    		portIdentifier = CommPortIdentifier.getPortIdentifier(port);
        	try{
        		CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
        		if ( commPort instanceof SerialPort ){
        			SerialPort serialPort = (SerialPort) commPort;
        			serialPort.setSerialPortParams(115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);          
        			InputStream in = serialPort.getInputStream();
        			OutputStream out = serialPort.getOutputStream();
        			(new Thread(new SerialReader(in, model))).start();
        			 sw = new SerialWriter(out,model);
        		}
        	}catch(gnu.io.PortInUseException e){
        		System.out.println("Puerto Ocupado!!");
        		return null;
        	}
    	}catch(gnu.io.NoSuchPortException e){
    		System.out.println("Puerto no conectado!!");
    		return null;
    	}
    	return portIdentifier;
   }
    
    public void connect(){
    	String nextPort;
    	CommPortIdentifier portIdentifier = null;
		try {
			while(portIdentifier == null){
				nextPort = getNextPort();
				nextPort.equals(nextPort);
				portIdentifier = tryConnection(nextPort);
			}
		} catch (NullPointerException e){
			System.out.println("Nos quedamos sin puertos");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
            		int a;
            		while(((a=buffer.read()) != '(') && (a!='/')){
            		}
            		String aux = buffer.readLine();
            		if (a=='/'){
            			System.out.println(aux);
            		}
            		else if(a=='('){           		
	            		aux= aux.replace("(","");
	            		aux= aux.replace(")","");
	            		
	        			String delims = ",";
	        			String[] tokens = aux.split(delims);
	        			float[] values = new float[tokens.length];
	        		
	        			for (int i=0; i<tokens.length;i++){
	        				values[i]=Float.parseFloat(tokens[i]);
	        			}
	            			//"(;d1;d2.....;)"
	        			
	        			model.setCurrent(values[0],values[1],values[2],values[3],values[4],values[5],
	        								values[6],values[7],values[8],values[9],values[10],values[11]);
            		}
            	}
            }catch (IOException e){}            
        }
		public void run() {
			while(true){
				readData();
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

