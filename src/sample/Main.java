package sample;

import filter.TextFilter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filter4j 演示程序 已经启动!");
        while (true) {
            String str = scanner.nextLine();
            System.out.println(TextFilter.isIllegal(str) ? "异常" : "正常");
        }
    }
}
