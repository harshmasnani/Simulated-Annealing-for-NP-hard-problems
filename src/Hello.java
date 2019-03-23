import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Hello{
	private static List<String> store = new ArrayList<>();
	private static String path="C:\\Users\\Harsh\\Desktop\\test.dimacs";
	private static int V;
	private static int C;
	private static int v1,v2,v3;
	private static String T;
	static {getClauses();}
	public static void getClauses() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line;
			for(int i=1; i<11; i++) {reader.readLine();}
			String temp=reader.readLine();
			String[] numbers=temp.split(" ");
			//for(String s: numbers) {System.out.print(s + " ");}
			V=Integer.parseInt(numbers[2]);
			C=Integer.parseInt(numbers[3]);
			while ((line = reader.readLine()) != null) {
				store.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String []args){
		String clause="12340";
		clause=clause.substring(0,clause.length()-1);
		System.out.println(clause);
    }
	public static List<String> getUnsatClauses(){
		List<String> usc= new ArrayList<>();
		for(int i=0; i<store.size(); i++){
			int flag1=0, flag2=0, flag3=0;
			String clause=store.get(i);
			String[] clauseArray = clause.split(" ");
			v1 = Integer.parseInt(clauseArray[0]);
			v2 = Integer.parseInt(clauseArray[1]);
			v3 = Integer.parseInt(clauseArray[2]);
			
			if(v1<0){v1=Math.abs(Character.getNumericValue(T.charAt(Math.abs(v1) - 1)) - 1); flag1=1;}
			else{Math.abs(Character.getNumericValue(T.charAt(Math.abs(v1) - 1)));}
			
			if(v2<0){v2=Math.abs(Character.getNumericValue(T.charAt(Math.abs(v2) - 1)) - 1); flag2=1;}
			else{Math.abs(Character.getNumericValue(T.charAt(Math.abs(v2) - 1)));}

			if(v3<0){v3=Math.abs(Character.getNumericValue(T.charAt(Math.abs(v3) - 1)) - 1); flag3=1;}
			else{Math.abs(Character.getNumericValue(T.charAt(Math.abs(v3) - 1)));}

			if(v1==flag1 & v2==flag2 & v3==flag3){usc.add(clause);}
		}
		return usc;
		}

	public static String getRandomAssignment() 
	{
		String bin = "";
		for (int i = 0; i < 600; i++)
			{bin+=Math.round( Math.random() );}
		return bin;
	}

}