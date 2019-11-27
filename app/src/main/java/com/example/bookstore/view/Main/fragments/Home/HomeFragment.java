package com.example.bookstore.view.Main.fragments.Home;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.network.APIInterface;
import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.model.ItemModel;
import com.example.bookstore.view.Adapter_Holder.Adapter;
import com.example.bookstore.view.Adapter_Holder.Enum;
import com.example.bookstore.view.Adapter_Holder.onItemClicked;
import com.example.bookstore.view.BookDetails.BookDetailsActivity;
import com.example.bookstore.view.Feature.FeatureActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements onItemClicked {


//    @BindView(R.id.image_recycleview)

    RecyclerView imageRecycleview;
//    @BindView(R.id.book_recycleview)
    RecyclerView bookRecycleview;
    Unbinder unbinder;

    TextView see_all;

    List<String> images_view ;

    List<ItemModel> books_view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageRecycleview = (RecyclerView) view.findViewById(R.id.image_recycleview);
        bookRecycleview = (RecyclerView) view.findViewById(R.id.book_recycleview);

        images_view = new ArrayList<>();
        books_view = new ArrayList<>();

        see_all = (TextView) view.findViewById(R.id.see_all);

        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent( getActivity(), FeatureActivity.class);
                    startActivity(intent);
            }
        });

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.getImages().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NotNull Call<List<String>> call,
                                   @NotNull Response<List<String>> response) {
                if (response.isSuccessful()){
                    images_view= response.body();

                    AdapterString imageAdapter = new AdapterString(images_view,
                            getActivity());

                    LinearLayoutManager linearLayoutManager_images = new
                            LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);

                    imageRecycleview.setLayoutManager(linearLayoutManager_images);

                    imageRecycleview.setAdapter(imageAdapter);
                }
            }

            @Override
             public void onFailure(@NotNull Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        apiInterface.getBooks().enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<ItemModel>> call,
                                   @NotNull Response<List<ItemModel>> response) {
                if (response.isSuccessful()){
                    books_view = response.body();

                    Adapter booksAdapter = new Adapter(books_view,getActivity(),
                            HomeFragment.this, Enum.Home);

                    LinearLayoutManager linearLayoutManager_books = new
                            LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);

                    bookRecycleview.setLayoutManager(linearLayoutManager_books);

                    bookRecycleview.setAdapter(booksAdapter);

                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ItemModel>> call,
                                  @NotNull Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
        //intent.putExtra("BOOk", books_view.get(position)));
        intent.putExtra("BOOK", (Serializable) books_view.get(position));
        getActivity().startActivity(intent);
    }
}
