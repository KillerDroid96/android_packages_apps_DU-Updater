/*
 * Copyright (C) 2015-2018 The Dirty Unicorns Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dirtyunicorns.duupdater.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dirtyunicorns.duupdater.MainActivity;
import com.dirtyunicorns.duupdater.R;
import com.dirtyunicorns.duupdater.adapters.CardAdapter;
import com.dirtyunicorns.duupdater.utils.GetFiles;
import com.dirtyunicorns.duupdater.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gapps extends Fragment {

    private static String prop = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view, containter, false);

        RecyclerView rv = rootView.findViewById(R.id.rv);
        Animation anim = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        rv.setAnimation(anim);
        rv.animate();
        CardAdapter adapter = new CardAdapter(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        try {
            Process process = new ProcessBuilder("/system/bin/getprop", "ro.build.flavor").redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                prop = line.replace("du_", "").replace("-userdebug", "");
            }
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GetFiles getArmGapps = new GetFiles("gapps/arm", false, adapter, (MainActivity) getActivity());
        GetFiles getArm64Gapps = new GetFiles("gapps/arm64", false, adapter, (MainActivity) getActivity());

        if (Utils.isOnline(getActivity())) {
            switch (prop) {
                case "shamu": {
                    getArmGapps.execute();
                    break;
                }
                case "hammerhead": {
                    getArmGapps.execute();
                    break;
                }
                case "tenderloin": {
                    getArmGapps.execute();
                    break;
                }
                default: {
                    getArm64Gapps.execute();
                    break;
                }
            }
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
        }
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }
}