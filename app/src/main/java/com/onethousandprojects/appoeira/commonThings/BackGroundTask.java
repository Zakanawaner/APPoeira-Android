package com.onethousandprojects.appoeira.commonThings;

import android.os.AsyncTask;

public class BackGroundTask extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        if (CommonMethods.AmILogged() && !CommonMethods.AmIVerified()) {
            Constants.periodicalRequests.checkForVerifiedEmail();
        }
        /*if (CommonMethods.AmILogged()) {
            Constants.periodicalRequests.sendNewsRequest();
        }*/
        return null;
    }
}
