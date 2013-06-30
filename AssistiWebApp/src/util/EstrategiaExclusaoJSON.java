package util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class EstrategiaExclusaoJSON implements ExclusionStrategy {  
  
    @Override  
    public boolean shouldSkipField( FieldAttributes fa ) {   
        return fa.getAnnotation( ExcluirJSON.class ) != null;  
    }  
  
    @Override  
    public boolean shouldSkipClass( Class<?> type ) {  
        return false;  
    } 
}
