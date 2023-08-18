package com.KoreaIT.java.AM;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.Controller;
import com.KoreaIT.java.AM.controller.MemberController;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  private List<Article> articles;
  private List<Member> members;

  App() {
    articles = new ArrayList<>();
    members = new ArrayList<>();
  }

  public void start() {
    System.out.println("== 프로그램 시작 ==");
    makeTestData();

    Scanner sc = new Scanner(System.in);
    MemberController memberController = new MemberController(members, sc);
    ArticleController articleController = new ArticleController(articles, sc);

    while (true) {
      System.out.printf("명령어 ) ");
      String cmd = sc.nextLine().trim();

      if (cmd.length() == 0) {
        System.out.println("명령어를 입력하세요");
        continue;
      }

      if (cmd.equals("system exit")) {
        break;
      }

      String[] cmdBits = cmd.split(" ");  // cmd = "article detail"
      String controllerName = cmdBits[0]; // "article"
      String actionMethodName = cmdBits[1]; // "detail"

      if (cmdBits.length == 1) {
        System.out.println("존재하지 않는 명령어입니다.");
        continue;
      }

      Controller controller = null;

      if (controllerName.equals("article")) {
        controller = articleController;
      } else if (controllerName.equals("member")) {
        controller = memberController;
      } else {
        System.out.println("존재하지 않는 명령어입니다.");
        continue;
      }

      controller.doAction(cmd, actionMethodName);
    }

    sc.close();
    System.out.println("== 프로그램 종료 ==");
  }

  private void makeTestData() {
    System.out.println("테스트데이터를 생성합니다");

    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }
}
