package com.example.library_manager.services;

import org.json.JSONException;

public interface IResult {
    void onResultComplete(String result) throws JSONException;
}
