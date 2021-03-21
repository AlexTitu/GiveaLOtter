package com.example.magiclifecounter;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentP1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentP1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_poisonDmgP1 = "posion";
    private static final String KEY_commanderDmgP1 = "commander";


    // TODO: Rename and change types of parameters
    private String mPoisonDmgP1;
    private String mCommanderDmgP1;

    private ImageButton minusCommdmgP1, plusCommdmgP1, minusPoisonP1, plusPoisonP1, closeFragment,resetButton;
    private TextView commDmgP1, poisonP1;
    private OnFragmentInteractionListener mListener;

    public FragmentP1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentP1 newInstance(String poison, String commander) {
        FragmentP1 fragment = new FragmentP1();
        Bundle args = new Bundle();
        args.putString(KEY_poisonDmgP1, poison );
        args.putString(KEY_commanderDmgP1, commander);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoisonDmgP1 = getArguments().getString(KEY_poisonDmgP1);
            mCommanderDmgP1 = getArguments().getString(KEY_commanderDmgP1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p1, container, false);

        minusCommdmgP1 = view.findViewById(R.id.minusCommDmgP1);
        plusCommdmgP1 = view.findViewById(R.id.plusCommDmgP1);
        minusPoisonP1 = view.findViewById(R.id.minuspoisonP1);
        plusPoisonP1 = view.findViewById(R.id.pluspoisonP1);
        commDmgP1 = view.findViewById(R.id.commanderdamageP1);
        poisonP1 = view.findViewById(R.id.poisoncounterP1);
        closeFragment = view.findViewById(R.id.closeFragment);
        resetButton = view.findViewById(R.id.resetCommPoison);

        commDmgP1.setText(mCommanderDmgP1);
        commDmgP1.requestFocus();
        poisonP1.setText(mPoisonDmgP1);
        poisonP1.requestFocus();

        Context context = getContext();
        if ( Integer.parseInt(commDmgP1.getText().toString()) > 20 ) commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.red));
        else commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.white));
        if ( Integer.parseInt(poisonP1.getText().toString()) > 9 ) poisonP1.setTextColor(ContextCompat.getColor(context, R.color.red));
        else poisonP1.setTextColor(ContextCompat.getColor(context, R.color.white));

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commDmgP1.setText("0");
                poisonP1.setText("0");
                commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.white));
                poisonP1.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        minusCommdmgP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(commDmgP1.getText().toString());
                if( dmg > 0 ) dmg--;
                commDmgP1.setText("" + dmg);
                if ( dmg > 20 ) commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.red));
                else commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        plusCommdmgP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(commDmgP1.getText().toString());
                dmg++;
                commDmgP1.setText("" + dmg);
                if ( dmg > 20 ) commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.red));
                else commDmgP1.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        plusPoisonP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(poisonP1.getText().toString());
                dmg++;
                poisonP1.setText("" + dmg);
                if ( dmg > 9 ) poisonP1.setTextColor(ContextCompat.getColor(context, R.color.red));
                else poisonP1.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        minusPoisonP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(poisonP1.getText().toString());
                if ( dmg > 0 ) dmg--;
                poisonP1.setText("" + dmg);
                if ( dmg > 9 ) poisonP1.setTextColor(ContextCompat.getColor(context, R.color.red));
                else poisonP1.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLifes();
            }
        });

        return view;
    }

    private void returnToLifes() {

        String sendBackCommDmgP1 = commDmgP1.getText().toString();
        String sendBackPoisonP1 = poisonP1.getText().toString();

        if ( mListener != null ){
            sendBack(sendBackCommDmgP1, sendBackPoisonP1);
            mListener.onFragmentClose();
        }
    }

    public void sendBack(String sendBackCommDmgP1, String sendBackPoisonP1) {
        if ( mListener != null ){
            mListener.onFragmentInteraction(sendBackCommDmgP1, sendBackPoisonP1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        void onFragmentInteraction(String sendBackCommanderDmgP1, String sendBackPoisonP1);
        void onFragmentClose();
    }
}