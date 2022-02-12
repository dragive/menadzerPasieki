package pl.pasiekaradosna.menadzerpasieki.services;

public abstract class Settings {


    public static final Integer DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main.db";

    public static final String TAG_APP = "menadzerPasieki";


    public static final String TABLE_APIARIES = "Apiaries";
    public static final String TABLE_HIVES = "Hive";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_TASK = "Task";
    public static final String TABLE_HIVES_AND_TASKS = "Hives_tasks";


    public static final String WIND_SPEED_UNIT = "m/s";
    public static final String AIR_TEMPERATURE_UNIT = "C";
    public static final String CLOUD_AREA_UNIT = "%";


}
