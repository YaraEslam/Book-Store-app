package com.example.bookstore.view.Main.fragments.profile;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.bookstore.R;
import com.example.bookstore.utils.PrefManager;
import com.example.bookstore.view.Main.fragments.profile.Sign.ProfileSignFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {


    @BindView(R.id.profile_fraq)
    FrameLayout profileFraq;
    Unbinder unbinder;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (PrefManager.getToken(getActivity()) == null){
            openFrag(new ProfileSignFragment());
        }else {
            openFrag(new ProfileUserFragment());
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void openFrag(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.profile_fraq, fragment);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
