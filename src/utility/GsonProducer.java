package utility;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import annotation.ExcludeFromGson;

public class GsonProducer {
	
	private static class CustomGsonExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(ExcludeFromGson.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> type) {
            return false;
        }
        
    }
	
	private static CustomGsonExclusionStrategy strategy = new CustomGsonExclusionStrategy();
	
	public static Gson getGson() {
		return new GsonBuilder().setExclusionStrategies(strategy).setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
	}

}
