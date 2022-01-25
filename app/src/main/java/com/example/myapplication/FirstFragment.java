package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.activity.TestLottieActivity;
import com.example.myapplication.activity.TryLottieActivity;
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

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_1);
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_2);
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_3);
            }
        });


        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_4);
            }
        });

        binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_5);
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLottieActivity.startTestLottieActivity(getActivity(), TestLottieActivity.PAGE_6);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}