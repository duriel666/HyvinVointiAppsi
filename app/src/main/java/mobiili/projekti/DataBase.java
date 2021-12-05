package mobiili.projekti;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataBase extends SQLiteOpenHelper {

    public static final String HVDB = "Hv.db";

    public DataBase(Context context) {
        super(context, HVDB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(tunnus TEXT primary key, salasana TEXT)");
        MyDB.execSQL("create Table quotes(quote TEXT primary key)");
        MyDB.execSQL("create Table vesi(aika datetime primary key, tunnus TEXT, vesiml INT)");
        MyDB.execSQL("create Table fiilis(aika datetime primary key, tunnus TEXT, fiilis INT)");
        MyDB.execSQL("create Table uni(aika datetime primary key, tunnus TEXT, minuutit INT)");

        MyDB.execSQL("create Table asetukset(tunnus TEXT primary key,vesi int,uni int,fiilis int)");
        MyDB.execSQL("create Table vesimuisti(tunnus TEXT primary key, vesitavoite int,vesijuodaan int)");

        MyDB.execSQL("insert into quotes(quote) values('HyvinVointia paskoihin päiviin!')");
        MyDB.execSQL("insert into quotes(quote) values('Aina voisi mennä huonomminkin')");
        MyDB.execSQL("insert into quotes(quote) values('Kyllä se siitä')");
        MyDB.execSQL("insert into quotes(quote) values('Mitä muuttaisit elämässäsi, jos et pelkäisi mitään?')");
        MyDB.execSQL("insert into quotes(quote) values('Ihminen joka ei tee virheitä, ei tavallisesti tee mitään')");
        MyDB.execSQL("insert into quotes(quote) values('Yksi asia mitä et voi kierrättää, on hukkaan heitetty aika')");
        MyDB.execSQL("insert into quotes(quote) values('On päiviä jolloin täytyy saada pysytellä mukavuusalueellaan')");
        MyDB.execSQL("insert into quotes(quote) values('Tänään on loppuelämäsi ensimmäinen päivä - nauti siitä')");
        MyDB.execSQL("insert into quotes(quote) values('Pyri edistymään, älä täydellisyyteen')");
        MyDB.execSQL("insert into quotes(quote) values('Vaikeat tiet johtavat kauniisiin kohteisiin')");
        MyDB.execSQL("insert into quotes(quote) values('Yritä olla sateenkaari jonkun pilvessä')");
        MyDB.execSQL("insert into quotes(quote) values('Mikään ei korvaa kovaa työtä')");
        MyDB.execSQL("insert into quotes(quote) values('Ota vastaan se loistava sotku, joka olet')");
        MyDB.execSQL("insert into quotes(quote) values('Älä koskaan anna tunteidesi voittaa älykkyyttäsi')");
        MyDB.execSQL("insert into quotes(quote) values('Jos puhut totta, sinun ei tarvitse muistaa mitään')");
        MyDB.execSQL("insert into quotes(quote) values('Toivo on heräävä unelma')");
        MyDB.execSQL("insert into quotes(quote) values('Yksi päivä kerrallaan')");
        MyDB.execSQL("insert into quotes(quote) values('Älä koskaan lopeta unelmoimista')");
        MyDB.execSQL("insert into quotes(quote) values('Tee jotain tänään, josta tuleva itsesi kiittää sinua')");
        //MyDB.execSQL("insert into quotes(quote) values('')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String tunnus, String salasana) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        int i = 1, i2 = 2400, i3 = 200;
        cv.put("tunnus", tunnus);
        cv.put("salasana", salasana);
        long result = MyDB.insert("users", null, cv);
        cv.clear();
        cv.put("tunnus", tunnus);
        cv.put("vesi", i);
        cv.put("uni", i);
        cv.put("fiilis", i);
        MyDB.insert("asetukset", null, cv);
        cv.clear();
        cv.put("tunnus", tunnus);
        cv.put("vesitavoite", i2);
        cv.put("vesijuodaan", i3);
        MyDB.insert("vesimuisti", null, cv);
        return result != -1;
    }

    public Boolean checktunnus(String tunnus) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor c = MyDB.rawQuery("select * from users where tunnus =?", new String[]{tunnus});
        boolean count = c.getCount() > 0;
        c.close();
        return count;
    }

    public Boolean checksalasana(String tunnus, String salasana) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor c = MyDB.rawQuery("select * from users where tunnus=? and salasana=?", new String[]{tunnus, salasana});
        boolean count = c.getCount() > 0;
        c.close();
        return count;
    }

    public ArrayList<String> randomquotelista() {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor c = MyDB.rawQuery("select * from quotes order by random()", null);
        c.moveToFirst();
        ArrayList<String> quotelista = new ArrayList<>();
        while (!c.isAfterLast()) {
            if ((c != null) && (c.getCount() > 0)) {
                quotelista.add(c.getString(c.getColumnIndexOrThrow("quote")));
                c.moveToNext();
            }
        }
        c.close();
        return quotelista;
    }

    private String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return df.format(date);
    }

    public void addFiilis(String tunnus, int fiilis) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tunnus", tunnus);
        cv.put("fiilis", fiilis);
        cv.put("aika", getDateTime());
        MyDB.insert("fiilis", null, cv);
    }

    public ArrayList<String> getFiilisToday(String tunnus) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor c = MyDB.rawQuery("select * from fiilis where tunnus =? and datetime(" +
                "'now', 'start of day')", new String[]{tunnus});
        c.moveToFirst();
        ArrayList<String> fiilisToday = new ArrayList<>();
        while ((!c.isAfterLast())) {
            if ((c != null) && (c.getCount() > 0)) {
                fiilisToday.add(c.getString(c.getColumnIndexOrThrow("fiilis")));
                c.moveToNext();
            }
        }
        c.close();
        return fiilisToday;
    }

    public void addVesi(String tunnus, int vesiml) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tunnus", tunnus);
        cv.put("vesiml", vesiml);
        cv.put("aika", getDateTime());
        MyDB.insert("vesi", null, cv);
    }

    public ArrayList<String> getVesiToday(String tunnus) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor c = MyDB.rawQuery("select * from vesi where tunnus =? and datetime(" +
                "'now', 'start of day')", new String[]{tunnus});
        c.moveToFirst();
        ArrayList<String> vesiToday = new ArrayList<>();
        while ((!c.isAfterLast())) {
            if ((c != null) && (c.getCount() > 0)) {
                vesiToday.add(c.getString(c.getColumnIndexOrThrow("vesiml")));
                c.moveToNext();
            }
        }
        c.close();
        return vesiToday;
    }

    public void setAsetukset(String tunnus, int vesi, int uni, int fiilis) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tunnus", tunnus);
        cv.put("vesi", vesi);
        cv.put("uni", uni);
        cv.put("fiilis", fiilis);
        MyDB.replace("asetukset", null, cv);
    }

    public ArrayList<String> getAsetukset(String tunnus) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor c = MyDB.rawQuery("select * from asetukset where tunnus =?", new String[]{tunnus});
        c.moveToFirst();
        ArrayList<String> asetukset = new ArrayList<>();
        asetukset.add(c.getString(c.getColumnIndexOrThrow("vesi")));
        asetukset.add(c.getString(c.getColumnIndexOrThrow("uni")));
        asetukset.add(c.getString(c.getColumnIndexOrThrow("fiilis")));
        c.close();
        return asetukset;
    }

    public void setVesiMuisti(String tunnus, int vesitavoite, int vesijuodaan) {
        SQLiteDatabase MyDB = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tunnus", tunnus);
        cv.put("vesitavoite", vesitavoite);
        cv.put("vesijuodaan", vesijuodaan);
        MyDB.replace("vesimuisti", null, cv);
    }

    public ArrayList<String> getVesiMuisti(String tunnus) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor c = MyDB.rawQuery("select * from vesimuisti where tunnus =?", new String[]{tunnus});
                c.moveToFirst();
        ArrayList<String> vesimuisti = new ArrayList<>();
        vesimuisti.add(c.getString(c.getColumnIndexOrThrow("vesitavoite")));
        vesimuisti.add(c.getString(c.getColumnIndexOrThrow("vesijuodaan")));
        c.close();
        return vesimuisti;
    }
}