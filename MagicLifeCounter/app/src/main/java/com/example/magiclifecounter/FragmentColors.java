package com.example.magiclifecounter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentColors#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentColors extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_COLOR = "Color";
    private static final String KEY_ROTATION = "Rotation";

    // TODO: Rename and change types of parameters
    private String mColor;
    private float mRotation;
    private String color;

    ImageButton redButton, greenButton, blackButton, whiteButton, blueButton;

    private FragmentColors.OnFragmentInteractionListener mListener;

    public FragmentColors() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentColors newInstance(String color, float rotation) {
        FragmentColors fragment = new FragmentColors();
        Bundle args = new Bundle();
        args.putString(KEY_COLOR, color);
        args.putFloat(KEY_ROTATION, rotation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getString(KEY_COLOR);
            mRotation = getArguments().getFloat(KEY_ROTATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_colors, container, false);

        redButton = view.findViewById(R.id.redButton);
        blackButton = view.findViewById(R.id.blackButton);
        greenButton = view.findViewById(R.id.greenButton);
        whiteButton = view.findViewById(R.id.whiteButton);
        blueButton = view.findViewById(R.id.blueButton);

        Context context = getContext();

        redButton.setRotation(mRotation);
        blackButton.setRotation(mRotation);
        greenButton.setRotation(mRotation);
        whiteButton.setRotation(mRotation);
        blueButton.setRotation(mRotation);

        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "red";
                sendColorMethod(color);
            }
        });

        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "white";
                sendColorMethod(color);
            }
        });

        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "black";
                sendColorMethod(color);
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "green";
                sendColorMethod(color);
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = "blue";
                sendColorMethod(color);
            }
        });

        return view;
    }

    public void sendColorMethod(String sendColor){
        if ( mListener != null ){
            mListener.onColorInteraction(sendColor);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentColors.OnFragmentInteractionListener) {
            mListener = (FragmentColors.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onColorInteraction(String sendColor);
        void closeColorFragment();
    }
}