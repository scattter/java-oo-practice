package com.twu;

import java.util.*;

class User {

    // 参数定义
    // 热搜榜 && 票数 && 用户名 && 添加热搜最低消费
    ArrayList rank = new ArrayList();
    Map<String,Integer> currentPolls = new HashMap<>();
    String name;
    int hotNum = 0;
    int addHotMoney;

    public User() {
    }

    // 传参 得到最新数据
    // 热搜榜单 && 每名用户的票数
    // 传入实参
    // 得到最新的热搜榜 && 当前用户拥有的票数 && 购买热搜的最低消费金额
    public User(ArrayList rank, Map<String,Integer> polls,int money){
        this.rank = rank;
        this.currentPolls = polls;
        this.addHotMoney = money;
        System.out.println("请输入您的名字");
        Scanner scanName = new Scanner(System.in);
        name = scanName.next();
        System.out.println();
        // 如果是新用户 设置新用户的票数为10
        if (!currentPolls.containsKey(name)){
            currentPolls.put(name,10);
        }
    }

    // 返回更新后的热搜排名  保证当前系统不关闭  数据始终同步
    public ArrayList backRank(){
        return this.rank;
    }
    // 返回更新后每一名用户的票数  保证系统不关闭 数据始终同步保存
    public Map<String,Integer> backPolls(){
        return this.currentPolls;
    }
    // 返回添加热搜的最低消费
    public int backMoney(){
        return this.addHotMoney;
    }

    // 辅助函数 处理输入命令不规范
    // regex 是正则表达式的形参
    public String isStandard(String regex){
        Scanner scanIsStand = new Scanner(System.in);
        String scanString = scanIsStand.next();
        System.out.println();
        while(true){
            if (scanString.matches(regex)){
                break;
            }else{
                System.out.println("请输入正确的命令");
                scanIsStand = new Scanner(System.in);
                scanString = scanIsStand.next();
            }
        }
        return scanString;
    }

    // 函数调用  实现具体功能
    public Integer achieve(){
        System.out.println(this.name+" 请输入您想要实现功能的数字");
        System.out.println("1 -> 查看热搜排行榜");
        System.out.println("2 -> 给热搜事件投票");
        System.out.println("3 -> 钞能力购买热搜");
        System.out.println("4 -> 过路人添加热搜");
        System.out.println("0 -> 退出当前身份");


        int require = Integer.parseInt(isStandard("[0-5]"));

        while (true){
            if(require == 1){
                watchHot(this.rank);
                // 执行完后重新进入功能选择
                achieve();
                break;
            }else if (require == 2){
                voteHot();
                achieve();
                break;
            }else if (require == 3){
                buyHot();
                achieve();
                break;
            }else if (require == 4){
                addHot(this.rank);
                achieve();
                break;
            }else if (require == 0){
                break;
            }else {
                System.out.println("请输入您的需求");
                achieve();
            }
        }
        // 默认返回0 退出当前身份
        return 0;
    }

    // 查看热搜
    public void watchHot(ArrayList rank){
        System.out.println("*************");
        System.out.println("当前热搜为:");
        rank.forEach(n -> System.out.println(n.toString()));
        System.out.println("*************");
        System.out.println();
    }

    // 辅助函数 判断当前是否有Top热搜  决定后续热搜的添加编号
    public Integer isHaveTop(){
        if (this.rank.size()==0){
            return 0;
        }
        String[] rankTop = this.rank.get(0).toString().split("-");
        if(rankTop[0].toString().equals("Top")){
            return 1;
        }
        return 0;
    }

    // 给当前热搜投票
    public void voteHot(){
        System.out.println("*************");
        // 当前无热搜
        if(rank.size() == 0){
            System.out.println("投票失败,当前无热搜,请添加热搜后投票!");
            System.out.println("*************");
            System.out.println();
            return;
        }
        // 判断当前是否有Top热搜
        int isHaveTop1;
        isHaveTop1 = isHaveTop();
        System.out.println(this.name+"  您目前有"+currentPolls.get(this.name) +"票");
        System.out.println("您可投票编号为"+1+" ~ "+(this.rank.size()-isHaveTop1));
        System.out.println("请输入您要投票的热度编号:");

        // 调用函数判断输入是否规范  投票的热搜编号
        int voteHotId = Integer.parseInt(isStandard("[1-9]+"));
        // 非投票范围内编号
        if(voteHotId > this.rank.size() || voteHotId < 1){
            System.out.println("投票失败,您的输入有误,无此热搜!");
            System.out.println("*************");
            System.out.println();
            return;
        }
        System.out.println("请输入您要投票的票数:");

        // 调用函数判断输入是否有误  投票的票数
        int voteHotNum = Integer.parseInt(isStandard("[0-9]+"));
        // 非持有票数范围内投票数
        if(voteHotNum > currentPolls.get(this.name) || voteHotNum < 0){
            System.out.println("投票失败,您的票数为"+currentPolls.get(this.name));
            System.out.println("*************");
            System.out.println();
            return;
        }

        // 投票成功,更改现有票数
        currentPolls.put(this.name,currentPolls.get(this.name) - voteHotNum);
        System.out.println("投票成功!您剩余票数为:"+currentPolls.get(this.name));
        System.out.println("*************");
        System.out.println();

        // 更新热搜
        // isSuper 判断当前热搜是否是超级热搜  以决定热度和票数的关系
        // superTagUpdate 如果是超级热搜  更新后需要加上Super后缀
        int isSuper = 1;
        String superTagUpdate = "";
        String[] target = this.rank.get(voteHotId-1+isHaveTop1).toString().split("-");
        if(target.length==4){
            isSuper = 2;
            superTagUpdate = "-Super";
        }else{
            isSuper = 1;
            superTagUpdate = "";
        }
        String targetInfo = target[0] + "-" + target[1] + "-" + (voteHotNum * isSuper + Integer.parseInt(target[2])+superTagUpdate);
        // 删去原来元素  添加新的元素
        this.rank.remove(voteHotId-1+isHaveTop1);
        this.rank.add(voteHotId-1+isHaveTop1,targetInfo);

        // 对投票后的热搜进行排名
        this.rank = sortRank(this.rank);
        System.out.println("-----------");
    }

