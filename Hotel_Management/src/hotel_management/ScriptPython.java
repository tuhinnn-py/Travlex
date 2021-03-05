package hotel_management;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScriptPython 
{
	Process mProcess;
        String email, code;
        private String location;
        
        public ScriptPython(String email, String code)
        {
            this.email = email;
            this.code = code;
            this.location = getFileLocation();
        }
        
        public String getFileLocation()
        {
            //creating a dummy file to get the absolute path
            File file = new File("_");
            String path = file.getAbsolutePath().replace("\\", "\\\\");

            return path.substring(0, path.length() - 1);
        }   
        
	public void runScript_email()
	{
		Process process;
		try
		{
                        System.err.println("Sending mail to : " + this.email);
			process = Runtime.getRuntime().exec("python " + location + "email_script.py " + this.email + "  " + this.code);
			mProcess = process;
		}
		catch(IOException e) 
		{
			System.out.println("Exception Raised" + e.toString());
		}
		InputStream stdout = mProcess.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
		String line;
		try
		{
			while((line = reader.readLine()) != null)
			{
				System.out.println("stdout: "+ line);
			}
		}
		catch(IOException e)
		{
		 System.out.println("Exception in reading output"+ e.toString());
		}
	}
        
        public void runScript_mobile()
	{
		Process process;
		try
		{
                        System.err.println("Sending sms to : " + this.email);
			process = Runtime.getRuntime().exec("python " + location + "mobile_script.py " + this.email + "  " + this.code);
			mProcess = process;
		}
		catch(IOException e) 
		{
			System.out.println("Exception Raised" + e.toString());
		}
		InputStream stdout = mProcess.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
		String line;
		try
		{
			while((line = reader.readLine()) != null)
			{
				System.out.println("stdout: "+ line);
			}
		}
		catch(IOException e)
		{
		 System.out.println("Exception in reading output"+ e.toString());
		}
	}
        
        public void run_pic_cir()
	{
		Process process;
		try
		{
                        System.err.println("Resizing picture " + this.email);
			process = Runtime.getRuntime().exec("python " + location + "pic_cir.py " + this.email);
			mProcess = process;
		}
		catch(IOException e) 
		{
			System.out.println("Exception Raised" + e.toString());
		}
		InputStream stdout = mProcess.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
		String line;
		try
		{
			while((line = reader.readLine()) != null)
			{
				System.out.println("stdout: "+ line);
			}
		}
		catch(IOException e)
		{
		 System.out.println("Exception in reading output"+ e.toString());
		}
	}
}
