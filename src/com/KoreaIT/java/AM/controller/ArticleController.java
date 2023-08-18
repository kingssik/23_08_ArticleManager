package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller {
  private List<Article> articles;
  private Scanner sc;
  private String cmd;
  private String actionMethodName;

  public ArticleController(Scanner sc) {
    this.sc = sc;
    this.articles = new ArrayList<Article>();
  }

  @Override
  public void doAction(String cmd, String actionMethodName) {
    this.cmd = cmd;
    this.actionMethodName = actionMethodName;

    switch (actionMethodName) {
      case "list":
        showList();
        break;
      case "detail":
        showDetail();
        break;
      case "write":
        doWrite();
        break;
      case "modify":
        doModify();
        break;
      case "delete":
        doDelete();
        break;
    }
  }

  private void doWrite() {
    int id = articles.size() + 1;

    String regDate = Util.getNowDateStr();
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    Article article = new Article(id, regDate, title, body);
    articles.add(article);

    System.out.printf("%d번 글이 생성 되었습니다.\n", id);
  }

  private void showList() {
    if (articles.size() == 0) {
      System.out.println("게시글이 없습니다.");
      return;
    } else {
      String searchKeyword = cmd.substring("article list".length()).trim();

      List<Article> forPrintArticles = articles;
      if (searchKeyword.length() > 0) {
        forPrintArticles = new ArrayList<>();

        for (Article article : articles) {
          if (article.title.contains(searchKeyword)) {
            forPrintArticles.add(article);
          }
        }
        if (forPrintArticles.size() == 0) {
          System.out.println("검색결과가 없습니다");
          return;
        }
      }

      System.out.println("번호    |    제목    |    조회수");
      for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
        Article article = forPrintArticles.get(i);
        System.out.printf("%4d    |    %2s    |    %3d\n", article.id, article.title, article.viewCnt);
      }
    }
  }

  private void showDetail() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    foundArticle.increaseViewCnt();
    System.out.printf("번호 : %d\n", foundArticle.id);
    System.out.printf("날짜 : %s\n", foundArticle.regDate);
    System.out.printf("제목 : %s\n", foundArticle.title);
    System.out.printf("내용 : %s\n", foundArticle.body);
    System.out.printf("조회수 : %d\n", foundArticle.viewCnt);
  }

  private void doModify() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    Article foundArticle = getArticleById(id);

    if (foundArticle == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    foundArticle.title = title;
    foundArticle.body = body;

    System.out.printf("%d번 글이 수정 되었습니다.\n", id);
  }

  private void doDelete() {
    String[] cmdBits = cmd.split(" ");
    int id = Integer.parseInt(cmdBits[2]);

    int foundIdx = getArticleIndexById(id);

    if (foundIdx == -1) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    articles.remove(foundIdx);
    System.out.printf("%d번 글이 삭제 되었습니다.\n", id);
  }

  private int getArticleIndexById(int id) {
    int i = 0;

    for (Article article : articles) {
      if (article.id == id) {
        return i;
      }
      i++;
    }

    return -1;
  }

  private Article getArticleById(int id) {
    int index = getArticleIndexById(id);
    if (index != -1) {
      return articles.get(index);
    }
    return null;
  }

  public void makeTestData() {
    System.out.println("테스트데이터를 생성합니다");

    articles.add(new Article(1, Util.getNowDateStr(), "title1", "body1", 11));
    articles.add(new Article(2, Util.getNowDateStr(), "title2", "body2", 22));
    articles.add(new Article(3, Util.getNowDateStr(), "title3", "body3", 33));
  }
}
