package de.enderland.skyblock.util.serializer;

import java.io.*;
import java.util.Base64;

public class Base64Serializer {

	public static <T> String serialize(T obj) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream dataOutput = new ObjectOutputStream(outputStream);

			dataOutput.writeObject(obj);
			dataOutput.close();
			return Base64.getEncoder().encodeToString(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Unable to serialize object", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(String base64) {
		if (base64 == null || base64.isEmpty()) {
			return null;
		}

		try {
			byte[] data = Base64.getDecoder().decode(base64);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

            return (T) new ObjectInputStream(inputStream).readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("Unable to deserialize object", e);
		}
	}
}
