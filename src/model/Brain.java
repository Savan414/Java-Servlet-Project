package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Brain
{
	public static final double BITS_PER_DIGIT = 3.0;
	public static final Random RNG = new Random();	
	public static final String TCP_SERVER = "red.eecs.yorku.ca";
	public static final int TCP_PORT = 12345;
	public static final String DB_URL = "jdbc:derby://red.eecs.yorku.ca:64413/EECS;user=student;password=secret";
	public static final String HTTP_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/World.cgi";
	public static final String ROSTER_URL = "https://www.eecs.yorku.ca/~roumani/servers/4413/f18/Roster.cgi";
	
	private static Brain brain =null;
	public Brain()
	{
		
	}
	
	public static Brain getInstance()
	{
		if (brain == null)
			brain = new Brain();
		return brain;
	}
	public String doTime()
	{
		// your code goes here
		Date date = new Date();
		String s = date.toString();
		return s;
	}
	public BigInteger doPrime(String digits) throws IOException
	{	
		int d = Integer.parseInt(digits);
		Random rng = new Random();
		double bits = d * 3.5;
		int bitLength = (int) Math.floor(bits);
		int probability = 10;
		
		BigInteger prime = new BigInteger(bitLength, probability, rng);
		return prime;
	}	
	public String doTcp(String digits) throws IOException
	{
		Socket client = new Socket(TCP_SERVER, TCP_PORT);
		client.setKeepAlive(true);
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out.println("Prime " + digits);
		String prime = br.readLine();
		out.write("bye");
		return prime;
	}
	public String doDb(String itemNo) throws Exception
	{
		  Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		  Connection con = DriverManager.getConnection(DB_URL);
		  Statement s = con.createStatement();
		  s.executeUpdate("set schema roumani");
		  String query = "SELECT NAME,PRICE FROM ITEM WHERE NUMBER='" + itemNo + "'"; //SQL query to obtain the NAME and PRICE of an item whose number is itemNo in a table ITEM
		  ResultSet r = s.executeQuery(query);
		  String result = "";
		  if (r.next())
		  {
		  	result = "$" + r.getDouble("PRICE") + " - " + r.getString("NAME");
		  }
		  else
		  {
		  	throw new Exception(itemNo + " not found!");
		  }
		  r.close(); s.close(); con.close();
		  return result;
	}
	public String doHttp(String country, String request) throws IOException
	{
		URL url = new URL(HTTP_URL + "?country=" + country.trim() + "&query=" + request.trim());
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		
		connect.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String result = br.readLine();
		
		return result;
	}
	public String doRoster(String course) throws IOException 
	{
		URL url = new URL(ROSTER_URL + "?course=" + course);
		HttpURLConnection connect = (HttpURLConnection) url.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
		String result = br.readLine();
		return result;
	}
	
}
