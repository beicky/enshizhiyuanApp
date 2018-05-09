package com.achievement.eszy.myachievement;

/**
 * 尹耀强
 * 787760669@qq.com
 * 2018.5.9
 */

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Resolve {
    public  String[] jiexidata(String str1){
        Document doc = Jsoup.parse(str1);
        Element tb1 = doc.getElementById("Table1");
        Elements tb1_tr2 =tb1.select("font");
        String aa= tb1_tr2.text();
        String[] a= aa.split("姓名");
        String b[] = a[1].split("]");
        String c[] = b[0].split("：");
        //System.out.println(c[1]);

        Element cjxx = doc.getElementById("cjxx");
        Elements cjxx_tr = cjxx.select("tr");

        List<List> list = new ArrayList<List>();
        int a1=0;
        for(Element cjxx_tr_tr:cjxx_tr){
            if(a1!=0){
                Elements cjxx_tr_tr_td = cjxx_tr_tr.select("td");
                List lisr1 = new ArrayList<String>();
                for(Element cjxx_tr_tr_td_td:cjxx_tr_tr_td){
                    lisr1.add(cjxx_tr_tr_td_td.text());
                }
                list.add(lisr1);}a1++;
        }
        System.out.println(list.toString());
        Elements tbody = doc.select("tbody");
        Element tbody_3 = tbody.get(2);
        Elements tbody_3_td = tbody_3.select("span");
        List list2 = new ArrayList<String>();
        for(Element fen : tbody_3_td){
            list2.add(fen.text());
        }
        String all1 = "获得总学分 "+ list2.get(0)+" ( 必修学分 "+list2.get(1)+" 专业选修学分 "+list2.get(2)+" 公共选修学分 "+list2.get(3)+"  )平均学分绩点 "+list2.get(4)+ " 所修总学时 "+list2.get(5)+"  课程总门数 " +list2.get(6)+"   不及格门数 "+list2.get(7);
        String [] re= new String[4];
        System.out.println("-----------------------------------------------------------"+c[1]);
        re[0]=c[1];
        re[1]=list.toString();
        re[2]= all1;
        return re;
    }
}
