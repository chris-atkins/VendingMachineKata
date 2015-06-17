package chris.atkins.vendingmachine.testutils;

import java.lang.reflect.Field;


public class InjectionHelper {

	private InjectionHelper() {
		// private so it can't be instantiated
	}

	public static void injectIntoClassWithObjectForFieldName(final Object target, final Object value, final String fieldName) throws Exception {
		final Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}
}
