package com.example.bookstore.view.Main.fragments.MyBooks;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.network.APIInterface;
import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.models.ItemModel;
import com.example.bookstore.network.models.MyBooks;
import com.example.bookstore.utils.PrefManager;
import com.example.bookstore.view.Adapter_Holder.Adapter;
import com.example.bookstore.view.Adapter_Holder.Enum;
import com.example.bookstore.view.Adapter_Holder.onItemClicked;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment implements onItemClicked {

    RecyclerView booksRecycleView;

    List<ItemModel> MyBook;

    Button open_book;


    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        booksRecycleView = (RecyclerView) view.findViewById(R.id.books_recycleview);

        MyBook = new ArrayList<>();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        if (PrefManager.getToken(getActivity()) == null) {
            Toast.makeText(getActivity(), "Sign Up ", Toast.LENGTH_SHORT).show();
        } else {

            apiInterface.getMyBooks(PrefManager.getToken(getActivity())).
                   enqueue(new Callback<MyBooks>() {
                       @Override
                       public void onResponse(@NotNull Call<MyBooks> call,
                                              @NotNull Response<MyBooks> response) {

                           if (response.isSuccessful()){

                               MyBook = response.body().getBooks();

                               Adapter booksAdapter = new Adapter(MyBook, getActivity(),
                                       BooksFragment.this, Enum.User);

                               LinearLayoutManager linearLayoutManager_books = new
                                       LinearLayoutManager(getActivity(),
                                       LinearLayoutManager.VERTICAL, false);

                               booksRecycleView.setLayoutManager(linearLayoutManager_books);

                               booksRecycleView.setAdapter(booksAdapter);
                           }

                       }

                       @Override
                       public void onFailure(@NotNull Call<MyBooks> call,
                                             @NotNull Throwable t) {

                           Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
        }
        return view;
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
