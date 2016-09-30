package com.example.henryzheng.xutils3;

/**
 * Created by henryzheng on 2016/9/30.
 */
public class TempSql {
    //查找一定范围的数据
    String sql="select  top 10* from client_layout where fieldPosition in (select top 20  fieldPosition from client_layout where pageid='-9' order by fieldPosition asc /*asc desc*/) and pageid=-9  order by fieldPosition desc";
}
