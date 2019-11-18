import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CS245A1 {

	private Trie rootTrie;

	private BinaryTree binaryTree;

	private List<String> words;

	private boolean isTrie = true;

	public CS245A1() {
		this.rootTrie = new Trie(' ');
		this.binaryTree = new BinaryTree();
		words = new ArrayList<String>();
	}

	// Read Jazzy Spell Checker file
	public void readDictionaryByTrie() {
		ArrayList<String> strArray = new ArrayList<String>();
		BufferedReader reader;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("english.0"));
			reader = new BufferedReader(inputStreamReader);
			String line;
			line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					strArray.add(line);
				}
				line = reader.readLine();
			}
			words = strArray;
			for (String word : strArray) {
				rootTrie.addWord(word, rootTrie);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find english.0 file.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	// Read Jazzy Spell Checker file
	public void readDictionaryByBT() {
		ArrayList<String> strArray = new ArrayList<String>();
		BufferedReader reader;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("english.0"));
			reader = new BufferedReader(inputStreamReader);
			String line;
			line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					strArray.add(line);
				}
				line = reader.readLine();
			}
			words = strArray;
			generate(strArray,0,strArray.size()-1);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find english.0 file.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	private void generate(List<String> strings, int start, int end) {
        if(start>end){
            return;
        }
        int mid=(start+end)/2;
        binaryTree.insert(strings.get(mid));
        generate(strings,0,mid-1);
        generate(strings,mid+1,end);
    }

	// Accept configuration for storage
	public void readConfiguration() {
		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("alproperties.txt");
			prop.load(fis);
			if (prop.getProperty("storage").trim().equals("trie")) {
				readDictionaryByTrie();
			} else if (prop.getProperty("storage").trim().equals("tree")) {
				isTrie = false;
				readDictionaryByBT();
			} else {
				readDictionaryByTrie();
			}
		} catch (FileNotFoundException e) {
			readDictionaryByTrie();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readInputTrie(String inputFileName,String outPutFileName) {
		Map<String, Integer> maps = new HashMap<String, Integer>();
		ArrayList<String> strArray = new ArrayList<String>();
		File file = new File(outPutFileName);
		BufferedReader reader;
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inputFileName));
			reader = new BufferedReader(inputStreamReader);
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			String line;
			line = reader.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					strArray.add(line);
				}
				line = reader.readLine();
			}
			for (String word : strArray) {
				if (isTrie) {
					if (rootTrie.search(word, rootTrie)) {
						writer.write(word + "\n");
					} else {
						for (String correctWord : words) {
							maps.put(correctWord, getEditDistance(word, correctWord));
						}
						List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
								maps.entrySet());
						list.sort(new Comparator<Map.Entry<String, Integer>>() {
							@Override
							public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
								return o1.getValue().compareTo(o2.getValue());
							}
						});
						int j = 0;
						for (int i = 0; i < list.size(); i++) {
							if (j < 3) {
								writer.write(list.get(i).getKey() + " ");
							}
							j++;
						}
						writer.write("\n");
					}
				} else {
					if (binaryTree.search(binaryTree.getRoot(), word)) {
						writer.write(word + "\n");
					} else {
						for (String correctWord : words) {
							maps.put(correctWord, getEditDistance(word, correctWord));
						}
						List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
								maps.entrySet()); 
						list.sort(new Comparator<Map.Entry<String, Integer>>() {
							@Override
							public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
								return o1.getValue().compareTo(o2.getValue());
							}
						});
						int j = 0;
						for (int i = 0; i < list.size(); i++) {
							if (j < 3) {
								writer.write(list.get(i).getKey() + " ");
							}
							j++;
						}
						writer.write("\n");
					}
				}

			}
			writer.close();
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input.txt file.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public int getEditDistance(String word1, String word2) {
		int length1 = word1.length();
		int length2 = word2.length();
		int[][] lens = new int[length1 + 1][length2 + 1];

		for (int i = 0; i <= length1; i++) {
			lens[i][0] = i;
		}
		for (int j = 0; j <= length2; j++) {
			lens[0][j] = j;
		}
		for (int i = 1; i <= length1; i++) {
			for (int j = 1; j <= length2; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					lens[i][j] = lens[i - 1][j - 1];
				} else {
					 lens[i][j] = Math.min(lens[i - 1][j] + 1,
	                            Math.min(lens[i][j - 1] + 1, lens[i - 1][j - 1] + 1));
				}
			}
		}
		return lens[length1][length2];
	}

	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Please input two file!");
		}else {
			CS245A1 cs245a1 = new CS245A1();
			cs245a1.readConfiguration();
			cs245a1.readInputTrie(args[0],args[1]);
//			System.out.print(cs245a1.getEditDistance("ANSK", "Ada"));
		}
	}

}
