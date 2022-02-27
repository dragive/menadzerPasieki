package pl.pasiekaradosna.menadzerpasieki.services;

public abstract class Settings {


    public static final Integer DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "main.db";

    public static final String TAG_APP = "menadzerPasieki";


    public static final String TABLE_APIARIES_OLD = "Apiary";
    public static final String TABLE_HIVES_OLD = "Hive";
    public static final String TABLE_USERS_OLD = "Users";
    public static final String TABLE_TASK_OLD = "Task";
    public static final String TABLE_HIVES_AND_TASKS_OLD = "Hives_tasks";


    public static final String TABLE_TASK_TYPE  = "Task_Type";
    public static final String TABLE_TASK  = "Task";
    public static final String TABLE_QUEEN  = "Queen";
    public static final String TABLE_QUEEN_BREED  = "Queen_Breed";
    public static final String TABLE_HIVE  = "Hive";
    public static final String TABLE_APIARY  = "Apiary";


    public static final String WIND_SPEED_UNIT = "m/s";
    public static final String AIR_TEMPERATURE_UNIT = "C";
    public static final String CLOUD_AREA_UNIT = "%";


}
