package com.leo.libcputype;

import android.support.annotation.StringDef;

@StringDef({
        CpuType.ARM64_V8A,
        CpuType.ARMEABI_V7A,
        CpuType.X86,
        CpuType.X86_64
})
public @interface CpuType {
    String ARM64_V8A = "arm64-v8a";
    String ARMEABI_V7A = "armeabi-v7a";
    String X86 = "x86";
    String X86_64 = "x86-64";
}
