package com.example.tempfile;

import android.content.Intent;

public interface ActivityResultListener {
    void onResult(int resultCode, Intent data);
}
