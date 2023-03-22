# crushlyh-blog
实现功能：
前台：普通用户
后台：管理员管理
前台系统和后台系统会出现重复接口：利用多模块形式，将两套系统可能都会用到的代码写到一个公共模块中，
                                让前台系统和后台系统分别依赖公共模块
# 父模块
1.进行依赖的版本控制<dependencyManagement>
2.聚合模块：<Modules>
3.子模块中添加依赖就不需要进行版本控制，直接利用父模块的版本
# 前台准备工作
1.启动类Application，@SpringBootApplication @MapperScan对Mapper类进行扫描，加入Spring容器
2.application.yaml:配置端口，数据库，mbp
3.利用easycode生成实体类、service、controller
4.loombok注解：Data，NoArgsConstructor，AllArgsConstructor
5.ServiceImpl实现类上需要加@Service注解
6.Controller需要放在子模块下：@RestController @RequestMapper("请求路径")
  注入sevice，@Autowired
  请求方法：需要加入@GetMapping，@POSTMapping等，请求类型注解
7.@TableName("数据库的实际表名")，@TableId：主键
# 通用响应类
1.code，msg，data：利用枚举类AppHttpCodeEnum来对code和msg进行封装

# 接口开发
1.返回通用响应类，ResponseResult
2.controller中不能调用方法，需要利用service，来调用mapper去查询数据库
3.service中生成方法，在实现类中实现这个方法
# 查询
1.LamdaQueryWrapper<返回类型,如Article>：查询条件
  相等：wrapper.eq(Article::getStatus,0);
      字面值处理：实际项目中不允许再代码中使用字面值，需要定义成常量。提高代码可维护性。
      定义SystemConstants常量类：public static final int ARTICLE_STATUS_NORMAL=0；
  排序：wrapper.orderByDesc(Article::getViewCount);
  
2.分页查询：Page<Article> page;
           page(page,wrapper);
3.获取结果：page.getRecords();
4.返回:ResponseResult.okResponse(articles)
      okResponse方法会创建一个ResponseResult对象，并且如果传入的data不为空，就会setData给这个ResponseResult
5.跨域问题：WebConfig
6.使用VO优化：Article返回的字段太多了，前端不需要这么多字段。
             创建ArticleVo类，只包含需要返回的字段，加上注解
  怎么将原始的类转化为Vo类：利用Bean拷贝.BeanCopy.copyProperties() 
7.BeanUtils拷贝类封装：
  public static <V> V copyBeans(Object obj,Class<V> classz)//利用字节码的反射方式来创建目标拷贝类的对象
          V Bobj=class.newInstance();//利用泛型，传入什么类型的字节码就返回该类型的对象
          BeanUtils.copyProperties(obj,Bobj)
          return result;
