package marshalp.ID3.com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;

public class ID3FileReader {

	public DataSet readFile(String fileName, Boolean testSet)
			throws IOException {

		ID3Global globalObj = ID3Global.getInstance();
		DataSet dataset = new DataSet();
		FileReader file = null;

		try {
			file = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BufferedReader bufferReader = new BufferedReader(file);

		String line;
		int attrId = 0;

		try {
			while ((line = bufferReader.readLine()) != null) {

				String[] attributes = line.split(",");
				Example ex = new Example();

				for (String attribute : attributes) {

					ex.addAttributes(new Attribute(attrId, attribute));

					if (!testSet) {
						HashSet<String> tempSet = globalObj
								.getAttributeValues(attrId);

						if (tempSet == null) {
							tempSet = new HashSet<String>();
						}
						tempSet.add(attribute);
						if (globalObj.getAttributeValues(attrId) == null)
							globalObj.putAttribute(attrId, tempSet);
					}

					attrId++;
				}

//				globalObj.setClassVariable(15);
				// ex.printRow();
				attrId = 0;
				dataset.addRows(ex);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			bufferReader.close();
		}

		return dataset;
	}

}
