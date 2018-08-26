# springboot-mybatis
该项目是一个基于springboot、mybatis、druid框架，整合的一个web系统示例。整个项目是一个maven工程，包含若干个maven model。<br><br>
1、springboot-mybatis：<br>
	&nbsp;最简单的一个单数据源示例，整合了springboot和mybatis的使用；
<br><br>
2、springboot-mybatis-druid：<br>
	&nbsp;在上一个model基础上，加入了druid数据库连接池功能；
<br><br>
3、springboot-mybatis-mutilds：<br>
	&nbsp;在上一个model基础上，引入了多数据源的需求，介绍了如何静态创建多个数据源；
<br><br>
4、springboot-mybatis-dynamic-wr：<br>
	&nbsp;在第二model的基础上，引入了读写分离的需求，介绍了如何动态切换数据源（多数据源的另一种实现方式）；
<br><br>
5、springboot-mybatis-dynamic-mutilds：<br>
	&nbsp;将第三、第四个model进行了整合，静态的创建了两个数据源，每个数据源都是读写分离；
<br><br>