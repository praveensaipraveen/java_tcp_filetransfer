import java.io.*;
import java.net.*;
import java.util.*;

class DataCopyNet{
static int linecount=0;
static int port=2000;
static int size=1024*1024;

static void server() throws IOException,SocketException{

ServerSocket srv=new ServerSocket(port);
Socket skt=srv.accept();

System.out.println("Enter Filename to send:");
String str=System.console().readLine();


FileInputStream fis=new FileInputStream(str);
BufferedInputStream bis=new BufferedInputStream(fis);


OutputStream fos=skt.getOutputStream();
BufferedOutputStream bos=new BufferedOutputStream(fos);

byte[] buf=new byte[size];
int c,ctot=0;
float tm=0;

while(true){
long start=new Date().getTime();
if((c=bis.read(buf,0,size))!=-1){
bos.write(buf,0,c);
linecount+=1;

System.out.println(c+" bytes read from file");
long stop=new Date().getTime();
ctot+=c;
tm+=(stop-start);
//System.out.println("Server::Bytes written: "+ctot+" 

speed:"+c+" bytes"+(float)(stop-start)/1000+" seconds");
}
else{
bis.close();
bos.close();
System.out.println("Written successfully: bytes:"+ctot+" 

time in milliseconds:"+tm+" linecount:"+linecount);
break;
}

}

}

static void client() throws IOException,SocketException{
System.out.println("Enter host address");
String ha=System.console().readLine();
Socket skt=new Socket(ha,port);
FileOutputStream fos=new FileOutputStream("Received");
BufferedOutputStream bos=new BufferedOutputStream(fos);

InputStream fis=skt.getInputStream();
BufferedInputStream bis=new BufferedInputStream(fis);

byte[] buf=new byte[size];
int c,ctot=0;
float tm=0;

while(true){
long start=new Date().getTime();
if((c=bis.read(buf,0,size))!=-1){
bos.write(buf,0,c);
linecount+=1;
System.out.println(c+" bytes read from socket");
long stop=new Date().getTime();
ctot+=c;
tm+=(stop-start);
//System.out.println("Client::Bytes written :"+ctot+" 

speed:"+c+" bytes"+(float)(stop-start)/1000+" seconds");
}
else{
bis.close();
bos.close();
System.out.println("Taken all: bytes:"+ctot+" time in 

milliseconds:"+tm+" linecount:"+linecount);
break;
}
}

}

public static void main(String[] arg) throws 

IOException,SocketException{
if(arg.length==1){
System.out.println("Server started");
server();
}
else{
System.out.println("Client initiated");
client();
}
}

}
