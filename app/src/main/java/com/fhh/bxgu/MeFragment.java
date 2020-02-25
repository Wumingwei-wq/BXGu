package com.fhh.bxgu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class MeFragment extends Fragment {
    //存储FrameworkActivity.
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @SuppressWarnings("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        ImageView qrButton = view.findViewById(R.id.btn_qr);
        TextView updateTheme = view.findViewById(R.id.text_theme);
        final int[] radioButtons = {R.id.radio_green, R.id.radio_blue, R.id.radio_gray, R.id.radio_yellow, R.id.radio_purple};
        final String[] colors = {"green", "blue", "gray", "yellow", "purple"};
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成二维码。
                String data = "@Unknown";//只需要传输用户id即可。
                ImageView generatedQR = new ImageView(getContext());
                generatedQR.setImageBitmap(QRCodeUtil.createQRCodeBitmap(data, 512, 512,StaticVariablePlacer.meFragmentCallbacks.getMainColorDark()));
                new AlertDialog.Builder(getContext())
                        .setView(generatedQR)
                        .setTitle(R.string.qr_title)
                        .setPositiveButton(R.string.str_ok, null)
                        .show();
            }
        });
        updateTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View themeChooser = getLayoutInflater().inflate(R.layout.dialog_theme_chooser, null);
                final RadioGroup rg = themeChooser.findViewById(R.id.rg_theme);
                new AlertDialog.Builder(getContext())
                        .setView(themeChooser)
                        .setTitle("请选择主题")
                        .setPositiveButton(R.string.str_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int selected = rg.getCheckedRadioButtonId();
                                for (int i = 0; i < radioButtons.length; i++) {
                                    if (selected == radioButtons[i]) {
                                        StaticVariablePlacer.meFragmentCallbacks.onThemeChanged(colors[i]);
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.str_cancel, null)
                        .show();

            }
        });
        return view;
    }

    //MeFragment中的回调接口。
    public interface Callbacks {
        void onThemeChanged(String theme);
        int getMainColorDark();
    }
}