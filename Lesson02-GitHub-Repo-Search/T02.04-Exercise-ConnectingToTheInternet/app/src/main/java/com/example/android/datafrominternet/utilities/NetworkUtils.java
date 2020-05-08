/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.datafrominternet.utilities;

import android.net.Uri;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    /**
     * Builds the URL used to query GitHub.
     *
     * @param githubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub server.
     */
    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

//    /**
//     * This method returns the entire result from the HTTP response.
//     *
//     * @param url The URL to fetch the HTTP response from.
//     * @return The contents of the HTTP response.
//     * @throws IOException Related to network and stream reading
//     */
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        try {
//            InputStream in = urlConnection.getInputStream();
//
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");
//
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }



//    public static void readJson(String jsonString) {
//        if(jsonString.length()>0) {
//            try {
//                JSONObject root = new JSONObject(jsonString);
//                int totalCount = root.getInt("total_count");
//                Log.d("HeyHey", "readJson: Total count: " + totalCount);
//            } catch (JSONException e) {
//                Log.e("HeyHey","readJson: Error");
//                e.printStackTrace();
//            }
//        } else {
//            Log.e("HeyHey", "readJson: Empty string");
//        }
//    }

    public static void readJson(String jsonString) throws IOException {
        if(jsonString.length()>0) {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<RepositorySearchResult> jsonAdapter = moshi.adapter(RepositorySearchResult.class);

            RepositorySearchResult repositorySearchResult = jsonAdapter.fromJson(jsonString);
            Log.d("HeyHey", "readJson: Total count: " + repositorySearchResult.total_count);
        } else {
            Log.d("HeyHey", "readJson: Empty string");
        }
    }

    private static class RepositorySearchResult {
        int total_count;
    }
}