    // 投票后热搜的排名
    public ArrayList sortRank(ArrayList data){
        // 以热度为key 热搜内容为value  为了防止热度重复丢失数据  加上冗余 拿出来key进行排名
        // 然后使用新的map将排名后的value,key新数组再传给rank
        // 先判断是否有Top热搜  如果有,将其放入afterVoteRankSortInfo
        this.rank = data;
        Map<String,String> afterVoteRankSortInfo = new HashMap<>();
        // 将afterVoteRankSortInfo内的信息放入afterVoteRank 并返回给rank
        ArrayList afterVoteRank = new ArrayList();
        int isHaveTopSort = isHaveTop();
        if(isHaveTopSort == 1){
            afterVoteRank.add(this.rank.get(0));
        }

        // 排序规则 将 热度 && 总长度-热搜编号 使用"-"存入key
        // 如 1-a-0  2-b-0  3-c-0
        // 如果给2投5票  ->  2-b-5
        // 存入情况为 0-2  5-1  0-0
        // 热搜内容作为value放入afterVoteRankSortInfo
        for (int i = isHaveTopSort; i < this.rank.size(); i++){
            String[] str = this.rank.get(i).toString().split("-");
            String key = str[2]+"-"+(this.rank.size()-Integer.parseInt(str[0]));
            afterVoteRankSortInfo.put(key,this.rank.get(i).toString().split("-")[1]);
        }

        // 根据  key -> 编号加热度  进行拆分为根据热度(after[i].toString().split("-")[0])排序
        Object[] after = afterVoteRankSortInfo.keySet().toArray();
        for (int i = 0; i < after.length; i++){
            for (int j = i+1; j < after.length; j++){
                if (Integer.parseInt(after[i].toString().split("-")[0]) < Integer.parseInt(after[j].toString().split("-")[0])){
                    Object obj = after[i];
                    after[i] = after[j];
                    after[j] = obj;
                }
            }
        }

        // afterVoteRank依次添加内容 : 利用排好序的key值 从after中依次遍历 找到afterVoteRankSortInfo中对应的value
        for (int i = 0; i < after.length; i++){
            // 如果是超级热搜 排序的时候加上Super后缀  i+isHaveTop 是考虑到有钞能力热搜的情况 i会小1
            String superTagSort = "";
            String info2 = afterVoteRankSortInfo.get(after[i].toString());
            // -1 是因为编号从1开始 但是数组遍历从0开始 + isHaveTopSort是应对有钞能力热搜情景
            if(this.rank.get(this.rank.size()-1+isHaveTopSort-Integer.parseInt(after[i].toString().split("-")[1])).toString().split("-").length == 4){
                superTagSort = "-Super";
            }else{
                superTagSort = "";
            }
            afterVoteRank.add((i+1)+ "-" + info2 + "-" + after[i].toString().split("-")[0] + superTagSort);
        }
        return afterVoteRank;
    }

    // 使用钞能力购买热搜
    public void buyHot(){
        System.out.println("*************");
        System.out.println("尊敬的 " + this.name + " 您可以使用钞能力添加头条热搜");
        System.out.println("当前添加TOP-1热搜至少需 " + addHotMoney + " 元,继续请按-1,返回请按-2");
        int isContinueAddHot = Integer.parseInt(isStandard("[1-3]"));
        if (isContinueAddHot == 2){
            System.out.println("欢迎您下次充值!");
            System.out.println("*************");
            System.out.println();
            return;
        }
        System.out.println("您想要添加的热搜为:");
        Scanner scanBuyHotName = new Scanner(System.in);
        String buyHotName = scanBuyHotName.next();
        System.out.println("您的购买金额 : " + "(需大于" + addHotMoney+ ")");
        int scanBuyHotMoney = Integer.parseInt(isStandard("[0-9]+"));
        // 判断购买金额是否满足条件
        if (scanBuyHotMoney > addHotMoney){
            System.out.println("您已购买成功");
            addHotMoney = scanBuyHotMoney;
            int isHaveTop3 = isHaveTop();
            // 如果当前已有Top热搜 撤销当前的 增加新的
            if (isHaveTop3==1) {
                rank.remove(0);
            }
            rank.add(0,"Top-" + buyHotName + "-0");
            System.out.println("*************");
            System.out.println();
        }else{
            System.out.println("购买失败,您的购买金额小于最低消费"+addHotMoney);
            System.out.println("*************");
            System.out.println();
            return;
        }
    }

    // 过路人添加热搜
    public void addHot(ArrayList data){
        this.rank = data;
        System.out.println("*************");
        System.out.println("您要添加的热搜名称为:");
        Scanner scanAddHotName = new Scanner(System.in);

        int isHavetop2 = isHaveTop();
        String hotNews = (this.rank.size()+1-isHavetop2) + "-" +scanAddHotName.nextLine() + "-" +hotNum;
        this.rank.add(hotNews);
        System.out.println("添加成功");
        System.out.println("*************");
        System.out.println();
    }
}
