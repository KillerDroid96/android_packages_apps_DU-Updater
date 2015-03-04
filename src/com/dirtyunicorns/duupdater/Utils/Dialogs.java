/*
* Copyright (C) 2015 Dirty Unicorns
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

package com.dirtyunicorns.duupdater.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.dirtyunicorns.duupdater.Services.DownloadIntent;

/**
 * Created by mazwoz on 28.02.15.
 */
public class Dialogs {

    private static String url;

    public static void DownloadDialog(final Context ctx, String text, String title, final String dir, final String file) {


        url = Vars.link + "/" + dir + "/" + file + ".zip";

        new AlertDialog.Builder(ctx)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setMessage(text)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ProgressDialog mProgressDialog;

                        // instantiate it within the onCreate method
                        mProgressDialog = new ProgressDialog(ctx);
                        mProgressDialog.setMessage("Downloading...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.setCancelable(false);
                        // execute this when the downloader must be fired
                        final Download downloadTask = new Download(ctx, file, mProgressDialog);
                        downloadTask.execute(url);

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    public static void DownloadFinished(Context ctx, String message, String title) {
        new AlertDialog.Builder(ctx)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadIntent.thisActivity.finish();
                    }
                })
                .show();
    }

    public static void OfflineDialog(Context ctx) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Offline");
        alertDialog.setMessage("Please check your data connection and relaunch app");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.show();
    }

}