מדריך: שילוב תצוגת PDF באפליקציה (AndroidX PDF)
מדריך זה מסביר איך להציג קובץ PDF בתוך האפליקציה, גם אם המידע שלך נמצא בזיכרון כמערך של בתים (byte[]).

שלב 1: הגדרות הפרויקט (build.gradle.kts)
הספרייה הרשמית של גוגל דורשת הגדרות ספציפיות כדי לתמוך ברינדור מתקדם (תהליך שבו המערכת "מציירת" מחדש את הטקסט בכל פעם שעושים זום, כך שהוא לא נראה מטושטש כמו בתמונה רגילה) . 
ודאי שהקובץ שלך כולל את השורות הבאות:
android {
    compileSdk = 36 // חובה 35 ומעלה
    compileSdkExtension = 19 // חובה עבור תמיכה ב-PDF

    defaultConfig {
        minSdk = 28 // הספרייה דורשת רמה גבוהה של SDK
        // ...
    }
}

dependencies {
    // הספרייה הרשמית של אנדרואיד לצפייה ב-PDF
    implementation("androidx.pdf:pdf-viewer:1.0.0-alpha18")
}
שלב 2: עיצוב המסך (activity_main.xml)
כדי להציג את ה-PDF, נשתמש ב-"מיכל" (Container) שבתוכו ירוץ רכיב התצוגה. הוסיפי FragmentContainerView לקובץ ה-XML:
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/pdf_viewer_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
שלב 3: מימוש בתוך הקוד (MainActivity.java)
הספרייה של אנדרואיד מציגה PDF מתוך כתובת קובץ (Uri). אם יש לך byte[], עלינו לשמור אותו לקובץ זמני בזיכרון המטמון (Cache) של האפליקציה ואז להציגו.
1. פונקציית עזר להמרת byte[] לקובץ:
הוסיפי את הפונקציה הזו בתוך ה-Class שלך:
private Uri savePdfToCache(byte[] pdfBytes) {
    try {
        // יצירת קובץ זמני בתיקיית ה-Cache
        File tempFile = new File(getCacheDir(), "display_document.pdf");
        
        // כתיבת הבתים לתוך הקובץ
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(pdfBytes);
        fos.close();
        
        // החזרת הכתובת של הקובץ שנוצר
        return Uri.fromFile(tempFile);
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}
2. הצגת ה-PDF ב-onCreate:
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // נניח שזה המידע שלך (הבתים של ה-PDF)
    byte[] myPdfBytes = getPdfData(); 

    if (savedInstanceState == null) {
        // א. יצירת הפרגמנט של מציג ה-PDF
        PdfViewerFragment pdfViewerFragment = new PdfViewerFragment();

        // ב. החלפת המיכל ב-XML בפרגמנט שיצרנו
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pdf_viewer_container, pdfViewerFragment)
                .commit();

        // ג. המרת הבתים לקובץ וטעינה לתצוגה
        Uri pdfUri = savePdfToCache(myPdfBytes);
        if (pdfUri != null) {
            // טעינת ה-PDF לתצוגה
            pdfViewerFragment.setDocumentUri(pdfUri);
        }
    }
}
דגשים חשובים לביצוע:
ייבוא (Imports): ודאי שייבאת את המחלקות הנכונות (Uri, File, FileOutputStream, PdfViewerFragment).
יעילות: שימוש ב-getCacheDir() הוא הפתרון הטוב ביותר כי הקבצים נמחקים אוטומטית כשהמכשיר צריך מקום, והם פרטיים לאפליקציה שלך.
שימי לב: הספרייה היא בגרסת alpha, לכן חשוב להישאר מעודכנת בשינויים עתידיים של גוגל.
זהו! כעת האפליקציה תדע לקחת מערך של בתים, להפוך אותו לקובץ זמני ולהציג אותו בצורה חלקה בתוך ה-Activity.



