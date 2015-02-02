package com.example.pedro.cpdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Displays the text returned for each request
 *
 * Created by pedro on 2/1/15.
 */
public class ResponseDialogFragment extends DialogFragment {

    private String title;
    private String message;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.response_view, null);
        builder.setView(view);
        EditText textView = (EditText)view.findViewById(R.id.responseTextView);
        textView.setText(message);
        builder.setTitle(title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    public static void showResponse(FragmentActivity activity, int id, String response) {
        ResponseDialogFragment dialog = new ResponseDialogFragment();
        dialog.title = String.format("Fetch %d", id);
        dialog.message = response;
        dialog.show(activity.getFragmentManager(), dialog.getClass().getSimpleName());
    }
}
