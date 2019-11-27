package com.example.bookstore.view.Main.fragments.profile;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookstore.R;
import com.example.bookstore.network.APIInterface;
import com.example.bookstore.network.api.APIClient;
import com.example.bookstore.network.model.SignForm;
import com.example.bookstore.utils.PrefManager;
import com.example.bookstore.view.Main.fragments.profile.Sign.ProfileSignFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileUserFragment extends Fragment {


    //@BindView(R.id.log_out)
    TextView logOut;
    @BindView(R.id.image_profile)
    CircleImageView imageProfile;
    @BindView(R.id.profile_username)
    TextView profileUsername;
    @BindView(R.id.profile_email)
    TextView profileEmail;
    @BindView(R.id.profile_mobile)
    TextView profileMobile;
    Unbinder unbinder;


    public ProfileUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);

        logOut = (TextView) view.findViewById(R.id.log_out);
        logOut.setOnClickListener(this::onViewClicked);


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        apiInterface.getMy(PrefManager.getToken(getActivity())).
                enqueue(new Callback<SignForm>() {
                    @Override
                    public void onResponse(@NotNull Call<SignForm> call,
                                           @NotNull Response<SignForm> response) {
                        if (response.isSuccessful()){

                            Picasso.get()
                                    .load(response.body().getImage())
                                    .placeholder(R.drawable.ic_image)
                                    .into(imageProfile);

                            profileUsername.setText(response.body().getName());
                            profileEmail.setText(response.body().getEmail());
                            profileMobile.setText(response.body().getPhone());

                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call<SignForm> call,
                                          @NotNull Throwable t) {

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

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.log_out:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.profile_fraq, new ProfileSignFragment());
                transaction.commit();
                PrefManager.deleteToken(getActivity());
                break;

            case R.id.image_profile:
                break;
        }
    }
}
