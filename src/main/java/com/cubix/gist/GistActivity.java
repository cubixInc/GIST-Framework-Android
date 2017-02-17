package com.cubix.gist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cubix.gist.synresource.ISyncComplete;
import com.cubix.gist.synresource.SyncHelper;
import com.cubix.gist.synresource.SyncResourceManager;
import com.cubix.gist.utils.GistConstants;
import com.cubix.gist.utils.GistUtils;

public class GistActivity extends AppCompatActivity implements ISyncComplete {

    private SyncHelper syncHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SyncResourceManager.getInstance().initSyncHelper(this);
        //syncHelper = new SyncHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SyncResourceManager.getInstance().getSyncHelper().syncUpdatedResources();
        //syncHelper.syncUpdatedResources();
    }

    @Override
    public void onComplete() {

        GistUtils.Log(GistConstants.LOG_E, GistConstants.TAG, "GistActivity: onComplete");
    }
}
