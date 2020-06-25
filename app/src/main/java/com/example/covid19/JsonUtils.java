package com.example.covid19;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class JsonUtils {

    public static String loadJSONFromAsset(Context context) {

        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static ArrayList<InfoModel> extractPreventionOrSymptomsData(Context context, String category){
        ArrayList<InfoModel> list = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(loadJSONFromAsset(context));
            JSONArray preventionArray = root.getJSONArray(category);

            for(int i=0; i<preventionArray.length(); i++) {
                JSONObject prevention = preventionArray.getJSONObject(i);
                String image = prevention.getString("img");
                int id = getResourseId(context, image, "drawable", context.getPackageName());
                String title = prevention.getString("title");
                String description = prevention.getString("description");
                if (prevention.has("points")){
                    JSONArray points = prevention.getJSONArray("points");
                    ArrayList<String> pointList = new ArrayList<>();

                    for (int j = 0; j < points.length(); j++) {
                        pointList.add(points.getString(j));
                    }
                    list.add(new InfoModel(id, title, description, pointList));
                }else{
                    list.add(new InfoModel(id,title,description));
                }
            }
        }catch (JSONException e){
            Log.e("json_error","error from extractPrevention");

        }

        return list;
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

}
