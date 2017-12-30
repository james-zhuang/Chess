package WindowsAndMenu;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class FileIO {

	private String lineSeparator;
	private String fileData = null;
	
	public FileIO() {
		setLineSeparator(System.getProperty("line.separator"));
	}
	
	/**
	 * Reads String from file
	 * @param filename
	 * @return string
	 */
	public String readFile (String filename){
		BufferedReader breeder = null;
		FileReader reader = null;
		Scanner in = null;
		
		try {
			
			reader = new FileReader (filename);
			breeder = new BufferedReader(reader);
			in = new Scanner(breeder);
			fileData = "";
			
			StringBuffer changingFileData = new StringBuffer();
			
			
			while(in.hasNextLine()){
				changingFileData.append(in.nextLine());
				changingFileData.append(lineSeparator);
			}
			
			
			fileData = changingFileData.toString();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in != null)
				in.close();
		}

		
		//System.out.println(fileData);
		return fileData;
	}

	/**
	 * Writes string to a file
	 * @param filename
	 * @param input
	 */
	public void writeFile (String filename, String input){
		BufferedWriter bwriter = null;
		FileWriter writer = null;
		
		try {
			
			writer = new FileWriter(filename);
			bwriter = new BufferedWriter(writer);
			
			writer.write(input);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
 		}catch (IOException e) {
 			e.printStackTrace();
		}finally{
			if(bwriter != null){
				try{
					bwriter.close();
				}
				catch (IOException e) {
	 			e.printStackTrace();
				}
			}
		}

	}
	
	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
	
	
}
