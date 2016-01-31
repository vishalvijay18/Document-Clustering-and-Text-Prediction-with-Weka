import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WordSuggesterSingleLength {

		
		public WordSuggesterSingleLength() {
			super();
		// TODO Auto-generated constructor stub
		}

		public static void main(String args[]){
			
			WordSuggesterSingleLength wssl = new WordSuggesterSingleLength();
			wssl.run();
			
		}
		
		private void createList(List<String> list, String clusterName)
		{
			String line = "";
			BufferedReader br2 = null;
			
			try{
			br2 = new BufferedReader(new FileReader(clusterName));

			while ((line = br2.readLine()) != null)
			{
				String[] content = line.split(",");
				list.add(content[0]);
			}
			
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		private String findMissingWords(List<String> clusterList, HashMap<String, String> wordMap, String outline)
		{
			int count = 0;
			String cvsSplitBy = ",";
			Iterator<String> it = null;
			it = clusterList.iterator();
						
			while(it != null && it.hasNext() && count < 5)
			{
				String temp = (String) it.next();
				if(!wordMap.containsKey(temp))
				{
					outline = outline.concat(cvsSplitBy).concat(temp);
					count++;
				}
			}
			
			return outline;
		}	

		private void run()
		{
			String cluster0 = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/FPCluster0(single).csv";
			String cluster1 = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/FPCluster1(single).csv";
			String cluster2 = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/FPCluster2(single).csv";
			String cluster3 = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/FPCluster3(single).csv";
			
			String docWords = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/DocumentWordsFP60-90.csv";
			String outFile = "/home/vvijayva/sfuhome/personal/ProjectDataUpdated/MissingWordsSingle.csv";
			
			BufferedReader br = null, br1 = null;
			String outline = "";
			String cvsSplitBy = ",";
			HashMap<String, String> wordMap = new HashMap<String, String>();
			
			List<String> cluster0List = new ArrayList<String>();
			List<String> cluster1List = new ArrayList<String>();
			List<String> cluster2List = new ArrayList<String>();
			List<String> cluster3List = new ArrayList<String>();
			
			try {

				String currentLine;
				
				PrintWriter writer = new PrintWriter(outFile, "UTF-8");
				br = new BufferedReader(new FileReader(docWords));
				
				createList(cluster0List, cluster0);
				createList(cluster1List, cluster1);
				createList(cluster2List, cluster2);
				createList(cluster3List, cluster3);
				
				while ((currentLine = br.readLine()) != null) {
					
					outline="";
					wordMap.clear();
					String[] words = currentLine.split(cvsSplitBy);
					
					String docId = words[0];
					int docLength = Integer.parseInt(words[2]);
					String cluster = words[1];
					
					for(int i=0; i < docLength; i++)
					{
						wordMap.put(words[i+3], "Yes");
					}
					
					outline = outline.concat(docId);
					
					if(cluster.equals("cluster0"))
					{
						outline = findMissingWords(cluster0List, wordMap, outline);
					}
					else if(cluster.equals("cluster1"))
					{
						outline = findMissingWords(cluster1List, wordMap, outline);
					}
					else if(cluster.equals("cluster2"))
					{
						outline = findMissingWords(cluster2List, wordMap, outline);
					}
					else if(cluster.equals("cluster3"))
					{
						outline = findMissingWords(cluster3List, wordMap, outline);
					}
					
					writer.println(outline);
				}
				
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			}
		}
			
}
