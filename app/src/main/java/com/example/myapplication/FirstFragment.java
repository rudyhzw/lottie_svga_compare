package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.activity.CommenAnimationActivity;
import com.example.myapplication.activity.LottieActivity;
import com.example.myapplication.activity.SvgaActivity;
import com.example.myapplication.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                Intent intent = new Intent(getActivity(), LottieActivity.class);
//                startActivity(intent);
                CommenAnimationActivity.startCommenAnimationActivity(getActivity(), CommenAnimationActivity.TAG_LOTTIE);

            }
        });

        binding.buttonSvga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), SvgaActivity.class);
//                startActivity(intent);
                CommenAnimationActivity.startCommenAnimationActivity(getActivity(), CommenAnimationActivity.TAG_SVGA);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}