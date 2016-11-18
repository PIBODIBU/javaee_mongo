package helper;

public class Config {
    public static final String DATABASE_NAME = "issd";
    public static final String COLLECTION_NAME = "apteka";
    public static final String DATABASE_HOST = "localhost";
    public static final String MONGO_URI = "mongodb://root:qwerty111111@ds157187.mlab.com:57187/issd";
    public static final int DATABASE_PORT = 27017;

    public static class DB {
        public static final String COLUMN_ID_HUMAN = "Ідентифікатор документу";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "Назва препарату";
        public static final String COLUMN_DESCRIPTION = "Опис препарату";
        public static final String COLUMN_INDICATION = "Показання для застосування";
        public static final String COLUMN_CONTRAINDICATION = "Протипоказання";
        public static final String COLUMN_SALES_FORM = "Форма продажу";
    }
}