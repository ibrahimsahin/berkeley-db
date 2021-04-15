package cs.bil342.experiment3.examples.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Demonstration of some basic JSON features.
 * 
 * @author Safa Sofuoglu
 */
public class JSONExample
{
	public static void main(String[] args) throws JSONException
	{
		//Build a JSON object and print its string representation: 
		JSONObject movieInfo = new JSONObject();
		movieInfo.put("title", "Groundhog Day");
		movieInfo.put("rating", 8.2);
		movieInfo.put("genre", new String[]{"comedy","fantasy","romance"});
		System.out.println(movieInfo.toString(4)); //pretty print
		
		//Parse a JSON string, then modify the JSON object:
		JSONObject studentInfo = new JSONObject("{\"grades\":{\"midterms\":[74,46],\"final\":83}}");
		JSONArray midterms = studentInfo.getJSONObject("grades").getJSONArray("midterms");
		double finalExam = studentInfo.getJSONObject("grades").getDouble("final");
		double average = (((midterms.getDouble(0) + midterms.getDouble(1)) / 2) + finalExam) / 2;
		studentInfo.getJSONObject("grades").put("average", average);
		System.out.println(studentInfo);
		
		//JSONStringer is another way to build a JSON object:
		String personInfo = new JSONStringer()
			.object()
				.key("name")
				.value("Johnny")
				.key("age")
				.value(34)
				.key("married")
				.value(false)
				.key("children")
				.value(null)
				.key("address")
				.object()
					.key("street")
					.value("Walnut")
					.key("no")
					.value("16")
				.endObject()
			.endObject().toString();
		System.out.println(personInfo);
	}
}
