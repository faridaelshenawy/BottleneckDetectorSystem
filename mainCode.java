import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;


public class work {
	
	public static void readCSV() throws IOException{
		String path= "src/java.csv" ;
		
		ArrayList<String>result= new ArrayList<String>();
		String currentLine = "";
		FileReader fileReader= new FileReader(path);
		BufferedReader br = new BufferedReader(fileReader);
		while ((currentLine = br.readLine()) != null) {
			result.add(currentLine);
		}
		
		String initialResult[][]= new String [result.size()][3];
		for(int i=0;i<result.size();i++){
			String []split= result.get(i).split(",");
			
			for(int j=0;j<3;j++){
				if(j==0 && i!=0){
					String concat="";
					for(int z=0; z<split[j].length();z++){
						if(z>13){
							concat= concat+split[j].charAt(z)+"";
						}
					}
					initialResult[i][j]=concat;
				}
				else{
					if(j==2){
						initialResult [i][j]= split[j+1];
					}
					else{
							initialResult [i][j]= split[j];
						
					}
				}
					
			}
		}
		String[][] finalResult= new String [initialResult.length-1][initialResult[0].length];
		
		for(int i=0;i<finalResult.length;i++){
			for(int j=0;j<finalResult[i].length;j++){
				
				if(j==2 ){
					String concat="";
					for(int z=0;z<initialResult[i+1][j].length()-2;z++){
						if(initialResult[i+1][j].charAt(z)==':'){
							concat=concat+".";
						}
						else{
							concat=concat+initialResult[i+1][j].charAt(z)+"";
						}
					}
					finalResult[i][j]=concat;
				}
				else{
					finalResult[i][j]= initialResult[i+1][j];
				}
				
			}
		}
		System.out.println("ONEE");
		initialTable(finalResult);
	}
	
	public static void initialTable(String[][]arr){
		
		ArrayList<String> caseIds= getCaseIds(arr);
		
		String[][] allDetails = new String[caseIds.size()][];
		String[][] allTimestamp= new String [caseIds.size()][];
		
		
		for(int i=0;i<allDetails.length;i++){
			ArrayList<String> details= getActivities(Integer.parseInt(caseIds.get(i)),arr);
			
			
			
			allDetails[i]= new String[details.size() +1];
			
			for(int j=0;j<allDetails[i].length;j++){
				if(j==0){
					allDetails[i][j]=caseIds.get(i);
				}
				else{
					allDetails[i][j]= details.get(j-1);
				}
				
			}
		}
		
		for(int i=0;i<allTimestamp.length;i++){
			ArrayList<String> timestamp= getTimestamp(Integer.parseInt(caseIds.get(i)),arr);
			allTimestamp[i]= new String[timestamp.size() +1];
			
			for(int j=0;j<allTimestamp[i].length;j++){
				if(j==0){
					allTimestamp[i][j]=caseIds.get(i);
				}
				else{
					allTimestamp[i][j]= timestamp.get(j-1);
				}
				
			}
		}
		
		String[][]finalResult= new String[caseIds.size() *2][];
		
		int counter=0;
		for(int i=0;i<finalResult.length;i++){
			if(i%2==0){
				finalResult[i]= new String[allDetails[counter].length];
				
				for(int j=0;j<finalResult[i].length;j++){
					if(i%2==0){
						finalResult[i][j]=allDetails[counter][j];
					}
				}
				counter++;
			}
			
		}
		int counter2=0;
		for(int i=0;i<finalResult.length;i++){
			if(finalResult[i]==null){
				finalResult[i]= new String[allTimestamp[counter2].length];
				for(int j=0;j<finalResult[i].length;j++){
					if(finalResult[i][j]==null){
						finalResult[i][j]=allTimestamp[counter2][j];
					}
				}
				counter2++;
			}
			
		}
		System.out.println("TWOOOO");
		writeCSV(finalResult, "InitialResult");
		calculateDiffreneces(finalResult);
	}
	
	
	public static void calculateDiffreneces(String[][]arr){
		
		
		String [][] initialResult= new String[arr.length][];
		
		DecimalFormat doubleFormat = new DecimalFormat("#.###");
	
		
		for(int i=0;i<initialResult.length;i++){
			initialResult[i]= new String[arr[i].length];
			for(int j=0;j<initialResult[i].length;j++){
					if(i%2==0 || j==0 ){
						initialResult[i][j]=arr[i][j];
					}
					else{
						if(j==1){
							initialResult[i][j]="00.00";
						}
					
					else{
						initialResult[i][j]=doubleFormat.format((Double.parseDouble(arr[i][j])- Double.parseDouble(arr[i][j-1]))) +"";
					}
					}
					}
			}
		ArrayList<String[]>improvedResult= new ArrayList<String[]>();
		
		for(int i=0;i<initialResult.length;i++){
			ArrayList<String>activity= new ArrayList<String>();
			ArrayList<String>timestamp= new ArrayList<String>();
			
			for(int j=0;j<initialResult[i].length;j++){
				if(i%2==0){
					activity.add(initialResult[i][j]);
					timestamp.add(initialResult[i+1][j]);
				}
			}
			for(int z=0;z<timestamp.size();z++){
				if(Double.compare(Double.parseDouble(timestamp.get(z)), 0.0)<0){
					timestamp.remove(z);
					activity.remove(z);
				}
			}
			if(i%2==0){
				String []temp1= new String[activity.size()];
				String []temp2= new String[timestamp.size()];
				for(int x=0;x<timestamp.size();x++){
					temp1[x]=activity.get(x);
					temp2[x]=timestamp.get(x);
				}
				improvedResult.add(temp1);
				improvedResult.add(temp2);
			}	
		}
		String finalResult[][]= new String[improvedResult.size()][];
		
		for(int i=0;i<finalResult.length;i++){
			finalResult[i]= new String[improvedResult.get(i).length];
			for(int j=0;j<finalResult[i].length;j++){
				finalResult[i][j]= improvedResult.get(i)[j];
			}
		}
		System.out.println("THREEE");
		findMaximum(finalResult, arr);
		
	}
	
