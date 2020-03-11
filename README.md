# mybatis-generator
### 配置 pom.xml

```
    <pluginRepositories>
        <pluginRepository>
            <id>weilus923</id>
            <name>weilus923</name>
            <url>https://raw.githubusercontent.com/weilus923/mybatis-generator/master/</url>
        </pluginRepository>
    </pluginRepositories>
    
    ...
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>
                <dependencies>
                    ...
                    <dependency>
                        <groupId>com.weilus</groupId>
                        <artifactId>mybatis-generator-jsr303-plugin</artifactId>
                        <version>1.0</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
### 配置 generatorConfig.xml

```
<generatorConfiguration>
    <context>
      <plugin type="org.mybatis.generator.plugins.ValidationPlugin"/>
      ...
    </context>
</generatorConfiguration> 
```

### 配置 mybatis-jsr303.yml

```
com.weilus.zxhtml.entity.Test:
  name:
  - "@Size(min = 2,max = 10)"
  age:
  - "@Range(min = 0,max = 150)"
```