## common-dao使用
必须使用[mybatis-util]生成相关代码后方可使用

https://github.com/frostding/mybatis-util

```xml
<dependency>
    <groupId>com.dessert</groupId>
    <artifactId>mybatis-util-create-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
####方法清单
公共的mapper有
查询:
- `T selectByPrimaryKey(Serializable... vals)`
- `T selectOne(T parameter);`
- `Map<String, Object> selectMapOne(Map<String, Object> parameter);`
- `List<T> selectList(T parameter);`
- `List<T> selectListPrmMap(Map<String, Object> parameter);`
- `List<Map<String, Object>> selectMapListPrmBean(T parameter);`
- `List<Map<String, Object>> selectMapList(Map<String, Object> parameter);`
- `int selectCountPrmMap(Map<String, Object> parameter);`
- `boolean isExist(T parameter);`
- `boolean isExistByPrimaryKey(Serializable... vals);`
- `int selectCount(T parameter);`
- `List<T> selectMoreCondList(String key, Object... vals);`
- `T selectFist(T parameter);`
- `List<T> selectListByQuantity(T parameter, int quantity);`
- `<K> Map<K, T> selectConversionMap(T parameter, String mapKey);`
- `Page<T> selectPage(Page<T> page, T parameter);`
- `Page<T> selectPage(Page<T> page, Map<String, Object> parameter);`
- `Page<T> selectPage(int count, Page<T> page, Map<String, Object> parameter);`
- `age<Map<String, Object>> selectPageMap(Page<Map<String, Object>> page, Map<String, Object> parameter);`
新增:
- `int insert(T var1);`
- `int insertPrmMap(Map<String, Object> var1);`
- `int insertBatch(List<T> var1);`
修改:
- `int updateByPrimaryKey(T parameter);`
- `int updateByPrimaryKeyPrmMap(Map<String, Object> parameter);`
- `int update(T parameter);`
- `int updatePrmMap(Map<String, Object> parameter);`
- `int updateByChoseKey(T parameter, String... choseKey);`
- `int insertOrUpdateByChoseKey(T parameter, String... choseKey)`
删除:
- `int deleteByPrimaryKey(T parameter);`
- `int deleteByPrimaryKeyPrmMap(Map<String, Object> parameter);`
- `int delete(T parameter);`(默认禁用)
- `int deletePrmMap(Map<String, Object> parameter);`默认禁用)

#### Criteria使用
`com.dessert.common.dao.criteria.Criteria`
提供基础的扩展查询
```
        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate())
        .WHERE_IsNotNull(AwsQueueWarehouse.COLUMN_createdate())
        .WHERE_EqualTo(AwsQueueWarehouse.COLUMN_destinationName(),"out_bound")
        .WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,20);
```


#### 使用
参数分为两种类型
- Java Bean
- Map

##### 参数:Jave Bean 相关使用
###### 基础条件参数:
```
        AwsQueueWarehouse warehouse = new AwsQueueWarehouse();
        warehouse.setDestinationName("out_bound");
        List<AwsQueueWarehouse> warehouses = mapper.selectList(warehouse);
        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
```
2017-10-10 09:27:06.915 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE aqw.destination_name = ? 
2017-10-10 09:27:06.970 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - ==> Parameters: out_bound(String)
2017-10-10 09:27:07.108 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - <==      Total: 116
```
###### 复杂条件使用 
使用com.dessert.common.dao.criteria.Criteria动态生成子类

`Criteria类提供基础的排序,WHERE等查询方法,详细查看Criteria类`
- selectList
```
        AwsQueueWarehouse warehouse = new AwsQueueWarehouse();
        warehouse.setDestinationName("out_bound");

        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
//        criteria.WHERE_IsNotNull(AwsQueueWarehouse.COLUMN_createdate());
        criteria.WHERE_EqualTo(AwsQueueWarehouse.COLUMN_destinationName(),"out_bound");
//        criteria.WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,20);
        criteria.WHERE_NotBetween(AwsQueueWarehouse.COLUMN_id(), 10, 20);

        List<Object> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        criteria.WHERE_In(AwsQueueWarehouse.COLUMN_id(), ids);
		
		//动态生成查询参数Bean的子类
        warehouse = MapperBeanFactory.newInstance(warehouse, criteria);
        List<AwsQueueWarehouse> warehouses = mapper.selectList(warehouse);

        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
```
2017-10-10 09:33:14.089 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE aqw.destination_name = ? AND destination_name = ? AND id NOT BETWEEN ? AND ? AND id IN ( ? , ? , ? ) ORDER BY updatedate DESC 
2017-10-10 09:33:14.160 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - ==> Parameters: out_bound(String), out_bound(String), 10(Integer), 20(Integer), 1(Integer), 2(Integer), 3(Integer)
2017-10-10 09:33:14.188 [main] - [DEBUG] [|.b.d.d.mapper.AwsQueueWarehouseMapper.selectList] - <==      Total: 3
```
- selectPage
```
        AwsQueueWarehouse warehouse = new AwsQueueWarehouse();
        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
        criteria.WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,30);
        warehouse = MapperBeanFactory.newInstance(warehouse, criteria);

        Page<AwsQueueWarehouse> page = new Page<>();
        page = mapper.selectPage(page,warehouse);
        List<AwsQueueWarehouse> warehouses = page.getPageList();
        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
```
2017-10-10 09:43:38.562 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE id BETWEEN ? AND ? ORDER BY updatedate DESC LIMIT ? , ? 
2017-10-10 09:43:38.562 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==> Parameters: 10(Integer), 30(Integer), 0(Integer), 10(Integer)
2017-10-10 09:43:38.575 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - <==      Total: 10
```

