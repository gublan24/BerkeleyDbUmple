import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;


public class RunUmpleJar
{
    public static void main(String[] args) {
    	
  		System.out.println("...");
  		
  		/*
			try 
			{
				Process proc = Runtime.getRuntime().exec("java -jar /home/abdulaziz/umple/dist/umple.jar umplific/Master.ump");
				int terminationNum = 0;//= proc.waitFor();
				String line;
			  BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			  while ((line = input.readLine()) != null) {
			    System.out.println(line);
			  }
			  input.close();
			  if(terminationNum==0)
			  	System.out.println("STEP 2 Finished");
			  else
					System.out.println("STEP 2 has some errors.");
			  
				 System.out.println(proc.getOutputStream().toString());


			}
//			catch (InterruptedException e) {
	//			e.printStackTrace();
		//	}    	
			catch (IOException e) {
				e.printStackTrace();
			}    	
    	//
    	 * 
    	 */
  		
  		String result = "";
      int counter = 0;
      int fileCount = 1;
      try
      {            
          Runtime rt = Runtime.getRuntime();
          Process proc = rt.exec("java -jar /home/abdulaziz/umple/dist/umple.jar umplific/Master.ump");
          InputStream stderr = proc.getErrorStream();
          InputStreamReader isr = new InputStreamReader(stderr);
          BufferedReader br = new BufferedReader(isr);
          String line = null;
          System.out.println("run umple.jar ...");
          while ( (line = br.readLine()) != null)
{
              result += line + "\n";
counter ++;

if( counter == 70 || br.readLine() == null ) // last line.
{

		try {
  			BufferedWriter logFile = new BufferedWriter(new FileWriter("umpleJar"+fileCount+".txt"));
  			logFile.write(result+ "\n");
  			logFile.close();
        fileCount++;
result ="";		
  		} 
  		catch (IOException e) {
  			e.printStackTrace();
  		}

}

}
          System.out.println("***************************************************");
          int exitVal = proc.waitFor();
          System.out.println("Process exitValue: " + exitVal);
      } catch (Throwable t)
        {
          t.printStackTrace();
        }
      
      
      
      
  
      
      
      
      
      
      
      
      
      
      
    }
}
