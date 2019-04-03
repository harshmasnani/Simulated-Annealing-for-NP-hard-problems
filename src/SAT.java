import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class SAT{
	private static List<String> u=new ArrayList<>();
	private static List<Integer> maximas= new ArrayList<>();
	private static int y;
	private static int E;
	private static String T;
	private static int Flips;
	private static int cE;
	private static int delta;
	private static int V;
	private static int C;
	private static int v1,v2, v3;
	private static List<String> store = new ArrayList<>();
	private static final double e=2.71828;
	static int MAX_TRIES; static double MAX_TEMP; static double MIN_TEMP;
	static int i=0; static int tries=0;
	private static String path="/Users/Sunny/Desktop/aim-50-2_0-yes1-1.cnf";
	static {getClauses();}
	
public static void main(String []args) 
{
	System.out.println("Select 1 for GSAT");
	System.out.println("Select 2 for SASAT");
	System.out.println("Select 3 for 0-SASAT");
	System.out.println("Select 4 for R-SASAT");
	System.out.println("Select 5 for R-GSAT");
	
	Scanner reader = new Scanner(System.in);
	System.out.print("Your Input:");
	int x =reader.nextInt();
	reader.close();
	switch(x) {
	case 1:	
		System.out.println(GSAT(10,100));
		break;
	case 2:
		System.out.println(SASAT(10, 0.3, 0.01));
		break;
	case 3:
		System.out.println(SASAT0(10, 0.3, 0.01));
		break;
	case 4:
		System.out.println(SASATR(10, 0.3, 0.01));
		break;
	case 5:	
		System.out.println(RGSAT(10,100));
		break;
	default:
		System.out.println("Invalid Selection");
		break;
	}
	
	System.out.println();
}

//GSAT
public static String GSAT(int MAX_FLIPS, int MAX_TRIES) 
{
	for(tries=0; tries<MAX_TRIES; tries++)
	{
		T=getRandomAssignment();
		E=calculate(T); maximas.add(E); delta++;
		if(E==C) {return T;}
		for(int j=0; j<MAX_FLIPS; j++)
		{
				int v = ThreadLocalRandom.current().nextInt(0, V+1);
				char[] Tdash=T.toCharArray();
				if(T.charAt(v)=='0') {Tdash[v]='1';} else {Tdash[v]='0';}
				String T1=new String(Tdash);
				cE=calculate(T1)-E;  delta++;
								
				if(cE>=0) {T=T1; y=0; Flips++;E=calculate(T); maximas.add(E); delta++; if(E==C) {return T;}}
				else if(y<2){T=T1; y++;E=calculate(T); maximas.add(E); delta++; if(E==C) {return T;};}
			
		}
	}
	System.out.println("Truth assignment not found.");
	System.out.println("Either equation is not Satisfiable or Computaional Constrainsts are causing Global Maxima to hide.");
	System.out.println("Current Maxima:"+Collections.max(maximas));
	System.out.println("δ=" +delta); 
	return("FLips=" + Flips);
}

//SASAT
public static String SASAT(int MAX_TRIES, double MAX_TEMP, double MIN_TEMP)
{
	while(tries<=MAX_TRIES)
	{
		i++;
		T=getRandomAssignment(); int j=0; //j is the counter for flips
		double decay_rate=1/(i*V);			
		for(int v=0; v<V; v++)
		{
			E=calculate(T); maximas.add(E); delta++;
			if(E==C) {return T;}
			double temprature=MAX_TEMP*Math.pow(e, -j*decay_rate);
			if(temprature<MIN_TEMP) break;
		 	
			char[] Tdash=T.toCharArray();
			if(T.charAt(v)=='0') {Tdash[v]='1';} else {Tdash[v]='0';}
			String T1=new String(Tdash);
			cE=calculate(T1)-E;  delta++;
			
			double probability =1/(1+Math.pow(e,(-cE/temprature)));
			if(Math.random()<=probability) 
			{
				T=T1; j++; tries++; Flips++;
				maximas.add(calculate(T)); delta++;
			}
		}
	}
	System.out.println("Truth assignment not found.");
	System.out.println("Either equation is not Satisfiable or Computaional Constrainsts are causing Global Maxima to hide.");
	System.out.println("Current Maxima:"+Collections.max(maximas));
	System.out.println("δ=" +delta); 
	return("FLips=" + Flips);
}

//0-SASAT
public static String SASAT0(int MAX_TRIES, double MAX_TEMP, double MIN_TEMP)
{
	String T;
	while(tries<=MAX_TRIES)
	{
		i++;
		T=getRandomAssignment(); int j=0; //j is the counter for flips
		double decay_rate=1/(i*V);			
		for(int v=0; v<V; v++)
		{
			E=calculate(T); maximas.add(E); delta++;
			if(E==C) {return T;}
			double temprature=MAX_TEMP*Math.pow(e, -j*decay_rate);
			if(temprature<MIN_TEMP) break;
		 	
			char[] Tdash=T.toCharArray();;
			if(T.charAt(v)=='0') {Tdash[v]='1';} else {Tdash[v]='0';}
			String T1=new String(Tdash);
			cE=calculate(T1)-E; delta++;
			
			double probability = delta==0 ? 0.5:1.0;
			if(Math.random()<=probability) 
			{
				T=T1; j++; tries++; Flips++;
				maximas.add(calculate(T)); delta++; 
			}
		}
	}
	System.out.println("Truth assignment not found.");
	System.out.println("Either equation is not Satisfiable or Computaional Constrainsts are causing Global Maxima to hide.");
	System.out.println("Current Maxima:"+Collections.max(maximas));
	System.out.println("δ=" +delta); 
	return("FLips=" + Flips);
}

//R-SASAT
public static String SASATR(int MAX_TRIES, double MAX_TEMP, double MIN_TEMP) 
{
	double Pin=1/V;
	while(tries<=MAX_TRIES)
	{
		i++;
		T=getRandomAssignment(); int j=0; //j is the counter for flips
		double decay_rate=1/(i*V);			
		for(int v=0; v<V; v++)
		{
			E=calculate(T); maximas.add(E); delta++;
			if(E==C) {return T;}
			double temprature=MAX_TEMP*Math.pow(e, -j*decay_rate);
			if(temprature<MIN_TEMP) break;
		 	
			char[] Tdash=T.toCharArray();
			if(T.charAt(v)=='0') {Tdash[v]='1';} else {Tdash[v]='0';}
			String T1=new String(Tdash);
			cE=calculate(T1)-E;  delta++;
			
			double probability=0.0;
			
			if(Math.random()<Pin)
			{
				List<String> u=new ArrayList<>(); u=getUnsatClauses();
				for(String s:u)
				{
					if (s.contains("v")){probability=1.0;}
				}
			}
			else{probability =1/(1+Math.pow(e,(-cE/temprature)));}
			
			if(Math.random()<=probability) 
			{
				T=T1; j++; tries++; Flips++;
				maximas.add(calculate(T)); delta++;
			}
		}
	}
	System.out.println("Truth assignment not found.");
	System.out.println("Either equation is not Satisfiable or Computaional Constrainsts are causing Global Maxima to hide.");
	System.out.println("Current Maxima:"+Collections.max(maximas));
	System.out.println("δ=" +delta); 
	return("FLips=" + Flips);
}

//RGSAT
public static String RGSAT(int MAX_FLIPS, int MAX_TRIES)
{
	for(tries=0; tries<MAX_TRIES; tries++)
	{
		T=getRandomAssignment();
		E=calculate(T); maximas.add(E); delta++;
		if(E==C) {return T;}
		for(int j=0; j<MAX_FLIPS; j++)
		{	
				u=getUnsatClauses();
				if(u.size()!=0) {
				//System.out.println(u.size());System.out.println(E);
				int c = ThreadLocalRandom.current().nextInt(0, u.size());
				String rc=u.get(c);
				String[] crc=rc.split(" ");
				int r = ThreadLocalRandom.current().nextInt(0, 3);
				int v=Math.abs(Integer.parseInt(crc[r]));
				char[] Tdash=T.toCharArray();
				if(T.charAt(v)=='0') 
				{Tdash[v]='1';} 
				else {Tdash[v]='0';}
				String T1=new String(Tdash); T=T1; Flips++; E=calculate(T); delta++; maximas.add(E); if(E==C) {return T;}
				/*cE=calculate(T1)-E;  delta++;
								
				if(cE>=0) {T=T1; y=0; Flips++;E=calculate(T); maximas.add(E); delta++; if(E==C) {return T;}}
				else if(y<2){T=T1; y++;E=calculate(T); maximas.add(E); delta++; if(E==C) {return T;};}*/
			
				}
		}
	}
	System.out.println("Truth assignment not found.");
	System.out.println("Either equation is not Satisfiable or Computaional Constrainsts are causing Global Maxima to hide.");
	System.out.println("Current Maxima:"+Collections.max(maximas));
	System.out.println("δ=" +delta); 
	return("FLips=" + Flips);
}

//random truth assignment
public static String getRandomAssignment() 
	{
		String bin = "";
		for (int i = 0; i < 600; i++)
			{bin+=Math.round( Math.random() );}
		return bin;
	}

//return number of clauses that were satisfied.
public static int calculate(String bin) {
		int count = 0;
		for (String clause : store) {
			count = objective(clause, bin) ? count + 1 : count + 0;
		}
		return count;
	}

//helper for calculate method
public static boolean objective(String line, String bin) {
		String[] parts = line.split(" ");
		int var1 = Integer.parseInt(parts[0]);
		int var2 = Integer.parseInt(parts[1]);
		int var3 = Integer.parseInt(parts[2]);
		String s1 = var1 < 0 ? "" + Math.abs(Character.getNumericValue(bin.charAt(Math.abs(var1) - 1)) - 1)
				: "" + bin.charAt(Math.abs(var1 - 1));
		if (s1.equals("0")) {
			String s2 = var2 < 0 ? "" + Math.abs(Character.getNumericValue(bin.charAt(Math.abs(var2) - 1)) - 1)
					: "" + bin.charAt(Math.abs(var2 - 1));
			if (s2.equals("0")) {
				String s3 = var3 < 0 ? "" + Math.abs(Character.getNumericValue(bin.charAt(Math.abs(var3) - 1)) - 1)
						: "" + bin.charAt(Math.abs(var3 - 1));
				return s3.equals("1");
			}
		}
		return true;
	}

//read clauses from file
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

//get unsatisfied clauses
public static List<String> getUnsatClauses(){
	List<String> usc= new ArrayList<>();
	for(int i=0; i<store.size(); i++){
		int flag1=0, flag2=0, flag3=0;
		String clause=store.get(i);
		clause=clause.substring(0,clause.length()-1);
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
}
