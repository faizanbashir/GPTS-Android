# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\DELL\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn org.apache.**
-dontwarn com.sun.**
-dontwarn com.google.**
-dontwarn com.android.**
-dontwarn javax.activation.**
-keep class javamail.**{*;}
-keep class javax.activation.**{*;}
-keep class javax.security.**{*;}
-keep class com.sun.mail.dsn.**{*;}
-keep class com.sun.mail.handlers.**{*;}
-keep class com.sun.mail.smtp.**{*;}
-keep class mimetypes.**{*;}
-keep class myjava.awt.datatransfer.**{*;}
-keep class org.apache.harmony.awt.**{*;}
-keep class org.apache.harmony.misc.**{*;}
-keep class javax.mail.**{*;}



