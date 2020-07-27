package com.twu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Admin extends User{
    // 热搜排行榜
    ArrayList rankAdmin = new ArrayList();
    // 记录每个普通用户的票数
    Map<String,Integer> currentPollsAdmin = new HashMap<>();
    // 记录购买热搜的最低消费
    int addHotMoneyAdmin;
    // 验证管理员账户和密码是否正确
    Boolean flag =true;
    // 记录登陆者昵称
    String name;

    // 得到实参 && 验证管理员登录
    public Admin(ArrayList data, Map<String,Integer> polls,int money){
        super();
        this.rankAdmin = data;
        this.currentPollsAdmin = polls;
        this.addHotMoneyAdmin = money;
        while (flag){
            login();
        }
    }

    // 管理员登录验证
    public void login(){
        System.out.println("请输入您的管理员账户");
        Scanner adminName = new Scanner(System.in);
        String name = adminName.next();
        System.out.println("请输入您的管理员密码");
        Scanner adminPwd = new Scanner(System.in);
        String pwd = adminPwd.next();
        // 如果账户和密码正确 flag为false  跳出构造函数的while循环 即登陆成功,执行后续
        if(name.equals("admin") && pwd.equals("admin")){
            System.out.println(name + "----" + "欢迎您的登录!");
            flag = false;
            this.name = name;
        }else{
            System.out.println("您的账户名或密码错误!");
            login();
        }
    }

    // 保证系统不退出 数据始终同步保存
    // 返回当前热搜榜单
    public ArrayList backRankAdmin(){
        return this.rankAdmin;
    }
    // 返回更新后每一名用户的票数
    public Map<String,Integer> backPollsAdmin(){
        return this.currentPollsAdmin;
    }
    // 返回购买热搜的最低消费
    public int backMoneyAdmin(){
        return this.addHotMoneyAdmin;
    }

    // 主要逻辑功能实现  作为执行接口  调用基础函数实现各个功能
    public Integer achieveAdmin() {
        System.out.println(this.name+" 请输入您想要实现功能的数字");
        System.out.println("1 -> 查看热搜排行榜");
        System.out.println("2 -> 添加热搜");
        System.out.println("3 -> 添加超级热搜");
        System.out.println("0 -> 退出当前身份");
        // 验证管理员输入是否规范  输入0-4之间的任意一个数字  require确定用户功能需求
        int require = Integer.parseInt(isStandard("[0-4]"));
        // 根据用户输入编号 判断执行哪一个功能
        while (true){
            // 查看热搜
            if(require == 1){
                watchHot(this.rankAdmin);
                // 查看热搜完毕后 重新进入achieveAdmin函数 持续性提供服务
                this.achieveAdmin();
                break;
            // 添加热搜
            }else if (require == 2){
                addHot(this.rankAdmin);
                this.achieveAdmin();
                break;
            // 添加超级热搜  此处Admin自己构造一个
            }else if (require == 3){
                addSuperHot(this.rankAdmin);
                this.achieveAdmin();
                break;
            // 如果按键为0 即选择退出当前身份 返回EnterSys类  主界面
            }else if (require == 0) {
                break;
            }
        }
        // 默认退出当前身份
        return 0;
    }

    // 添加超级热搜
    public void addSuperHot(ArrayList data){
        this.rankAdmin = data;
        System.out.println("*************");
        System.out.println("您要添加的超级热搜名称为:");
        Scanner scanAddHotName = new Scanner(System.in);
        // 判断是否有钞能力热搜
        int isHavetop5 = isHaveTopAdmin();
        String hotNews = (this.rankAdmin.size()+1-isHavetop5) + "-" +scanAddHotName.nextLine() + "-" + hotNum + "-Super";
        this.rankAdmin.add(hotNews);
        System.out.println("添加成功");
        System.out.println("*************");
        System.out.println();
    }

    // 判断当前是否有Top热搜  决定后续热搜的添加编号
    // 因为使用User类的isHaveTop有一些问题  所以又重新写了一个
    public Integer isHaveTopAdmin(){
        if (this.rankAdmin.size()==0){
            return 0;
        }
        String[] rankTopAdmin = this.rankAdmin.get(0).toString().split("-");
        if(rankTopAdmin[0].toString().equals("Top")){
            return 1;
        }
        return 0;
    }
}
