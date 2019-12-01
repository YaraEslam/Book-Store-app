package com.example.bookstore.view.Feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookstore.R;
import com.example.bookstore.network.APIInterface;
import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.models.ItemModel;
import com.example.bookstore.view.Adapter_Holder.Adapter;
import com.example.bookstore.view.Adapter_Holder.Enum;
import com.example.bookstore.view.Adapter_Holder.onItemClicked;
import com.example.bookstore.view.BookDetails.BookDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeatureActivity extends AppCompatActivity implements onItemClicked, View.OnClickListener {

    RecyclerView featureRecycleView;
    List<ItemModel> featuredBooks;
    @BindView(R.id.back_feature)
    TextView backFeature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        ButterKnife.bind(this);

        featureRecycleView = (RecyclerView) findViewById(R.id.feature_recycleview);
        featuredBooks = new ArrayList<>();

        backFeature.setOnClickListener(this);

       getBooks();

    }

    void getBooks(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterface.getBooks().enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<ItemModel>> call,
                                   @NotNull Response<List<ItemModel>> response) {
                if (response.isSuccessful()) {

                    featuredBooks = response.body();

                    Adapter booksAdapter = new Adapter(featuredBooks, FeatureActivity.this,
                            FeatureActivity.this, Enum.Featured);

                    LinearLayoutManager linearLayoutManager_books = new
                            LinearLayoutManager(FeatureActivity.this,
                            LinearLayoutManager.VERTICAL, false);

                    featureRecycleView.setLayoutManager(linearLayoutManager_books);

                    featureRecycleView.setAdapter(booksAdapter);
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ItemModel>> call,
                                  @NotNull Throwable t) {
                Toast.makeText(FeatureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(FeatureActivity.this, BookDetailsActivity.class);
        intent.putExtra("BOOK", (Serializable) featuredBooks.get(position));
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
        finish();
    }
}
