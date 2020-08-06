#### 反射知识
* 反射概念: 
> 对于任何一个类, 都能知道其中的 构造器(Constructor), 变量(Field), 修饰符(Modifier), 方法(Method)
> 对于任何一个实例, 都能调用这个实例中的任意一个方法, 
> 这种动态获取的信息以及动态调用对象的方法的功能, 叫做 java 语言的反射机制; 

* 反射提供的功能
> 运行期判断任意一个对象所属的类
> 运行时能够构造任意一个类的对象
> 运行期间判断任意一个类所具有的成员变量和方法
> 在运行期间调用任意一个对象的所有方法. 

* Class 和 A.class 
> A.class 存在于磁盘上, 是一个写满了字节码的文件;
> Class 是 JVM 将 A.class 加载到内存中之后, 在永久代/方法区构建的实例对象, 是内存中的; 
> 位于永久代中的 Class 实例与 .class 一一对应, 因为双亲委派原则, 这个 Class 在永久代/方法区内只会存放 1 份
> 这个 Class -> .class new 出来的对象实例会存放在 JVM 的堆中会存在多个; 
> Class 这个 class 中并没有 Constructor, Class 实例是通过 JVM & ClassLoader#defineClass 自动构造的. 
>

* 几种获取 Class 的方式
> Class<?> cls = Class.forName("com.xxx");
> Class<?> cls = A.class; 
> A a = new A(); Class<?> cls = a.getClass(); 

* 根据 Class 获取 Method

<code>
Class<?> aCls = null; 

try {
   aCls = Class.forName("com.test.A");
} catch (ClassNotFoundException e) {
   e.printStackTrace();
}

Method [] methods = aCls.getMethods();

for (Method m: methods) {
    System.out.println(m);
}
</code>
 
* 根据 Class 获取 Constructor
<code>
Class<?> aCls = null; 

try {
    aCls = Class.forName("com.test.A");
    Constructor<?> [] cons  = aCls.getConstructors();
} catch (ClassNotFoundException e) {
    e.printStackTrace();
}
</code>

 
* 根据 Class  获取父类 
<code>
Class<?> aCls = Class.forName("");
Class<?> superCls = aCls.getSuperclass(); 
</code>

* 获取当前 Class 范围内(不包括父类和接口中声明的 class) 所有方法和属性, private, protected 等修饰符修饰的方法也可以正常获取得到
<code>
// all methods 
Method [] declaredMethods = cls.getDeclaredMethods();

// all fields 
Field [] declaredFields = cls.getDeclaredFields(); 
</code>


#### Proxy 知识点
* 代理模式: 给某一个对象加一个代理之后, 该代理负责控制对原对象的引用; 
* java 中的代理模式分为 2 种, 静态代理 & 动态代理
* 静态代理 vs. 动态代理 二者之间的不同之处是, 代理 .class 的生成时机, 
> 静态代理理, 程序运行期之前, proxy class 已经生成;
> 动态代理, 程序跑起来之前, proxy class 还没有生成, 在运行时通过反射的机制来创建生成; 

> 静态代理: 静态代理对象通常 wrap 1 个目标对象的类实例, 来作为单个类的代理类; 
> 动态代理: 通过 interface 来实现 1 个代理对象代理了 1 个 interface 下的多个类实例对象; 

* 动态代理
> JDK 动态代理: 通过 Proxy + InvocationHandler 来实现;
> Cglib 动态代理: 运行时期构建一个目标对象的子类实例对象, 通过继承的方式来调用  && 扩展目标对象中的种种方法;

> Cglib 支持在运行期间扩展 Java 类, 以及实现 Java 接口, 被广泛用在许多 AOP 中为其提供 interceptor 拦截器的实现方法; 

> Cglib 底层是基于 ASM 框架的, ASM 是用来转换字节码的, 并直接生成新的类. 
> Cglib Enhancer 执行过程
```java
* 生成代理类 class 的字节码(.class 文件到磁盘)
* 通过 Class.forName() 加载二进制字节码(load .class 到内存), 构造 Class 实例对象
* 通过反射的方式获取 Constructor#newInstance() 方法来构造实例, 生成代理对象
```  