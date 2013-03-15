package be.kdg.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qualcomm.QCARSamples.CloudRecognition.R;

/**
 * User: Sander
 * Date: 26/02/13 11:36
 */
public class ChatFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_layout, container, false);
    }
}