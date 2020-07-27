package com.twu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // 项目分为三个大类
    // EnterSys为系统身份选择 && Admin管理员页面 && 普通用户页面
    // Admin类继承自User类 增加了超级热搜等特有功能
    // 系统管理员账户密码均是 : admin
    // 系统可以在管理员和不同的普通用户登陆之间反复切换 && 数据保持同步

    public static void main(String[] args) {
        ArrayList data = new ArrayList();
        System.out.println("请输入您的身份 : ");
        EnterSys enter = new EnterSys();
        enter.enterSys(data);
    }
}
