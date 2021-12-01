package com.we3j.demo;
import com.we3j.demo.test.WalletDemo;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 本案例作为web3j的示例项目
 * 1 连接以太坊客户端
 * 2 创建冷钱包
 * 3 加载钱包文件
 * 4 转账
 * 5 部署合约
 * 6 读取&更新 智能合约的数据
 * 7 观察智能合约event
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception {
//        SpringApplication app = new SpringApplication(Application.class);
//        ConfigurableApplicationContext context = app.run();
//        context.start();
        WalletDemo.main(args);
    }

}