##### 参数:Map 相关使用
- selectListPrmMap
```
        Map<String,Object> params = new HashMap<>();
        params.put("destinationName","out_bound");
        List<AwsQueueWarehouse> warehouses = mapper.selectListPrmMap(params);

        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
```
2017-10-10 10:18:14.977 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE aqw.destination_name = ? 
2017-10-10 10:18:15.019 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==> Parameters: out_bound(String)
2017-10-10 10:18:15.234 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - <==      Total: 116
```
Map与Criteria结合使用:
```
        Map<String,Object> params = new HashMap<>();
        Criteria criteria = new Criteria();
        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
        criteria.WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,30);

        params.put("criteria",criteria);
        List<AwsQueueWarehouse> warehouses = mapper.selectListPrmMap(params);

        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
```
2017-10-10 10:22:00.180 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE id BETWEEN ? AND ? ORDER BY updatedate DESC 
2017-10-10 10:22:00.233 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==> Parameters: 10(Integer), 30(Integer)
2017-10-10 10:22:00.411 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - <==      Total: 21
```
- selectPagePramMap
```
        Map<String, Object> params = new HashMap<>();
        params.put("destinationName","out_bound");

        Page<AwsQueueWarehouse> page = new Page<>();
        page = mapper.selectPage(page,params);
        List<AwsQueueWarehouse> warehouses = page.getPageList();
        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
语句输出:
	
```
2017-10-10 10:25:19.539 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE aqw.destination_name = ? LIMIT ? , ? 
2017-10-10 10:25:19.541 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==> Parameters: out_bound(String), 0(Integer), 10(Integer)
2017-10-10 10:25:19.551 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - <==      Total: 10
```

- Map与Criteria结合使用:
```
        Map<String, Object> params = new HashMap<>();
        params.put("destinationName","out_bound");
        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
        criteria.WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,30);

        params.put("criteria",criteria);


        Page<AwsQueueWarehouse> page = new Page<>();
        page = mapper.selectPage(page,params);
        List<AwsQueueWarehouse> warehouses = page.getPageList();
        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(tmep.getId()));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```

语句输出:
```
2017-10-10 10:27:11.662 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==>  Preparing: SELECT aqw.id AS "id" , aqw.message_id AS "messageId" , aqw.destination_name AS "destinationName" , aqw.house_type AS "houseType" , aqw.order_type AS "orderType" , aqw.order_num AS "orderNum" , aqw.wh_order_num AS "whOrderNum" , aqw.status AS "status" , aqw.createdate AS "createdate" , aqw.updatedate AS "updatedate" FROM aws_queue_warehouse aqw WHERE aqw.destination_name = ? AND id BETWEEN ? AND ? ORDER BY updatedate DESC LIMIT ? , ? 
2017-10-10 10:27:11.663 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - ==> Parameters: out_bound(String), 10(Integer), 30(Integer), 0(Integer), 10(Integer)
2017-10-10 10:27:11.676 [main] - [DEBUG] [|.b.d.d.m.AwsQueueWarehouseMapper.selectListPrmMap] - <==      Total: 10
```

- 返回结果指定字段:
当criteria.EXCLUDE_COLUMN,criteria.INCLUDE_COLUMN 同时存在,优先使用EXCLUDE_COLUMN

```
        AwsQueueWarehouse warehouse = new AwsQueueWarehouse();
        warehouse.setDestinationName("out_bound");
        Criteria criteria = new Criteria();
        criteria.EXCLUDE_COLUMN(AwsQueueWarehouse.COLUMN_messageId(),AwsQueueWarehouse.COLUMN_id(),AwsQueueWarehouse.COLUMN_createdate());

```



#### 总结使用
关于参数为Jave Bean 与Map 查询条件为简单的单表'=='操作,直接使用Jave Bean 与Map作为参数就行.涉及到复杂点的查询需要配合`com.dessert.common.dao.criteria.Criteria`使用
- Java Bean
使用`com.dessert.common.dao.binding.MapperBeanFactory#newInstance`动态创建子类作为参数
```
        AwsQueueWarehouse warehouse = new AwsQueueWarehouse();
        warehouse.setDestinationName("out_bound");

        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
        criteria.WHERE_NotBetween(AwsQueueWarehouse.COLUMN_id(), 10, 20);

        List<Object> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        criteria.WHERE_In(AwsQueueWarehouse.COLUMN_id(), ids);

        warehouse = MapperBeanFactory.newInstance(warehouse, criteria);
        List<AwsQueueWarehouse> warehouses = mapper.selectList(warehouse);
```
- Map
使用`java.util.Map#put`把`criteria`作为参数放入
```
        Map<String, Object> params = new HashMap<>();
        Criteria criteria = new Criteria();

        criteria.ORDER_BY_DESC(AwsQueueWarehouse.COLUMN_updatedate());
        criteria.WHERE_Between(AwsQueueWarehouse.COLUMN_id(),10,20);

        params.put("criteria",criteria);

        Page<Map<String, Object>> page = new Page<>();
        page = mapper.selectPageMap(page,params);
        List<Map<String, Object>> warehouses = page.getPageList();
        System.out.println("------------------------");
        warehouses.forEach(tmep -> System.out.println(MapUtils.getString(tmep,"id")));
        System.out.println("------------------------");
        System.out.println(warehouses.size());
        System.out.println("------------------------");
```
