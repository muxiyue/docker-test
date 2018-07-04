package singleton;

public enum DataSourceEnum {
    DATASOURCE;
    private DBConnection connection = null;
    private DataSourceEnum() {
        connection = new DBConnection();
    }
    public DBConnection getConnection() {
        return connection;
    }
}  