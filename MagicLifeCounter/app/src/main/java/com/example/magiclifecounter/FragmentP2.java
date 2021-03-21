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
public class FragmentP2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_poisonDmgP2 = "posion";
    private static final String KEY_commanderDmgP2 = "commander";


    // TODO: Rename and change types of parameters
    private String mPoisonDmgP2;
    private String mCommanderDmgP2;

    private ImageButton minusCommdmgP2, plusCommdmgP2, minusPoisonP2, plusPoisonP2, closeFragment2,resetButton2;
    private TextView commDmgP2, poisonP2;
    private OnFragmentInteractionListener mListener;

    public FragmentP2() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentP2 newInstance(String poison, String commander) {
        FragmentP2 fragment = new FragmentP2();
        Bundle args = new Bundle();
        args.putString(KEY_poisonDmgP2, poison );
        args.putString(KEY_commanderDmgP2, commander);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPoisonDmgP2 = getArguments().getString(KEY_poisonDmgP2);
            mCommanderDmgP2 = getArguments().getString(KEY_commanderDmgP2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p2, container, false);

        minusCommdmgP2 = view.findViewById(R.id.minusCommDmgP2);
        plusCommdmgP2 = view.findViewById(R.id.plusCommDmgP2);
        minusPoisonP2 = view.findViewById(R.id.minuspoisonP2);
        plusPoisonP2 = view.findViewById(R.id.pluspoisonP2);
        commDmgP2 = view.findViewById(R.id.commanderdamageP2);
        poisonP2 = view.findViewById(R.id.poisoncounterP2);
        closeFragment2 = view.findViewById(R.id.closeFragment2);
        resetButton2 = view.findViewById(R.id.resetCommPoison2);

        commDmgP2.setText(mCommanderDmgP2);
        commDmgP2.requestFocus();
        poisonP2.setText(mPoisonDmgP2);
        poisonP2.requestFocus();

        Context context = getContext();
        if ( Integer.parseInt(commDmgP2.getText().toString()) > 20 ) commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.red));
        else commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.white));
        if ( Integer.parseInt(poisonP2.getText().toString()) > 9 ) poisonP2.setTextColor(ContextCompat.getColor(context, R.color.red));
        else poisonP2.setTextColor(ContextCompat.getColor(context, R.color.white));

        resetButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commDmgP2.setText("0");
                poisonP2.setText("0");
                commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.white));
                poisonP2.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        minusCommdmgP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(commDmgP2.getText().toString());
                if ( dmg > 0 ) dmg--;
                commDmgP2.setText("" + dmg);
                if ( dmg > 20 ) commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.red));
                else commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        plusCommdmgP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(commDmgP2.getText().toString());
                dmg++;
                commDmgP2.setText("" + dmg);
                if ( dmg > 20 ) commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.red));
                else commDmgP2.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        plusPoisonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(poisonP2.getText().toString());
                dmg++;
                poisonP2.setText("" + dmg);
                if ( dmg > 9 ) poisonP2.setTextColor(ContextCompat.getColor(context, R.color.red));
                else poisonP2.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        minusPoisonP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dmg;
                Context context = getContext();
                dmg = Integer.parseInt(poisonP2.getText().toString());
                if ( dmg > 0 ) dmg--;
                poisonP2.setText("" + dmg);
                if ( dmg > 9 ) poisonP2.setTextColor(ContextCompat.getColor(context, R.color.red));
                else poisonP2.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        });

        closeFragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLifes();
            }
        });

        return view;
    }

    private void returnToLifes() {

        String sendBackCommDmgP2 = commDmgP2.getText().toString();
        String sendBackPoisonP2 = poisonP2.getText().toString();

        if ( mListener != null ){
            sendBack2(sendBackCommDmgP2, sendBackPoisonP2);
            mListener.onFragmentClose2();
        }
    }

    public void sendBack2(String sendBackCommDmgP2, String sendBackPoisonP2) {
        if ( mListener != null ){
            mListener.onFragmentInteraction2(sendBackCommDmgP2, sendBackPoisonP2);
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
        void onFragmentInteraction2(String sendBackCommanderDmgP2, String sendBackPoisonP2);
        void onFragmentClose2();
    }
}