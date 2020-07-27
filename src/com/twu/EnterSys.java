package com.twu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class EnterSys {

    Map<String,Integer> userPoll = new HashMap<>();
    Map<String,Integer> userBuyHot = new HashMap<>();
    int topHotMoney;
    int isSuperHot;

    public void enterSys(ArrayList data){
        System.out.println("1->管理员  2->普通用户  0->退出");
        Scanner scannerFirst = new Scanner(System.in);

        // 判断输入命令是否规范正确
        // 在User类中进行了封装 因为此类中用到的地方少,所以此处没有封装
        String scanString = scannerFirst.next();
        while(true){
            if (scanString.matches("[0-9]")){
                break;
            }else{
                System.out.println("请输入正确的命令");
                scannerFirst = new Scanner(System.in);
                scanString = scannerFirst.next();
            }
        }
        int id = Integer.parseInt(scanString);
//        Integer id = scannerFirst.nextInt();
//        while (true){
//            if (id == 1){
//                Admin admin = new Admin();
//            }else if(id == 2){
//                User user = new User();
//                if(user.achive() == 0){
//                    System.out.println("请输入您的身份 : ");
//                    System.out.println("1->管理员  2->普通用户  0->退出");
//                    enterSys();
//
//                }
//            }else if(id == 0){
//                return;
//            }else{
//                System.out.println("----------   ");
//                System.out.println("请重新输入正确数字!!!");
//                enterSys();
//            }
//        }
        // 主要登录实现  作为身份登录接口
        switch (id) {
            case 1 :
                // 将最新的data:热搜排行榜 && 用户票数 && 购买热搜最低消费 传入
                Admin admin = new Admin(data,userPoll,topHotMoney);
                // 如果用户选择的是退出当前身份  那么更新数据 重新进入身份选择
                if(admin.achiveAdmin() == 0){
                    userPoll = admin.backPollsAdmin();
                    topHotMoney = admin.backMoneyAdmin();
                    System.out.println("请输入您的身份 : ");
                    ArrayList infoAdmin = admin.backRankAdmin();
                    enterSys(infoAdmin);
                }
                break;
            case 2 :
                User user = new User(data,userPoll,topHotMoney);
                if(user.achive() == 0){
                    userPoll = user.backPolls();
                    topHotMoney = user.backMoney();
                    System.out.println("请输入您的身份 : ");
                    ArrayList infoUser = user.backRank();
                    enterSys(infoUser);
                }
                break;
            case 0 :
                return;
            default:
                System.out.println("----------");
                System.out.println("请重新输入正确命令!!!");
                enterSys(data);
        }
    }
}
