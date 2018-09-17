package singleton;

import serializable.JavaSerialize.JavaSerialize;
import serializable.protostuff.IoProtostuff;

import java.io.Serializable;

public class DBConnection implements Serializable {

    public static void main(String[] args) {
        DBConnection dbConnection = DataSourceEnum.DATASOURCE.getConnection();
        DBConnection dbConnection2 = (DBConnection) IoProtostuff.unserialize(IoProtostuff.serialize(DataSourceEnum.DATASOURCE.getConnection()));
        System.out.println(dbConnection  + "  " + dbConnection2 + "  " + (dbConnection2 == dbConnection)) ;

        dbConnection2 = (DBConnection) JavaSerialize.unserializeOld(JavaSerialize.serializeOld(DataSourceEnum.DATASOURCE.getConnection()));
        System.out.println(dbConnection  + "  " + dbConnection2 + "  " + (dbConnection2 == dbConnection)) ;
    }

    //readResolve to prevent another instance of Singleton
    private Object readResolve(){
        return  DataSourceEnum.DATASOURCE.getConnection();
    }
}
