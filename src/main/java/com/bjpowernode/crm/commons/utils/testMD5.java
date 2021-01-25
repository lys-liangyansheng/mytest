package com.bjpowernode.crm.commons.utils;

public class testMD5 {
    public  static void main(String args[]){
        String newpassword=MD5Util.getMD5("ls");
        System.out.println(newpassword);
    }
}
