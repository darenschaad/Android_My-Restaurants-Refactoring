//package com.epicodus.myrestaurants.services;
//
//
//import com.epicodus.myrestaurants.Constants;
//import com.epicodus.myrestaurants.models.Restaurant;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.HttpUrl;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
//import se.akerfeldt.okhttp.signpost.SigningInterceptor;
//
//public class YelpService {
//    public static void findRestaurants(String location, Callback callback) {
//        String CONSUMER_KEY = Constants.YELP_CONSUMER_KEY;
//        String CONSUMER_SECRET = Constants.YELP_CONSUMER_SECRET;
//        String TOKEN = Constants.YELP_TOKEN;
//        String TOKEN_SECRET = Constants.YELP_TOKEN_SECRET;
//        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
//        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new SigningInterceptor(consumer))
//                .build();
//
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
//        String url = urlBuilder.build().toString();
//
//        Request request= new Request.Builder()
//                .url(url)
//                .build();
//
//        Call call = client.newCall(request);
//        call.enqueue(callback);
//    }
//
//    public ArrayList<Restaurant> processResults(Response response) {
//        ArrayList<Restaurant> restaurants = new ArrayList<>();
//
//        try {
//            String jsonData = response.body().string();
//            if (response.isSuccessful()) {
//                JSONObject yelpJSON = new JSONObject(jsonData);
//                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
//                for (int i = 0; i < businessesJSON.length(); i++) {
//                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
//                    String name = restaurantJSON.getString("name");
//                    String phone = restaurantJSON.getString("display_phone");
//                    String website = restaurantJSON.getString("url");
//                    double rating = restaurantJSON.getDouble("rating");
//                    String imageUrl = restaurantJSON.getString("image_url");
//                    double latitude = restaurantJSON.getJSONObject("location")
//                            .getJSONObject("coordinate").getDouble("latitude");
//                    double longitude = restaurantJSON.getJSONObject("location")
//                            .getJSONObject("coordinate").getDouble("longitude");
//                    ArrayList<String> address = new ArrayList<>();
//                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
//                            .getJSONArray("display_address");
//                    for (int y = 0; y < addressJSON.length(); y++) {
//                        address.add(addressJSON.get(y).toString());
//                    }
//
//                    ArrayList<String> categories = new ArrayList<>();
//                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");
//
//                    for (int y = 0; y < categoriesJSON.length(); y++) {
//                        categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
//                    }
//                    Restaurant restaurant = new Restaurant(name, phone, website, rating,
//                            imageUrl, address, latitude, longitude, categories);
//                    restaurants.add(restaurant);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return restaurants;
//    }
//}

package com.epicodus.myrestaurants.services;


import android.util.Log;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

public class YelpService {


    public void findRestaurants(String location, int totalItemsCount, Callback callback) {
        String CONSUMER_KEY = Constants.YELP_CONSUMER_KEY;
        String CONSUMER_SECRET = Constants.YELP_CONSUMER_SECRET;
        String TOKEN = Constants.YELP_TOKEN;
        String TOKEN_SECRET = Constants.YELP_TOKEN_SECRET;
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        urlBuilder.addQueryParameter(Constants.YELP_OFFSET_QUERY_PARAMETER, "" + totalItemsCount);
        String url = urlBuilder.build().toString();

        Log.v("url: ", url);
        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            Log.v("jsonData: ", jsonData);
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                    String name = restaurantJSON.getString("name");
                    String phone = restaurantJSON.optString("display_phone", "No phone available");
                    String website = restaurantJSON.getString("url");
                    double rating = restaurantJSON.getDouble("rating");
                    String imageUrl = restaurantJSON.optString("image_url", "https://s-media-cache-ak0.pinimg.com/736x/68/d4/c1/68d4c1092837a068a2fe65b8a29b867e.jpg");
                    double latitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("latitude");
                    double longitude = restaurantJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.get(y).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
                    }
                    Restaurant restaurant = new Restaurant(name, phone, website, rating,
                            imageUrl, address, latitude, longitude, categories);
                    restaurants.add(restaurant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
