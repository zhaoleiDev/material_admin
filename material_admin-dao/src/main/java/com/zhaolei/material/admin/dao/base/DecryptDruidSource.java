package com.zhaolei.material.admin.dao.base;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * 为了加密数据源配置
 * @author ZHAOLEI
 */

public class DecryptDruidSource extends DruidDataSource {
    private static final long serialVersionUID = 5529179455146258541L;
    @Override
    public void setUsername(String encryptUserName){
        String userName = null;
        try {
            userName = ConfigTools.decrypt(encryptUserName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setUsername(userName);
    }

    @Override
    public void setPassword(String encryptPassword){
        String password = null;
        try {
            password = ConfigTools.decrypt(encryptPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.setPassword(password);
    }

    public static void main(String[] args) throws Exception {
        String str1 = ConfigTools.encrypt("root");
        String str2 = ConfigTools.encrypt("123456");
        System.out.println(str1);
        System.out.println(str2);
    }
}
