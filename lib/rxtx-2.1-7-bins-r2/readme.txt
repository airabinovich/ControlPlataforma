Installation Instructions for RXTX

The JControl/IDE needs a JAVA Communication API to communicate with a JControl device via the serial line (RS232). We recommend Trent Jarvi's RXTX package or the JAVA Communication API from Sun Microsystems.

NOTE: If you choose one of the fully integrated installer packages, the JAVA Communication API is installed automatically. Otherwise, please follow the installation procedure described below.

Windows 98/NT/2000/XP:

NOTE: It was reported that the current RXTX distribution is not working properly with Microsoft's Windows Server 2003 operating system. If you want to use Windows Server 2003 together with the JControl/IDE, please install the JAVA Communication API available at http://java.sun.com/products/javacomm and follow the installation instructions described in Readme.html.

For installing Trent Jarvi's RXTX package, please download the RXTX binaries for Windows 98/NT/2000/XP from the JControl Download Section.

Installation procedure:

     unpack rxtx-*-win32.zip,
     copy rxtxSerial.dll to %JAVA_HOME%\bin,
    (%JAVA_HOME% is the folder where JRE is installed on your system; e.g. c:\Program Files\Java\j2re1.4.1_01)
     copy RXTXcomm.jar to %JAVA_HOME%\lib\ext


Linux:

In order to enable serial communication on Linux/x86 systems we recommend Trent Jarvi's RXTX package which can be downloaded from http://www.rxtx.org for free. We have assembled a rxtx linux binary distribution for your convenience, you may download from the JControl Download Section.

Installation procedure:

     unpack the archive: rxtx-*-linux.zip,
     copy librxtxSerial.so to %JAVA_HOME%/jre/lib/i386,
    (%JAVA_HOME% is the folder where JRE is installed on your system; e.g. /usr/local/j2sdk1.4.1_01)
     copy RXTXcomm.jar to %JAVA_HOME%/jre/lib/ext
