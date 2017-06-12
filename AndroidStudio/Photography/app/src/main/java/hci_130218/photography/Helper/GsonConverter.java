package hci_130218.photography.Helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Developer on 29.04.2017..
 */

public class GsonConverter {

    public static <T> List<T> JsonToListArray(String jsonArray, Type listType){

        Gson gson= new GsonBuilder().create();
        List<T> list=(List<T>)gson.fromJson(jsonArray,listType);
        return list;

    }

    public static <T> T JsonToObject(String json, Class<T>  tClass ){

        Gson gson= new GsonBuilder().create();

       T x= (T) gson.fromJson(json,tClass);
        return x;

    }


    public static <T> String ObjectToJson(T object ){

        Gson gson= new GsonBuilder().create();

        String x=  gson.toJson(object).toString();
        return x;

    }


    public static <T> String ListObjectsToJson(List<T> object ){

        Gson gson= new GsonBuilder().create();

        String x=  gson.toJson(object).toString();
        return x;

    }
}
