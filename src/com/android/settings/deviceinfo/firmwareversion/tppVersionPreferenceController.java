/*
 * Copyright (C) 2021 Fusion OS
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
package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemProperties;
import android.text.TextUtils;
import androidx.preference.Preference;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class tppVersionPreferenceController extends BasePreferenceController {

    private static final String TAG = "tppVersionPreferenceController";
    private static final String ROM_PROPERTY = "ro.custom.showversion";
    private static final String TPP_WEBSITE_URL = "https://github.com/The-Pixel-Project";

    public tppVersionPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public CharSequence getSummary() {
        String rom = SystemProperties.get(ROM_PROPERTY,
                this.mContext.getString(R.string.device_info_default));
        return rom;
    }

    @Override
    public void updateState(Preference preference) {
        super.updateState(preference);

        // Update the summary on every build
        String version = SystemProperties.get(ROM_PROPERTY, 
                this.mContext.getString(R.string.device_info_default));
        preference.setSummary(version);

        // Listener to open the URL
        preference.setOnPreferenceClickListener(pref -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TPP_WEBSITE_URL));
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                mContext.startActivity(intent);
            }
            return true;
        });
    }
}
