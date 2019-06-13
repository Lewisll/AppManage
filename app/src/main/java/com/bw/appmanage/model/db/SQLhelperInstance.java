package com.bw.appmanage.model.db;

import com.bw.appmanage.app.AppApplication;

/**
 * <pre>
 * <br/>author: Ben
 * <br/>email :
 * <br/>time  : 2019/6/6
 * <br/>desc  :
 * </pre>
 */
public class SQLhelperInstance {
    private final static SQLhelperInstance INSTANCE=new SQLhelperInstance();
    private SQLHelper sqlHelper;

    private SQLhelperInstance(){}

    public static SQLhelperInstance getInstance(){
        return INSTANCE;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null)
            sqlHelper = new SQLHelper(AppApplication.getIntance());
        return sqlHelper;
    }
}
