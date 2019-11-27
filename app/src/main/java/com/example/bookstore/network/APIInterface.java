package com.example.bookstore.network;

import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.model.ItemModel;
import com.example.bookstore.network.model.MyBooks;
import com.example.bookstore.network.model.SignForm;
import com.example.bookstore.network.model.Token;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    @GET( APIClient.images_url)
    Call<List<String>> getImages();

    @GET(APIClient.books_url)
    Call<List<ItemModel>> getBooks();

    @GET(APIClient.books_url + "{id}")
    Call<ItemModel> getBookById(@Path("id")String id);

    @GET(APIClient.MyBooks)
    Call<MyBooks> getMyBooks(@Header("x-auth-token") String token);

    @POST(APIClient.UserRegister)
    Call<Token> register(@Body SignForm signForm);

    @POST(APIClient.UserLogin)
    Call<Token> login(@Body SignForm signForm);

    @GET(APIClient.GetMe)
    Call<SignForm> getMy(@Header("x-auth-token") String token);
}
