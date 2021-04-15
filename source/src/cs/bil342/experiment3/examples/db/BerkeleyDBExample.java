package cs.bil342.experiment3.examples.db;

import java.io.File;
import java.util.Locale;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.CursorConfig;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.OperationStatus;

public class BerkeleyDBExample
{
	private static final File DB_ENV_PATH = new File("./dbenv");
	private static final String DB_NAME = "mydb";

	private Environment env;
	private Database db;

	public static void main(String args[])
	{
		BerkeleyDBExample bde = new BerkeleyDBExample();
		try
		{
			bde.run(args);
		}
		catch(DatabaseException dbe)
		{
			System.err.println("DatabaseException: " + dbe.toString());
			dbe.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("Exception: " + e.toString());
			e.printStackTrace();
		}
		finally
		{
			bde.cleanup();
		}
		System.out.println("All done.");
	}

	private void run(String args[]) throws Exception
	{
		init();
		performOperations();
	}

	private void performOperations() throws Exception
	{
		printrecords();

		//create:
		DatabaseEntry key = new DatabaseEntry();
		DatabaseEntry data = new DatabaseEntry();

		key = new DatabaseEntry("abc".getBytes("UTF-8"));
		data = new DatabaseEntry("abc data abc".getBytes("UTF-8"));
		db.put(null, key, data);

		key = new DatabaseEntry("def".getBytes("UTF-8"));
		data = new DatabaseEntry("def data def".getBytes("UTF-8"));
		db.put(null, key, data);

		printrecords();

		//delete:
		key = new DatabaseEntry("def".getBytes("UTF-8"));
		db.delete(null, key);
		
		printrecords();
	}

	private void printrecords()
	{
		System.out.println("Printing all records in the database");
		DatabaseEntry key = new DatabaseEntry();
		DatabaseEntry data = new DatabaseEntry();

		Cursor cursor = db.openCursor(null, new CursorConfig());
		while(cursor.getNext(key, data, null) == OperationStatus.SUCCESS)
		{
			System.out.println("Key: " + new String(key.getData()) + " Data: "
					+ new String(data.getData()));
		}
		cursor.close();
	}

	private void init()
	{
		Locale.setDefault(Locale.ENGLISH); //This ugly workaround is needed for BerkeleyDB to run properly

		EnvironmentConfig environmentConfig = new EnvironmentConfig();
		environmentConfig.setAllowCreate(true);

		if(!DB_ENV_PATH.exists())
		{
			System.out.println("Creating environment directory...");
			DB_ENV_PATH.mkdirs();
		}
		env = new Environment(DB_ENV_PATH, environmentConfig);

		DatabaseConfig databaseConfig = new DatabaseConfig();
		databaseConfig.setAllowCreate(true);

		db = env.openDatabase(null, DB_NAME, databaseConfig);
	}

	private void cleanup()
	{
		System.out.println("Releasing resources...");
		db.close();
		env.close();
	}
}
