
package ru.svetkin.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Util {
    
    public <T> List<T> fromIterToList(Iterator<T> iter){
        List<T> res=new ArrayList<>();
        while(iter.hasNext()){
            res.add(iter.next());
        }
        return res;
    }
}
