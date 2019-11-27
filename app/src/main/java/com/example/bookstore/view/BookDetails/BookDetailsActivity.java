package com.example.bookstore.view.BookDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.network.APIInterface;
import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.model.ItemModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.book_image_details)
    ImageView bookImageDetails;
    @BindView(R.id.book_name_details)
    TextView bookNameDetails;
    @BindView(R.id.book_author_name_details)
    TextView bookAuthorNameDetails;
    @BindView(R.id.book_price_details)
    TextView bookPriceDetails;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.bookInformation)
    TextView bookInformation;
    @BindView(R.id.aboutAuthor)
    TextView aboutAuthor;
    @BindView(R.id.place_order)
    Button placeOrder;
    @BindView(R.id.book_details)
    TextView bookDetails;

    private ItemModel itemModel;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        ButterKnife.bind(this);
        itemModel = (ItemModel) getIntent().getSerializableExtra("BOOK");

        assert itemModel != null;
        id = itemModel.getId();

        bookDetails.setOnClickListener(this);

       getBook();

    }

    void getBook(){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.getBookById(id).enqueue(new Callback<ItemModel>() {
            @Override
            public void onResponse(@NotNull Call<ItemModel> call,
                                   @NotNull Response<ItemModel> response) {
                if (response.isSuccessful()) {

                    Picasso.get()
                            .load(response.body().getImage_book())
                            .placeholder(R.drawable.ic_image)
                            .into(bookImageDetails);

                    bookNameDetails.setText(response.body().getBook_name());
                    bookAuthorNameDetails.setText(response.body().getAuthor());
                    bookPriceDetails.setText("$" + response.body().getBook_price());
                    bookInformation.setText(response.body().getBookDescription());
                    aboutAuthor.setText(response.body().getAboutAuthor());
                    rating.setNumStars(response.body().getTotalRating());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ItemModel> call,
                                  @NotNull Throwable t) {
                Toast.makeText(BookDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.book_details:
                super.onBackPressed();
                finish();
                break;

            case R.id.place_order:

                break;
        }

    }
}
