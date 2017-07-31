package com.db.seu.Neo4jTest;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Hello world!
 *
 */
public class App 
{
	// Driver objects are thread-safe and are typically made available application-wide.
    Driver driver;
    
	public App(String uri, String user, String password)
    {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }
	
	private void printPerson(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
        	//"MATCH (a:Person) WHERE a.name STARTS WITH {x} RETURN a.name AS name"
        	StatementResult result = session.run(
                    "match  (person:Person{name:{x}})-[r]-(n) return person,r,n",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
//                System.out.println(record.get("name").asString());
                System.out.println(record.get("person").get("name").asString() +" "+ record.get("r").asRelationship().type() + " " + record.get("n").get("name").asString());
            }
        }
        
    }
	
	
	private void printCommand(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "match  (command:Command{name:{x}})-[r]-(n) return command,r,n",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("command").get("name").asString() +" "+ record.get("r").asRelationship().type() + " " + record.get("n").get("name").asString());
            }
        }
        
    }
	
	private void printNorelationship(String x, String y)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
        	Map<String, Object> parameters = new HashMap<String, Object>();
        	parameters.put("x", x);
        	parameters.put("y", y);

        	StatementResult result = session.run(
                    "match (person:Person)-[r]-(m)-[l]-(e) where person.name={x} and e.name={y}return person,m,e,r,l",
                    parameters);
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("person").get("name").asString() +" "+ record.get("r").asRelationship().type()+" "+ record.get("m").get("name").asString());
                System.out.println(record.get("m").get("name").asString() + " " + record.get("l").asRelationship().type()+" "+record.get("e").get("name").asString());
            }
        }
        
    }
	
	private void printrelationship(String initial)
    {
        try (Session session = driver.session())
        {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "match (person:Person)-[:属于]-(m)-[:subClassOf]-(e) where person.name={x} return person,m,e",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext())
            {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("person").get("name").asString() +" "+ record.get("m").get("name").asString() + " " + record.get("e").get("name").asString());
            }
        }
        
    }
	
    public void close()
    {
        // Closing a driver immediately shuts down all open connections.
        driver.close();
    }
    
    public static void main( String[] args )
    {
    	//使用jdbc时端口号为7687
        App app = new App("bolt://ubuntu1:7687", "neo4j", "123456");
        System.out.println("输入的查询格式为： 数字 查询字段");
        System.out.println("1. 查询主语关系");
        System.out.println("2. 查询宾语关系");
        System.out.println("3. 查询subClassOf关系");
        System.out.println("4. 查询主语与主语之间的关系");
        System.out.println();
        while(1 != 0){
        	Scanner sc = new Scanner(System.in);
        	int i = sc.nextInt();
        	sc.nextLine();
        	if(i == 1){
        		String str = sc.nextLine();
                long start = System.currentTimeMillis();
        		app.printPerson(str);
        		System.out.println(System.currentTimeMillis()-start);
        	}else if(i == 2){
        		String str = sc.nextLine();
                long start = System.currentTimeMillis();
        		app.printCommand(str);
        		System.out.println(System.currentTimeMillis()-start);
        	}else if(i == 3){
        		String str = sc.nextLine();
                long start = System.currentTimeMillis();
                app.printrelationship(str);
        		System.out.println(System.currentTimeMillis()-start);
        	}
        	if(i == 4){
        		String str1 = sc.nextLine();
        		String str2 = sc.nextLine();
                long start = System.currentTimeMillis();
                app.printNorelationship(str1,str2);
        		System.out.println(System.currentTimeMillis()-start);

        	}
        	System.out.println();
        }
    }
}