	public static void findMaximum(String[][]arr,String[][]originalArr){
		double totalSum=0.0;
		
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(i%2!=0 && j!=0){
					totalSum=totalSum+Double.parseDouble(arr[i][j]);
				}
			}
		}
		
		String[][]finalResult= new String[arr.length/2 +1][3];
		finalResult[0][0]= "CaseId";
		finalResult[0][1]= "Activity";
		finalResult[0][2]= "Max. Timestamp";
		
		int counter=1;
		for(int i=1;i<arr.length;i++){
			double max=0;
			String process="";
			String caseId="";
			
			for(int j=0;j<arr[i].length;j++){
				if(i%2 !=0 && j!=0){
					if(Double.parseDouble(arr[i][j])>max){
						max=Double.parseDouble(arr[i][j]);
						process= arr[i-1][j];
						caseId= arr[i][0];
					}
				}
			}
			if(i%2 !=0 ){
				finalResult[counter][0]= caseId;
				finalResult[counter][1]= process;
				finalResult[counter][2]= max+"";
				counter++;
			}
			
		}
		System.out.println("FIVEEE");
		writeCSV(arr,"Differences");
		writeCSV(finalResult, "AllMaxTimestamp");
		String [][]reportA=reportA(finalResult, totalSum);
		String [][]reportB=reportB(finalResult,originalArr);
		finalReport(reportA, reportB);
	}
	public static String[][] reportA(String[][]arr, double totalSum){
		
		DecimalFormat doubleFormat = new DecimalFormat("#.###");
		
		ArrayList<String[]> allResults= new ArrayList<String[]>();
		
		for(int i=1;i<arr.length;i++){
			allResults.add(arr[i]);
		}
		
		ArrayList<String[]>finalResult= new ArrayList<String[]>();
		ArrayList<Integer>positions= new ArrayList<Integer>();
		String [] temp2={"Activity","TotalSum","%"};
		finalResult.add(temp2);
		
		while(!allResults.isEmpty()){
			String process= allResults.get(0)[1];
			double sum=0.0;
			
			for(int j=0;j<allResults.size();j++){
				if(process.equals(allResults.get(j)[1])){
					positions.add(j);
					sum= sum+ Double.parseDouble(allResults.get(j)[2]);
				}
			}
			
			for(int i=0;i<positions.size();i++){
				int temp1= positions.get(i);
				if(temp1==0){
					temp1=0;
				}
				else{
					temp1= temp1-i;
				}
				allResults.remove(temp1);
			}
			positions.clear();
			String average= doubleFormat.format((sum/totalSum)*100);
			String [] temp={process,sum+"", average};
			finalResult.add(temp);
		}
		
		String[][] transform= new String[finalResult.size()][];
		for(int i=0;i<transform.length;i++){
			transform[i]= new String[finalResult.get(i).length];
			for(int j=0;j<transform[i].length;j++){
				transform[i][j]= finalResult.get(i)[j];
			}
		}
		System.out.println("SIXXXX");
		writeCSV(transform, "finalResultA");
		return transform;
	}
	
	public static String[][] reportB(String[][]maxActivities, String[][]originalArr){

		String[][]finalResult= new String[maxActivities.length][3];
		finalResult[0][0]="CaseId";
		finalResult[0][1]="Activity";
		finalResult[0][2]="Number of queueing activities";
		int position =1;
		
		for(int i=0;i<originalArr.length;i++){	
			if(i%2==0){
				String timestamp="";
				int counter=0;
				String caseId=maxActivities[position][0];
				String activity=maxActivities[position][1];
				
				if(originalArr[i][0].equals(caseId)){
					for(int j=0;j<originalArr[i].length;j++){
						if(originalArr[i][j].equals(activity)){
							timestamp=originalArr[i+1][j];
							break;
						}
					}
					for(int x=0;x<originalArr.length;x++){
						for(int y=0;y<originalArr[x].length;y++){
							if(x%2!=0 && y!=0 && originalArr[x][0].equals(caseId)){
								if(Double.compare(Double.parseDouble(originalArr[x][y]), Double.parseDouble(timestamp))<0){
									counter++;
								}
									
							}
						}
					}
			}
				finalResult[position][0]=caseId;
				finalResult[position][1]=activity;
				finalResult[position][2]=counter+"";
				position++;	
		}
		
	}
		System.out.println("SEVEENNNN");
		writeCSV(finalResult,"finalResultB");
		return finalResult;
	}
	public static void finalReport(String [][]reportA, String[][]reportB){
		
		String [][] finalRepo= new String [3][];
		finalRepo[0]= new String[1];
		finalRepo[0][0]= "Bottleneck Processes Identified";
		double maxA= 0.0;
		String processA="";
		for(int i=1;i<reportA.length;i++){
				if(Double.parseDouble(reportA[i][2])>maxA){
					maxA= Double.parseDouble(reportA[i][2]);
					processA= reportA[i][0];
				}
		}
		finalRepo[1]= new String[3];
		finalRepo[1][0]="Highest Execution Time Technique";
		finalRepo[1][1]=processA;
		finalRepo[1][2]= maxA+"%";
		
		int maxB= 0;
		String processB="";
		for(int i=1;i<reportB.length;i++){
				if(Integer.parseInt(reportB[i][2])>maxB){
					maxB= Integer.parseInt(reportB[i][2]);
					processB= reportB[i][1];
				}
		}
		finalRepo[2]= new String[3];
		finalRepo[2][0]="Highest Number of Queuing Processes Technique";
		finalRepo[2][1]=processB;
		finalRepo[2][2]= maxB+"";
		
		writeCSV(finalRepo,"SummaryRepo");
	
	}
	
	public static ArrayList<String> getActivities(int id, String [][]arr){
		
		ArrayList<String> finalResult = new ArrayList<String>();
		
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++){
				if(j==0 && Integer.parseInt(arr[i][j])== id){
					finalResult.add(arr[i][j+1]);
				}
			}
		}
		int counter =0;

			while(counter<finalResult.size()){
			if(finalResult.get(counter).equals("Start trip") || finalResult.get(counter).equals("End trip")){
				finalResult.remove(counter);
			}
			else{
				counter++;
			}
		}
		return finalResult;
	}
	public static ArrayList<String> getTimestamp(int id, String [][]arr){
			
			ArrayList<String> finalResult = new ArrayList<String>();
			
			for(int i=0;i<arr.length;i++){
				for(int j=0;j<arr[i].length;j++){
					if(j==0 && Integer.parseInt(arr[i][j])== id){
						finalResult.add(arr[i][j+2]);
					}
				}
			}
			int counter =0;

			while(counter<finalResult.size()){
			if(finalResult.get(counter).equals("00.00")){
				finalResult.remove(counter);
			}
			else{
				counter++;
			}
		}
			
			return finalResult;
	}
	
	public static ArrayList<String> getCaseIds(String[][]arr){
		ArrayList<String> caseIds= new ArrayList<String>();
		caseIds.add(arr[0][0]);
		
		for(int i=1;i<arr.length;i++){
			boolean found = false;
			for(int j=0;j<caseIds.size();j++){
				if(arr[i][0].equals(caseIds.get(j))){
					found = true;
				}
			}
			if(!found){
				caseIds.add(arr[i][0]);
				
			}
		}
		
		return caseIds;
	}
	public static void writeCSV(String [][]array, String fileName){
			
			PrintWriter pw = null;
			try {
				pw = new PrintWriter("src/"+fileName+".csv");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			String s="";
			  StringBuilder result=new StringBuilder(s);
			  
			  for(int i=0;i<array.length;i++){
				  for(int j=0;j<array[i].length;j++){
					  result.append(array[i][j]).append(",");  
				  }
				  result.append("\n");
			  }
			  
			  pw.write(result.toString());
			  pw.close();
			
		}
	
	public static void main(String[]args) throws IOException{
		readCSV();
		GUI window= new GUI();
	}
}
