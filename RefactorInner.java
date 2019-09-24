package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class RefactorInner {

	public static void main(String[] args) {

		String dir = new File("").getAbsolutePath();
		String outDir = dir + "/refactor_innerStaticClass_backup";
		if (args.length > 0)
			dir = args[0];
		if (args.length > 1)
			outDir = args[1];

		BufferedWriter logFile = null;
		try {
			logFile = new BufferedWriter(new FileWriter(outDir+"log.txt", true));
			logFile.append("Start: ---------------------------------------------------------" + "\n");

			ArrayList<String> fileNames = getAllFilesInDirectory(dir);
			if (fileNames.size() > 0) {
				for (String file : fileNames) {
					writeFilesWithCommentedInnerStaticClasses(file, outDir, logFile);
				}
			}
			logFile.append("End: ---------------------------------------------------------" + "\n");

			logFile.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void writeFilesWithCommentedInnerStaticClasses(String file, String outDir, BufferedWriter logFile) {

		try 
		{
			BufferedReader buffReader = new BufferedReader(new FileReader(file));
			
			File inputFile = new File(file);
			File directory = new File(outDir);
			
			if (!directory.exists()) 
			{
				directory.mkdir();
			}
			String line = "";
			boolean ecounterStatic = false;
			int open_bracket_count = 0;
			int close_bracket_count = 0;

			try 
			{
				String originalText = new String(Files.readAllBytes(Paths.get(file)), StandardCharsets.UTF_8);
				
				String outputFullFileName = outDir +"/"+ inputFile.getName();
				System.out.println(outputFullFileName);
				String newJavaFilecontent = "";
				boolean toWriteNewJavaFile = false;
				
				while ((line = buffReader.readLine()) != null) 
				{
					if (line.contains("static class")) 
					{
						ecounterStatic = true;
						open_bracket_count += countOccurrence(line, "{");
						close_bracket_count += countOccurrence(line, "}");
						// System.out.println("//"+line);
						newJavaFilecontent += "// START_OF_STATIC_CLASS \n";
						newJavaFilecontent += "//"+ line + "\n";						
						toWriteNewJavaFile = true;
					} 
					else if (ecounterStatic) 
					{
						open_bracket_count += countOccurrence(line, "{");
						close_bracket_count += countOccurrence(line, "}");
						newJavaFilecontent += "//"+ line + "\n";

						if (close_bracket_count == open_bracket_count) { // reset
							ecounterStatic = false;
							open_bracket_count = 0;
							close_bracket_count = 0;
							newJavaFilecontent += "//"+ line + "\n";
							newJavaFilecontent += "// END_OF_STATIC_CLASS \n";
						}
					} 
					else
					{
						newJavaFilecontent += line + "\n";
					}
				} // END while(...)
				
				buffReader.close(); // closing the file after reading 
				
				if(toWriteNewJavaFile)
				{
					//writeToFile(newJavaFilecontent,logFile,file,file);
					writeToFile(originalText,logFile,outputFullFileName,file);

				}
				logFile.flush();
			}

			catch (IOException e) 
			{
				e.printStackTrace();
			}

		} 
		catch (FileNotFoundException e) {
			System.out.println("-----------------------------------------");
			e.printStackTrace();
		}

	}

	private static void writeToFile(String fileContent,BufferedWriter logFile ,String outputFullFileName, String file) throws IOException {
		
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFullFileName));
		if(bufferedWriter != null)
		{
			bufferedWriter.write("// Original file location:  "+file+"\n");
			bufferedWriter.write(fileContent );
			bufferedWriter.flush();
			bufferedWriter.close();
			logFile.append("" + file + "\n \t\t\t>>\n \t\t\t" + outputFullFileName + "\n");

		}
	}
	
	

	private static ArrayList<String> getAllFilesInDirectory(String dir) {

		ArrayList<String> files = new ArrayList<>();
		File f = new File(dir);
		File[] allSubFiles = f.listFiles();

		for (File file : allSubFiles) {
			if (file.isDirectory()) {
				files.addAll(getAllFilesInDirectory(file.getAbsolutePath()));
			} 
			else {
				files.add(file.getAbsolutePath());

			}
		}

		return files;
	}

	public static int countOccurrence(String source, String sentence) {
		int occurrences = 0;
		if (source.contains(sentence)) {
			int withSentenceLength = source.length();
			int withoutSentenceLength = source.replace(sentence, "").length();
			occurrences = (withSentenceLength - withoutSentenceLength) / sentence.length();
		}
		return occurrences;
	}

}
