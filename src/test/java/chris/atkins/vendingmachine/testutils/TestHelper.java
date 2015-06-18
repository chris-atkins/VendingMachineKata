package chris.atkins.vendingmachine.testutils;

import java.lang.reflect.Field;


public class TestHelper {

	private TestHelper() {
		// private so it can't be instantiated
	}

	public static void injectIntoClassWithObjectForFieldName(final Object target, final Object value, final String fieldName) throws Exception {
		final Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValueFromObject(final Object object, final String fieldName, final Class<T> expectedClass) throws Exception {
		final Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return (T) field.get(object);
	}
}
